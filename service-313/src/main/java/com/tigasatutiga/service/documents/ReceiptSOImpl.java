package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.ReceiptEntity;
import com.tigasatutiga.mapper.documents.ReceiptMapper;
import com.tigasatutiga.models.documents.ReceiptModel;
import com.tigasatutiga.repository.documents.ReceiptRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReceiptSOImpl extends BaseSOImpl<ReceiptEntity, ReceiptModel, Long> implements ReceiptSO {
    private final ReceiptRepository repository;
    private final ReceiptMapper mapper;

    public ReceiptSOImpl(ReceiptRepository repository, ReceiptMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<ReceiptModel> getAllReceipts(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ReceiptEntity> entityPage = repository.findAll(pageable);
        List<ReceiptModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }
}
