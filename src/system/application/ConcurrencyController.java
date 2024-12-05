package system.application;

public class ConcurrencyController {
    private final int maxConcurrentTasks;
    private int currentTaskCount = 0;

    public ConcurrencyController(int maxConcurrentTasks) {
        this.maxConcurrentTasks = maxConcurrentTasks;
    }

    public synchronized void acquire() throws InterruptedException {
        while (currentTaskCount >= maxConcurrentTasks) {
            wait();
        }
        currentTaskCount++;
    }

    public synchronized void release() {
        currentTaskCount--;
        notifyAll();
    }
}
