package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Grounds.Board;
import com.example.fierydragon.Grounds.Cave;
import com.example.fierydragon.Animals.Animal;
import com.example.fierydragon.Grounds.Volcano;
import com.google.gson.annotations.Expose;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * The BoardView class is responsible for rendering the game board, players, and caves.
 * It extends the Pane class and provides methods to update and draw the board elements.
 */
public class BoardView extends Pane {
    private final double boardWidth = 1000;
    private final double boardHeight = 600;
    private final double centerX = boardWidth / 2;
    private final double centerY = boardHeight / 2;
    private final int cellSize = 37; // Size of each cell
    private final double circleRadius = 275; // Radius of the board's circle
    private final double adjustedCircleRadius = circleRadius - cellSize; // This reduces the radius to bring cells inward

    public void setBoard(Board board) {
        this.board = board;
    }
    private Board board;
    private final HashMap<String, Image> imageCache = new HashMap<>();
    @Expose
    private Player currentPlayerTurn;
    private Text currentPlayerText = new Text();
    private Rectangle backgroundBox = new Rectangle();
    private boolean arcsInitialized = false;


    /**
     * Constructor for the BoardView class.
     *
     * @param board The Board object representing the game board.
     */
    public BoardView(Board board) {
        this.board = board;
        drawBoard();
        currentPlayerTurn = board.getPlayerList().get(0); // Set the current player to the first player in the list
        Platform.runLater(this::addCurrentPlayerText); // Add the current player text label
    }

    /**
     * Updates the board by clearing and redrawing it.
     */
    public void updateBoard(boolean loadGame) {
        clearBoard();
        drawBoard();
        if (loadGame){
            Platform.runLater(this::addCurrentPlayerText);
        }
        else {
            addCurrentPlayerText();
        }
        System.out.println("Layout Children called");
    }

    public double getBoardWidth() {
        return this.boardWidth;
    }

    public double getBoardHeight() {
        return this.boardHeight;
    }

    public Pane getBoardPane() {
        return this;
    }

    /**
     * Loads an image from a URL and caches it for future use.
     *
     * @param imageURL The URL of the image to be loaded.
     * @return The loaded Image object.
     */
    private Image loadImage(String imageURL) {
        // Check if the image is already cached
        Image cachedImage = imageCache.get(imageURL);
        if (cachedImage != null) {
            return cachedImage; // Return the cached image
        } else {
            // Load the image
            Image newImage = new Image(imageURL);
            // Cache the loaded image
            imageCache.put(imageURL, newImage);
            return newImage; // Return the newly loaded image
        }
    }

    /**
     * Draws the entire game board, including cells, players, and caves.
     */
    private void drawBoard() {
        int totalCells = board.getBOARD_SIZE();
        double angleStep = 360.0 / totalCells;
        double outerRadius = Math.min(centerX, centerY) - (double) 50 / 2;
        double innerRadius = outerRadius - (double) 50 / 2;

        loadBackgroundImage();
        int cellCount = 0;
        PlayerView playerView = new PlayerView(cellSize);
        CaveView caveView = new CaveView(cellSize);
        for (Volcano volcano : board.getVolcanoes()){
            for ( int i=0; i < volcano.getCellCount(); i++) {
                double currentAngle = angleStep * cellCount; // Calculate the current angle based on the cell index
                drawCell(cellCount, angleStep, volcano,i);
                Map.Entry<String, Player> currPlayer = getFirstPlayerInCell(cellCount, volcano, i);
                if (currPlayer != null) {
                    drawPlayer(playerView, currPlayer.getValue(), cellCount, angleStep);
                }
                if (i == volcano.getCellCount()/2) {
                    Cave cave = board.getCaveHashMap().get(Integer.toString(volcano.getVolcanoNumber() ));
                    if (cave != null) {
                        drawCave(caveView, cave,volcano, i, currentAngle);
                    }
                }
                cellCount++;
            }
        }
    }

    /**
     * Draws a single cell on the board.
     *
     * @param i        The index of the cell.
     * @param angleStep The angle step for positioning the cell.
     */
    private void drawCell(int i, double angleStep, Volcano volcano, int cellNum) {
        double angle = Math.toRadians(angleStep * i);

        double imageX = centerX + adjustedCircleRadius * Math.cos(angle);
        double imageY = centerY + adjustedCircleRadius * Math.sin(angle);

        Rectangle cellCard = new Rectangle(cellSize, cellSize + 10);
        cellCard.setFill(Color.PEACHPUFF);
        cellCard.setStroke(Color.DARKCYAN);
        cellCard.setStrokeWidth(1);

        cellCard.setX(imageX - cellSize / 2);
        cellCard.setY(imageY - cellSize / 2);

        Animal currAnimal = volcano.getCells().get(cellNum).getAnimalLabel();
        Image animalImage = imageCache.get(currAnimal.getPictureURL());
        if (animalImage == null) {
            System.out.println(currAnimal.getPictureURL() + currAnimal.getAnimal());
            animalImage = loadImage(currAnimal.getPictureURL());
            imageCache.put(currAnimal.getPictureURL(), animalImage);
        }

        ImageView cellImageView = new ImageView(animalImage);
        cellImageView.setFitWidth(cellSize);
        cellImageView.setFitHeight(cellSize);
        cellImageView.setX(imageX - (double) cellSize / 2);
        cellImageView.setY(imageY - (double) cellSize / 2);

        if (!this.getChildren().contains(cellCard)) {
            this.getChildren().add(cellCard);
        }
        if (!this.getChildren().contains(cellImageView)) {
            this.getChildren().add(cellImageView);
        }

    }

