package src;

public class Chopstick {
    private volatile Philosopher owner;

    public synchronized boolean release(Philosopher p) {
        if (p != owner) {
            return false;
        }
        owner = null;
        return true;
    }

    public synchronized boolean acquire(Philosopher p) throws InterruptedException {
        if (owner != null) {
            return false;
        }
        owner = p;
        return true;
    }

    public synchronized Philosopher getOwner() {
        return owner;
    }
}
