package com.tigasatutiga.service.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.mapper.tuitionez.student.ParentMapper;
import com.tigasatutiga.models.tuitionez.student.ParentModel;
import com.tigasatutiga.repository.tuitionez.student.ParentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentSOImpl extends BaseSOImpl<ParentEntity, ParentModel, Long> implements ParentSO {

    private final ParentRepository repository;
    private final ParentMapper mapper;

    public ParentSOImpl(ParentRepository repository, ParentMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;

    }

    public Page<ParentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ParentEntity> entityPage = repository.findAll(pageable);
        List<ParentModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    @Override
    public List<ParentModel> getAll() {
        return mapper.toModelList(repository.findAll());
    }
}
