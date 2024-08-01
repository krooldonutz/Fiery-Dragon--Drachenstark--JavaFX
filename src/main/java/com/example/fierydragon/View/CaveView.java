package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Grounds.Cave;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * The CaveView class is responsible for managing the visual representation of caves in the game.
 * It includes methods for adding cave images and the player images if they are present in the cave.
 */
public class CaveView {
    private final int cellSize;
    private ImageView playerImageView;
    private ImageView caveImageView;
    private ImageView aniImageView;

    /**
     * Constructor for CaveView.
     *
     * @param cellSize The size of each cell in the game board.
     */
    public CaveView(int cellSize) {
        this.cellSize = cellSize;
    }

    /**
     * Gets the ImageView for the cave.
     *
     * @return The ImageView for the cave.
     */
    public ImageView getCaveImageView() {
        return caveImageView;
    }

    /**
     * Gets the ImageView for the player.
     *
     * @return The ImageView for the player.
     */
    public ImageView getPlayerImageView() {
        return playerImageView;
    }

    /**
     * Gets the ImageView for the animal.
     *
     * @return The ImageView for the animal.
     */
    public ImageView getAniImageView() {
        return aniImageView;
    }

    /**
     * Adds a cave image and, if present, a player image to the specified coordinates.
     *
     * @param cave The cave to add.
     * @param x    The x-coordinate for the cave image.
     * @param y    The y-coordinate for the cave image.
     */
    public void addCave(Cave cave, double x, double y) {
        // Load and set the cave image
        Image caveImage = new Image(cave.getCaveURL());
        caveImageView = new ImageView(caveImage);
        caveImageView.setFitWidth(cellSize);
        caveImageView.setFitHeight(cellSize);
        caveImageView.setX(x - caveImageView.getFitWidth() / 2);
        caveImageView.setY(y - caveImageView.getFitHeight() / 2);

        // Load and set the animal image
        aniImageView = new ImageView(new Image(cave.getAnimalLabel().getPictureURL()));
        aniImageView.setFitWidth(cellSize / 2);
        aniImageView.setFitHeight(cellSize / 2);
        aniImageView.setX(x - aniImageView.getFitWidth() / 2);
        aniImageView.setY(y - aniImageView.getFitHeight() / 2);

        // If the cave is not empty, load and set the player image
        if (!cave.isEmpty()) {
            Player playerInCave = cave.getPlayersInside().values().iterator().next();
            if (playerInCave.isInCave()) {
                String playerName = playerInCave.getName();
                playerImageView = new ImageView(String.valueOf(getClass().getClassLoader().getResource("com/example/fierydragon/Actors/" + playerName + ".png")));
                playerImageView.setFitWidth((double) cellSize / 2);
                playerImageView.setFitHeight((double) cellSize / 2);
                playerImageView.setX(x - (double) cellSize / 2);
                playerImageView.setY(y - (double) cellSize / 2);
            }
        }
    }
}
