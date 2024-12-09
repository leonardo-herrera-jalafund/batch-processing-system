package system;

import com.sun.net.httpserver.HttpServer;
import system.application.BatchLoader;
import system.application.BatchProcessor;
import system.domain.ProcessResult;
import system.presentation.BatchLoaderController;
import system.presentation.ProcessStatusController;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        BatchLoader batchLoader = new BatchLoader();
        BatchProcessor batchProcessor = new BatchProcessor(5);
        ProcessResult processResult = new ProcessResult();

        server.createContext("/api/batch-processor", new BatchLoaderController(batchLoader, batchProcessor, processResult));
        server.createContext("/api/status", new ProcessStatusController(processResult));

        server.setExecutor(null);
        System.out.println("Server started at http://localhost:8080");
        server.start();
    }
}