    /**
     * Draws a player on the board.
     *
     * @param playerView The PlayerView object for rendering the player.
     * @param player     The Player object to be drawn.
     * @param i          The index of the cell where the player is located.
     * @param angleStep  The angle step for positioning the player.
     */
    private void drawPlayer(PlayerView playerView, Player player, int i, double angleStep) {
        double angle = Math.toRadians(angleStep * i);
        double imageX = centerX + adjustedCircleRadius * Math.cos(angle);
        double imageY = centerY + adjustedCircleRadius * Math.sin(angle);

        playerView.addPlayer(player, imageX, imageY);
        if (!this.getChildren().contains(playerView.getPlayerImageView())) {
            this.getChildren().add(playerView.getPlayerImageView());
        }
    }


    private void drawCave(CaveView caveView, Cave cave, Volcano volcano, int i, double currentAngle) {
        double radiusOffset = cellSize * 1.5;
        double angle = Math.toRadians(currentAngle);
        double caveX = centerX  + (adjustedCircleRadius + radiusOffset) * Math.cos(angle);
        double caveY = centerY  + (adjustedCircleRadius + radiusOffset )* Math.sin(angle);

        // Use the calculated position for the cave
        caveView.addCave(cave, caveX, caveY);
        if (caveView.getPlayerImageView() != null) {
            if (!this.getChildren().contains(caveView.getCaveImageView())) {
                this.getChildren().add(caveView.getCaveImageView());
            }
            if (!this.getChildren().contains(caveView.getAniImageView())) {
                this.getChildren().add(caveView.getAniImageView());
            }
            if (!this.getChildren().contains(caveView.getPlayerImageView())) {
                this.getChildren().add(caveView.getPlayerImageView());
            }
        } else {
            if (!this.getChildren().contains(caveView.getCaveImageView())) {
                this.getChildren().add(caveView.getCaveImageView());
            }
            if (!this.getChildren().contains(caveView.getAniImageView())) {
                this.getChildren().add(caveView.getAniImageView());
            }
        }
    }

    /**
     * Retrieves the first player in a given cell.
     *
     * @param i The index of the cell.
     * @return The first player in the cell, or null if no players are present.
     */
    private Map.Entry<String, Player> getFirstPlayerInCell(int i, Volcano volcano, int cellNum){
        Map<String, Player> currPlayerHashMap = volcano.getCells().get(cellNum).getPlayersInside();
        Map.Entry<String, Player> currPlayer = null;
        if (!currPlayerHashMap.isEmpty()) {
            System.out.println("Player found at cell " + cellNum + " in volcano " + volcano.getId() + " : " + currPlayerHashMap.entrySet().iterator().next().getValue().getName());            currPlayer = currPlayerHashMap.entrySet().iterator().next();
        }
        return currPlayer;
    }

    /**
     * Loads the background image for the board.
     */
    private void loadBackgroundImage() {
        Image bgImage = loadImage(getClass().getClassLoader().getResource("Background.png").toString());
        BackgroundImage bg = new BackgroundImage(bgImage,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        this.setBackground(new Background(bg));
    }

    /**
     * Adds a text label to display the current player's turn.
     */
    private void addCurrentPlayerText() {
        currentPlayerText = new Text("Current Player: " + currentPlayerTurn.getName()); // Assuming Player has a getName() method
        currentPlayerText.setFont(Font.font(16)); // Adjust font size if needed
        currentPlayerText.setTranslateX(getWidth() - 200); // Adjust position as needed
        currentPlayerText.setTranslateY(20); // Adjust position as needed

        // Create a white background box
        backgroundBox = new Rectangle(currentPlayerText.getBoundsInLocal().getWidth() + 60, currentPlayerText.getBoundsInLocal().getHeight() + 10);
        backgroundBox.setFill(Color.WHITE);
        backgroundBox.setStroke(Color.BLACK);
        backgroundBox.setTranslateX(currentPlayerText.getTranslateX() - 30); // Adjust position to align with the text
        backgroundBox.setTranslateY(currentPlayerText.getTranslateY() - currentPlayerText.getBoundsInLocal().getHeight() - 5); // Adjust position to align with the text

        ImageView playerImage = new ImageView(String.valueOf(getClass().getClassLoader().getResource("com/example/fierydragon/Actors/" + currentPlayerTurn.getName() + ".png")));
        // Create a circle representing the player's color
        playerImage.setTranslateX(currentPlayerText.getTranslateX() - 40); // Adjust position to align with the background box
        playerImage.setTranslateY(currentPlayerText.getTranslateY() - 20); // Adjust position to center vertically within the background box

        getChildren().addAll(backgroundBox, playerImage, currentPlayerText);
    }

    /**
     * Sets the current player and updates the display.
     *
     * @param player The Player object representing the current player.
     */
    public void setCurrentPlayer(Player player) {
        currentPlayerTurn = player;
        currentPlayerText.setText("Current Player: " + currentPlayerTurn.getName());

        // Update the background box size to fit the updated text
        backgroundBox.setWidth(currentPlayerText.getBoundsInLocal().getWidth() + 60);
        backgroundBox.setHeight(currentPlayerText.getBoundsInLocal().getHeight() + 10);
        backgroundBox.setTranslateX(currentPlayerText.getTranslateX() - 30); // Adjust position to align with the text
        backgroundBox.setTranslateY(currentPlayerText.getTranslateY() - currentPlayerText.getBoundsInLocal().getHeight() - 5); // Adjust position to align with the text
    }

    /**
     * Clears the board by removing all children from the Pane.
     */
    private void clearBoard() {
        getChildren().clear();
    }
}
