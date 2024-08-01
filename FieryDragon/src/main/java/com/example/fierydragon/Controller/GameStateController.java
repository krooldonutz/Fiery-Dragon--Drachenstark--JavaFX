package com.example.fierydragon.Controller;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Animals.Bat;
import com.example.fierydragon.Chits.ChitCard;
import com.example.fierydragon.Grounds.Board;
import com.example.fierydragon.Utils.AnimalDeserializer;
import com.example.fierydragon.View.BoardView;
import com.example.fierydragon.View.ChitView;
import com.example.fierydragon.Animals.Animal;
import com.example.fierydragon.Animals.animalType;
import com.example.fierydragon.View.SaveButtonPane;
import com.google.gson.annotations.Expose;
import javafx.animation.PauseTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * The GameStateController class manages the state and actions of the game,
 * including player movements, handling chit card actions, and updating the game board.
 */
public class GameStateController {
    @Expose
    private Board board; // The game board
    @Expose
    private final BoardView boardPane; // The view for the game board
    private transient final ChitView chitView; // The view for the chit cards
    @Expose
    private int currentPlayerIndex = 0; // The index of the current player
    private transient Pane chitCardPane; // The pane for the chit cards

    /**
     * Constructs a GameStateController with the specified board, board view, and chit card pane.
     * Initializes the chit view and chit cards.
     *
     * @param board the game board
     * @param boardPane the view for the game board
     * @param chitCardPane the pane for the chit cards
     */
    public GameStateController(Board board, BoardView boardPane, Pane chitCardPane) {
        this.board = board;
        this.boardPane = boardPane;
        this.chitCardPane = chitCardPane;
        this.chitView = new ChitView(board, chitCardPane, 1.7, boardPane.getBoardWidth(), boardPane.getBoardHeight(), this);
        chitView.initializeChitCards();
        addSaveButton();
    }

    /**
     * Handles the move button click action, moving the current player if allowed
     * and updating the game state accordingly.
     *
     * @param lastFlippedAnimal the last flipped animal card
     * @param chitCard the chit card used for the move
     * @param move a boolean indicating whether the move action is valid
     */
    public void onMoveButtonClick(Animal lastFlippedAnimal, ChitCard chitCard, boolean move) {
        Player currentPlayer = board.getPlayerList().get(currentPlayerIndex);

        boolean success = false;
        if (!move) {
            currentPlayer.incrementWrongClick();
        } else {
            success = board.getMoveAction().movePlayer(currentPlayer, chitCard, board, this);
            System.out.println(success);
        }

        if (!success || !move) {
            incrementCurrentPlayer(board.getPlayerList());
            boardPane.updateBoard(false);
        }
        if (isRobinCell(currentPlayer)){
            handleRobinCard(currentPlayer);
            incrementCurrentPlayer(board.getPlayerList());
            boardPane.updateBoard(false);
        }

        boardPane.updateBoard(false);
        addSaveButton();
    }

    /**
     * Increments the current player index to the next player and updates the game state.
     *
     * @param players the list of players in the game
     */
    private void incrementCurrentPlayer(ArrayList<Player> players) {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        Player currentPlayer = players.get(currentPlayerIndex);
        System.out.println(currentPlayer.getName() + "'s turn");
        boardPane.setCurrentPlayer(currentPlayer);
        resetChitCardAfterPause();
    }

    /**
     * Resets the chit card after a short pause.
     */
    private void resetChitCardAfterPause() {
        chitView.disableChitCards();
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            chitView.enableChitCards();
            chitView.resetChitCards();
                });
        pause.play();
    }

    /**
     * Returns the index of the current player.
     *
     * @return the current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Checks if the specified player is in a cave.
     *
     * @param player the player to check
     * @return true if the player is in a cave, false otherwise
     */
    public boolean isPlayerInCave(Player player) {
        return player.isInCave();
    }

    /**
     * Checks if the specified animal matches the animal in the cave of the specified player.
     *
     * @param player the player whose cave to check
     * @param animal the animal to match
     * @return true if the animal matches, false otherwise
     */
    public boolean isAnimalMatchInCave(Player player, Animal animal) {
        return board.getCaveHashMap().get(Integer.toString(player.getPositionNow())).getAnimalLabel().getAnimal().equals(animal.getAnimal());
    }


    public boolean isAnimalMatchOnBoard(Player player, Animal animal) {
        if (board.findPlayerCurrentCell(player) == null) {
            return false;
        }
        return board.findPlayerCurrentCell(player).getAnimalLabel().getAnimal().equals(animal.getAnimal());
    }

    /**
     * Checks if the specified animal is a dragon pirate.
     *
     * @param animal the animal to check
     * @return true if the animal is a dragon pirate, false otherwise
     */
    public boolean isDragonPirate(Animal animal) {
        return animal.getAnimal() == animalType.DRAGON_PIRATE;
    }

    public boolean isReverseDragon(Animal animal) {
        return animal.getAnimal() == animalType.REVERSE_DRAGON;
    }

    /**
     * Checks if the specified player's current position is a Thief cell.
     *
     * @param player the player whose position to check
     * @return true if the cell contains a Thief, false otherwise
     */
    public boolean isRobinCell(Player player) {
        System.out.println(board.findPlayerCurrentCell(player).getAnimalLabel().getAnimal() + " is the animal");
        return board.findPlayerCurrentCell(player).getAnimalLabel().getAnimal() == animalType.THIEF;
    }

    private void handleRobinCard(Player player) {
        Random rand = new Random();
        boolean moveForward = rand.nextBoolean();
        int steps = 3;
        Animal animal = new Bat();
        if (moveForward) {
            board.getMoveAction().movePlayer(player, new ChitCard(animal, 0, steps), board, this);
            System.out.println(player.getName() + " moved forward 3 steps.");
        } else {
            board.getMoveAction().movePlayer(player, new ChitCard(animal, 0, steps * -1), board, this);
            System.out.println(player.getName() + " moved backward 3 steps.");
        }

        incrementCurrentPlayer(board.getPlayerList());
    }

    public void saveGameState() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Game State");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null) {
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .setPrettyPrinting()
                    .create();
            String json = gson.toJson(this);
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadGameState(String filename) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Animal.class, new AnimalDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        try {
            String json = new String(Files.readAllBytes(Paths.get(filename)));
            GameStateController loadedState = gson.fromJson(json, GameStateController.class);
            board = loadedState.board;
            currentPlayerIndex = loadedState.currentPlayerIndex;
            boardPane.setCurrentPlayer(board.getPlayerList().get(currentPlayerIndex));
            boardPane.setBoard(board);
            boardPane.updateBoard(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSaveButton() {
        SaveButtonPane saveButtonPane = new SaveButtonPane(this, this.chitCardPane);
    }

    public Board getBoard() {
        return board;
    }
}