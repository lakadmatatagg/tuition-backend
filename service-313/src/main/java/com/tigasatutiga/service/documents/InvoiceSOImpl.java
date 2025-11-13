package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.exception.BusinessException;
import com.tigasatutiga.mapper.documents.InvoiceItemMapper;
import com.tigasatutiga.mapper.documents.InvoiceMapper;
import com.tigasatutiga.models.ApiResponseModel;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.documents.RunningNoModel;
import com.tigasatutiga.repository.documents.InvoiceItemRepository;
import com.tigasatutiga.repository.documents.InvoiceRepository;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceSOImpl extends BaseSOImpl<InvoiceEntity, InvoiceModel, Long> implements InvoiceSO {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    @Autowired
    private ParentRepository parentRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private InvoiceItemMapper invoiceItemMapper;

    @Autowired
    private RunningNoSO runningNoSO;


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
    public ResponseEntity<ApiResponseModel<InvoiceModel>> createInvoiceWithItems(InvoiceModel model) {

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


//        if (model.getInvoiceItems() != null && !model.getInvoiceItems().isEmpty()) {
//            for (InvoiceItemModel itemModel : model.getInvoiceItems()) {
//                InvoiceItemEntity itemEntity = invoiceItemMapper.toEntity(itemModel);
//                itemEntity.setInvoice(savedInvoice); // link child → parent
//                invoiceItemRepository.save(itemEntity);
//            }
//        }

        // 4️⃣ Convert back to model for response
        InvoiceModel savedModel = mapper.toModel(savedInvoice);
        savedModel.setInvoiceItems(model.getInvoiceItems());

        // ✅ Return success response
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseModel.success(savedModel, "Invoice created successfully"));
    }

}