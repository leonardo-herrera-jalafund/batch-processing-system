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
            String response = "";

            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(200, response.length());
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } else {
            exchange.sendResponseHeaders(405, -1);
        }
    }

    private String serializeProcessResultToJson(ProcessResult result) {
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"processId\":\"").append(result.getProcessId()).append("\",")
                .append("\"status\":").append(result.isStatus()).append(",")
                .append("\"totalBatches\":").append(result.getTotalBatches()).append(",")
                .append("\"completedBatches\":").append(result.getCompletedBatches()).append(",")
                .append("\"totalSales\":").append(result.getTotalSales()).append(",")
                .append("\"totalItemsSold\":").append(result.getTotalItemsSold()).append(",")
                .append("\"totalTax\":").append(result.getTotalTax()).append(",")
                .append("\"averageRating\":").append(result.getAverageRating()).append(",")
                .append("\"errorMessages\":[");

        for (int i = 0; i < result.getErrorMessages().size(); i++) {
            jsonBuilder.append("\"").append(result.getErrorMessages().get(i)).append("\"");
            if (i < result.getErrorMessages().size() - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]}");
        return jsonBuilder.toString();
    }
}
