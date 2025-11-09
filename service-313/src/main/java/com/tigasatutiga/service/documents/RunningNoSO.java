package com.tigasatutiga.service.documents;

import com.tigasatutiga.entities.documents.RunningNoEntity;
import com.tigasatutiga.models.documents.RunningNoModel;
import com.tigasatutiga.service.BaseSO;

public interface RunningNoSO extends BaseSO<RunningNoEntity, RunningNoModel, Long> {

    RunningNoModel getNextRunningNo(String docCode);
}
