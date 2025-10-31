package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunningNoRepository extends BaseRepository<RunningNoEntity, Long> {
    Optional<RunningNoEntity> findByDocCode(String docCode);
}