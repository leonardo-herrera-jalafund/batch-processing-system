package system;

import org.junit.Test;
import system.application.BatchLoader;
import system.domain.Batch;
import system.domain.Invoice;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvoicesCreatedTest {
    @Test
    public void testInvoicesCreated() throws IOException {
        BatchLoader batchLoader = new BatchLoader();
        List<Batch> batches = batchLoader.processDirectory("resources");

        assertEquals(10, batches.size());
        assertEquals(100, batches.get(0).getItems().size());
    }
}
