package com.tigasatutiga.service.documents;

import com.tigasatutiga.exception.BusinessException;
import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import com.tigasatutiga.models.setting.SystemSettingModel;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.models.student.StudentModel;
import com.tigasatutiga.service.reference.ReferenceCodeSO;
import com.tigasatutiga.service.setting.SystemSettingSO;
import com.tigasatutiga.service.student.ParentSO;
import com.tigasatutiga.service.student.StudentSO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@Slf4j
public class InvoiceBatchSOImpl implements InvoiceBatchSO {

    @Autowired
    private StudentSO studentSO;

    @Autowired
    private SystemSettingSO systemSettingSO;

    @Autowired
    private ParentSO parentSO;

    @Autowired
    private ReferenceCodeSO referenceCodeSO;

    @Autowired
    private InvoiceSO invoiceSO;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${microservices.filer.url}")
    private String filerURL;

    @Value("${microservices.filer.batch-api}")
    private String filerBatchApi;


    public ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>> batchGenerateInvoice() throws Exception {
        // Fetch all invoices with their invoice items eagerly to avoid lazy-loading leaks
        Page<InvoiceTableModel> invoicePage = invoiceSO.getAllInvoices(0, Integer.MAX_VALUE, "id", "ASC", LocalDate.now());
        List<InvoiceTableModel> invoiceList = invoicePage.getContent();

        List<InvoiceModel> createdInvoices = new ArrayList<>();
        List<InvoiceTemplateModel> createdTemplates = new ArrayList<>();

        // Get system setting once outside the loop
        SystemSettingModel systemSetting = systemSettingSO.getByKey("subject_fee_kuantan");
        if (systemSetting == null) {
            throw new BusinessException("System setting for subject fee not found.");
        }
        int subjectFee = Integer.parseInt(systemSetting.getValue());

        // Generate invoices for parents without invoices
        for (InvoiceTableModel invoiceTableModel : invoiceList) {
            if (invoiceTableModel.getInvoices().isEmpty()) {
                List<StudentModel> studentList = studentSO.getListByParentId(invoiceTableModel.getId());

                List<InvoiceItemModel> invoiceItemModels = new ArrayList<>();
                int totalAmount = 0;

                for (StudentModel student : studentList) {
                    for (var subject : student.getSubjects()) {
                        InvoiceItemModel item = new InvoiceItemModel();
                        item.setStudent(student);
                        item.setSubject(subject);
                        item.setAmount(BigDecimal.valueOf(subjectFee));
                        item.setDescription("");

                        invoiceItemModels.add(item);
                        totalAmount += subjectFee;
                    }
                }

                ParentModel parent = parentSO.findById(invoiceTableModel.getId())
                        .orElseThrow(() -> new BusinessException("Parent not found with ID: " + invoiceTableModel.getId()));

                InvoiceModel invoiceModel = new InvoiceModel();
                invoiceModel.setInvoiceType("invoice");
                invoiceModel.setParent(parent);
                invoiceModel.setIssueDate(LocalDate.now());
                invoiceModel.setBillingMonth(LocalDate.now());
                invoiceModel.setTotalAmount(BigDecimal.valueOf(totalAmount));
                invoiceModel.setIsCancelled(false);
                invoiceModel.setInvoiceItems(invoiceItemModels);

                // Save invoice and detach entity to avoid long-lived connection
                InvoiceModel savedInvoice = invoiceSO.createInvoiceWithItems(invoiceModel);
                createdInvoices.add(savedInvoice);
            }
        }

        // Fetch labels only once
        List<ReferenceCodeModel> companyInfo = referenceCodeSO.getListByGroup("COMPANY_INFO");
        List<ReferenceCodeModel> invoiceLabels = referenceCodeSO.getListByGroup("INVOICE_LABELS");

        // Build templates in memory
        for (InvoiceModel invoice : createdInvoices) {
            createdTemplates.add(buildInvoiceTemplate(invoice, companyInfo, invoiceLabels));
        }

        // Call external generator
        BatchInvoiceResponseModel response = this.callExternalGenerator(createdTemplates);

        return ResponseEntity.ok(new ApiResponseModel<>(true, "Batch processed", response));
    }

