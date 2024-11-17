import javax.swing.*;
import java.awt.*;
<<<<<<< Updated upstream
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
=======
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMain {
    private JFrame frame;
    private JTextField experienceField;
    private JTextField timescaleField;
    private JTextArea outputArea;
    private RailPanel railPanel;
    private List<MovingObject> movingObjects;
    private Timer timer;

    public GUIMain() {
        frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Experience:"));
        experienceField = new JTextField();
        inputPanel.add(experienceField);

        inputPanel.add(new JLabel("Timescale:"));
        timescaleField = new JTextField();
        inputPanel.add(timescaleField);

        JButton startButton = new JButton("Start");
        inputPanel.add(startButton);

        startButton.addActionListener(e -> {
            System.out.println("Start button clicked");
            if (experienceField.getText().trim().isEmpty() || 
                timescaleField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter both Experience and Timescale values",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            startSimulation();
        });

        railPanel = new RailPanel();
        frame.add(railPanel, BorderLayout.CENTER);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(200, frame.getHeight()));
        frame.add(scrollPane, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);
>>>>>>> Stashed changes
        frame.setVisible(true);

        movingObjects = new ArrayList<>();
    }

    private void startSimulation() {
        // Add logic to start the simulation
        // For example, create MovingObject instances and add them to the movingObjects list
        Recyclableitem item1 = new Metal(5.0);
        Recyclableitem item2 = new Plastic(10.0);
        Recyclableitem item3 = new Glass(7.0);
        Recyclableitem item4 = new Paper(3.0);
        movingObjects.add(new MovingObject(item1, 0));
        movingObjects.add(new MovingObject(item2, 100));
        movingObjects.add(new MovingObject(item3, 200));
        movingObjects.add(new MovingObject(item4, 300));
        railPanel.setMovingObjects(movingObjects);
        railPanel.repaint();

        // Start the timer to move objects
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                railPanel.repaint();
            }
        });
        timer.start();
    }

    public void updateSortingStatus(Recyclableitem item, boolean status) {
        // Add logic to update sorting status
        System.out.println("Sorting status updated for: " + item.getItemType() + " to " + status);
        item.setisDone_sorting(status);
    }

    public void updateDistributionStatus(Recyclableitem item) {
        // Add logic to update distribution status
        System.out.println("Distribution status updated for: " + item.getItemType());
        item.setisdone_distribute(true);
    }

<<<<<<< Updated upstream
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
=======
    private class MovingObject {
        int x, y;
        Color color;
        Recyclableitem item;
        boolean sorted;

        MovingObject(Recyclableitem item, int startX) {
            this.item = item;
            this.x = startX; // Starting X position with delay
            this.y = 0; // Y position will be set based on the middle line
            this.color = getColorForType(item.getItemType());
            this.sorted = false;
        }

        private Color getColorForType(String itemType) {
            switch (itemType) {
                case "Metal":
                    return Color.GRAY;
                case "Plastic":
                    return Color.BLUE;
                case "Glass":
                    return Color.GREEN;
                case "Paper":
                    return Color.YELLOW;
                default:
                    return Color.BLACK;
            }
        }

        public void draw(Graphics g, int middleY, int mainBeltEnd) {
            if (!sorted) {
                this.y = middleY; // Set the Y position based on the middle line
                g.setColor(color);
                g.fillOval(x, y - 10, 20, 20); // Draw the object centered on the middle line
                // Update the position for the next frame
                x += 5; // Move right
                if (x > mainBeltEnd) { // Use mainBeltEnd instead of getWidth()
                    // Redirect to the respective lane
                    sorted = true;
                    switch (item.getItemType()) {
                        case "Metal":
                            y = middleY - 60;
                            break;
                        case "Plastic":
                            y = middleY - 30;
                            break;
                        case "Glass":
                            y = middleY + 30;
                            break;
                        case "Paper":
                            y = middleY + 60;
                            break;
                    }
                    x = mainBeltEnd; // Move to the start of the lane
                }
            } else {
                g.setColor(color);
                g.fillOval(x, y - 10, 20, 20); // Draw the object in its lane
                // Update the position for the next frame
                x += 5; // Move right
                if (x > mainBeltEnd + 100) { // Use mainBeltEnd + 100 for the lanes
                    x = mainBeltEnd; // Reset to start of the lane
                }
            }
            // Use the item field to display item information (optional)
            g.setColor(Color.BLACK);
            g.drawString(item.getItemType(), x, y - 20);
        }
    }

    private class RailPanel extends JPanel {
        private List<MovingObject> movingObjects;

        RailPanel() {
            this.movingObjects = new ArrayList<>();
        }

        void setMovingObjects(List<MovingObject> movingObjects) {
            this.movingObjects = movingObjects;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int middleY = getHeight() / 2;
            int mainBeltEnd = getWidth() - 150; // Shorten the main conveyor belt
            // Draw the main conveyor belt
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, middleY - 5, mainBeltEnd, 10);
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < mainBeltEnd; i += 20) {
                g.fillRect(i, middleY - 5, 10, 10);
            }
            // Draw the additional lanes
            g.setColor(Color.DARK_GRAY);
            g.fillRect(mainBeltEnd, middleY - 65, 150, 10); // Metal lane
            g.fillRect(mainBeltEnd, middleY - 35, 150, 10); // Plastic lane
            g.fillRect(mainBeltEnd, middleY + 25, 150, 10); // Glass lane
            g.fillRect(mainBeltEnd, middleY + 55, 150, 10); // Paper lane
            g.setColor(Color.LIGHT_GRAY);
            for (int i = mainBeltEnd; i < mainBeltEnd + 150; i += 20) {
                g.fillRect(i, middleY - 65, 10, 10); // Metal lane
                g.fillRect(i, middleY - 35, 10, 10); // Plastic lane
                g.fillRect(i, middleY + 25, 10, 10); // Glass lane
                g.fillRect(i, middleY + 55, 10, 10); // Paper lane
            }
            // Draw the moving objects
            for (MovingObject obj : movingObjects) {
                obj.draw(g, middleY, mainBeltEnd);
            }
>>>>>>> Stashed changes
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIMain::new);
    }
<<<<<<< Updated upstream

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
=======
}
>>>>>>> Stashed changes
