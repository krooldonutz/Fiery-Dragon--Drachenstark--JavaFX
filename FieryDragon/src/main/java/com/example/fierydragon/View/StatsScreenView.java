package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Animals.Animal;
import com.example.fierydragon.Controller.GameStateController;
import com.example.fierydragon.Utils.AnimalDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;

public class StatsScreenView {
    private final Stage stage;

    public StatsScreenView(Stage stage) {
        this.stage = stage;
    }

    public void showStatsScreen(String saveFilePath) {
        // Load the saved game state
        GameStateController gameStateController = loadGameState(saveFilePath);

        if (gameStateController != null) {
            // Extract player statistics
            ArrayList<Player> players = gameStateController.getBoard().getPlayerList();
            players.sort(Comparator.comparingInt(Player::getSteps).reversed()); // Sort players by steps

            // Create the table
            TableView<PlayerStats> table = new TableView<>();
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<PlayerStats, String> playerNameCol = new TableColumn<>("Player");
            playerNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            playerNameCol.setStyle("-fx-font-weight: bold; -fx-alignment: CENTER;");

            TableColumn<PlayerStats, Image> imageCol = new TableColumn<>("Image");
            imageCol.setCellValueFactory(new PropertyValueFactory<>("playerImage"));
            imageCol.setCellFactory(param -> new TableCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(Image image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null) {
                        setGraphic(null);
                    } else {
                        imageView.setImage(image);
                        imageView.setFitWidth(50); // adjust the width and height as needed
                        imageView.setFitHeight(50);
                        setGraphic(imageView);
                    }
                }
            });

            TableColumn<PlayerStats, Integer> stepsCol = new TableColumn<>("Total Steps");
            stepsCol.setCellValueFactory(new PropertyValueFactory<>("steps"));

            TableColumn<PlayerStats, Integer> backwardStepsCol = new TableColumn<>("Backward Steps");
            backwardStepsCol.setCellValueFactory(new PropertyValueFactory<>("backwardSteps"));

            TableColumn<PlayerStats, Integer> wrongClickCol = new TableColumn<>("Wrong Guesses");
            wrongClickCol.setCellValueFactory(new PropertyValueFactory<>("wrongClicks"));

            TableColumn<PlayerStats, Double> wrongClickRatioCol = new TableColumn<>("Wrong Click Ratio");
            wrongClickRatioCol.setCellValueFactory(new PropertyValueFactory<>("wrongClickRatio"));

            TableColumn<PlayerStats, Integer> totalMovesCol = new TableColumn<>("Total Moves");
            totalMovesCol.setCellValueFactory(new PropertyValueFactory<>("totalMoves"));

            TableColumn<PlayerStats, Integer> successfulMovesCol = new TableColumn<>("Successful Moves");
            successfulMovesCol.setCellValueFactory(new PropertyValueFactory<>("successfulMoves"));

            TableColumn<PlayerStats, Integer> failedMovesCol = new TableColumn<>("Failed Moves");
            failedMovesCol.setCellValueFactory(new PropertyValueFactory<>("failedMoves"));

            TableColumn<PlayerStats, Double> successRateCol = new TableColumn<>("Success Rate");
            successRateCol.setCellValueFactory(new PropertyValueFactory<>("successRate"));

            TableColumn<PlayerStats, Double> failureRateCol = new TableColumn<>("Failure Rate");
            failureRateCol.setCellValueFactory(new PropertyValueFactory<>("failureRate"));

            table.getColumns().addAll(playerNameCol, imageCol,stepsCol, backwardStepsCol, wrongClickCol, wrongClickRatioCol, totalMovesCol, successfulMovesCol, failedMovesCol, successRateCol, failureRateCol);

            // Add player statistics to the table
            for (Player player : players) {
                table.getItems().add(new PlayerStats(player));
            }

            // Create a label to indicate the number of players
            Label numPlayersLabel = new Label("Number of Players: " + players.size());
            numPlayersLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");

            // Create a back button
            Button backButton = new Button("Back");
            backButton.setOnAction(e -> {
                stage.close();
                new UserInterface().start(new Stage()); // Assuming UserInterface is your main menu class
            });

            // Create the layout and add the table
            VBox vbox = new VBox(10, new Label("Player Statistics"), numPlayersLabel, table, backButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setBackground(new Background(new BackgroundFill(Color.ORANGE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));

            Scene scene = new Scene(new StackPane(vbox), 800, 600);
            Platform.runLater(() -> {
                stage.setScene(scene);
                stage.setTitle("Stats Screen");
                stage.show();
            });
        }
    }

    public GameStateController loadGameState(String filename) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Animal.class, new AnimalDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            return gson.fromJson(json, GameStateController.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class PlayerStats {
        private final String name;
        private final int steps;
        private final int backwardSteps;
        private final int wrongClicks;
        private final double wrongClickRatio;
        private final int totalMoves;
        private final int successfulMoves;
        private final int failedMoves;
        private final double successRate;
        private final double failureRate;
        private final Image playerImage;

        public PlayerStats(Player player) {
            this.name = player.getName();
            this.steps = player.getSteps();
            this.backwardSteps = player.getBackwardSteps();
            this.wrongClicks = player.getWrongClick();
            this.wrongClickRatio = player.getWrongClickRatio();
            this.totalMoves = player.getTotalMoves();
            this.successfulMoves = player.getSuccessfulMoves();
            this.failedMoves = player.getFailedMoves();
            this.successRate = player.getSuccessRate();
            this.failureRate = player.getFailureRate();
            this.playerImage = new Image(getClass().getClassLoader().getResource("com/example/fierydragon/Actors/" + player.getName() + ".png").toString());
        }

        public String getName() {
            return name;
        }

        public int getSteps() {
            return steps;
        }

        public int getBackwardSteps() {
            return backwardSteps;
        }

        public int getWrongClicks() {
            return wrongClicks;
        }

        public double getWrongClickRatio() {
            return wrongClickRatio;
        }

        public int getTotalMoves() {
            return totalMoves;
        }

        public int getSuccessfulMoves() {
            return successfulMoves;
        }

        public int getFailedMoves() {
            return failedMoves;
        }

        public double getSuccessRate() {
            return successRate;
        }

        public double getFailureRate() {
            return failureRate;
        }
        public Image getPlayerImage() {
            return playerImage;
        }
    }
}