package com.tigasatutiga.repository.documents;

import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.repository.BaseRepository;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RunningNoRepository extends BaseRepository<RunningNoEntity, Long> {
    // Add pessimistic lock to prevent concurrent reads of the same row
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT r FROM RunningNoEntity r WHERE r.docCode = :docCode")
    Optional<RunningNoEntity> findByDocCodeForUpdate(String docCode);
}