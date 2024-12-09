package system.application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import system.domain.Batch;
import system.domain.DataItem;
import system.domain.Invoice;
import system.domain.ProcessResult;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;


public class BatchProcessorTest {

    private BatchProcessor batchProcessor;
    private ProcessResult processResult;
    private BatchWriter batchWriter;

    @BeforeEach
    public void setUp() {
        batchProcessor = new BatchProcessor(2);
        processResult = new ProcessResult();
        batchWriter = new BatchWriter(new ArrayList<>(), processResult);
    }

    @Test
    public void testProcessBatch() throws InterruptedException {
        Batch mockBatch = mock(Batch.class);
        when(mockBatch.getId()).thenReturn(UUID.randomUUID());

        List<DataItem> mockItems = new ArrayList<>();
        DataItem mockItem1 = mock(DataItem.class);
        DataItem mockItem2 = mock(DataItem.class);
        mockItems.add(mockItem1);
        mockItems.add(mockItem2);

        Invoice mockInvoice1 = mock(Invoice.class);
        Invoice mockInvoice2 = mock(Invoice.class);
        when(mockItem1.getInvoice()).thenReturn(mockInvoice1);
        when(mockItem2.getInvoice()).thenReturn(mockInvoice2);

        when(mockInvoice1.getTotal()).thenReturn(100.0);
        when(mockInvoice1.getQuantity()).thenReturn(1);
        when(mockInvoice1.getTax()).thenReturn(10.0);
        when(mockInvoice1.getRating()).thenReturn(4.5);

        when(mockInvoice2.getTotal()).thenReturn(200.0);
        when(mockInvoice2.getQuantity()).thenReturn(2);
        when(mockInvoice2.getTax()).thenReturn(20.0);
        when(mockInvoice2.getRating()).thenReturn(3.5);

        when(mockBatch.getItems()).thenReturn(mockItems);

        List<Batch> batches = new ArrayList<>();
        batches.add(mockBatch);
        processResult.setBatches(batches);

        batchProcessor.processBatch(mockBatch, batchWriter);

        Thread.sleep(2000);

        verify(mockItem1).getInvoice();
        verify(mockItem2).getInvoice();

        System.out.println("Total Sales: " + processResult.getTotalSales());
        System.out.println("Total Items Sold: " + processResult.getTotalItemsSold());
        System.out.println("Total Tax: " + processResult.getTotalTax());
        System.out.println("Average Rating: " + processResult.getAverageRating());

        assert processResult.getTotalSales() == 300.0;
        assert processResult.getTotalItemsSold() == 3;
        assert processResult.getTotalTax() == 30.0;
        assert processResult.getAverageRating() == 4.0;
    }
}
