package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceEntity;
import com.tigasatutiga.mapper.documents.InvoiceMapper;
import com.tigasatutiga.models.documents.InvoiceModel;
import com.tigasatutiga.repository.documents.InvoiceRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceSOImpl extends BaseSOImpl<InvoiceEntity, InvoiceModel, Long> implements InvoiceSO {
    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    public InvoiceSOImpl(InvoiceRepository repository, InvoiceMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<InvoiceModel> getAllInvoices(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<InvoiceEntity> entityPage = repository.findAll(pageable);
        List<InvoiceModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }
}
