package src;

class Philosopher implements Runnable {
    public Philosopher() {
        System.out.println("Philosopher");
    }

    public void run() {
        System.out.println("I'm a philosopher");
    }
}