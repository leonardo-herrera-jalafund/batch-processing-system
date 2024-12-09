package system.application;

import system.domain.Batch;
import system.domain.DataItem;
import system.domain.Invoice;
import system.domain.ProcessResult;
import java.util.List;

public class ResultHandler {
    private final ProcessResult processResult;

    public ResultHandler(List<Batch> batches, ProcessResult processResult) {
        this.processResult = processResult;
        this.processResult.setBatches(batches);
    }

    public synchronized void handleBatchCompletion(Batch batch) {
        processResult.incrementCompletedBatches();
        System.out.println("Batch completed: " + (batch != null ? batch.getId() : "null"));

        double batchTotalSales = 0;
        int batchTotalItems = 0;
        double batchTotalTax = 0;
        double batchTotalRating = 0;

        for (DataItem item : batch.getItems()) {
            Invoice invoice = item.getInvoice();

            if (invoice == null) {
                System.out.println("Warning: Invoice is null for DataItem.");
                continue;
            }

            batchTotalSales += invoice.getTotal();
            batchTotalItems += invoice.getQuantity();
            batchTotalTax += invoice.getTax();
            batchTotalRating += invoice.getRating();
        }

        processResult.setTotalSales(processResult.getTotalSales() + batchTotalSales);
        processResult.setTotalItemsSold(processResult.getTotalItemsSold() + batchTotalItems);
        processResult.setTotalTax(processResult.getTotalTax() + batchTotalTax);

        int currentCompleted = processResult.getCompletedBatches();
        processResult.setAverageRating(
                ((processResult.getAverageRating() * (currentCompleted - 1)) + (batchTotalRating / batch.getItems().size()))
                        / currentCompleted
        );

        if (processResult.getCompletedBatches() == processResult.getTotalBatches()) {
            processResult.setStatus(true);
            System.out.println("All batches completed. Process ID: " + processResult.getProcessId());
        }
    }

    public synchronized void handleBatchFailure(Batch batch, String errorMessage) {
        System.out.println("Batch failed: " + batch.getId() + " | Error: " + errorMessage);
        processResult.getErrorMessages().add("Batch " + batch.getId() + ": " + errorMessage);
    }
}
