package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.mapper.documents.InvoiceItemMapper;
import com.tigasatutiga.mapper.documents.InvoiceMapper;
import com.tigasatutiga.mapper.student.ParentMapper;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.models.documents.InvoiceTableModel;
import com.tigasatutiga.models.documents.RunningNoModel;
import com.tigasatutiga.models.student.ParentModel;
import com.tigasatutiga.repository.documents.InvoiceItemRepository;
import com.tigasatutiga.repository.documents.InvoiceRepository;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
    public int createInvoiceWithItems(InvoiceModel model) {
        try {
            // 1️⃣ Save the invoice
            InvoiceEntity invoiceEntity = mapper.toEntity(model);

            // Set invoice No
            RunningNoModel runningNo = runningNoSO.getNextRunningNo(model.getInvoiceType());
            invoiceEntity.setInvoiceNo(runningNo.getPrefix() + runningNo.getRunningNo() + runningNo.getSuffix());

            InvoiceEntity savedInvoice = repository.save(invoiceEntity);

            // 2️⃣ Save all invoice items
            if (model.getInvoiceItems() != null && !model.getInvoiceItems().isEmpty()) {
                for (InvoiceItemModel itemModel : model.getInvoiceItems()) {
                    InvoiceItemEntity itemEntity = invoiceItemMapper.toEntity(itemModel);
                    itemEntity.setInvoice(savedInvoice); // link child → parent
                    invoiceItemRepository.save(itemEntity);
                }
            }

            return 1; // or return savedInvoice.getId() if you want to return the ID of the created invoice
        } catch (Exception e) {
            // ❌ force rollback by throwing a RuntimeException
            throw new RuntimeException("Failed to save invoice with items", e);
        }
    }
}