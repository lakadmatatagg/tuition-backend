package com.tigasatutiga.service.tuitionez.student;

import com.tigasatutiga.entities.tuitionez.student.ParentEntity;
import com.tigasatutiga.mapper.tuitionez.student.ParentMapper;
import com.tigasatutiga.models.tuitionez.student.ParentModel;
import com.tigasatutiga.repository.tuitionez.student.ParentRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.springframework.stereotype.Service;

@Service
public class ParentSOImpl extends BaseSOImpl<ParentEntity, ParentModel, Long> implements ParentSO {

    public ParentSOImpl(ParentRepository repository, ParentMapper mapper) {
        super(repository, mapper);
    }
}
