package system.domain;

import java.util.List;
import java.util.UUID;

public class ProcessResult {
    private UUID processId;
    private boolean status;
    private List<Batch> batches;
    private int totalBatches;
    private int completedBatches;
    private long startTime;
    private long endTime;
    private String description;
    private List<String> errorMessages;
    private double totalSales;
    private int totalItemsSold;
    private double totalTax;
    private double averageRating;

    public ProcessResult() {
        this.processId = UUID.randomUUID();
        this.status = false;
        this.startTime = System.currentTimeMillis();
    }

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
        if (status) {
            this.endTime = System.currentTimeMillis();
        }
    }

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
        this.totalBatches = batches.size();
    }

    public int getTotalBatches() {
        return totalBatches;
    }

    public int getCompletedBatches() {
        return completedBatches;
    }

    public void incrementCompletedBatches() {
        this.completedBatches++;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public double getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(double totalSales) {
        this.totalSales = totalSales;
    }

    public int getTotalItemsSold() {
        return totalItemsSold;
    }

    public void setTotalItemsSold(int totalItemsSold) {
        this.totalItemsSold = totalItemsSold;
    }

    public double getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(double totalTax) {
        this.totalTax = totalTax;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    @Override
    public String toString() {
        return "ProcessResult{" +
                "processId=" + processId +
                ", status=" + status +
                ", totalBatches=" + totalBatches +
                ", completedBatches=" + completedBatches +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", errorMessages=" + errorMessages +
                '}';
    }
}
