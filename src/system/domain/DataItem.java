package system.domain;

import java.util.UUID;

public class DataItem {
    private UUID id;
    private Invoice invoice;

    public DataItem(Invoice invoice) {
        this.id = UUID.randomUUID();
        this.invoice = invoice;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