    public ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>> batchGenerateInvoices() throws Exception {
        // Fetch all invoice table records
        Page<InvoiceTableModel> invoicePage = invoiceSO.getAllInvoices(0, Integer.MAX_VALUE, "id", "ASC", LocalDate.now());
        List<InvoiceTableModel> invoiceList = invoicePage.getContent();

        List<InvoiceTemplateModel> createdTemplates = new ArrayList<>();

        for (InvoiceTableModel invoiceTableModel : invoiceList) {
            try {
                // Reuse main function
                InvoiceTemplateModel template = invoiceSO.generateInvoiceForParent(invoiceTableModel.getId());
                createdTemplates.add(template);
            } catch (BusinessException e) {
                // Skip parents who already have invoices
                log.info("Skipping parent ID {}: {}", invoiceTableModel.getId(), e.getMessage());
            }
        }

        // Call external generator for batch
        BatchInvoiceResponseModel response = this.callExternalGenerator(createdTemplates);

        return ResponseEntity.ok(new ApiResponseModel<>(true, "Batch processed", response));
    }

    public InvoiceTemplateModel buildInvoiceTemplate(
            InvoiceModel invoice,
            List<ReferenceCodeModel> companyLabel,
            List<ReferenceCodeModel> invoiceLabel
    ) {

        Function<String, String> company = (key) ->
                companyLabel.stream()
                        .filter(x -> key.equals(x.getCode()))
                        .map(ReferenceCodeModel::getName)
                        .findFirst()
                        .orElse("");

        Function<String, String> invoiceLbl = (key) ->
                invoiceLabel.stream()
                        .filter(x -> key.equals(x.getCode()))
                        .map(ReferenceCodeModel::getName)
                        .findFirst()
                        .orElse("");

        InvoiceTemplateModel template = new InvoiceTemplateModel();

        // Company labels
        template.setCOMPANY_NAME(company.apply("COMPANY_NAME"));
        template.setCOMPANY_TAGLINE(company.apply("COMPANY_TAGLINE"));
        template.setCOMPANY_ADDRESS_1(company.apply("COMPANY_ADDRESS_1"));
        template.setCOMPANY_ADDRESS_2(company.apply("COMPANY_ADDRESS_2"));
        template.setCOMPANY_PHONE_NO(company.apply("COMPANY_PHONE_NO"));
        template.setPAYMENT_REMARKS(company.apply("PAYMENT_REMARKS"));
        template.setPAYMENT_ACCOUNT_INFO(company.apply("PAYMENT_ACCOUNT_INFO"));

        // Invoice labels
        template.setPHONE_LABEL(invoiceLbl.apply("PHONE_LABEL"));
        template.setINVOICE_LABEL(invoiceLbl.apply("INVOICE_LABEL"));
        template.setINVOICE_NO_LABEL(invoiceLbl.apply("INVOICE_NO_LABEL"));
        template.setDATE_LABEL(invoiceLbl.apply("DATE_LABEL"));
        template.setITEM_LABEL(invoiceLbl.apply("ITEM_LABEL"));
        template.setSUBJECT_LABEL(invoiceLbl.apply("SUBJECT_LABEL"));
        template.setPRICE_LABEL(invoiceLbl.apply("PRICE_LABEL"));
        template.setAMOUNT_LABEL(invoiceLbl.apply("AMOUNT_LABEL"));
        template.setTOTAL_LABEL(invoiceLbl.apply("TOTAL_LABEL"));
        template.setEND_LABEL(invoiceLbl.apply("END_LABEL"));
        template.setTO_LABEL(invoiceLbl.apply("TO_LABEL"));

        // Invoice info
        template.setINVOICE_NO(invoice.getInvoiceNo());
        template.setINVOICE_DATE(invoice.getIssueDate().format(DateTimeFormatter.ofPattern("d-M-y")));
        template.setPARENT_NAME(invoice.getParent().getName());
        template.setPARENT_PHONE_NO(invoice.getParent().getPhoneNo());
        template.setInvoiceItems(invoice.getInvoiceItems());
        template.setSUB_TOTAL(invoice.getTotalAmount());

        return template;
    }

    public BatchInvoiceResponseModel callExternalGenerator(List<InvoiceTemplateModel> template) {

        String url = filerURL + filerBatchApi;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<InvoiceTemplateModel>> entity = new HttpEntity<>(template, headers);

        ResponseEntity<BatchInvoiceResponseModel> response =
                restTemplate.postForEntity(url, entity, BatchInvoiceResponseModel.class);

        return response.getBody();
    }
}
