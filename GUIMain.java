import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class GUIMain {
    private JFrame frame;
    private JTextArea resultArea;
    private Employee sortingEmployee;
    private ItemPanel itemPanel;
    private Timer itemGenerationTimer;
    private List<MovingItem> movingItems; // List to hold multiple moving items
    private String[] itemTypes = {"Plastic", "Metal", "Glass", "Paper"}; // Available item types
    private Color[] itemColors = {Color.BLUE, Color.GRAY, Color.GREEN, Color.YELLOW}; // Colors for each material
    private int laneWidth = 150; // Width of each lane
    private int laneHeight = 120; // Height of each lane
    private int[] sortedCounts = new int[itemTypes.length]; // Count of sorted items per type

    public GUIMain() {
        // Initialize employee
        sortingEmployee = new Employee(1, 0.0, "Sorting Employee", 5);
        movingItems = new ArrayList<>(); // Initialize the list of moving items

        // Create the main frame with larger dimensions
        frame = new JFrame("Recycling Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500); // Increased size of the frame
        frame.setLayout(new FlowLayout());

        // Text area to display results
        resultArea = new JTextArea(10, 40); // Increased width of text area
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Item panel for drawing with larger size
        itemPanel = new ItemPanel();
        itemPanel.setPreferredSize(new Dimension(600, 350)); // Increased size of item panel

        // Start the item generation timer
        itemGenerationTimer = new Timer(2000, e -> generateNewItem());
        itemGenerationTimer.start(); // Start generating items every 2 seconds

        // Add components to the frame
        frame.add(scrollPane);
        frame.add(itemPanel);
        frame.setVisible(true);
    }

    private void generateNewItem() {
        // Randomly select an item type
        int randomIndex = new Random().nextInt(itemTypes.length);
        Color itemColor = itemColors[randomIndex]; // Get corresponding color
        double itemWeight = new Random().nextDouble() * 2; // Random weight between 0 and 2

        // Create a new moving item and add it to the list
        MovingItem item = new MovingItem(randomIndex, itemColor, itemWeight);
        movingItems.add(item); // Add the new item to the list
        item.startMovement(itemPanel, this::itemSorted); // Start moving the item
    }

    private void itemSorted(MovingItem item) {
        int materialIndex = item.getMaterialIndex();
        sortedCounts[materialIndex]++; // Increment sorted count for the material
        boolean sortedSuccessfully = sortingEmployee.sort(item.getRecyclableItem()); // Sort the item
        if (sortedSuccessfully) {
            resultArea.append(item.getItemType() + " sorted successfully.\n");
        } else {
            resultArea.append(item.getItemType() + " sorting failed.\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIMain::new);
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
        private double weight;
        private int itemX; // X position of the item
        private int itemY; // Y position of the item
        private Timer movementTimer;

        public MovingItem(int materialIndex, Color color, double weight) {
            this.materialIndex = materialIndex;
            this.color = color;
            this.weight = weight;
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
            // Create and return the appropriate Recyclableitem based on the material index
            double weight = this.weight; // Use the weight assigned to this item
            switch (materialIndex) {
                case 0: return new Plastic(weight);
                case 1: return new Metal(weight);
                case 2: return new Glass(weight);
                case 3: return new Paper(weight);
                default: return null; // Should not happen
            }
        }
    }
}

// Assume Recyclableitem, Plastic, Metal, Glass, and Paper classes exist with the appropriate methods.