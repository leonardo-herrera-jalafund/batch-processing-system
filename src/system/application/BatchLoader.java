package system.application;

import system.domain.Batch;
import system.domain.DataItem;

import java.util.ArrayList;
import java.util.List;

public class BatchLoader {
    private final BatchProcessor batchProcessor = new BatchProcessor();

    public void loadData() {
        List<DataItem> dataItems = fetchDataFromSource();

        List<Batch> batches = splitIntoBatches(dataItems);

        for (Batch batch : batches) {
            new Thread(() -> batchProcessor.processBatch(batch)).start();
        }
    }

    private List<DataItem> fetchDataFromSource() {
        List<DataItem> dataItems = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            dataItems.add(new DataItem("Item-" + i));
        }
        return dataItems;
    }

    private List<Batch> splitIntoBatches(List<DataItem> dataItems) {
        List<Batch> batches = new ArrayList<>();
        int batchSize = 100;
        for (int i = 0; i < dataItems.size(); i += batchSize) {
            int end = Math.min(i + batchSize, dataItems.size());
            batches.add(new Batch(dataItems.subList(i, end)));
        }
        return batches;
    }
}
