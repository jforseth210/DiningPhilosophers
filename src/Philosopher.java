package src;

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
        Thread.sleep(1000);
        state = State.HUNGRY;
        System.out.println(name + " is hungry");
    }

    private void eat() throws InterruptedException {
        System.out.println(name + " is eating");
        state = State.EATING;
        while (!hasChopsticks()) {
            System.out.println(name + " is trying to get chopsticks");
            getChopsticks();
            Thread.sleep(100);
        }
        System.out.println(name + " is eating");
        Thread.sleep((long) Math.random() * 1000);
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