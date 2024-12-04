import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.application.Platform;



public class GUIMain extends Application {
    private Stage stage;
    private TextField experienceField;
    private TextField numObjectsField;
    private int numObjects;
    private RailPane railPane;
    private List<MovingObject> movingObjects;
    private AnimationTimer clockTimer;
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
    private Label timeLabel;
    private long startTime;
    private long elapsedTimeBefore = 0;
    private TextArea sortingLogArea;
    private TextArea distributingLogArea;
    private Button speedUp2xButton;
    private Button speedUp4xButton;
    private Button speedUp1xButton;
    private Button stopButton;
    private boolean startButtonPressed = false;
    private long totalSortingTime = 0;
    private int totalSortedItems = 0;
    private Image mainBackgroundImage;
    private boolean pahse1_done = false;
    private Button returnButton;
    private double totalPlasticWeight = 0;
    private double totalMetalWeight = 0;
    private double totalGlassWeight = 0;
    private double totalPaperWeight = 0;
    private int totalErrors = 0;
    private Button changeExperienceButton;
    private Sorter sorter;
    private Distributor distributor;
    private Button resetTirednessButton;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("Main Page");

        mainBackgroundImage = new Image("file:main-background.jpg");

        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(20));
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        BackgroundImage backgroundImage = new BackgroundImage(mainBackgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        mainPane.setBackground(new Background(backgroundImage));

        Label numObjectsLabel = new Label("Number of Objects:");
        numObjectsField = new TextField();

        Button phase1Button = new Button("Phase 1");
        Button phase2Button = new Button("Phase 2");
        phase1Button.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        phase2Button.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        phase1Button.setOnAction(e -> {
            String numObjectsText = numObjectsField.getText().trim();
            if (numObjectsText.isEmpty()) {
                showAlert(AlertType.WARNING, "Input Required", "Please enter the number of objects");
                return;
            }
            try {
                numObjects = Integer.parseInt(numObjectsText);
                if (numObjects <= 0) {
                    showAlert(AlertType.WARNING, "Invalid Input", "Number of objects must be greater than 0");
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid number for the number of objects");
                return;
            }
            startPhase1();
        });

        phase2Button.setOnAction(e -> startPhase2());

        // Add components to the grid with specific positions
        mainPane.add(numObjectsLabel, 0, 0);
        mainPane.add(numObjectsField, 1, 0);
        mainPane.add(phase1Button, 0, 1);
        mainPane.add(phase2Button, 1, 1);

        // Set alignment for specific components
        GridPane.setHalignment(numObjectsLabel, HPos.RIGHT);
        GridPane.setHalignment(numObjectsField, HPos.LEFT);
        GridPane.setHalignment(phase1Button, HPos.CENTER);
        GridPane.setHalignment(phase2Button, HPos.CENTER);

        Scene scene = new Scene(mainPane, 400, 300);
        stage.setScene(scene);
        stage.setResizable(false); // Make the window fixed size
        stage.setOnCloseRequest(e -> Platform.exit()); // Stop the application when the window is closed
        stage.show();
    }

    private void startPhase1() {
        stage.close();
        stage = new Stage();
        stage.setTitle("Simulation");

        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setPadding(new Insets(20));
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setBackground(new Background(new BackgroundFill(Color.web("#5e5e5e"), CornerRadii.EMPTY, Insets.EMPTY)));

        Label experienceLabel = new Label("Experience:");
        experienceLabel.setTextFill(Color.WHITE);
        experienceField = new TextField();
        timeLabel = new Label("Time: 00:00:00");
        timeLabel.setTextFill(Color.WHITE);

        stopButton = new Button("Pause");
        speedUp1xButton = new Button("Play");
        speedUp2xButton = new Button("x2");
        speedUp4xButton = new Button("x4");
        resetTirednessButton = new Button("Reset Tiredness");
        resetTirednessButton.setDisable(true);

        resetTirednessButton.setOnAction(e -> {
            if (sorter != null) {
                sorter.setTiredness(0.0);
                distributor.setTiredness(0.0);
                showAlert(AlertType.INFORMATION, "Reset Successful", "Tiredness has been reset to 0!");
                System.out.println("Tiredness reset to 0.");
            } else {
                showAlert(AlertType.ERROR, "Error", "Sorter is not initialized. Please start the simulation first.");
            }
        });

        stopButton.setDisable(true);
        speedUp2xButton.setDisable(true);
        speedUp4xButton.setDisable(true);
        speedUp1xButton.setDisable(true);

        Button startButton = new Button("Start");
        returnButton = new Button("Return");
        returnButton.setDisable(true);
        returnButton.setOnAction(e -> returnToMainPage());

        changeExperienceButton = new Button("Change Experience");
        changeExperienceButton.setDisable(true);
        changeExperienceButton.setOnAction(e -> changeExperience());

        startButton.setOnAction(e -> {
            startButton.setDisable(true);
            if (startButtonPressed) {
                return;
            }
            startButtonPressed = true;
            System.out.println("Start button clicked");
            String experienceText = experienceField.getText().trim();
            if (experienceText.isEmpty()) {
                showAlert(AlertType.WARNING, "Input Required", "Please enter Experience value");
                startButtonPressed = false;
                startButton.setDisable(false);
                return;
            }
            try {
                int experienceInput = Integer.parseInt(experienceText);
                if (experienceInput < 0 || experienceInput > 25) {
                    showAlert(AlertType.WARNING, "Invalid Input", "Experience must be between 0 and 25 years");
                    startButtonPressed = false;
                    startButton.setDisable(false);
                    return;
                }
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid number for Experience");
                startButtonPressed = false;
                return;
            }
            stopButton.setDisable(false);
            speedUp2xButton.setDisable(false);
            speedUp4xButton.setDisable(false);
            speedUp1xButton.setDisable(false);
            startSimulation();
        });

        // Define event handlers for speed-up buttons
        speedUp1xButton.setOnAction(e -> {
            setTimeMultiplier(1);
            resumeSimulation();
        });
        speedUp2xButton.setOnAction(e -> {
            setTimeMultiplier(2);
            resumeSimulation();
        });
        speedUp4xButton.setOnAction(e -> {
            setTimeMultiplier(4);
            resumeSimulation();
        });
        stopButton.setOnAction(e -> {
            setTimeMultiplier(0);
            pauseSimulation();
        });

        railPane = new RailPane();
        sortingLogArea = new TextArea();
        sortingLogArea.setEditable(false);
        sortingLogArea.setPrefHeight(300);
        sortingLogArea.setPrefWidth(300);

        distributingLogArea = new TextArea();
        distributingLogArea.setEditable(false);
        distributingLogArea.setPrefHeight(300);
        distributingLogArea.setPrefWidth(300);

        // Add components to the grid with specific positions
        mainPane.add(experienceLabel, 0, 0);
        mainPane.add(experienceField, 1, 0);
        mainPane.add(timeLabel, 0, 1, 2, 1);

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(stopButton, speedUp1xButton, speedUp2xButton, speedUp4xButton, resetTirednessButton, startButton, returnButton, changeExperienceButton);
        mainPane.add(buttonBox, 0, 2, 2, 1);

        mainPane.add(railPane, 0, 3, 2, 1);

        VBox logBox = new VBox(10);
        Label sortingLogLabel = new Label("Sorting Log:");
        sortingLogLabel.setTextFill(Color.WHITE);
        Label distributingLogLabel = new Label("Distributing Log:");
        distributingLogLabel.setTextFill(Color.WHITE);
        logBox.getChildren().addAll(sortingLogLabel, sortingLogArea, distributingLogLabel, distributingLogArea);
        mainPane.add(logBox, 2, 0, 1, 4);

        // Set alignment for specific components
        GridPane.setHalignment(experienceLabel, HPos.RIGHT);
        GridPane.setHalignment(experienceField, HPos.LEFT);
        GridPane.setHalignment(timeLabel, HPos.CENTER);
        GridPane.setHalignment(buttonBox, HPos.CENTER);
        GridPane.setHalignment(railPane, HPos.CENTER);
        GridPane.setHalignment(logBox, HPos.CENTER);

        Scene scene = new Scene(mainPane, 1600, 900);
        stage.setScene(scene);
        stage.setResizable(false); // Make the window fixed size
        stage.setOnCloseRequest(e -> Platform.exit()); // Stop the application when the window is closed
        stage.show();

        // Ensure the rail is drawn when the Phase 1 page is shown
        railPane.repaint();

        movingObjects = new CopyOnWriteArrayList<>();

        backgroundImage = new Image("file:packaging-closing-machine.jpg");
        sorterImage = new Image("file:sorter.png");
        distributorImage = new Image("file:distbuter.png");
        plasticImage = new Image("file:PLASTIC.png");
        metalImage = new Image("file:METEL.png");
        glassImage = new Image("file:GLASS.png");
        paperImage = new Image("file:PAPER.png");
        errorImage = new Image("file:error.png");
    }

    private void returnToMainPage() {
        stage.close();
        start(new Stage());
    }

    private void startPhase2() {
        stage.close();
        stage = new Stage();
        stage.setTitle("Phase 2");

        Label label = new Label("Phase 2 Page");
        label.setAlignment(Pos.CENTER);

        Scene scene = new Scene(new StackPane(label), 1600, 900);
        stage.setScene(scene);
        stage.setResizable(false); // Make the window fixed size
        stage.setOnCloseRequest(e -> Platform.exit()); // Stop the application when the window is closed
        stage.show();
    }

    private void setTimeMultiplier(int multiplier) {
        long currentTime = System.currentTimeMillis();
        elapsedTimeBefore += (currentTime - startTime) * timeMultiplier;
        startTime = currentTime;
        this.timeMultiplier = multiplier;
    }

    private void pauseSimulation() {
        if (clockTimer != null) {
            clockTimer.stop();
        }
    }

    private void resumeSimulation() {
        if (clockTimer != null) {
            clockTimer.start();
        }
    }

    private void startSimulation() {
        List<Recyclableitem> items = Recyclableitem.createList(numObjects);
        int experienceInput = Integer.parseInt(experienceField.getText().trim());
        sorter = new Sorter(1, 5.0, "Moha");
        distributor = new Distributor(2, 5.0, "spotty");
        sorter.setExperienceYears(experienceInput);
        railPane.setMovingObjects(movingObjects);

        new Thread(() -> {
            int startX = -50;
            for (Recyclableitem item : items) {
                movingObjects.add(new MovingObject(item, startX, sorter, distributor));
                railPane.repaint();
                startX -= 35;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        startTime = System.currentTimeMillis();
        clockTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                int hours = (int) (elapsedTime / 3600000);
                int minutes = (int) (elapsedTime / 60000) % 60;
                int seconds = (int) (elapsedTime / 1000) % 60;
                timeLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
                railPane.repaint();
            }
        };
        clockTimer.start();
        changeExperienceButton.setDisable(false);
        resetTirednessButton.setDisable(false);
    }

    private void changeExperience() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Experience");
        dialog.setHeaderText(null);
        dialog.setContentText("Enter new experience value:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(experienceText -> {
            try {
                int newExperience = Integer.parseInt(experienceText.trim());
                if (newExperience < 0 || newExperience > 25) {
                    showAlert(AlertType.WARNING, "Invalid Input", "Experience must be between 0 and 25 years");
                    return;
                }
                if (sorter == null) {
                    showAlert(AlertType.ERROR, "Error", "Sorter is not initialized. Please check the code.");
                    return;
                }
                sorter.setExperienceYears(newExperience);
                showAlert(AlertType.INFORMATION, "Success", "Experience updated successfully to " + newExperience + " years");
            } catch (NumberFormatException ex) {
                showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid number for Experience");
            }
        });
    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
            this.x = startX;
            this.y = 0;
            this.image = getImageForType(item.getItemType());
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

        public void draw(GraphicsContext gc, int middleY, int mainBeltEnd, int sorterX, int distributorX, int[] lanePositions) {
            // Check if the object is at the sorter position and not already sorting
            if (!item.isDone_sorting() && x >= sorterX - 10 && x <= sorterX + 10 && !isSorting) {
                isSorting = true; // Set the sorting flag to true
                new Thread(() -> {
                    try {
                        sorterEmployee.sort(item); // Sort the item
                        if (item.getsortingError()) {
                            totalErrors++; // Increment total errors if there is a sorting error
                        }
                        long sortTimeMillis = (long) (item.get_time_to_sort() * 1000);
                        Thread.sleep(sortTimeMillis / timeMultiplier); // Sleep according to the sorting time
                        totalSortingTime += sortTimeMillis; // Update total sorting time
                        totalSortedItems++; // Increment total sorted items
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    item.setisDone_sorting(true); // Mark the item as sorted
                    sorterEmployee.incrementItemsDone(); // Increment the sorter's item count
                    this.image = getImageForType(item.getItemType()); // Update the image based on sorted type

                    isSorting = false; // Reset the sorting flag
                    sorterCount++; // Increment the sorter count
                    javafx.application.Platform.runLater(() -> {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                        int hours = (int) (elapsedTime / 3600000);
                        int minutes = (int) (elapsedTime / 60000) % 60;
                        int seconds = (int) (elapsedTime / 1000) % 60;
                        sortingLogArea.appendText("Item sorted\nItem: " + item.getItemType() + "\nTime: " + String.format("%02d:%02d:%02d\n", hours, minutes, seconds));
                    }); // Print sorted message
                }).start();
            }

            // If sorting, stop the object
            if (!item.isDone_sorting() && isSorting) {
                this.y = middleY; // Set the Y position based on the middle line
                gc.setFill(Color.WHITE); // Set the color to white
                gc.fillOval(x, y - 15, 30, 30); // Draw the object as a white circle
            } else {
                // Move the object after sorting
                if (!item.isDone_sorting()) {
                    this.y = middleY; // Set the Y position based on the middle line
                    gc.setFill(Color.WHITE); // Set the color to white
                    gc.fillOval(x, y - 15, 30, 30); // Draw the object as a white circle
                } else {
                    gc.drawImage(image, x, y - 15, 30, 30); // Draw the object centered on the middle line

                    if (item.getsortingError()) {
                        gc.setFill(Color.RED); // Set the color to red for errors
                    } else {
                        gc.setFill(Color.GREEN); // Set the color to green for correct sorting
                    }
                    gc.fillText(item.getItemType(), x, y - 25); // Display the item type
                }
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
                            metalCount++; // Increment metal count
                            totalMetalWeight += item.getItemWeight(); // Update total metal weight
                            break;
                        case "Plastic":
                            y = middleY - 80; // Set Y position for Plastic lane
                            x = mainBeltEnd + 10 + lanePositions[1]; // Set X position for Plastic lane
                            plasticCount++; // Increment plastic count
                            totalPlasticWeight += item.getItemWeight(); // Update total plastic weight
                            break;
                        case "Glass":
                            y = middleY + 40; // Set Y position for Glass lane
                            x = mainBeltEnd + 10 + lanePositions[2]; // Set X position for Glass lane
                            glassCount++; // Increment glass count
                            totalGlassWeight += item.getItemWeight(); // Update total glass weight
                            break;
                        case "Paper":
                            y = middleY + 80; // Set Y position for Paper lane
                            x = mainBeltEnd + 10 + lanePositions[3]; // Set X position for Paper lane
                            paperCount++; // Increment paper count
                            totalPaperWeight += item.getItemWeight(); // Update total paper weight
                            break;
                    }
                    distributed = true; // Mark the object as distributed
                    distributorCount++; // Increment the distributor count
                    isDistributing = false; // Reset the distributing flag
                    javafx.application.Platform.runLater(() -> {
                        long currentTime = System.currentTimeMillis();
                        long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                        int hours = (int) (elapsedTime / 3600000);
                        int minutes = (int) (elapsedTime / 60000) % 60;
                        int seconds = (int) (elapsedTime / 1000) % 60;
                        distributingLogArea.appendText("Item distributed\nItem: " + item.getItemType() + "\nTime: " + String.format("%02d:%02d:%02d\n", hours, minutes, seconds));
                    }); // Print distributed message
                }).start();
            }

            // If distributing, stop the object
            if (!item.isdone_distribute() && isDistributing) {
                this.y = middleY; // Set the Y position based on the middle line
                this.x = mainBeltEnd;
            } else if (item.isdone_distribute()) {
                if (x > mainBeltEnd + 160) { // Use mainBeltEnd + 160 for the lanes
                    x = mainBeltEnd + 150; // Stop at the basket
                    // Make the object disappear after 10 seconds
                    new Thread(() -> {
                        try {
                            Thread.sleep(100); 
                            javafx.application.Platform.runLater(() -> {
                                movingObjects.remove(this); // Remove the object from the list
                                railPane.repaint(); // Repaint the rail panel
                                if (allItemsDistributed() && !pahse1_done) {
                                    pahse1_done = true;
                                    clockTimer.stop(); // Stop the timer when all objects are distributed
                                    Alert alert = new Alert(AlertType.INFORMATION);
                                    alert.setTitle("Simulation Status");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Simulation completed!");
                                    alert.showAndWait(); // Wait for the user to press OK
                                    stopButton.setDisable(true); // Disable the stop button
                                    returnButton.setDisable(false); // Enable the return button
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }
            }
        }

        public void updatePosition() {
            while (true) {
                if (!item.isDone_sorting() && !isSorting) {
                    x += 1 * timeMultiplier; // Move right (adjusted by timeMultiplier)
                    break;
                }

                if (item.isDone_sorting() && !item.isdone_distribute() && !isDistributing) {
                    x += 1 * timeMultiplier; // Move right (adjusted by timeMultiplier)
                    break;
                }

                if (item.isdone_distribute()) {
                    x += 1 * timeMultiplier; // Move right in the lane (adjusted by timeMultiplier)
                    break;
                }

                break; // Ensure the loop terminates if none of the conditions are true
            }
        }

        private boolean allItemsDistributed() {
            for (MovingObject obj : movingObjects) {
                if (!obj.item.isdone_distribute()) {
                    return false;
                }
            }
            return true;
        }
    }

    private class RailPane extends Pane {
        private List<MovingObject> movingObjects;
        private int[] lanePositions;
        private Canvas canvas;

        RailPane() {
            this.movingObjects = new ArrayList<>();
            this.lanePositions = new int[4];
            this.canvas = new Canvas(1300, 900); // Set canvas size
            getChildren().add(canvas);
        }

        void setMovingObjects(List<MovingObject> movingObjects) {
            this.movingObjects = movingObjects;
            for (int i = 0; i < lanePositions.length; i++) {
                lanePositions[i] = 0;
            }
        }

        void repaint() {
            GraphicsContext gc = canvas.getGraphicsContext2D();
            draw(gc);
        }

        private void draw(GraphicsContext gc) {
            gc.setFill(Color.web("#5e5e5e")); // Set the background color
            gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

            // Draw the background image
            gc.drawImage(backgroundImage, 0, 0, canvas.getWidth(), canvas.getHeight());

            int middleY = (int) canvas.getHeight() / 2;
            int mainBeltEnd = (int) canvas.getWidth() - 300; // Shorten the main conveyor belt
            int sorterX = mainBeltEnd / 2; // Position of the sorter in the middle of the main belt
            int distributorX = mainBeltEnd + 10; // Position of the distributor at the end of the main belt

            // Draw the main conveyor belt
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(0, middleY - 10, mainBeltEnd, 20); // Make the main belt thicker
            gc.setFill(Color.LIGHTGRAY);
            for (int i = 0; i < mainBeltEnd; i += 20) {
                gc.fillRect(i, middleY - 10, 10, 20); // Make the main belt thicker
            }

            // Draw the sorter employee
            gc.drawImage(sorterImage, sorterX - 30, middleY - 60, 60, 120); // Adjusted sorter image size
            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
            gc.fillText("Sorter Employee", sorterX - 30, middleY - 70); // Sorter label
            gc.fillText("Sorted: " + sorterCount, sorterX - 30, middleY - 90); // Sorter counter

            // Display the experience of the sorter employee
            if (!movingObjects.isEmpty()) {
                Employee sorterEmployee = movingObjects.get(0).sorterEmployee;
                gc.fillText("Experience: " + sorterEmployee.getExperienceYears() + " years", sorterX - 30, middleY - 110);
                gc.fillText(String.format("Tiredness: %.2f", sorterEmployee.getTiredness()), sorterX - 30, middleY - 130);
                gc.fillText(String.format("Tiredness: %.2f", distributor.getTiredness()), distributorX + 50, middleY - 40);
            }

            // Draw the distributor employee in front of the main path
            gc.drawImage(distributorImage, distributorX - 15, middleY - 50, 60, 120);
            gc.setFill(Color.WHITE);
            gc.fillText("Distributor Employee", distributorX + 50, middleY - 0); // Distributor label
            gc.fillText("Distributed: " + distributorCount, distributorX + 50, middleY - 20); // Distributor counter

            // Draw the connecting paths to the additional lanes
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(mainBeltEnd, middleY - 10, 20, 20); // Make the connecting path thicker

            // Draw the lanes as steps forward
            gc.fillRect(mainBeltEnd + 10, middleY - 120, 150, 20); // Metal lane
            gc.fillRect(mainBeltEnd + 10, middleY - 80, 150, 20); // Plastic lane
            gc.fillRect(mainBeltEnd + 10, middleY + 40, 150, 20); // Glass lane
            gc.fillRect(mainBeltEnd + 10, middleY + 80, 150, 20); // Paper lane

            gc.setFill(Color.LIGHTGRAY);
            for (int i = mainBeltEnd + 10; i < mainBeltEnd + 160; i += 20) {
                gc.fillRect(i, middleY - 120, 10, 20); // Metal lane
                gc.fillRect(i, middleY - 80, 10, 20); // Plastic lane
                gc.fillRect(i, middleY + 40, 10, 20); // Glass lane
                gc.fillRect(i, middleY + 80, 10, 20); // Paper lane
            }

            // Add vertical connectors to make a smooth connection between the main belt and the lanes
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(mainBeltEnd, middleY - 130 + 10, 10, 100); // Metal vertical connector
            gc.fillRect(mainBeltEnd, middleY - 80 + 10, 10, 150);  // Plastic vertical connector
            gc.fillRect(mainBeltEnd, middleY + 10, 10, 10);       // Glass vertical connector
            gc.fillRect(mainBeltEnd, middleY + 20 + 10, 10, 70);  // Paper vertical connector

            // Draw baskets at the end of each lane
            gc.setFill(Color.ORANGE);
            gc.fillRect(mainBeltEnd + 160, middleY - 120, 30, 20); // Metal basket
            gc.fillRect(mainBeltEnd + 160, middleY - 80, 30, 20); // Plastic basket
            gc.fillRect(mainBeltEnd + 160, middleY + 40, 30, 20); // Glass basket
            gc.fillRect(mainBeltEnd + 160, middleY + 80, 30, 20); // Paper basket

            gc.setFill(Color.WHITE);
            gc.setFont(Font.font("Times New Roman", FontWeight.BOLD, 12));
            gc.fillText("Metal: " + metalCount, mainBeltEnd + 200, middleY - 110);
            gc.fillText("Plastic: " + plasticCount, mainBeltEnd + 200, middleY - 70);
            gc.fillText("Glass: " + glassCount, mainBeltEnd + 200, middleY + 50);
            gc.fillText("Paper: " + paperCount, mainBeltEnd + 200, middleY + 90);
            
            

            gc.fillText("Number of Objects Done: " + distributorCount, sorterX - 420, middleY - 330);
            gc.fillText("Number of Errors: " + totalErrors, sorterX - 420, middleY - 310);
            gc.fillText("Tons Done for Each Material:", sorterX - 420, middleY - 290);
            gc.fillText("Plastic: " + String.format("%.5f", totalPlasticWeight / 1000) + " tons", sorterX - 420, middleY - 270);
            gc.fillText("Metal: " + String.format("%.5f", totalMetalWeight / 1000) + " tons", sorterX - 420, middleY - 250);
            gc.fillText("Glass: " + String.format("%.5f", totalGlassWeight / 1000) + " tons", sorterX - 420, middleY - 230);
            gc.fillText("Paper: " + String.format("%.5f", totalPaperWeight / 1000) + " tons", sorterX - 420, middleY - 210);

            // Calculate and display hours of working based on total items sorted and distributed


            // Calculate and display total sorting time
            long totalSortingSeconds = totalSortingTime / 1000;
            int sortingHours = (int) (totalSortingSeconds / 3600);
            int sortingMinutes = (int) (totalSortingSeconds / 60) % 60;
            int sortingSeconds = (int) (totalSortingSeconds % 60);
            gc.fillText(String.format("Total Sorting Time: %02d:%02d:%02d", sortingHours, sortingMinutes, sortingSeconds), sorterX - 420, middleY - 190);

            // Calculate and display average sorting time
            double averageSortingTime = totalSortedItems > 0 ? (double) totalSortingTime / totalSortedItems / 1000 : 0;
            int avgSortingMinutes = (int) (averageSortingTime / 60);
            int avgSortingSeconds = (int) (averageSortingTime % 60);
            gc.fillText(String.format("Average Sorting Time: %02d:%02d", avgSortingMinutes, avgSortingSeconds), sorterX - 420, middleY - 170);

            // Draw the moving objects
            for (MovingObject obj : movingObjects) {
                obj.updatePosition(); // Update position here
                obj.draw(gc, middleY, mainBeltEnd, sorterX, distributorX, lanePositions);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}