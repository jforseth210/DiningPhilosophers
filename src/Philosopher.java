package src;

import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {

    // Each philosopher has two chopsticks (left and right) and a name.
    private Chopstick left;
    private Chopstick right;
    private String name;
    private State state;
    private long totalWaitTime = 0; // Total time spent waiting for chopsticks in milliseconds

    /**
     * Constructor for Philosopher.
     * @param name Name of the Philosopher (used in the logging table)
     * @param left Chopstick to the left of the Philosopher
     * @param right Chopstick to the right of the Philosopher
     */
    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    /**
     * Run method (called when the thread is started).
     * This method simulates the philosopher's behavior of thinking and eating.
     */
    public void run() {

        // Main run loop: Think, and when done thinking eat.  When done eating, think.
        try {
            while (true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Simulates the philosopher thinking.
     * @throws InterruptedException
     */
    private void think() throws InterruptedException {
        // Set the state to THINKING.
        state = State.THINKING;

        // Wait a random amount of time for the Philosopher to ponder the mysteries of the universe.
        Thread.sleep(ThreadLocalRandom.current().nextLong(60, 120));

        // Set the state to HUNGRY.  All that thinking has made the Philosopher crave sustinence.
        state = State.HUNGRY;
    }

    /**
     * Simulates the philosopher eating.
     * @throws InterruptedException
     */
    private void eat() throws InterruptedException {

        // While the Philosopher does not have chopsticks, try to get them.
        while (!hasChopsticks()) {
            getChopsticks();
            Thread.sleep(100);
        }

        // Once chopsticks are acquired, the Philosopher can eat.
        // Set the state to EATING.
        state = State.EATING;
        // It takes a random amount of time to eat.
        Thread.sleep(ThreadLocalRandom.current().nextLong(120, 180));

        //When done eating, set down the chopsticks and return to thinking.
        left.release(this);
        right.release(this);
        state = State.THINKING;
    }

    /**
     * Enum representing the state of the Philosopher.
     * THINKING: The Philosopher is thinking.
     * HUNGRY: The Philosopher is hungry and trying to acquire chopsticks.
     * EATING: The Philosopher is eating.
     */
    public enum State {
        THINKING,
        HUNGRY,
        EATING
    }

    /**
     * Attempts to acquire chopsticks.
     * @throws InterruptedException
     */
    private void getChopsticks() throws InterruptedException {
        long startWait = System.currentTimeMillis(); // Start measuring wait time

        // Set the order to acquire chopsticks in.
        // Each chopstick has an ID, and the Philosopher will always try to acquire the lower ID first.
        Chopstick first = left.getId() < right.getId() ? left : right;
        Chopstick second = left.getId() < right.getId() ? right : left;

        // Acquire the chopsticks in the order specified.
        first.acquire(this);
        second.acquire(this);

        // End measuring wait time and update the total wait time.
        long endWait = System.currentTimeMillis(); 
        totalWaitTime += (endWait - startWait);
    }

    /**
     * Checks if the Philosopher is in possession of both their left and right chopsticks.
     * @return True if the Philosopher has both chopsticks, false otherwise.
     */
    private synchronized boolean hasChopsticks() {
        return left.getOwner() == this && right.getOwner() == this;
    }

    /**
     * Getter for the Philosopher's state.
     * @return The current state of the Philosopher (THINKING, HUNGRY, or EATING).
     */
    public State getState() {
        return state;
    }

    /**
     * Getter for the Philosopher's name.
     * @return The name of the Philosopher.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the Philosopher's total wait time.
     * @return The total time spent waiting for chopsticks in milliseconds.
     */
    public long getTotalWaitTime() {
        return totalWaitTime;
    }
}
