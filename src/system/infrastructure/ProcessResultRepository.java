package system.infrastructure;

import system.domain.ProcessResult;

import java.util.Map;
import java.util.UUID;

public class ProcessResultRepository implements IProcessResultRepository {
    Map<UUID, ProcessResult> processResults;

    @Override
    public ProcessResult addProcessResult(ProcessResult processResult) {
        processResults.put(processResult.getProcessId(), processResult);
        return processResult;
    }

    @Override
    public ProcessResult getProcessResultById(UUID processResultId) {
        return processResults.get(processResultId);
    }

    @Override
    public void updateProcessResult(ProcessResult processResult) {
        processResults.put(processResult.getProcessId(), processResult);
    }

    @Override
    public void removeProcessResult(UUID processResultId) {
        processResults.remove(processResultId);
    }
}
