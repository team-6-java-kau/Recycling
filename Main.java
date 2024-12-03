import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class Main extends Application {
    private TextField numObjectsField;
    private TextArea sortingLogArea;
    private TextArea distributingLogArea;
    private Label timeLabel;
    private int numObjects;
    private int timeMultiplier = 1;
    private long startTime;
    private long elapsedTimeBefore = 0;
    private List<Recyclableitem> items;
    private Sorter sorter;
    private Distributor distributor;
    private Pane railPane;
    private AnimationTimer timer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Phase 2");

        VBox mainLayout = new VBox(20);
        mainLayout.setAlignment(Pos.CENTER);

        HBox inputLayout = new HBox(10);
        inputLayout.setAlignment(Pos.CENTER);
        Label numObjectsLabel = new Label("Number of Objects:");
        numObjectsField = new TextField();
        inputLayout.getChildren().addAll(numObjectsLabel, numObjectsField);

        HBox buttonLayout = new HBox(20);
        buttonLayout.setAlignment(Pos.CENTER);
        Button startButton = new Button("Start");
        Button stopButton = new Button("Pause");
        Button speedUp1xButton = new Button("Play");
        Button speedUp2xButton = new Button("x2");
        Button speedUp4xButton = new Button("x4");
        buttonLayout.getChildren().addAll(startButton, stopButton, speedUp1xButton, speedUp2xButton, speedUp4xButton);

        timeLabel = new Label("Time: 00:00:00");

        sortingLogArea = new TextArea();
        sortingLogArea.setEditable(false);
        sortingLogArea.setPrefHeight(200);

        distributingLogArea = new TextArea();
        distributingLogArea.setEditable(false);
        distributingLogArea.setPrefHeight(200);

        mainLayout.getChildren().addAll(inputLayout, buttonLayout, timeLabel, sortingLogArea, distributingLogArea);

        startButton.setOnAction(e -> startSimulation());
        stopButton.setOnAction(e -> setTimeMultiplier(0));
        speedUp1xButton.setOnAction(e -> setTimeMultiplier(1));
        speedUp2xButton.setOnAction(e -> setTimeMultiplier(2));
        speedUp4xButton.setOnAction(e -> setTimeMultiplier(4));

        railPane = new Pane();
        railPane.setPrefSize(1300, 900);
        railPane.setStyle("-fx-background-color: #5e5e5e;");

        mainLayout.getChildren().add(railPane);

        Scene scene = new Scene(mainLayout, 1300, 900);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void startSimulation() {
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

        items = Recyclableitem.createList(numObjects);
        sorter = new Sorter(1, 5.0, "Moha");
        distributor = new Distributor(2, 5.0, "Spotty");

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
                for (Recyclableitem item : items) {
                    // Simulate sorting and distributing using sensors
                    if (!item.isDone_sorting()) {
                        sorter.sort(item);
                        item.setisDone_sorting(true);
                        sorter.incrementItemsDone();
                    }
                    if (!item.isdone_distribute()) {
                        distributor.distributeItem(item);
                        item.setisdone_distribute(true);
                        distributor.incrementItemsDone();
                    }
                    // Draw item on railPane
                    ImageView imageView = new ImageView(getImageForType(item.getItemType()));
                    imageView.setX(100); // Example X position
                    imageView.setY(100); // Example Y position
                    imageView.setFitWidth(30);
                    imageView.setFitHeight(30);
                    railPane.getChildren().add(imageView);
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

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
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

    public static void main(String[] args) {
        launch(args);
    }
}