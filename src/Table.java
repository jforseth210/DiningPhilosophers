package src;

public class Table {
    private Chopstick[] chopsticks = new Chopstick[5];
    private String[] names = { "Charlie", "Justin", "Jack", "Abby", "Andrew" };

    public static void main(String[] args) {
        Table table = new Table();
        table.setTable();
    }

    public void setTable() {
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < chopsticks.length; i++) {
            Philosopher philosopher = new Philosopher(names[i], chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            new Thread(philosopher).start();
        }
    }
}
