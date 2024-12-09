package system.application;

import system.domain.Batch;
import system.domain.DataItem;
import system.domain.Invoice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BatchLoader {
    private static final int BATCH_SIZE = 100;

    public List<Batch> processDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] csvFiles = directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("No CSV files found in the directory.");
        }

        List<DataItem> dataItems = new ArrayList<>();
        for (File csvFile : csvFiles) {
            dataItems.addAll(parseCsvFile(csvFile));
        }

        return createBatches(dataItems);
    }

    private List<DataItem> parseCsvFile(File csvFile) throws IOException {
        List<DataItem> dataItems = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                Invoice invoice = parseLineToInvoice(line);
                dataItems.add(new DataItem(invoice));
            }
        }
        return dataItems;
    }

    private Invoice parseLineToInvoice(String line) {
        String[] parts = line.split(",");
        return new Invoice(
                parts[0],
                parts[1],
                parts[2],
                parts[3],
                parts[4],
                parts[5],
                Double.parseDouble(parts[6]),
                Integer.parseInt(parts[7]),
                Double.parseDouble(parts[8]),
                Double.parseDouble(parts[9]),
                parts[10],
                parts[11],
                parts[12],
                Double.parseDouble(parts[13]),
                Double.parseDouble(parts[14]),
                Double.parseDouble(parts[15]),
                Double.parseDouble(parts[16])
        );
    }

    private List<Batch> createBatches(List<DataItem> dataItems) {
        List<Batch> batches = new ArrayList<>();
        for (int i = 0; i < dataItems.size(); i += BATCH_SIZE) {
            List<DataItem> batchItems = dataItems.subList(i, Math.min(i + BATCH_SIZE, dataItems.size()));
            batches.add(new Batch(batchItems));
        }
        return batches;
    }
}
