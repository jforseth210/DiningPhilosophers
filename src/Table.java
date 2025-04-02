package src;

public class Table {
    private Chopstick[] chopsticks = new Chopstick[5];
    private Philosopher[] philosophers = new Philosopher[5];
    private String[] names = { "Charlie", "Justin", "Jack", "Abby", "Andrew" };

    public static void main(String[] args) {
        Table table = new Table();
        try {
            table.setTable();
        } catch (InterruptedException e) {
            System.out.println("Exiting");
        }
    }

    public void setTable() throws InterruptedException {
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick();
        }
        for (int i = 0; i < chopsticks.length; i++) {
            philosophers[i] = new Philosopher(names[i], chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            new Thread(philosophers[i]).start();
        }
        while (true) {
            System.out.println(
                    philosophers[0].getName() + " " + philosophers[0].getState() + " " +
                            philosophers[1].getName() + ":" + philosophers[1].getState() + " " +
                            philosophers[2].getName() + ":" + philosophers[2].getState() + " " +
                            philosophers[3].getName() + ":" + philosophers[3].getState() + " " +
                            philosophers[4].getName() + ":" + philosophers[4].getState()

            );
            Thread.sleep(100);
        }
    }
}
