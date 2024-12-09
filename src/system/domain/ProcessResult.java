package system.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProcessResult {
    private UUID processId;
    private boolean status;
    private List<Batch> batches;

    public ProcessResult() {
        this.processId = UUID.randomUUID();
        this.status = false;
        this.batches = new ArrayList<>();
    };

    public UUID getProcessId() {
        return processId;
    }

    public boolean isStatus() {
        return status;
    }

    public void setProcessId(UUID processId) {
        this.processId = processId;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
