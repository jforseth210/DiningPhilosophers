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

            System.out.print("\033[H\033[2J");
            System.out.flush();
            
            // Check adjacent philosophers
            for (int i = 0; i < philosophers.length; i++) {
                Philosopher current = philosophers[i];
                Philosopher next = philosophers[(i + 1) % philosophers.length];
                if (current.getState() == Philosopher.State.EATING && next.getState() == Philosopher.State.EATING) {
                    throw new IllegalStateException("Adjacent philosophers " +
                            current.getName() + " and " + next.getName() + " are eating simultaneously!");
                }
            }
            System.out.println("+-----------------+-----------+");
            System.out.println("| Philosopher     | State     |");
            System.out.println("+-----------------+-----------+");

            for (Philosopher philosopher : philosophers) {
                System.out.printf("| %-15s | %-9s |%n",
                        philosopher.getName(),
                        philosopher.getState());
            }

            System.out.println("+-----------------+-----------+");
            Thread.sleep(100);
        }

    }
}
