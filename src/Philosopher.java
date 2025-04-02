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
        System.out.println(name + " is thinking");
        state = State.THINKING;
        Thread.sleep(ThreadLocalRandom.current().nextLong(3000, 6000));
        state = State.HUNGRY;
        System.out.println(name + " is hungry");
    }

    private void eat() throws InterruptedException {
        state = State.EATING;
        while (!hasChopsticks()) {
            getChopsticks();
            Thread.sleep(100);
        }
        System.out.println(name + " is eating");
        Thread.sleep(ThreadLocalRandom.current().nextLong(1500, 3000));
        left.release(this);
        right.release(this);
        System.out.println(name + " is done eating");
        state = State.THINKING;
    }

    private enum State {
        THINKING,
        HUNGRY,
        EATING
    }

    private synchronized boolean getChopsticks() throws InterruptedException {
        Philosopher leftOwner = left.getOwner();
        Philosopher rightOwner = right.getOwner();
        if (leftOwner == null && rightOwner == null) {
            left.acquire(this);
            right.acquire(this);
            return true;
        }
        if (leftOwner != null) {
            System.out.println(name + " is waiting for " + leftOwner.name + " to release");
        }
        if (rightOwner != null) {
            System.out.println(name + " is waiting for " + rightOwner.name + " to release");
        }
        return false;
    }

    private boolean hasChopsticks() {
        return left.getOwner() == this && right.getOwner() == this;
    }
}