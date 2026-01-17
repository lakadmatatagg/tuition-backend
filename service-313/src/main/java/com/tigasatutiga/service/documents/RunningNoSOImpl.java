package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.mapper.documents.RunningNoMapper;
import com.tigasatutiga.models.documents.RunningNoModel;
import com.tigasatutiga.repository.documents.RunningNoRepository;
import com.tigasatutiga.service.BaseSOImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RunningNoSOImpl extends BaseSOImpl<RunningNoEntity, RunningNoModel, Long> implements RunningNoSO {

    private final RunningNoRepository repository;
    private final RunningNoMapper mapper;

    @Autowired
    public RunningNoSOImpl(RunningNoRepository repository, RunningNoMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public RunningNoModel getNextRunningNo(String docCode) {
        // 1️⃣ Fetch or create the entity
        RunningNoEntity entity = repository.findByDocCodeForUpdate(docCode)
                .orElseGet(() -> {
                    RunningNoEntity newEntity = new RunningNoEntity();
                    newEntity.setDocCode(docCode);
                    newEntity.setRunningNo("000001");
                    return repository.save(newEntity);
                });

        // 2️⃣ Increment numeric part
        int nextNumber = Integer.parseInt(entity.getRunningNo()) + 1;
        entity.setRunningNo(String.format("%06d", nextNumber));

        // 3️⃣ Save updated running number back to DB (atomic)
        repository.save(entity);

        // 4️⃣ Convert to model and return
        return mapper.toModel(entity);
    }

}
