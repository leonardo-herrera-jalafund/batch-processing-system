package system.infrastructure;

import system.domain.ProcessResult;

import java.util.UUID;

public interface IProcessResultRepository {
    ProcessResult addProcessResult(ProcessResult processResult);
    ProcessResult getProcessResultById(UUID processResultId);
    void updateProcessResult(ProcessResult processResult);
    void removeProcessResult(UUID processResultId);
}
