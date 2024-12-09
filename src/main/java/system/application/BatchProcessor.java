package system.application;


import system.domain.Batch;

public class BatchProcessor {
    private final ConcurrencyController concurrencyController;

    public BatchProcessor(int maxConcurrentTasks) {
        this.concurrencyController = new ConcurrencyController(maxConcurrentTasks);
    }

    public void processBatch(Batch batch, BatchWriter batchWriter) {
        new Thread(() -> {
            try {
                concurrencyController.acquire();
                System.out.println("Processing batch: " + batch.getId());
                Thread.sleep(1000);

                batchWriter.handleBatchCompletion(batch);
            } catch (InterruptedException e) {
                batchWriter.handleBatchFailure(batch, "Processing interrupted: " + e.getMessage());
            } finally {
                concurrencyController.release();
            }
        }).start();
    }

}