package src;

public class Chopstick {
    private volatile Philosopher owner;
    private final int id;

    public Chopstick(int id) {
        this.id = id;
    }

    public synchronized void release(Philosopher p) {
        if (p != owner) {
            throw new IllegalStateException("Philosopher " + p.getName() + " is not the owner of this chopstick");
        }
        owner = null;
        notify();
    }

    public synchronized void acquire(Philosopher p) throws InterruptedException {
        while (owner != null) {
            wait();
        }
        owner = p;
    }

    public Philosopher getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }
}
