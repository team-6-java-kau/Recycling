import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Recyclableitem> items = Recyclableitem.createList(30);

        if (items != null && !items.isEmpty()) {
            System.out.println("List created successfully with " + items.size() + " items.");
            for (Recyclableitem item : items) {
                System.out.println(item.getItemType());
            }
        } else {
            System.out.println("Failed to create list.");
        }
    }
}
