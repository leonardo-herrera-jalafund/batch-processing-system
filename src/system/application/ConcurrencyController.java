package system.application;

public class ConcurrencyController {
    private final int maxConcurrentTasks;
    private int currentTaskCount = 0;

    public ConcurrencyController(int maxConcurrentTasks) {
        this.maxConcurrentTasks = maxConcurrentTasks;
    }

    public synchronized void acquire() throws InterruptedException {
        while (currentTaskCount >= maxConcurrentTasks) {
            System.out.println("Waiting: Thread " + Thread.currentThread().getName() + " | Current Tasks: " + currentTaskCount);
            wait();
        }
        currentTaskCount++;
        System.out.println("Acquired: Thread " + Thread.currentThread().getName() + " | Current Tasks: " + currentTaskCount);
    }

    public synchronized void release() {
        currentTaskCount--;
        System.out.println("Released: Thread " + Thread.currentThread().getName() + " | Current Tasks: " + currentTaskCount);
        notifyAll();
    }
}
