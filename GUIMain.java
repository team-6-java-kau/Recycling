import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;


import java.util.ArrayList;
import java.util.List;


public class GUIMain extends Application {
    private TextField experienceField;
    private TextField numObjectsField;
    private TextArea sortingLogArea;
    private TextArea distributingLogArea;
    private Label timeLabel;
    private int numObjects;
    private int timeMultiplier = 1;
    private long startTime;
    private long elapsedTimeBefore = 0;
    private boolean startButtonPressed = false;
    private Sorter sorter;
    private Distributor distributor;
    private List<MovingObject> movingObjects;
    private Pane railPane;
    private AnimationTimer timer;
    private int totalErrors = 0;
    private double totalPlasticWeight = 0;
    private double totalMetalWeight = 0;
    private double totalGlassWeight = 0;
    private double totalPaperWeight = 0;
    private long totalSortingTime = 0;
    private int totalSortedItems = 0;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Main Page");

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);

        HBox inputLayout = new HBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        Label numObjectsLabel = new Label("Number of Objects:");
        numObjectsField = new TextField();
        inputLayout.getChildren().addAll(numObjectsLabel, numObjectsField);

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        Button phase1Button = new Button("Phase 1");
        Button phase2Button = new Button("Phase 2");
        buttonLayout.getChildren().addAll(phase1Button, phase2Button);

        mainLayout.getChildren().addAll(inputLayout, buttonLayout);

        phase1Button.setOnAction(e -> startPhase1(primaryStage));
        phase2Button.setOnAction(e -> startPhase2(primaryStage));

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startPhase1(Stage primaryStage) {
        String numObjectsText = numObjectsField.getText().trim();
        if (numObjectsText.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter the number of objects");
            return;
        }
        try {
            numObjects = Integer.parseInt(numObjectsText);
            if (numObjects <= 0) {
                showAlert(Alert.AlertType.WARNING, "Invalid Input", "Number of objects must be greater than 0");
                return;
            }
        } catch (NumberFormatException ex) {
            showAlert(Alert.AlertType.WARNING, "Invalid Input", "Please enter a valid number for the number of objects");
            return;
        }

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);

        HBox inputLayout = new HBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        Label experienceLabel = new Label("Experience:");
        experienceField = new TextField();
        inputLayout.getChildren().addAll(experienceLabel, experienceField);

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Pause");
        Button speedUp1xButton = new Button("Play");
        Button speedUp2xButton = new Button("x2");
        Button speedUp4xButton = new Button("x4");
        Button resetTirednessButton = new Button("Reset Tiredness");
        buttonLayout.getChildren().addAll(startButton, stopButton, speedUp1xButton, speedUp2xButton, speedUp4xButton, resetTirednessButton);

        timeLabel = new Label("Time: 00:00:00");

        sortingLogArea = new TextArea();
        sortingLogArea.setEditable(false);
        sortingLogArea.setPrefHeight(200);

        distributingLogArea = new TextArea();
        distributingLogArea.setEditable(false);
        distributingLogArea.setPrefHeight(200);

        layout.getChildren().addAll(inputLayout, buttonLayout, timeLabel, sortingLogArea, distributingLogArea);

        startButton.setOnAction(e -> startSimulation());
        stopButton.setOnAction(e -> setTimeMultiplier(0));
        speedUp1xButton.setOnAction(e -> setTimeMultiplier(1));
        speedUp2xButton.setOnAction(e -> setTimeMultiplier(2));
        speedUp4xButton.setOnAction(e -> setTimeMultiplier(4));
        resetTirednessButton.setOnAction(e -> resetTiredness());

        railPane = new Pane();
        railPane.setPrefSize(1300, 900);
        railPane.setStyle("-fx-background-color: #5e5e5e;");

        layout.getChildren().add(railPane);

        Scene scene = new Scene(layout, 1300, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startPhase2(Stage primaryStage) {
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        Label label = new Label("Phase 2 Page");
        layout.getChildren().add(label);

        Scene scene = new Scene(layout, 1300, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startSimulation() {
        List<Recyclableitem> items = Recyclableitem.createList(numObjects);
        int experienceInput = Integer.parseInt(experienceField.getText().trim());
        sorter = new Sorter(1, 5.0, "Moha");
        distributor = new Distributor(2, 5.0, "Spotty");
        sorter.setExperienceYears(experienceInput);

        movingObjects = new ArrayList<>();
        int startX = -50;
        for (Recyclableitem item : items) {
            movingObjects.add(new MovingObject(item, startX, sorter, distributor));
            startX -= 35;
        }

        startTime = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long currentTime = System.currentTimeMillis();
                long elapsedTime = elapsedTimeBefore + (currentTime - startTime) * timeMultiplier;
                int hours = (int) (elapsedTime / 3600000);
                int minutes = (int) (elapsedTime / 60000) % 60;
                int seconds = (int) (elapsedTime / 1000) % 60;
                timeLabel.setText(String.format("Time: %02d:%02d:%02d", hours, minutes, seconds));
                railPane.getChildren().clear();
                for (MovingObject obj : movingObjects) {
                    obj.updatePosition();
                    obj.draw(railPane);
                }
            }
        };
        timer.start();
    }

    private void setTimeMultiplier(int multiplier) {
        long currentTime = System.currentTimeMillis();
        elapsedTimeBefore += (currentTime - startTime) * timeMultiplier;
        startTime = currentTime;
        this.timeMultiplier = multiplier;
    }

    private void resetTiredness() {
        if (sorter != null) {
            sorter.setTiredness(0.0);
            distributor.setTiredness(0.0);
            showAlert(Alert.AlertType.INFORMATION, "Reset Successful", "Tiredness has been reset to 0!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Sorter is not initialized. Please start the simulation first.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private class MovingObject {
        private int x, y;
        private Image image;
        private Recyclableitem item;
        private boolean sorted;
        private boolean distributed;
        private Sorter sorterEmployee;
        private Distributor distributorEmployee;
        private boolean isDistributing = false;

        MovingObject(Recyclableitem item, int startX, Sorter sorterEmployee, Distributor distributorEmployee) {
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
                    return new Image("metal.png");
                case "Plastic":
                    return new Image("plastic.png");
                case "Glass":
                    return new Image("glass.png");
                case "Paper":
                    return new Image("paper.png");
                default:
                    return null;
            }
        }

        public void draw(Pane pane) {
            ImageView imageView = new ImageView(image);
            imageView.setX(x);
            imageView.setY(y);
            imageView.setFitWidth(30);
            imageView.setFitHeight(30);
            pane.getChildren().add(imageView);
        }

        public void updatePosition() {
            if (!sorted) {
                x += 5 * timeMultiplier;
                if (x >= 600 && !isDistributing) {
                    isDistributing = true;
                    new Thread(() -> {
                        try {
                            sorterEmployee.sort(item);
                            if (item.getsortingError()) {
                                totalErrors++;
                            }
                            long sortTimeMillis = (long) (item.get_time_to_sort() * 1000);
                            Thread.sleep(sortTimeMillis / timeMultiplier);
                            totalSortingTime += sortTimeMillis;
                            totalSortedItems++;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        item.setisDone_sorting(true);
                        sorterEmployee.incrementItemsDone();
                        sorted = true;
                        isDistributing = false;
                    }).start();
                }
            } else if (!distributed) {
                x += 5 * timeMultiplier;
                if (x >= 1200 && !isDistributing) {
                    isDistributing = true;
                    new Thread(() -> {
                        try {
                            distributorEmployee.distributeItem(item);
                            Thread.sleep((long) ((item.get_time_to_distribute() * 1000) / timeMultiplier));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        item.setisdone_distribute(true);
                        distributorEmployee.incrementItemsDone();
                        distributed = true;
                        isDistributing = false;
                    }).start();
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}