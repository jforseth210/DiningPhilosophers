package src;

public class Table {
    public static void main(String[] args) {
        Philosopher p = new Philosopher();
        Thread t = new Thread(p);
        t.start();
    }
}
