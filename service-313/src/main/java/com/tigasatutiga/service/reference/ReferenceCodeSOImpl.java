package com.tigasatutiga.service.reference;

import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.mapper.reference.ReferenceCodeMapper;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.repository.reference.ReferenceCodeRepository;
import com.tigasatutiga.service.BaseSOImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ReferenceCodeSOImpl extends BaseSOImpl<ReferenceCodeEntity,ReferenceCodeModel, Long> implements ReferenceCodeSO {
    private final ReferenceCodeRepository refCodeRepository;
    private final ReferenceCodeMapper refCodeMapper;

    public ReferenceCodeSOImpl(ReferenceCodeRepository repository, ReferenceCodeMapper mapper) {
        super(repository, mapper);
        this.refCodeRepository = repository;
        this.refCodeMapper = mapper;
    }

    public Page<ReferenceCodeModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<ReferenceCodeEntity> entityPage = refCodeRepository.findAll(pageable);
        List<ReferenceCodeModel> modelList = refCodeMapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    public List<ReferenceCodeModel> getListByGroup(String groupCode) {
        return refCodeMapper.toModelList(refCodeRepository.findByGroupCode(groupCode));
    }
}
