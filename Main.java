import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialize the GUI
        GUIMain gui = new GUIMain();

        // Get user input for experience and timescale
        Scanner input = new Scanner(System.in);
        System.out.print("Enter experience: ");
        Integer experience_input = input.nextInt();
        System.out.print("Enter timescale: ");
        Integer timescale = input.nextInt();
        input.close();

        // Create items
        List<Recyclableitem> items = Recyclableitem.createList(10);

        // Create employees
        Employee sorter = new Employee(1, 5.0, "Moha", experience_input);
        Employee distributor = new Employee(2, 5.0, "Spotty", 3);

        // Start the factory process
        Factory factory = new Factory(gui);
        factory.setTimescale(timescale);
        new Thread(() -> factory.manual(items, sorter, distributor)).start();

        // Print errors encountered
        System.out.println("Errors encountered: " + sorter.errorsNum);
    }
}
