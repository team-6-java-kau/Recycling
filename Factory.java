
/* 
import java.util.List;

public class Factory {
    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, Employee distributionEmployee) {
        for (Recyclableitem recyclable : recyclables) {
            boolean success = sortingEmployee.sort(recyclable);
            if (!success) {
                sortingEmployee.incrementerrorsNum();
            }
            distributionEmployee.distributeItem(recyclable);
            
        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}*/
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Factory {
<<<<<<< Updated upstream
    private static final int MAX_BUFFER_SIZE = 3; // Maximum number of items that can wait between sorting and distribution

    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, Employee distributionEmployee) {
        Queue<Recyclableitem> buffer = new LinkedList<>();

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
=======
    private GUIMain gui;
    private double Timescale;
    private Queue<Recyclableitem> buffer;
    private DistributionEmployee distributionEmployee;

    public Factory(GUIMain gui, double Timescale) {
        this.gui = gui;
        this.Timescale = Timescale;
        this.buffer = new LinkedList<>();
        this.distributionEmployee = new DistributionEmployee();
    }

    public void setTimescale(double Timescale) {
        this.Timescale = Timescale;
    }

    public void processItems(List<Recyclableitem> items) {
        for (Recyclableitem item : items) {
            // Update sorting status
            gui.updateSortingStatus(item, true);

            // Simulate the sorting delay
            try {
                Thread.sleep((long) (item.get_time_to_sort() * Timescale));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            item.setisDone_sorting(true);
            gui.updateSortingStatus(item, false);

            // Add item to buffer for distribution
            buffer.add(item);
>>>>>>> Stashed changes
        }

        // Distribute items from the buffer
        distributeItems();
    }

    private void distributeItems() {
        while (!buffer.isEmpty()) {
            distributionEmployee.distributeItem(buffer.poll());
        }
    }

    public void manual(List<Recyclableitem> recyclables, Employee sorter, Employee distributor) {
        // Implement manual processing logic here...
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }

    private class DistributionEmployee {
        public void distributeItem(Recyclableitem item) {
            // Logic to distribute item
            System.out.println("Distributing item: " + item.getItemType());
        }
    }
}