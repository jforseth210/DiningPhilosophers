package src;

import java.util.concurrent.ThreadLocalRandom;

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private String name;
    private State state;

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
        // System.out.println(name + " is thinking");
        state = State.THINKING;
        Thread.sleep(ThreadLocalRandom.current().nextLong(60, 120));
        state = State.HUNGRY;
        // System.out.println(name + " is hungry");
    }

    private void eat() throws InterruptedException {
        while (!hasChopsticks()) {
            getChopsticks();
            Thread.sleep(100);
        }
        state = State.EATING;
        // System.out.println(name + " is eating");
        Thread.sleep(ThreadLocalRandom.current().nextLong(120, 180));
        left.release(this);
        right.release(this);
        // System.out.println(name + " is done eating");
        state = State.THINKING;
    }

    public enum State {
        THINKING,
        HUNGRY,
        EATING
    }

    private boolean getChopsticks() throws InterruptedException {
        boolean hasLeft = left.acquire(this);
        if (!hasLeft) {
            return false;
        }

        boolean hasRight = right.acquire(this);
        if (!hasRight) {
            left.release(this);
            return false;
        }

        return true;
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
}