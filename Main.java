import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Recyclableitem> items = Recyclableitem.createList(30);
        Employee muhammed = new Employee(1, 100.0, "moha", 1);
        Factory one =new Factory();
        
        /*if (items != null && !items.isEmpty()) {
            System.out.println("List created successfully with " + items.size() + " items.");
            for (Recyclableitem item : items) {
                System.out.println(item.getItemType() + " - " + item.getSize() + " - " + item.getCondition() + " - " + item.getItemWeight());            }
        } else {
            System.out.println("Failed to create list.");
        }*/
        one.manual(items, muhammed);
        System.out.println(muhammed.tiredness);
        System.out.println(muhammed.errorsNum);

    }
}
