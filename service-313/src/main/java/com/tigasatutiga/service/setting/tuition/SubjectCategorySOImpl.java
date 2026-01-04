package com.tigasatutiga.service.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectCategoryEntity;
import com.tigasatutiga.entities.tuitionez.config.reference.ReferenceCodeEntity;
import com.tigasatutiga.mapper.setting.tuition.SubjectCategoryMapper;
import com.tigasatutiga.models.config.reference.ReferenceCodeModel;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.repository.setting.tuition.SubjectCategoryRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectCategorySOImpl
        extends BaseSOImpl<SubjectCategoryEntity, SubjectCategoryModel, Long>
        implements SubjectCategorySO {

    private final SubjectCategoryRepository repository;
    private final SubjectCategoryMapper mapper;

    public SubjectCategorySOImpl(
            SubjectCategoryRepository repository,
            SubjectCategoryMapper mapper
    ) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }


    public Page<SubjectCategoryModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SubjectCategoryEntity> entityPage = repository.findAll(pageable);
        List<SubjectCategoryModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    public List<SubjectCategoryModel> getList() {
        return mapper.toModelList(repository.findAll());
    }
}
