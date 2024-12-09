package system.application;

import system.domain.Batch;
import system.domain.DataItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BatchProcessor {
    private final BatchWriter batchWriter = new BatchWriter();

    public void processBatch(Batch batch) {
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (DataItem item : batch.getItems()) {
            executor.submit(() -> processItem(item));
        }

        executor.shutdown();
        batchWriter.writeBatch(batch);
    }

    private void processItem(DataItem item) {
        try {
            Thread.sleep(50);
            System.out.println("Processed: " + item);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
