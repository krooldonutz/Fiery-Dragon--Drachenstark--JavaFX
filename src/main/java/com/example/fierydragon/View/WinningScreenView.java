package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

/**
 * The WinningScreenView class is responsible for displaying the game over screen
 * when a player wins the game. It shows the winning player on a gold podium and
 * the other players on brown podiums. The screen also includes a winning message
 * and a close button to exit the application.
 */
public class WinningScreenView {

    /**
     * Shows the winning screen with the winning player and the other players.
     *
     * @param winner  The player who won the game.
     * @param players The list of all players in the game.
     */
    public static void showWinningScreen(Player winner, List<Player> players) {
        VBox winningLayout = new VBox(20);
        winningLayout.setAlignment(Pos.CENTER);
        winningLayout.setBackground(new Background(new BackgroundImage(
                new Image(WinningScreenView.class.getClassLoader().getResource("Background.png").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false))));

        // Create and configure the winner's podium
        StackPane winnerPane = createWinnerPane(winner);

        // Create and configure the losers' podium
        HBox losersPane = createLosersPane(players, winner);

        // Create the winning message label
        Label winningMessage = new Label(winner.getName() + " wins the game!");
        winningMessage.setTextFill(Color.GOLDENROD);
        winningMessage.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Create the close button with custom styles
        Button closeButton = new Button("Close");
        closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-text-fill: black; -fx-font-family: Chiller; -fx-font-size: 40;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-font-family: Chiller; -fx-font-size: 40; -fx-text-fill: green;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; " +
                "-fx-font-family: Chiller; -fx-font-size: 40; -fx-text-fill: black;"));
        closeButton.setOnAction(e -> Platform.exit());

        // Add all components to the layout
        winningLayout.getChildren().addAll(winningMessage, winnerPane, losersPane, closeButton);

        // Create the scene and set it on a new stage
        Scene winningScene = new Scene(winningLayout, 1000, 570);
        Stage newWindow = new Stage();
        newWindow.setTitle("Game Over");
        newWindow.setScene(winningScene);
        newWindow.show();
        newWindow.setResizable(false);
    }

    /**
     * Creates the winner's podium pane.
     *
     * @param winner The player who won the game.
     * @return The StackPane representing the winner's podium.
     */
    private static StackPane createWinnerPane(Player winner) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(150);
        rectangle.setWidth(150);
        rectangle.setFill(Color.GOLD);

        Text rectangleText = new Text("1st Place");
        rectangleText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        rectangleText.setFill(Color.BLACK);

        ImageView winnerImage = new ImageView(new Image(
                WinningScreenView.class.getClassLoader().getResource("com/example/fierydragon/Actors/" + winner.getName().toLowerCase() + ".png").toString()));
        winnerImage.setFitHeight(100);
        winnerImage.setFitWidth(100);

        StackPane winnerPane = new StackPane(rectangle, winnerImage, rectangleText);
        StackPane.setAlignment(winnerImage, Pos.TOP_CENTER);
        StackPane.setAlignment(rectangleText, Pos.BOTTOM_CENTER);
        winnerPane.setAlignment(Pos.CENTER);

        return winnerPane;
    }

    /**
     * Creates the losers' podium pane.
     *
     * @param players The list of all players in the game.
     * @param winner  The player who won the game.
     * @return The HBox representing the losers' podiums.
     */
    private static HBox createLosersPane(List<Player> players, Player winner) {
        HBox losersLayout = new HBox(10);
        losersLayout.setAlignment(Pos.CENTER);

        for (Player player : players) {
            if (!player.equals(winner)) {
                StackPane loserPane = createLoserPane(player);
                losersLayout.getChildren().add(loserPane);
            }
        }

        return losersLayout;
    }

    /**
     * Creates an individual loser's podium pane.
     *
     * @param loser The player who lost the game.
     * @return The StackPane representing the loser's podium.
     */
    private static StackPane createLoserPane(Player loser) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(100);
        rectangle.setWidth(100);
        rectangle.setFill(Color.SADDLEBROWN);

        Text rectangleText = new Text("Loser");
        rectangleText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        rectangleText.setFill(Color.WHITE);

        ImageView loserImage = new ImageView(new Image(
                WinningScreenView.class.getClassLoader().getResource("com/example/fierydragon/Actors/" + loser.getName().toLowerCase() + ".png").toString()));
        loserImage.setFitHeight(70);
        loserImage.setFitWidth(70);

        StackPane loserPane = new StackPane(rectangle, loserImage, rectangleText);
        StackPane.setAlignment(loserImage, Pos.TOP_CENTER);
        StackPane.setAlignment(rectangleText, Pos.BOTTOM_CENTER);
        loserPane.setAlignment(Pos.CENTER);

        return loserPane;
    }
}
