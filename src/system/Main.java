package system;

import com.sun.net.httpserver.HttpServer;
import system.application.BatchLoader;
import system.presentation.BatchProcessorController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        BatchLoader batchLoader = new BatchLoader();

        server.createContext("/api/batch-processor", new BatchProcessorController(batchLoader));

        server.setExecutor(null);
        System.out.println("Server stated at http://localhost:8080");
        server.start();
    }
}
