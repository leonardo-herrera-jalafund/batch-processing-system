package system.application;

import system.domain.Batch;
import system.domain.DataItem;
import java.util.ArrayList;
import java.util.List;


public class BatchLoader {
    private final BatchProcessor batchProcessor = new BatchProcessor();
    private final ConcurrencyController concurrencyController = new ConcurrencyController(5);

    public void loadData() {
        List<DataItem> dataItems = fetchDataFromSource();
        List<Batch> batches = splitIntoBatches(dataItems);

        for (int i = 0; i < batches.size(); i++) {
            int batchNumber = i + 1;
            Batch batch = batches.get(i);

            new Thread(() -> {
                try {
                    concurrencyController.acquire();
                    System.out.println("Processing Batch " + batchNumber + " | Thread: " + Thread.currentThread().getName());
                    batchProcessor.processBatch(batch);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    concurrencyController.release();
                    System.out.println("Finished Batch " + batchNumber + " | Thread: " + Thread.currentThread().getName());
                }
            }).start();
        }
    }


    private List<DataItem> fetchDataFromSource() {
        List<DataItem> dataItems = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            dataItems.add(new DataItem(null));
        }
        return dataItems;
    }

    private List<Batch> splitIntoBatches(List<DataItem> dataItems) {
        List<Batch> batches = new ArrayList<>();
        int batchSize = 10;
        for (int i = 0; i < dataItems.size(); i += batchSize) {
            int end = Math.min(i + batchSize, dataItems.size());
            batches.add(new Batch(dataItems.subList(i, end)));
        }
        return batches;
    }
}
