import java.util.List;

public class Batch {
    private final List<DataItem> items;

    public Batch(List<DataItem> items) {
        this.items = items;
    }

    public List<DataItem> getItems() {
        return items;
    }
}
