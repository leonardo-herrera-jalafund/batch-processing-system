public class DataItem {
    private final String name;
    private boolean processed;

    public DataItem(String name) {
        this.name = name;
        this.processed = false;
    }

    public String getName() {
        return name;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        return "DataItem{" +
                "name='" + name + '\'' +
                ", processed=" + processed +
                '}';
    }
}
