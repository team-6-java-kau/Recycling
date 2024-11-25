import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUIMain {
    private JFrame frame;
    private JTextField experienceField;
    private JTextArea outputArea;
    private RailPanel railPanel;
    private List<MovingObject> movingObjects;
    private Timer clockTimer;
    private int timeMultiplier = 1;
    private int metalCount = 0;
    private int plasticCount = 0;
    private int glassCount = 0;
    private int paperCount = 0;
    private int sorterCount = 0;
    private int distributorCount = 0;
    private Image backgroundImage;
    private Image sorterImage;
    private Image distributorImage;
    private Image plasticImage;
    private Image metalImage;
    private Image glassImage;
    private Image paperImage;
    private Image errorImage;
    private boolean isSorting = false;
    private JLabel timeLabel;
    private long startTime;
    private long elapsedTimeBefore = 0;
    private JTextArea sortingLogArea;
    private JTextArea distributingLogArea;

    public GUIMain() {
        frame = new JFrame("Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1300, 900); // Set to HD resolution
        frame.setResizable(false); // Make the window fixed size
        frame.getContentPane().setBackground(Color.decode("#5e5e5e"));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Experience:"));
        experienceField = new JTextField();
        inputPanel.add(experienceField);

        timeLabel = new JLabel("Time: 00:00:00");
        inputPanel.add(timeLabel);

        JButton speedUp2xButton = new JButton("x2");
        JButton speedUp4xButton = new JButton("x4");
        JButton normalSpeedButton = new JButton("Normal");

        inputPanel.add(speedUp2xButton);
        inputPanel.add(speedUp4xButton);
        inputPanel.add(normalSpeedButton);

        speedUp2xButton.addActionListener(e -> setTimeMultiplier(2));
        speedUp4xButton.addActionListener(e -> setTimeMultiplier(4));
        normalSpeedButton.addActionListener(e -> setTimeMultiplier(1));

        JButton startButton = new JButton("Start");
        inputPanel.add(startButton);

        startButton.addActionListener(e -> {
            System.out.println("Start button clicked");
            String experienceText = experienceField.getText().trim();
            if (experienceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter Experience value",
                    "Input Required",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                int experienceInput = Integer.parseInt(experienceText);
                if (experienceInput < 0 || experienceInput > 25) {
                    JOptionPane.showMessageDialog(frame, 
                        "Experience must be between 0 and 25 years",
                        "Invalid Input",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, 
                    "Please enter a valid number for Experience",
                    "Invalid Input",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            startSimulation();
        });

        railPanel = new RailPanel();
        frame.add(railPanel, BorderLayout.CENTER);

        sortingLogArea = new JTextArea();
        sortingLogArea.setEditable(false);
        JScrollPane sortingScrollPane = new JScrollPane(sortingLogArea);
        sortingScrollPane.setPreferredSize(new Dimension(200, frame.getHeight() / 2));

        distributingLogArea = new JTextArea();
        distributingLogArea.setEditable(false);
        JScrollPane distributingScrollPane = new JScrollPane(distributingLogArea);
        distributingScrollPane.setPreferredSize(new Dimension(200, frame.getHeight() / 2));

        JPanel logPanel = new JPanel();
        logPanel.setLayout(new GridLayout(2, 1));
        logPanel.add(sortingScrollPane);
        logPanel.add(distributingScrollPane);

        frame.add(logPanel, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.setVisible(true);

        movingObjects = new ArrayList<>();

        // Load the background image
        backgroundImage = new ImageIcon("packaging-closing-machine.jpg").getImage();
        // Load the sorter and distributor images
        sorterImage = new ImageIcon("sorter.png").getImage();
        distributorImage = new ImageIcon("distbuter.png").getImage();
        // Load the recyclable item images
        plasticImage = new ImageIcon("PLASTIC.png").getImage();
        metalImage = new ImageIcon("METEL.png").getImage();
        glassImage = new ImageIcon("GLASS.png").getImage();
        paperImage = new ImageIcon("PAPER.png").getImage();
        errorImage = new ImageIcon("error.png").getImage();
    }

    private void setTimeMultiplier(int multiplier) {
        long currentTime = System.currentTimeMillis();
        elapsedTimeBefore += (currentTime - startTime) * timeMultiplier;
        startTime = currentTime;
        this.timeMultiplier = multiplier;
    }

    private void startSimulation() {
        List<Recyclableitem> items = Recyclableitem.createList(30);
        int experienceInput = Integer.parseInt(experienceField.getText().trim());
        Employee sorter = new Employee(1, 5.0, "Moha", experienceInput);
        Employee distributor = new Employee(2, 5.0, "Sara", 3); 
        railPanel.setMovingObjects(movingObjects);
        railPanel.repaint();

        new Thread(() -> {
            for (Recyclableitem item : items) {
                movingObjects.add(new MovingObject(item, -50, sorter, distributor)); // Pass employees
                railPanel.repaint();
                try {
                    Thread.sleep(1000); // Wait for 1 second before adding the next item
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        startTime = System.currentTimeMillis();
        clockTimer = new Timer(100 / timeMultiplier, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                int hours = (int) (elapsedTime / 3600000);
                int minutes = (int) (elapsedTime / 60000) % 60;
                int seconds = (int) (elapsedTime / 1000) % 60;
                timeLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
                railPanel.repaint(); // Repaint the rail panel to move objects
            }
        });
        clockTimer.start();
    }
    
   

    private class MovingObject {
        int x, y;
        Image image;
        Recyclableitem item;
        boolean sorted;
        boolean distributed;
        Employee sorterEmployee;
        Employee distributorEmployee;
        boolean isDistributing = false;

        MovingObject(Recyclableitem item, int startX, Employee sorterEmployee, Employee distributorEmployee) {
            this.item = item;
            this.x = startX; // Starting X position with delay
            this.y = 0; // Y position will be set based on the middle line
            this.image = getImageForType(item.getItemType()); // Set the image based on the item type
            this.sorted = false;
            this.distributed = false;
            this.sorterEmployee = sorterEmployee;
            this.distributorEmployee = distributorEmployee;
        }

        private Image getImageForType(String itemType) {
            switch (itemType) {
                case "Metal":
                    return metalImage;
                case "Plastic":
                    return plasticImage;
                case "Glass":
                    return glassImage;
                case "Paper":
                    return paperImage;
                case "error":
                    return errorImage;
                default:
                    return null;
            }
        }

        public void draw(Graphics g, int middleY, int mainBeltEnd, int sorterX, int distributorX, int[] lanePositions) {
            // Check if the object is at the sorter position and not already sorting
            if (!item.isDone_sorting() && x >= sorterX - 10 && x <= sorterX + 10 && !isSorting) {
                isSorting = true; // Set the sorting flag to true
                new Thread(() -> {
                    try {
                        sorterEmployee.sort(item); // Sort the item
                        Thread.sleep((long) ((item.get_time_to_sort() * 1000) / timeMultiplier)); // Sleep according to the sorting time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    item.setisDone_sorting(true); // Mark the item as sorted
                    sorterEmployee.incrementItemsDone(); // Increment the sorter's item count
                    if (item.getsortingError()){
                        this.image = getImageForType("error"); // Update the image based on sorted type
                    }
                    else{
                        this.image = getImageForType(item.getItemType()); // Update the image based on sorted type
                    }

                    isSorting = false; // Reset the sorting flag
                    sorterCount++; // Increment the sorter count
                    SwingUtilities.invokeLater(() -> {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                        int hours = (int) (elapsedTime / 3600000);
                        int minutes = (int) (elapsedTime / 60000) % 60;
                        int seconds = (int) (elapsedTime / 1000) % 60;
                        sortingLogArea.append("Item sorted\nItem: " + item.getItemType() + "\nTime: " + String.format("%02d:%02d:%02d\n", hours, minutes, seconds));
                    }); // Print sorted message
                }).start();
            }

            // If sorting, stop the object
            if (!item.isDone_sorting() && isSorting) {
                this.y = middleY; // Set the Y position based on the middle line
                g.setColor(Color.WHITE); // Set the color to white
                g.fillOval(x, y - 15, 30, 30); // Draw the object as a white circle
            } else {
                // Move the object after sorting
                if (!item.isDone_sorting()) {
                    this.y = middleY; // Set the Y position based on the middle line
                    g.setColor(Color.WHITE); // Set the color to white
                    g.fillOval(x, y - 15, 30, 30); // Draw the object as a white circle
                } else {
                    g.drawImage(image, x, y - 15, 30, 30, null); // Draw the object centered on the middle line
                    g.setColor(Color.BLACK); // Set the color to black
                    g.drawString(item.getItemType(), x, y - 25); // Display the item type
                }
                x += 5 * timeMultiplier; // Move right (adjusted by timeMultiplier)
            }

            // Check if the object is at the distributor position and not already distributing
            if (item.isDone_sorting() && !item.isdone_distribute() && x >= distributorX - 10 && x <= distributorX + 10 && !isDistributing) {
                isDistributing = true; // Set the distributing flag to true
                new Thread(() -> {
                    try {
                        distributorEmployee.distributeItem(item); // Distribute the item
                        Thread.sleep((long) ((item.get_time_to_distribute() * 1000) / timeMultiplier)); // Sleep according to the distribution time
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    item.setisdone_distribute(true); // Mark the item as distributed
                    distributorEmployee.incrementItemsDone(); // Increment the distributor's item count
                    // Determine the lane based on item type and update position
                    switch (item.getItemType()) {
                        case "Metal":
                            y = middleY - 120; // Set Y position for Metal lane
                            x = mainBeltEnd + 10 + lanePositions[0]; // Set X position for Metal lane
                            lanePositions[0] += 30; // Space out objects in the lane
                            metalCount++; // Increment metal count
                            break;
                        case "Plastic":
                            y = middleY - 80; // Set Y position for Plastic lane
                            x = mainBeltEnd + 10 + lanePositions[1]; // Set X position for Plastic lane
                            lanePositions[1] += 30; // Space out objects in the lane
                            plasticCount++; // Increment plastic count
                            break;
                        case "Glass":
                            y = middleY + 40; // Set Y position for Glass lane
                            x = mainBeltEnd + 10 + lanePositions[2]; // Set X position for Glass lane
                            lanePositions[2] += 30; // Space out objects in the lane
                            glassCount++; // Increment glass count
                            break;
                        case "Paper":
                            y = middleY + 80; // Set Y position for Paper lane
                            x = mainBeltEnd + 10 + lanePositions[3]; // Set X position for Paper lane
                            lanePositions[3] += 30; // Space out objects in the lane
                            paperCount++; // Increment paper count
                            break;
                    }
                    distributed = true; // Mark the object as distributed
                    distributorCount++; // Increment the distributor count
                    isDistributing = false; // Reset the distributing flag
                    SwingUtilities.invokeLater(() -> {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                        int hours = (int) (elapsedTime / 3600000);
                        int minutes = (int) (elapsedTime / 60000) % 60;
                        int seconds = (int) (elapsedTime / 1000) % 60;
                        distributingLogArea.append("Item distributed\nItem: " + item.getItemType() + "\nTime: " + String.format("%02d:%02d:%02d\n", hours, minutes, seconds));
                    }); // Print distributed message
                }).start();
            }

            // If distributing, stop the object
            if (!item.isdone_distribute() && isDistributing) {
                this.y = middleY; // Set the Y position based on the middle line
                this.x = mainBeltEnd;
            } else if (item.isdone_distribute()) {
                x += 5 * timeMultiplier; // Move right in the lane (adjusted by timeMultiplier)
                if (x > mainBeltEnd + 160) { // Use mainBeltEnd + 160 for the lanes
                    x = mainBeltEnd + 150; // Stop at the basket
                    // Make the object disappear after 10 seconds
                    new Thread(() -> {
                        try {
                            Thread.sleep(10000); // Wait for 10 seconds
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        movingObjects.remove(this); // Remove the object from the list
                        SwingUtilities.invokeLater(railPanel::repaint); // Repaint the rail panel
                    }).start();
                }
            }
        }
    }

    private class RailPanel extends JPanel {
        private List<MovingObject> movingObjects;
        private int[] lanePositions;

        RailPanel() {
            this.movingObjects = new ArrayList<>();
            this.lanePositions = new int[4]; // To keep track of positions in each lane
        }

        void setMovingObjects(List<MovingObject> movingObjects) {
            this.movingObjects = movingObjects;
            // Reset lane positions
            for (int i = 0; i < lanePositions.length; i++) {
                lanePositions[i] = 0;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            setBackground(Color.decode("#5e5e5e")); // Set the background color

            // Draw the background image
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

            int middleY = getHeight() / 2;
            int mainBeltEnd = getWidth() - 300; // Shorten the main conveyor belt
            int sorterX = mainBeltEnd / 2; // Position of the sorter in the middle of the main belt
            int distributorX = mainBeltEnd + 10; // Position of the distributor at the end of the main belt

            // Draw the main conveyor belt
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, middleY - 10, mainBeltEnd, 20); // Make the main belt thicker
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < mainBeltEnd; i += 20) {
                g.fillRect(i, middleY - 10, 10, 20); // Make the main belt thicker
            }

            // Draw the sorter employee
            g.drawImage(sorterImage, sorterX - 30, middleY - 60, 60, 120, this); // Adjusted sorter image size
            g.setColor(Color.BLACK);
            g.drawString("Sorter Employee", sorterX - 30, middleY - 70); // Sorter label
            g.drawString("Sorted: " + sorterCount, sorterX - 30, middleY - 90); // Sorter counter

            // Draw the distributor employee in front of the main path
            g.drawImage(distributorImage, distributorX - 15, middleY - 50, 60, 120, this);
            g.setColor(Color.BLACK);
            g.drawString("Distributor", distributorX - 30, middleY - 60); // Distributor label
            g.drawString("Distributed: " + distributorCount, distributorX - 30, middleY - 80); // Distributor counter

            // Draw the connecting paths to the additional lanes
            g.setColor(Color.DARK_GRAY);
            g.fillRect(mainBeltEnd, middleY - 10, 20, 20); // Make the connecting path thicker

            // Draw the lanes as steps forward
            g.fillRect(mainBeltEnd + 10, middleY - 120, 150, 20); // Metal lane
            g.fillRect(mainBeltEnd + 10, middleY - 80, 150, 20); // Plastic lane
            g.fillRect(mainBeltEnd + 10, middleY + 40, 150, 20); // Glass lane
            g.fillRect(mainBeltEnd + 10, middleY + 80, 150, 20); // Paper lane

            g.setColor(Color.LIGHT_GRAY);
            for (int i = mainBeltEnd + 10; i < mainBeltEnd + 160; i += 20) {
                g.fillRect(i, middleY - 120, 10, 20); // Metal lane
                g.fillRect(i, middleY - 80, 10, 20); // Plastic lane
                g.fillRect(i, middleY + 40, 10, 20); // Glass lane
                g.fillRect(i, middleY + 80, 10, 20); // Paper lane
            }

            // Add vertical connectors to make a smooth connection between the main belt and the lanes
            g.setColor(Color.DARK_GRAY);
            g.fillRect(mainBeltEnd, middleY - 130 + 10, 10, 100); // Metal vertical connector
            g.fillRect(mainBeltEnd, middleY - 80 + 10, 10, 150);  // Plastic vertical connector
            g.fillRect(mainBeltEnd, middleY + 10, 10, 10);       // Glass vertical connector
            g.fillRect(mainBeltEnd, middleY + 20 + 10, 10, 70);  // Paper vertical connector

            // Draw baskets at the end of each lane
            g.setColor(Color.ORANGE);
            g.fillRect(mainBeltEnd + 160, middleY - 120, 30, 20); // Metal basket
            g.fillRect(mainBeltEnd + 160, middleY - 80, 30, 20); // Plastic basket
            g.fillRect(mainBeltEnd + 160, middleY + 40, 30, 20); // Glass basket
            g.fillRect(mainBeltEnd + 160, middleY + 80, 30, 20); // Paper basket

            // Draw the counters for each material type
            g.setColor(Color.BLACK);
            g.setFont(new Font("Times New Roman", Font.PLAIN, 12));
            g.drawString("Metal: " + metalCount, mainBeltEnd + 200, middleY - 110);
            g.drawString("Plastic: " + plasticCount, mainBeltEnd + 200, middleY - 70);
            g.drawString("Glass: " + glassCount, mainBeltEnd + 200, middleY + 50);
            g.drawString("Paper: " + paperCount, mainBeltEnd + 200, middleY + 90);

            // Draw the big squares for hours of working and plastic sorted
            g.setColor(Color.WHITE);
            g.fillRect(sorterX - 150, middleY - 200, 200, 100); // Square for hours of working
            g.fillRect(sorterX + 50, middleY - 200, 200, 100); // Square for plastic sorted

            g.setColor(Color.BLACK);
            g.drawRect(sorterX - 150, middleY - 200, 200, 100); // Border for hours of working
            g.drawRect(sorterX + 50, middleY - 200, 200, 100); // Border for plastic sorted

            g.drawString("Hours of Working", sorterX - 140, middleY - 180);
            g.drawString("Plastic Sorted: " + plasticCount, sorterX + 60, middleY - 180);

            // Calculate and display hours of working based on total items sorted and distributed
            int totalItemsProcessed = sorterCount + distributorCount;
            int hoursOfWorking = totalItemsProcessed / 25;
            g.drawString("Hours: " + hoursOfWorking, sorterX - 140, middleY - 160);

            // Draw the moving objects
            for (MovingObject obj : movingObjects) {
                obj.draw(g, middleY, mainBeltEnd, sorterX, distributorX, lanePositions);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIMain::new);
    }
}