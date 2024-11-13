import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.Queue;
import java.util.LinkedList;

public class GUIMain {
    private JFrame frame;
    private JTextArea resultArea;
    private Employee sortingEmployee;
    private Employee distributionEmployee;
    private ItemPanel itemPanel;
    private Timer itemGenerationTimer;
    private List<MovingItem> movingItems;
    private String[] itemTypes = {"Plastic", "Metal", "Glass", "Paper"};
    private Color[] itemColors = {Color.BLUE, Color.GRAY, Color.GREEN, Color.YELLOW};
    private int laneWidth = 150;
    private int laneHeight = 120;
    private int[] sortedCounts = new int[itemTypes.length];
    private Queue<Recyclableitem> buffer;
    private Factory factory;
    private static final int MAX_BUFFER_SIZE = 3;
    private int timeScale = 1000; // Default timescale (1 second = 1000ms)
    private int totalMaterials; // Add this field

    public GUIMain() {
        // Prompt for number of materials at startup
        String input = JOptionPane.showInputDialog(null, 
            "Enter the number of materials to process:", 
            "Number of Materials", 
            JOptionPane.QUESTION_MESSAGE);
        
        try {
            totalMaterials = Integer.parseInt(input);
            if (totalMaterials <= 0) {
                JOptionPane.showMessageDialog(null, "Invalid number. Using default value of 10.");
                totalMaterials = 10;
            }
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Invalid input. Using default value of 10.");
            totalMaterials = 10;
        }

        // Initialize components
        sortingEmployee = new Employee(1, 0.0, "Sorter", 5);
        distributionEmployee = new Employee(2, 0.0, "Distributor", 3);
        movingItems = new ArrayList<>();
        buffer = new LinkedList<>();
        factory = new Factory();
        factory.setTimescale(1); // Set default timescale

        // Create GUI components
        frame = new JFrame("Recycling Simulator (" + totalMaterials + " items remaining)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Control panel
        JPanel controlPanel = new JPanel();
        JButton startButton = new JButton("Start");
        JTextField timeScaleField = new JTextField("1", 5);
        controlPanel.add(new JLabel("Time Scale:"));
        controlPanel.add(timeScaleField);
        controlPanel.add(startButton);

        // Main display components
        resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        itemPanel = new ItemPanel();
        itemPanel.setPreferredSize(new Dimension(600, 350));

        // Layout
        frame.add(controlPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.EAST);
        frame.add(itemPanel, BorderLayout.CENTER);

        startButton.addActionListener(e -> {
            try {
                int newTimeScale = Integer.parseInt(timeScaleField.getText());
                factory.setTimescale(newTimeScale);
                timeScale = newTimeScale * 1000;
                if (itemGenerationTimer == null || !itemGenerationTimer.isRunning()) {
                    itemGenerationTimer = new Timer(2000, ev -> {
                        if (totalMaterials > 0) {
                            generateNewItem();
                            totalMaterials--;
                            frame.setTitle("Recycling Simulator (" + totalMaterials + " items remaining)");
                            if (totalMaterials == 0) {
                                itemGenerationTimer.stop();
                                JOptionPane.showMessageDialog(frame, "All materials processed!");
                            }
                        }
                    });
                    itemGenerationTimer.start();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for time scale");
            }
        });

        frame.setVisible(true);
    }

    private void generateNewItem() {
        int randomIndex = new Random().nextInt(itemTypes.length);
        double itemWeight = 0.1 + (2.0 - 0.1) * new Random().nextDouble();
        
        Recyclableitem recyclable;
        switch (randomIndex) {
            case 0: recyclable = new Plastic(itemWeight); break;
            case 1: recyclable = new Metal(itemWeight); break;
            case 2: recyclable = new Glass(itemWeight); break;
            default: recyclable = new Paper(itemWeight); break;
        }

        MovingItem movingItem = new MovingItem(randomIndex, itemColors[randomIndex], recyclable);
        movingItems.add(movingItem);
        movingItem.startMovement(itemPanel, this::processItem);
    }

    private void processItem(MovingItem item) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                Recyclableitem recyclable = item.getRecyclableItem();
                
                // Sorting
                boolean sortSuccess = sortingEmployee.sort(recyclable);
                publish("Sorting " + recyclable.getItemType() + 
                       " - Time: " + recyclable.get_time_to_sort() + "s" +
                       (sortSuccess ? " - Success" : " - Failed"));
                
                Thread.sleep((long)(recyclable.get_time_to_sort() * timeScale));

                // Distribution
                buffer.add(recyclable);
                if (buffer.size() >= MAX_BUFFER_SIZE) {
                    Recyclableitem toDistribute = buffer.poll();
                    distributionEmployee.distributeItem(toDistribute);
                    publish("Distributing " + toDistribute.getItemType() + 
                           " - Time: " + toDistribute.get_time_to_distribute() + "s");
                    Thread.sleep((long)(toDistribute.get_time_to_distribute() * timeScale));
                }

                sortedCounts[item.getMaterialIndex()]++;
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                for (String message : chunks) {
                    resultArea.append(message + "\n");
                }
                itemPanel.repaint();
            }
        };
        worker.execute();
    }

    // Inner class for custom drawing
    class ItemPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Draw the main lane
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(0, 100, 600, 40); // Main lane

            // Draw lanes with visible paths
            for (int i = 0; i < itemTypes.length; i++) {
                // Draw lane background
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(i * laneWidth, 140, laneWidth, laneHeight); // Draw each lane

                // Draw lane border
                g.setColor(Color.BLACK);
                g.drawRect(i * laneWidth, 140, laneWidth, laneHeight); // Draw border around each lane

                // Draw lane labels and counts
                g.setColor(Color.BLACK);
                g.drawString(itemTypes[i], i * laneWidth + 10, 135); // Label each lane
                g.drawString("Count: " + sortedCounts[i], i * laneWidth + 10, 270); // Display sorted count
            }

            // Draw all moving items
            for (MovingItem item : movingItems) {
                item.draw(g); // Draw each item
            }
        }
    }

    // Class to represent each moving item
    class MovingItem {
        private int materialIndex;
        private Color color;
        private Recyclableitem recyclable;
        private int itemX; // X position of the item
        private int itemY; // Y position of the item
        private Timer movementTimer;

        public MovingItem(int materialIndex, Color color, Recyclableitem recyclable) {
            this.materialIndex = materialIndex;
            this.color = color;
            this.recyclable = recyclable;
            this.itemX = 0; // Start at the beginning of the main lane
            this.itemY = 0; // Initialize itemY as 0, will be set during lane branching
        }

        public void startMovement(ItemPanel panel, Consumer<MovingItem> sortingCompleteListener) {
            movementTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemX < 600) { // Allow full lane length
                        itemX += 2; // Move 2 pixels to the right
                        panel.repaint(); // Repaint the panel
                    } else {
                        movementTimer.stop(); // Stop the main lane movement
                        branchToLane(panel, sortingCompleteListener); // Branch to the correct lane
                    }
                }
            });
            movementTimer.start(); // Start the timer for item movement
        }

        private void branchToLane(ItemPanel panel, Consumer<MovingItem> sortingCompleteListener) {
            itemY = 140 + (materialIndex * laneHeight); // Set Y position to the corresponding lane
            Timer laneTimer = new Timer(50, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (itemX < laneWidth) { // Move item into the lane
                        itemX += 2; // Move 2 pixels to the right
                        panel.repaint(); // Repaint the panel
                    } else {
                        ((Timer) e.getSource()).stop(); // Stop the lane movement timer
                        sortingCompleteListener.accept(MovingItem.this); // Notify sorting completion
                    }
                }
            });
            laneTimer.start(); // Start the timer for lane movement
        }

        public void draw(Graphics g) {
            g.setColor(color); // Set the item's color
            g.fillRect(itemX, itemY + 100, 20, 20); // Draw the item as a rectangle along the main path
        }

        public int getMaterialIndex() {
            return materialIndex; // Return the index for sorting
        }

        public String getItemType() {
            return itemTypes[materialIndex]; // Return the item type as a string
        }

        public Recyclableitem getRecyclableItem() {
            return recyclable;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIMain::new);
    }
}