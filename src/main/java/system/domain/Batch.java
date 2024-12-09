package system.domain;

import java.util.List;
import java.util.UUID;

public class Batch {
    private UUID id;
    private final List<DataItem> items;

    public Batch(List<DataItem> items) {
        this.id = UUID.randomUUID();
        this.items = items;
    }

    public List<DataItem> getItems() {
        return items;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
