package com.example.fierydragon.View;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Chits.ChitCard;
import com.example.fierydragon.Controller.GameStateController;
import com.example.fierydragon.Grounds.Board;
import com.example.fierydragon.Animals.Animal;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * The ChitView class is responsible for managing the visual representation of the chit cards in the game.
 * It includes methods for initializing and resetting the chit cards, as well as handling user interactions.
 */
public class ChitView {
    private final Board board;
    private final Pane chitCardPane;
    private final double chitCardScale;
    private final double boardWidth;
    private final double boardHeight;
    private final GameStateController gameStateController;

    /**
     * Constructor for ChitView.
     *
     * @param board               The game board.
     * @param chitCardPane        The pane to hold chit cards.
     * @param chitCardScale       The scale for chit cards.
     * @param boardWidth          The width of the game board.
     * @param boardHeight         The height of the game board.
     * @param gameStateController The controller to manage game state.
     */
    public ChitView(Board board, Pane chitCardPane, double chitCardScale, double boardWidth, double boardHeight, GameStateController gameStateController) {
        this.board = board;
        this.chitCardPane = chitCardPane;
        this.chitCardScale = chitCardScale;
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.gameStateController = gameStateController;
    }

    /**
     * Initializes the chit cards on the board.
     */
    public void initializeChitCards() {
        int size = board.getChitCardHashMap().size();
        int rows = (int) Math.sqrt(size);
        double totalWidth = 4 * 60 / chitCardScale;
        double totalHeight = 4 * 60 / chitCardScale;
        double startX = (boardWidth - totalWidth + 16) / 2;
        double startY = (boardHeight - totalHeight) / 2;

        for (int i = 0; i < size; i++) {
            ChitCard chitCard = board.getChitCardHashMap().get(Integer.toString(i));
            Image chitCardImage = new Image(chitCard.getPictureURL());
            Image animalImage = new Image(chitCard.getAnimal().getPictureURL());

            ImageView imageView = new ImageView(chitCardImage);
            imageView.setFitWidth(50 / chitCardScale);
            imageView.setFitHeight(50 / chitCardScale);

            Circle circle = new Circle(25 / chitCardScale, new ImagePattern(chitCardImage));
            circle.setFill(Color.BEIGE);

            StackPane stack = new StackPane();
            stack.setLayoutX(startX + (i / 4 * 2) * board.getChitCardHashMap().size());
            stack.setLayoutY(startY + (i % 4 * 2) * board.getChitCardHashMap().size());
            stack.getChildren().addAll(circle, imageView);

            stack.setOnMouseClicked(e -> {
                stack.setDisable(true);
                imageView.setImage(animalImage);
                int playerPosition = board.getPlayerList().get(gameStateController.getCurrentPlayerIndex()).getPositionNow();
                Player currentPlayer = board.getPlayerList().get(gameStateController.getCurrentPlayerIndex());
                Animal currentAnimal = chitCard.getAnimal();

                // Add multiple animal images based on steps
                for (int j = 0; j < chitCard.getSteps(); j++) {
                    ImageView animalImageView = new ImageView(animalImage);
                    animalImageView.setFitWidth(15 / chitCardScale);
                    animalImageView.setFitHeight(15 / chitCardScale);
                    animalImageView.setTranslateX((j - (chitCard.getSteps() - 1) / 2.0) * 20 / chitCardScale);
                    stack.getChildren().add(animalImageView);
                }

                if (gameStateController.isPlayerInCave(currentPlayer) && gameStateController.isAnimalMatchInCave(currentPlayer, currentAnimal)) {
                    gameStateController.onMoveButtonClick(currentAnimal, chitCard, true);
                } else if (gameStateController.isAnimalMatchOnBoard(currentPlayer, currentAnimal)) {
                    gameStateController.onMoveButtonClick(currentAnimal, chitCard, true);
                } else if (gameStateController.isDragonPirate(currentAnimal)) {
                    gameStateController.onMoveButtonClick(currentAnimal, chitCard, true);
                } else if (gameStateController.isReverseDragon(currentAnimal) && !gameStateController.isPlayerInCave(currentPlayer)){
                    gameStateController.onMoveButtonClick(currentAnimal, chitCard, true);
                } else {
                    gameStateController.onMoveButtonClick(currentAnimal, chitCard, false);
                }
            });

            chitCardPane.getChildren().add(stack);
        }
    }

    /**
     * Resets the chit cards to their initial state.
     */
    public void resetChitCards() {
        for (var node : chitCardPane.getChildren()) {
            if (node instanceof StackPane stack) {
                stack.setDisable(false);
                ImageView imageView = (ImageView) stack.getChildren().get(1);
                int index = chitCardPane.getChildren().indexOf(stack);
                ChitCard chitCard = board.getChitCardHashMap().get(Integer.toString(index));
                imageView.setImage(new Image(chitCard.getPictureURL()));

                // Remove all children except the circle and the single imageView
                stack.getChildren().removeIf(child -> !(child instanceof Circle) && !(child instanceof ImageView && child == imageView));
            }
        }
    }

    public void disableChitCards() {
        for (var node : chitCardPane.getChildren()) {
            if (node instanceof StackPane stack) {
                stack.setDisable(true);
            }
        }
    }

    public void enableChitCards() {
        for (var node : chitCardPane.getChildren()) {
            if (node instanceof StackPane stack) {
                stack.setDisable(false);
            }

        }
    }
}