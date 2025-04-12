package src;

public class Table {

    // Setting up the Philosophers and Chopsticks (5 of each)
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

    /**
     * Starts the simulation.
     * @throws InterruptedException
     */
    public void setTable() throws InterruptedException {
        
        // Initialize chopsticks.
        for (int i = 0; i < chopsticks.length; i++) {
            chopsticks[i] = new Chopstick(i);
        }

        // Initialize philosophers, and start their threads.
        for (int i = 0; i < chopsticks.length; i++) {
            philosophers[i] = new Philosopher(names[i], chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            new Thread(philosophers[i]).start();
        }

        // Main run loop.
        while (true) {

            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Check adjacent philosophers and throw an exception if they are both eating (Chopsticks being owned by 2 philosophers simultaneously).
            for (int i = 0; i < philosophers.length; i++) {
                Philosopher current = philosophers[i];
                Philosopher next = philosophers[(i + 1) % philosophers.length];
                if (current.getState() == Philosopher.State.EATING && next.getState() == Philosopher.State.EATING) {
                    throw new IllegalStateException("Adjacent philosophers " +
                            current.getName() + " and " + next.getName() + " are eating simultaneously!");
                }
            }

            System.out.println("+-----------------+-----------+---------------+");
            System.out.println("| Philosopher     | State     | Wait Time (ms)|");
            System.out.println("+-----------------+-----------+---------------+");

            // Get philosopher states
            for (Philosopher philosopher : philosophers) {
                System.out.printf("| %-15s | %-9s | %-13d |%n",
                        philosopher.getName(),
                        philosopher.getState(),
                        philosopher.getTotalWaitTime());
            }

            System.out.println("+-----------------+-----------+---------------+");

            System.out.println("+-----------------+-----------+");

            // Add chopstick table
            System.out.println("+------------+-----------------+");
            System.out.println("| Chopstick  | Owned By        |");
            System.out.println("+------------+-----------------+");

            // Get chopstick states
            for (Chopstick chopstick : chopsticks) {
                String owner = chopstick.getOwner() != null ? chopstick.getOwner().getName() : "None";
                System.out.printf("| %-10d | %-15s |%n", chopstick.getId(), owner);
            }

            System.out.println("+------------+-----------------+");

            Thread.sleep(100);
        }

    }
}
