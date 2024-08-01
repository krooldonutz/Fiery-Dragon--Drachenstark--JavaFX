package com.example.fierydragon.View;

import com.example.fierydragon.Controller.GameStateController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class SaveButtonPane extends Pane {
    private Button saveButton;
    private Label toastLabel;

    public SaveButtonPane(GameStateController gameStateController, Pane pane) {
        saveButton = new Button("Save Game");
        saveButton.setOnAction(e -> {
            // Save the game
            gameStateController.saveGameState();
            showToast("Game saved!");
        });
        saveButton.setLayoutX(pane.getWidth()); // Adjust these values as needed
        saveButton.setLayoutY(pane.getHeight()); // Adjust these values as needed
        pane.getChildren().add(saveButton);

        // Initialize the toast label
        toastLabel = new Label();
        toastLabel.setLayoutX(pane.getWidth() / 2);
        toastLabel.setLayoutY(pane.getHeight() / 2);
        toastLabel.setVisible(false);
        pane.getChildren().add(toastLabel);
    }

    public Button getSaveButton() {
        return saveButton;
    }

    private void showToast(String text) {
        toastLabel.setText(text);
        toastLabel.setVisible(true);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> toastLabel.setVisible(false)));
        timeline.play();
    }
}