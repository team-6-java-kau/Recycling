import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Factory {
    private static final int MAX_BUFFER_SIZE = 3; // Maximum number of items that can wait between sorting and distribution
    private Queue<Recyclableitem> buffer = new LinkedList<>();

    public void setTimescale(int timescale) {
        // Method removed as timescale is no longer used
    }

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
        for (Recyclableitem recyclable : recyclables) {
            boolean sorted = automatedSort(recyclable);
            if (!sorted) {
                System.out.println("Error: Failed to sort item " + recyclable.getItemType());
                continue; // Skip to the next item if sorting fails
            }
            buffer.add(recyclable);

            // Ensure buffer size
            if (buffer.size() > MAX_BUFFER_SIZE) {
                Recyclableitem item = buffer.poll();
                automatedDistribute(item);
            }
        }

        // Distribute remaining items in the buffer
        while (!buffer.isEmpty()) {
            automatedDistribute(buffer.poll());
        }
    }

    // Simulated automated sorting method
    private boolean automatedSort(Recyclableitem item) {
        // Logic for automated sorting (e.g., based on item properties)
        // Assume a basic check for simplicity
        return true; // Simplified for example
    }

    // Simulated automated distribution method
    private void automatedDistribute(Recyclableitem item) {
        // Logic for automated distribution (e.g., moving item to a specific location)
        System.out.println("Item " + item.getItemType() + " distributed successfully.");
    }

    public void sensorBased(List<Recyclableitem> items, Sorter sorter, Distributor distributor) {
        for (Recyclableitem item : items) {
            sorter.sort(item);
            if (!item.getsortingError()) {
                distributor.distributeItem(item);
            }
        }
    }
}