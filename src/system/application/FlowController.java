package system.application;

import java.util.concurrent.Semaphore;

public class FlowController {
    private final Semaphore semaphore = new Semaphore(10);

    public void applyBackpressure(Runnable task) {
        try {
            semaphore.acquire();
            task.run();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}
