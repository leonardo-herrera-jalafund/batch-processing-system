package system.application;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ConcurrencyControllerTest {

    @Test
    public void testConcurrencyController() throws InterruptedException {
        ConcurrencyController controller = new ConcurrencyController(3);
        AtomicInteger activeTasks = new AtomicInteger(0);

        Runnable task = () -> {
            try {
                controller.acquire();
                int current = activeTasks.incrementAndGet();
                assertTrue(current <= 3);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                activeTasks.decrementAndGet();
                controller.release();
            }
        };

        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(task));
        }

        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
    }

    @Test
    public void testSingleThreadExecution() throws InterruptedException {
        ConcurrencyController controller = new ConcurrencyController(1);
        AtomicInteger activeTasks = new AtomicInteger(0);

        Runnable task = () -> {
            try {
                controller.acquire();
                int current = activeTasks.incrementAndGet();
                assertTrue(current <= 1);
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                activeTasks.decrementAndGet();
                controller.release();
            }
        };

        Thread thread = new Thread(task);
        thread.start();
        thread.join();
    }

    @Test
    public void testInterruptedThread() throws InterruptedException {
        ConcurrencyController controller = new ConcurrencyController(1);

        Thread thread = new Thread(() -> {
            try {
                controller.acquire();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted while waiting.");
                Thread.currentThread().interrupt();
            }
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();
        thread.join();
        assertTrue(thread.isInterrupted());
    }

}
