package src;

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private State state;

    public Philosopher(Chopstick left, Chopstick right) {
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
        Thread.sleep(1000);
        state = State.HUNGRY;
    }

    private void eat() throws InterruptedException {
        state = State.EATING;
        while (!hasChopsticks()) {
            getChopsticks();
            Thread.sleep(100);
        }
        Thread.sleep(1000);
        left.release(this);
        right.release(this);
        state = State.THINKING;
    }

    private enum State {
        THINKING,
        HUNGRY,
        EATING
    }

    private synchronized void getChopsticks() throws InterruptedException {
        if (left.getOwner() == null && right.getOwner() == null) {
            left.acquire(this);
            right.acquire(this);
        }
    }

    private boolean hasChopsticks() {
        return left.getOwner() == this && right.getOwner() == this;
    }
}