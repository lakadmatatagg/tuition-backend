package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.InvoiceItemEntity;
import com.tigasatutiga.mapper.documents.InvoiceItemMapper;
import com.tigasatutiga.models.documents.InvoiceItemModel;
import com.tigasatutiga.repository.documents.InvoiceItemRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemSOImpl extends BaseSOImpl<InvoiceItemEntity, InvoiceItemModel, Long> implements InvoiceItemSO {
    private final InvoiceItemRepository repository;
    private final InvoiceItemMapper mapper;

    public InvoiceItemSOImpl(InvoiceItemRepository repository, InvoiceItemMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<InvoiceItemModel> getAllInvoiceItems(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<InvoiceItemEntity> entityPage = repository.findAll(pageable);
        List<InvoiceItemModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }
}
