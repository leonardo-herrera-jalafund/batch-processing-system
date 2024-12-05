public class BatchWriter {
    private final FlowController flowController = new FlowController();

    public void writeBatch(Batch batch) {
        flowController.applyBackpressure(() -> {
            for (DataItem item : batch.getItems()) {
                System.out.println("Writing: " + item);
            }
        });
    }
}
