import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class Factory {
    private static final int MAX_BUFFER_SIZE = 3; // Maximum number of items that can wait between sorting and distribution
    private int Timescale;
    private GUIMain gui;

    public Factory(GUIMain gui) {
        this.gui = gui;
    }

    // New constructor
    public Factory(GUIMain gui, int timescale) {
        this.gui = gui;
        this.Timescale = timescale;
    }

    public Integer getTimescale() { return Timescale; }
    public void setTimescale(Integer Timescale) { this.Timescale = Timescale; }

    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, Employee distributionEmployee) {
        Queue<Recyclableitem> buffer = new LinkedList<>();

        for (Recyclableitem recyclable : recyclables) {
            boolean success = sortingEmployee.sort(recyclable);
            gui.updateSortingStatus(recyclable, success);
            if (!success) {
                sortingEmployee.incrementerrorsNum();
            }

            // Simulate the sorting delay
            try {
                Thread.sleep((long) (recyclable.get_time_to_sort() * Timescale));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            recyclable.setisDone_sorting(true);
            buffer.add(recyclable);

            // Wait if the buffer is full
            while (buffer.size() >= MAX_BUFFER_SIZE) {
                Recyclableitem item = buffer.poll();
                distributionEmployee.distributeItem(item);
                gui.updateDistributionStatus(item);

                // Simulate the distribution delay
                try {
                    Thread.sleep((long) (item.get_time_to_distribute() * Timescale));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                item.setisdone_distribute(true);
            }
        }

        // Distribute remaining items in the buffer
        while (!buffer.isEmpty()) {
            Recyclableitem item = buffer.poll();
            distributionEmployee.distributeItem(item);
            gui.updateDistributionStatus(item);

            // Simulate the distribution delay
            try {
                Thread.sleep((long) (item.get_time_to_distribute() * Timescale));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            item.setisdone_distribute(true);
        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}
