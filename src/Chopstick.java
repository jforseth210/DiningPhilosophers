package src;

public class Chopstick {

    // Each chopstick has an owner and an id.
    private volatile Philosopher owner;
    private final int id;

    /**
     * Constructor for the Chopstick class.
     * @param id The ID of the chopstick.
     */
    public Chopstick(int id) {
        this.id = id;
    }

    /**
     * Releases the chopstick, allowing other philosophers to acquire it.
     * @param p The philosopher who is releasing the chopstick.
     */
    public synchronized void release(Philosopher p) {
        
        // Only the owner of a chopstick can release it.
        if (p != owner) {
            throw new IllegalStateException("Philosopher " + p.getName() + " is not the owner of this chopstick");
        }

        // Set owner to null, indicating that the chopstick is now free.
        // Notify any waiting philosophers that the chopstick is now available.
        owner = null;
        notify();
    }

    /**
     * Acquires the chopstick for the given philosopher.
     * @param p The philosopher who is trying to acquire the chopstick.
     * @throws InterruptedException
     */
    public synchronized void acquire(Philosopher p) throws InterruptedException {
        
        // Wait until the chopstick is free (owner becomes null).
        while (owner != null) {
            wait();
        }

        // Set the owner of the chopstick to the given philosopher.
        owner = p;
    }

    /**
     * Getter for the chopstick's owner.
     * @return The philosopher who owns the chopstick, or null if it is free.
     */
    public Philosopher getOwner() {
        return owner;
    }

    /**
     * Getter for the chopstick's ID.
     * @return The ID of the chopstick.
     */
    public int getId() {
        return id;
    }
}
