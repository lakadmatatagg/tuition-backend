package com.tigasatutiga.service.setting.tuition;

import com.tigasatutiga.entities.setting.tuition.SubjectEntity;
import com.tigasatutiga.mapper.setting.tuition.SubjectCategoryMapper;
import com.tigasatutiga.mapper.setting.tuition.SubjectMapper;
import com.tigasatutiga.models.setting.tuition.SubjectCategoryModel;
import com.tigasatutiga.models.setting.tuition.SubjectModel;
import com.tigasatutiga.repository.setting.tuition.SubjectRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectSOImpl
        extends BaseSOImpl<SubjectEntity, SubjectModel, Long>
        implements SubjectSO {

    private final SubjectRepository repository;
    private final SubjectMapper mapper;

    @Autowired
    private SubjectCategoryMapper subjectCategoryMapper;

    public SubjectSOImpl(
            SubjectRepository repository,
            SubjectMapper mapper
    ) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    public Page<SubjectModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<SubjectEntity> entityPage = repository.findAll(pageable);
        List<SubjectModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    @Override
    public List<SubjectModel> getListByGrades(List<SubjectCategoryModel> grades) {
        return mapper.toModelList(repository.findByGrades(subjectCategoryMapper.toEntityList(grades)));
    }
}
