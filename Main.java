import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
<<<<<<< Updated upstream
        List<Recyclableitem> items = Recyclableitem.createList(30);
        Scanner input = new Scanner(System.in);
        Integer experience_input = input.nextInt();
        input.close();

        Employee muhammed = new Employee(1, 5.0, "Moha", experience_input);
        Employee distributor = new Employee(2, 5.0, "Sara", 3); // Create a distribution employee

        Factory one = new Factory();
        one.manual(items, muhammed, distributor); // Pass both employees

        if (items != null && !items.isEmpty()) {
            System.out.println("List created successfully with " + items.size() + " items.");
            for (Recyclableitem item : items) {
                System.out.println(item.getItemType() + " - " +  item.getsortingError() + " - " + item.get_time_to_sort() + " - " + item.getItemWeight());
            }
        } else {
            System.out.println("Failed to create list.");
        }

        System.out.println("Errors encountered: " + muhammed.errorsNum);
    }
=======
        GUIMain gui = new GUIMain();
        double timescale = 1.0; // Example timescale value
        Factory factory = new Factory(gui, timescale);

        // Example usage
        List<Recyclableitem> items = new ArrayList<>();
        items.add(new Metal(5.0));
        items.add(new Metal(10.0));

        factory.setTimescale(2.0); // Set timescale
        factory.processItems(items); // Process items

        // Example manual processing
        Employee sorter = new Employee("Sorter");
        Employee distributor = new Employee("Distributor");
        factory.manual(items, sorter, distributor);
    }
}

class Employee {
    private String role;

    public Employee(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
>>>>>>> Stashed changes
}