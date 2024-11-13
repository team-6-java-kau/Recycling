import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class GUIMain {
    private JFrame frame;
    private JTextArea outputArea;
    private JTextField experienceField;
    private JTextField timescaleField;
    private List<Recyclableitem> items;
    private RailPanel railPanel;
    private List<MovingObject> movingObjects;
    private int currentItemIndex = 0;
    private Queue<Recyclableitem> buffer;
    private Employee sorter;
    private Employee distributor;
    private int timescale;
    private static final int SLOT_WIDTH = 50;
    private static final int SLOT_HEIGHT = 20;
    private static final int SLOT_SPACING = 10;
    private static final int NUM_SLOTS = 3;
    
    // Constants for rail layout
    private static final int MAIN_RAIL_Y = 100;
    private static final int RAIL_SPACING = 80;
    private static final int RAIL_START_X = 50;
    private static final int RAIL_WIDTH = 500;
    private static final int MAX_BUFFER_SIZE = 3;
    private static final int SORTING_POINT_X = 200;
    private static final int DISTRIBUTION_POINT_X = 400;

    public GUIMain() {
        movingObjects = new CopyOnWriteArrayList<>();
        buffer = new LinkedList<>();
        frame = new JFrame("Recycling Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        experienceField = new JTextField(5);
        timescaleField = new JTextField(5);
        JButton startButton = new JButton("Start Simulation");

        inputPanel.add(new JLabel("Experience:"));
        inputPanel.add(experienceField);
        inputPanel.add(new JLabel("Timescale:"));
        inputPanel.add(timescaleField);
        inputPanel.add(startButton);

        // Set default values for input fields
        experienceField.setText("1");
        timescaleField.setText("1");
        
        // Add input validation
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

        // Add rail panel
        railPanel = new RailPanel();
        frame.add(railPanel, BorderLayout.CENTER);

        // Add output area to the right
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setPreferredSize(new Dimension(200, frame.getHeight()));
        frame.add(scrollPane, BorderLayout.EAST);

        frame.add(inputPanel, BorderLayout.NORTH);
        frame.setVisible(true);
    }

    private class MovingObject {
        int x, y;
        Color color;
        Recyclableitem item;

        MovingObject(Recyclableitem item) {
            this.item = item;
            this.x = RAIL_START_X;
            this.y = MAIN_RAIL_Y;
            this.color = getColorForType(item.getItemType());
        }

        private Color getColorForType(String type) {
            switch (type) {
                case "Plastic": return Color.BLUE;
                case "Metal": return Color.GRAY;
                case "Glass": return Color.GREEN;
                case "Paper": return Color.YELLOW;
                default: return Color.BLACK;
            }
        }
    }

    private class RailPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // Draw main rail
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(RAIL_START_X, MAIN_RAIL_Y, RAIL_WIDTH, 10);

            // Draw sorting rails
            String[] types = {"Plastic", "Metal", "Glass", "Paper"};
            for (int i = 0; i < types.length; i++) {
                int y = MAIN_RAIL_Y + RAIL_SPACING * (i + 1);
                g.fillRect(RAIL_START_X, y, RAIL_WIDTH, 10);
                g.drawString(types[i], RAIL_START_X - 40, y + 10);
            }

            // Draw sorting and distribution points
            g.setColor(Color.RED);
            g.fillRect(SORTING_POINT_X, MAIN_RAIL_Y - 10, 10, 30);
            g.setColor(Color.BLUE);
            g.fillRect(DISTRIBUTION_POINT_X, MAIN_RAIL_Y - 10, 10, 30);

            // Draw slots on the rail
            g.setColor(Color.BLACK);
            for (int i = 0; i < NUM_SLOTS; i++) {
                int slotX = RAIL_START_X + i * (SLOT_WIDTH + SLOT_SPACING);
                g.drawRect(slotX, MAIN_RAIL_Y - SLOT_HEIGHT, SLOT_WIDTH, SLOT_HEIGHT);
            }

            // Draw moving objects
            for (MovingObject obj : movingObjects) {
                g.setColor(obj.color);
                g.fillOval(obj.x, obj.y - 5, 20, 20);
            }
        }
    }

    private void startSimulation() {
        try {
            System.out.println("Starting simulation...");
            int experience = Integer.parseInt(experienceField.getText().trim());
            timescale = Integer.parseInt(timescaleField.getText().trim());
    
            System.out.println("Experience: " + experience);
            System.out.println("Timescale: " + timescale);
    
            if (experience <= 0 || timescale <= 0) {
                JOptionPane.showMessageDialog(frame,
                    "Experience and Timescale must be positive numbers",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
    
            // Clear any previous output
            outputArea.setText("");
            outputArea.append("Starting simulation...\n");
            outputArea.append("Experience: " + experience + "\n");
            outputArea.append("Timescale: " + timescale + "\n\n");
            
            // Create items
            items = Recyclableitem.createList(10);
            currentItemIndex = 0;
    
            // Create employees
            sorter = new Employee(1, 5.0, "Moha", experience);
            distributor = new Employee(2, 5.0, "Spotty", 3);
    
            // Start the factory process
            Factory factory = new Factory(this);
            factory.setTimescale(timescale);
            new Thread(() -> factory.manual(items, sorter, distributor)).start();
    
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame,
                "Please enter valid numbers for Experience and Timescale",
                "Invalid Input",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void updateSortingStatus(Recyclableitem item, boolean success) {
        SwingUtilities.invokeLater(() -> {
            MovingObject obj = findMovingObject(item);
            if (obj != null) {
                obj.x = SORTING_POINT_X;
                railPanel.repaint();
                outputArea.append("Sorted: " + item.getItemType() + " - Success: " + success + "\n");
            }
        });
    }

    public void updateDistributionStatus(Recyclableitem item) {
        SwingUtilities.invokeLater(() -> {
            MovingObject obj = findMovingObject(item);
            if (obj != null) {
                obj.x = DISTRIBUTION_POINT_X;
                railPanel.repaint();
                outputArea.append("Distributed: " + item.getItemType() + "\n");
            }
        });
    }

    private MovingObject findMovingObject(Recyclableitem item) {
        for (MovingObject obj : movingObjects) {
            if (obj.item == item) {
                return obj;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GUIMain::new);
    }
}
