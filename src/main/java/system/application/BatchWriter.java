package system.application;

import system.domain.Batch;
import system.domain.DataItem;

public class BatchWriter {
    private final ConcurrencyController concurrencyController;

    public BatchWriter(int maxConcurrentTasks) {
        this.concurrencyController = new ConcurrencyController(maxConcurrentTasks);
    }

    public void writeBatch(Batch batch, ResultHandler resultHandler) {
        new Thread(() -> {
            try {
                concurrencyController.acquire();
                System.out.println("Writing batch: " + batch.getId());
                Thread.sleep(500); // Replace with actual logic
                resultHandler.handleBatchCompletion(batch); // Notify completion
            } catch (InterruptedException e) {
                resultHandler.handleBatchFailure(batch, "Writing interrupted: " + e.getMessage());
            } finally {
                concurrencyController.release();
            }
        }).start();
    }
}