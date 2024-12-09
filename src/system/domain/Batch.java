package system.domain;

import java.util.List;
import java.util.UUID;

public class Batch {
    private UUID id;
    private final List<DataItem> items;

    public Batch(List<DataItem> items) {
        this.items = items;
    }

    public List<DataItem> getItems() {
        return items;
    }
}
