package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.exception.BusinessException;
import com.tigasatutiga.mapper.documents.InvoiceMapper;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.documents.*;
import com.tigasatutiga.models.setting.SystemSettingModel;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.models.student.StudentModel;
import com.tigasatutiga.repository.documents.InvoiceRepository;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import com.tigasatutiga.service.reference.ReferenceCodeSO;
import com.tigasatutiga.service.setting.SystemSettingSO;
import com.tigasatutiga.service.student.ParentSO;
import com.tigasatutiga.service.student.StudentSO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class InvoiceSOImpl extends BaseSOImpl<InvoiceEntity, InvoiceModel, Long> implements InvoiceSO {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private RunningNoSO runningNoSO;

    @Autowired
    private StudentSO studentSO;

    @Autowired
    private ParentSO parentSO;

    @Autowired
    private ReferenceCodeSO referenceCodeSO;

    @Autowired
    private SystemSettingSO systemSettingSO;


    public InvoiceSOImpl(InvoiceRepository repository, InvoiceMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<InvoiceTableModel> getAllInvoices(int pageNo, int pageSize, String sortField, String sortDir, LocalDate billingMonth) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ParentEntity> entityPage = parentRepository.findAll(pageable);

        // 3️⃣ Fetch invoices for all parent IDs in this page
        List<Long> parentIds = entityPage.getContent().stream()
                .map(ParentEntity::getId)
                .toList();

        List<InvoiceEntity> invoices = repository.findByParentIdInAndBillingMonth(parentIds, billingMonth);

        List<InvoiceTableModel> modelList = mapper.toTableModelList(invoices, entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    @Transactional
    public InvoiceModel createInvoiceWithItems(InvoiceModel model) {

        // 1️⃣ Check if invoice already exists
        Optional<InvoiceEntity> optInvoiceEntity = repository.findByParentIdAndBillingMonth(
                model.getParent().getId(), model.getBillingMonth()
        );

        if (optInvoiceEntity.isPresent()) {
            // Throw a BusinessException to trigger rollback and global handler
            throw new BusinessException("Invoice for the specified parent and billing month already exists.");
        }

        // 2️⃣ Save the invoice
        InvoiceEntity invoiceEntity = mapper.toEntity(model);

        RunningNoModel runningNo = runningNoSO.getNextRunningNo(model.getInvoiceType());
        invoiceEntity.setInvoiceNo(runningNo.getPrefix() + runningNo.getRunningNo() + runningNo.getSuffix());

        // 3️⃣ Save invoice items (child entities)
        for (InvoiceItemEntity item : invoiceEntity.getInvoiceItems()) {
            item.setInvoice(invoiceEntity);
        }

        InvoiceEntity savedInvoice = repository.save(invoiceEntity);

        // 4️⃣ Convert back to model for response
        InvoiceModel savedModel = mapper.toModel(savedInvoice);
        savedModel.setInvoiceItems(model.getInvoiceItems());

        // ✅ Return success response
        return savedModel;
    }

    @Transactional
    @Override
    public InvoiceTemplateModel generateInvoiceForParent(Long parentId) throws Exception {

        BigDecimal annualFee =
                new BigDecimal(systemSettingSO.getByKey("annual_fee").getValue());
        String annualDesc =
                systemSettingSO.getByKey("annual_fee_description").getValue();

        InvoiceModel invoiceModel =
                buildInvoiceModel(parentId, annualFee, annualDesc);

        InvoiceModel savedInvoice =
                createInvoiceWithItems(invoiceModel);

        List<ReferenceCodeModel> companyInfo =
                referenceCodeSO.getListByGroup("COMPANY_INFO");
        List<ReferenceCodeModel> invoiceLabels =
                referenceCodeSO.getListByGroup("INVOICE_LABELS");

        return buildInvoiceTemplate(savedInvoice, companyInfo, invoiceLabels);
    }


    public InvoiceTemplateModel viewInvoice(Long parentId, LocalDate billingMonth) {
        // Fetch labels
        List<ReferenceCodeModel> companyInfo =
                referenceCodeSO.getListByGroup("COMPANY_INFO");
        List<ReferenceCodeModel> invoiceLabels =
                referenceCodeSO.getListByGroup("INVOICE_LABELS");

        Optional<InvoiceEntity> optInvoice = repository.findByParentIdAndBillingMonth(parentId, billingMonth);

        if (optInvoice.isEmpty()) {
            throw new BusinessException("Invoice not found for the specified parent and billing month.");
        }

        return buildInvoiceTemplate(mapper.toModel(optInvoice.get()), companyInfo, invoiceLabels);
    }

    public InvoiceModel buildInvoiceModel(
            Long parentId,
            BigDecimal annualFee,
            String annualFeeDesc
    ) throws Exception {

        List<StudentModel> studentList = studentSO.getListByParentId(parentId);

        List<InvoiceItemModel> invoiceItemModels = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (StudentModel student : studentList) {

            for (var subject : student.getSubjects()) {
                BigDecimal price = subject.getCategory().getPrice();

                InvoiceItemModel item = new InvoiceItemModel();
                item.setStudent(student);
                item.setSubject(subject);
                item.setAmount(price);
                item.setDescription("");

                invoiceItemModels.add(item);
                totalAmount = totalAmount.add(price);
            }

            if (student.getRegistrationDate() != null &&
                    student.getRegistrationDate().withDayOfMonth(1)
                            .equals(LocalDate.now().withDayOfMonth(1))) {

                InvoiceItemModel annualItem = new InvoiceItemModel();
                annualItem.setStudent(student);
                annualItem.setAmount(annualFee);
                annualItem.setDescription(
                        student.getName() + " - " + annualFeeDesc + " (" +
                                student.getRegistrationDate().format(
                                        DateTimeFormatter.ofPattern("dd-MM-yyyy")) + ")"
                );

                invoiceItemModels.add(annualItem);
                totalAmount = totalAmount.add(annualFee);
            }
        }

        Optional<ParentModel> Optparent = parentSO.findById(parentId);

        if (Optparent.isEmpty()) {
            throw new BusinessException("Parent not found with ID: " + parentId);
        }

        InvoiceModel invoiceModel = new InvoiceModel();
        invoiceModel.setInvoiceType("invoice");
        invoiceModel.setParent(Optparent.get());
        invoiceModel.setIssueDate(LocalDate.now());
        invoiceModel.setBillingMonth(LocalDate.now().withDayOfMonth(1));
        invoiceModel.setTotalAmount(totalAmount);
        invoiceModel.setIsCancelled(false);
        invoiceModel.setInvoiceItems(invoiceItemModels);

        return invoiceModel;
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
        template.setINVOICE_DATE(invoice.getIssueDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        template.setPARENT_NAME(invoice.getParent().getName());
        template.setPARENT_PHONE_NO(invoice.getParent().getPhoneNo());
        template.setInvoiceItems(invoice.getInvoiceItems());
        template.setSUB_TOTAL(invoice.getTotalAmount());

        return template;
    }
}