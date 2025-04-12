package src;

import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private String name;
    private State state;
    private long totalWaitTime = 0; // Total time spent waiting for chopsticks in milliseconds

    public Philosopher(String name, Chopstick left, Chopstick right) {
        this.name = name;
        this.left = left;
        this.right = right;
    }

    public void run() {
        try {
            while (true) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        state = State.THINKING;
        Thread.sleep(ThreadLocalRandom.current().nextLong(60, 120));
        state = State.HUNGRY;
    }

    private void eat() throws InterruptedException {
        while (!hasChopsticks()) {
            getChopsticks();
            Thread.sleep(100);
        }
        state = State.EATING;
        Thread.sleep(ThreadLocalRandom.current().nextLong(120, 180));
        left.release(this);
        right.release(this);
        state = State.THINKING;
    }

    public enum State {
        THINKING,
        HUNGRY,
        EATING
    }

    private void getChopsticks() throws InterruptedException {
        long startWait = System.currentTimeMillis(); // Start measuring wait time

        Chopstick first = left.getId() < right.getId() ? left : right;
        Chopstick second = left.getId() < right.getId() ? right : left;

        first.acquire(this);
        second.acquire(this);

        long endWait = System.currentTimeMillis(); // End measuring wait time
        totalWaitTime += (endWait - startWait); // Add to total wait time
    }

    private synchronized boolean hasChopsticks() {
        return left.getOwner() == this && right.getOwner() == this;
    }

    public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    public long getTotalWaitTime() {
        return totalWaitTime;
    }
}
