package com.tigasatutiga.service.tuitionez.config.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceGroupEntity;
import com.tigasatutiga.mapper.tuitionez.config.reference.ReferenceGroupMapper;
import com.tigasatutiga.models.tuitionez.config.reference.ReferenceGroupModel;
import com.tigasatutiga.repository.tuitionez.config.reference.ReferenceGroupRepository;
import com.tigasatutiga.service.BaseSOImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReferenceGroupSOImpl
        extends BaseSOImpl<ReferenceGroupEntity, ReferenceGroupModel, Long>
        implements ReferenceGroupSO {

    private final ReferenceGroupRepository refGroupRepository;
    private final ReferenceGroupMapper refGroupMapper;

    public ReferenceGroupSOImpl(ReferenceGroupRepository repository, ReferenceGroupMapper mapper) {
        super(repository, mapper);
        this.refGroupRepository = repository;
        this.refGroupMapper = mapper;
    }

    public Page<ReferenceGroupModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ReferenceGroupEntity> entityPage = refGroupRepository.findAll(pageable);
        List<ReferenceGroupModel> modelList = refGroupMapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    public List<ReferenceGroupModel> getList() {
        return refGroupMapper.toModelList(refGroupRepository.findAll());
    }
}

