package system.presentation;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import system.domain.ProcessResult;
import java.io.IOException;
import java.io.OutputStream;


public class ProcessStatusController implements HttpHandler {
    private final ProcessResult processResult;

    public ProcessStatusController(ProcessResult processResult) {
        this.processResult = processResult;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("GET".equals(exchange.getRequestMethod())) {
            try {
                String response = serializeProcessResultToJson(processResult);

                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            } catch (Exception e) {
                String response = "{ \"error\": \" internal server error\" } \" }";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(500, response.length());
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private String serializeProcessResultToJson(ProcessResult result) {
        if (result == null) {
            return "{\n\"Result\": \"Null\" \n}";
        }

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"processId\":\"").append(result.getProcessId() != null ? result.getProcessId() : "N/A").append("\",").append("\n")
                .append("\"status\":").append(result.isStatus()).append(",").append("\n")
                .append("\"totalBatches\":").append(result.getTotalBatches()).append(",").append("\n")
                .append("\"completedBatches\":").append(result.getCompletedBatches()).append(",").append("\n")
                .append("\"totalSales\":").append(result.getTotalSales()).append(",").append("\n")
                .append("\"totalItemsSold\":").append(result.getTotalItemsSold()).append(",").append("\n")
                .append("\"totalTax\":").append(result.getTotalTax()).append(",").append("\n")
                .append("\"averageRating\":").append(result.getAverageRating()).append(",").append("\n")
                .append("\"errorMessages\":[");

        if (result.getErrorMessages() == null || result.getErrorMessages().isEmpty()) {
            jsonBuilder.append("\"No errors\"]");
        } else {
            for (int i = 0; i < result.getErrorMessages().size(); i++) {
                String errorMessage = result.getErrorMessages().get(i) != null ? result.getErrorMessages().get(i) : "Unknown error";
                jsonBuilder.append("\"").append(errorMessage).append("\"");
                if (i < result.getErrorMessages().size() - 1) {
                    jsonBuilder.append(",");
                }
            }
            jsonBuilder.append("]");
        }

        jsonBuilder.append("}");
        return jsonBuilder.toString();
    }
}
