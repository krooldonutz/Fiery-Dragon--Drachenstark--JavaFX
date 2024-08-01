package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import javafx.scene.image.ImageView;

/**
 * The PlayerView class is responsible for managing the visual representation
 * of a player in the game. It includes methods for adding a player image to
 * the board and retrieving the ImageView of the player.
 */
public class PlayerView {
    private final int cellSize;
    private ImageView playerImageView;

    /**
     * Constructor for PlayerView.
     *
     * @param cellSize The size of each cell on the game board.
     */
    public PlayerView(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Adds a player's image to the specified coordinates on the board.
     *
     * @param player The player whose image is to be added.
     * @param x      The x-coordinate where the player's image will be placed.
     * @param y      The y-coordinate where the player's image will be placed.
     */
    public void addPlayer(Player player, double x, double y) {
        String playerName = player.getName();
        playerImageView = new ImageView(getClass().getClassLoader().getResource("com/example/fierydragon/Actors/" + playerName + ".png").toString());
        playerImageView.setFitWidth(cellSize / 2);
        playerImageView.setFitHeight(cellSize / 2);
        playerImageView.setX(x - cellSize / 2);
        playerImageView.setY(y - cellSize / 2);
    }

    /**
     * Retrieves the ImageView of the player.
     *
     * @return The ImageView of the player.
     */
    public ImageView getPlayerImageView() {
        return playerImageView;
    }
}
