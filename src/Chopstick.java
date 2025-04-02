package src;

public class Chopstick {
    private volatile Philosopher owner;

    public Chopstick() {
        System.out.println("Chopstick");
    }

    public synchronized boolean release(Philosopher p) {
        if (p != owner) {
            return false;
        }
        owner = null;
        notify();
        return true;
    }

    public synchronized boolean acquire(Philosopher p) throws InterruptedException {
        while (owner != null) {
            wait();
        }
        owner = p;
        return true;
    }
}
