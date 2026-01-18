package com.tigasatutiga.service.documents;

import com.tigasatutiga.exception.BusinessException;
import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.BatchInvoiceResponseModel;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTemplateModel;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.service.reference.ReferenceCodeSO;
import com.tigasatutiga.service.setting.SystemSettingSO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@Slf4j
public class InvoiceBatchSOImpl implements InvoiceBatchSO {

    @Autowired
    private SystemSettingSO systemSettingSO;

    @Autowired
    private ParentRepository parentRepository;

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


    @Transactional
    public List<InvoiceTemplateModel> generateBatchInvoices(List<Long> parentIds) {

        BigDecimal annualFee =
                new BigDecimal(systemSettingSO.getByKey("annual_fee").getValue());
        String annualDesc =
                systemSettingSO.getByKey("annual_fee_description").getValue();

        List<ReferenceCodeModel> companyInfo =
                referenceCodeSO.getListByGroup("COMPANY_INFO");
        List<ReferenceCodeModel> invoiceLabels =
                referenceCodeSO.getListByGroup("INVOICE_LABELS");

        List<InvoiceTemplateModel> templates = new ArrayList<>();

        for (Long parentId : parentIds) {
            try {
                InvoiceModel invoiceModel =
                        invoiceSO.buildInvoiceModel(parentId, annualFee, annualDesc);

                InvoiceModel saved =
                        invoiceSO.createInvoiceWithItems(invoiceModel);

                templates.add(
                        buildInvoiceTemplate(saved, companyInfo, invoiceLabels)
                );

            } catch (BusinessException e) {
                log.info("BusinessException : Skipping parent {}: {}", parentId, e.getMessage());
            } catch (Exception e) {
                log.info("Exception : Skipping parent {}: {}", parentId, e.getMessage());
                throw new RuntimeException(e);
            }
        }

        return templates;
    }

    public ResponseEntity<ApiResponseModel<BatchInvoiceResponseModel>>
    batchGenerateInvoices() {

        List<Long> parentIds = parentRepository.findParentIdsWithoutInvoice(LocalDate.now().withDayOfMonth(1));

        List<InvoiceTemplateModel> templates =
                generateBatchInvoices(parentIds);

        BatchInvoiceResponseModel response =
                callExternalGenerator(templates);

        return ResponseEntity.ok(
                new ApiResponseModel<>(true, "Batch processed", response)
        );
    }
    public List<Long> getParentsWithoutInvoiceForCurrentMonth() {
        return parentRepository.findParentIdsWithoutInvoice(LocalDate.now().withDayOfMonth(1));
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
        template.setGRADE_LABEL(invoiceLbl.apply("GRADE_LABEL"));
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
