package system;

import system.application.BatchLoader;

public class Main {
    public static void main(String[] args) {
        BatchLoader batchLoader = new BatchLoader();
        batchLoader.loadData();
    }
}
