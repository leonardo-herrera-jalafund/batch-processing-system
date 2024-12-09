package system.presentation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import system.application.BatchLoader;
import system.application.BatchProcessor;
import system.application.BatchWriter;
import system.domain.Batch;
import system.domain.ProcessResult;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;


public class BatchProcessorController implements HttpHandler {
    private final BatchLoader batchLoader;
    private final BatchProcessor batchProcessor;
    private final ProcessResult processResult;

    public BatchProcessorController(BatchLoader batchLoader, BatchProcessor batchProcessor, ProcessResult processResult) {
        this.batchLoader = batchLoader;
        this.batchProcessor = batchProcessor;
        this.processResult = processResult;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equals(exchange.getRequestMethod())) {
            byte[] requestBody = exchange.getRequestBody().readAllBytes();
            String directoryPath = new String(requestBody).trim();

            File directory = new File(directoryPath);

            if (!directory.isDirectory()) {
                sendErrorResponse(exchange, 400, "Invalid directory path");
                return;
            }

            if (isDirectoryEmpty(directory)) {
                sendErrorResponse(exchange, 400, "Directory is empty");
                return;
            }

            try {
                List<Batch> batches = batchLoader.processDirectory(directoryPath);
                BatchWriter batchWriter = new BatchWriter(batches, processResult);
                for (Batch batch : batches) {
                        batchProcessor.processBatch(batch, batchWriter);
                }

                String response = "{ \"batchesProcessed\": " + batches.size() + " }";

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (IOException e) {
                sendErrorResponse(exchange, 500, "Error processing files: " + e.getMessage());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private boolean isDirectoryEmpty(File directory) {
        String[] files = directory.list();
        return files == null || files.length == 0;
    }

    private void sendErrorResponse(HttpExchange exchange, int statusCode, String errorMessage) throws IOException {
        String response = "{ \"error\": \"" + errorMessage + "\" }";
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, response.length());
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}
