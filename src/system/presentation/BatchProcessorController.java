package system.presentation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import system.domain.ProcessResult;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

public class BatchProcessorController implements HttpHandler {
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

            ProcessResult processResult = new ProcessResult();

            String response = "{ \"Process started\": \"id " +
                    processResult.getProcessId() +
                    "\" }";

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
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
