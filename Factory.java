import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Factory {
    private static final int MAX_BUFFER_SIZE = 3; // Maximum number of items that can wait between sorting and distribution
    private Queue<Recyclableitem> buffer = new LinkedList<>();

    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, Employee distributionEmployee) {
        for (Recyclableitem recyclable : recyclables) {
            boolean success = sortingEmployee.sort(recyclable);
            if (!success) {
                sortingEmployee.incrementerrorsNum();
            }
            buffer.add(recyclable);

            // Ensure buffer size
            if (buffer.size() > MAX_BUFFER_SIZE) {
                Recyclableitem item = buffer.poll();
                distributionEmployee.distributeItem(item);
            }
        }

        // Distribute items from the buffer
        distributeItems(distributionEmployee);
    }

    private void distributeItems(Employee distributionEmployee) {
        while (!buffer.isEmpty()) {
            distributionEmployee.distributeItem(buffer.poll());
        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}