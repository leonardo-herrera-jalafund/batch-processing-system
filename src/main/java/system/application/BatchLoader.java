package system.application;

import system.domain.Batch;
import system.domain.DataItem;
import system.domain.Invoice;
import system.domain.exceptions.EmptyDirectoryException;
import system.domain.exceptions.InvalidCSVException;

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
            throw new EmptyDirectoryException("No CSV files found in the directory.");
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
                try {
                    Invoice invoice = parseLineToInvoice(line);
                    if (invoice != null) {
                        dataItems.add(new DataItem(invoice));
                    } else {
                        System.out.println("Skipping invalid invoice: " + line);
                    }
                } catch (Exception e) {
                    throw new InvalidCSVException("Error parsing line: " + line + " | Error: " + e.getMessage());
                }
            }
        }
        return dataItems;
    }


    private Invoice parseLineToInvoice(String line) {
        String[] parts = line.split(",");
        try {
            if (parts.length != 17) {
                throw new InvalidCSVException("Incorrect number of columns in line: " + line);
            }

            return new Invoice(
                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5],
                    Double.parseDouble(parts[6]), Integer.parseInt(parts[7]),
                    Double.parseDouble(parts[8]), Double.parseDouble(parts[9]),
                    parts[10], parts[11], parts[12],
                    Double.parseDouble(parts[13]), Double.parseDouble(parts[14]),
                    Double.parseDouble(parts[15]), Double.parseDouble(parts[16])
            );
        } catch (Exception e) {
            throw new InvalidCSVException("Error parsing line: " + line + " | Error: " + e.getMessage());
        }
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
