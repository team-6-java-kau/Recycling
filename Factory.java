

import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class Factory {
    private static final int MAX_BUFFER_SIZE = 3; // Maximum number of items that can wait between sorting and distribution
    private int Timescale;

    public Integer getTimescale() { return Timescale; }
    public void setTimescale(Integer Timescale) { this.Timescale = Timescale; }

    public void manual(List<Recyclableitem> recyclables, Employee sortingEmployee, Employee distributionEmployee) {
        Queue<Recyclableitem> buffer = new LinkedList<>();

        for (Recyclableitem recyclable : recyclables) {
            boolean success = sortingEmployee.sort(recyclable);
            System.out.println(recyclable.getItemType() + " - " + recyclable.getsortingError() + " - " + recyclable.get_time_to_sort() + " - " + recyclable.isdone_distribute());
            if (!success) {
                sortingEmployee.incrementerrorsNum();
            }
            buffer.add(recyclable);

            // Simulate the sorting delay
            try {
                Thread.sleep((long) (recyclable.get_time_to_sort() * Timescale));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Wait if the buffer is full
            while (buffer.size() >= MAX_BUFFER_SIZE) {
                Recyclableitem item = buffer.poll();
                distributionEmployee.distributeItem(item);
                try {
                    Thread.sleep((long) (item.get_time_to_distribute() * Timescale));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }    
             

            }
        }

        // Distribute remaining items in the buffer
        while (!buffer.isEmpty()) {
            Recyclableitem item = buffer.poll();
            distributionEmployee.distributeItem(item);

            // Simulate the distribution delay
            try {
                Thread.sleep((long) (item.get_time_to_distribute() * Timescale));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void automation(List<Recyclableitem> recyclables) {
        // Implement automation logic here...
    }
}
