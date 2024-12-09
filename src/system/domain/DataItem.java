package system.domain;

import java.util.UUID;

public class DataItem {
    private UUID id;
    private Invoice invoice;

    public DataItem(Invoice invoice) {
        this.id = UUID.randomUUID();
        this.invoice = null;
    }

}
