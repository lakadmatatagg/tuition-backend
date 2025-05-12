package com.tigasatutiga.service.student;

import com.tigasatutiga.entities.tuitionez.student.StudentEntity;
import com.tigasatutiga.mapper.student.StudentMapper;
import com.tigasatutiga.models.student.StudentModel;
import com.tigasatutiga.repository.reference.ReferenceCodeRepository;
import com.tigasatutiga.repository.student.ParentRepository;
import com.tigasatutiga.repository.student.StudentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSOImpl extends BaseSOImpl<StudentEntity, StudentModel, Long> implements StudentSO {

    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final ReferenceCodeRepository referenceCodeRepository;
    private final ParentRepository parentRepository;

    public StudentSOImpl(StudentRepository repository, StudentMapper mapper, ReferenceCodeRepository referenceCodeRepository, ParentRepository parentRepository) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
        this.referenceCodeRepository = referenceCodeRepository;
        this.parentRepository = parentRepository;
    }

    public Page<StudentModel> getAll(int pageNo, int pageSize, String sortField, String sortDir) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), Strings.isBlank(sortField) ? "id" : sortField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<StudentEntity> entityPage = repository.findAll(pageable);
        List<StudentModel> modelList = mapper.toModelList(entityPage.getContent());
        return new PageImpl<>(modelList, pageable, entityPage.getTotalElements());
    }

    public List<StudentModel> getListByParentId(Long parentId) {

        return mapper.toModelList(repository.findByParentId(parentId));
    }
}
