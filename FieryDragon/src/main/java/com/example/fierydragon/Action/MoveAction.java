package com.example.fierydragon.Action;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Chits.ChitCard;
import com.example.fierydragon.Controller.GameStateController;
import com.example.fierydragon.Grounds.Board;
import com.example.fierydragon.Grounds.Cave;
import com.example.fierydragon.Grounds.Cell;
import com.example.fierydragon.Grounds.Volcano;
import com.example.fierydragon.View.WinningScreenView;
import com.example.fierydragon.Animals.animalType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * The MoveAction class manages player movement on the game board based on chit card actions.
 */
public class MoveAction {

    /**
     * Moves the specified player based on the provided chit card.
     *
     * @param player the player to move
     * @param chitCard the chit card used for the move
     * @param board the game board
     * @return true if the move is successful, false otherwise
     */
    public boolean movePlayer(Player player, ChitCard chitCard, Board board, GameStateController controller) {
        int steps = chitCard.getSteps();
        int direction = chitCard.getAnimal().getAnimal() == animalType.DRAGON_PIRATE ? -1 : 1;
        System.out.println(player.getName() + " draws a " + chitCard.getAnimal().getAnimal() + " card" + " with " + steps + " steps");
        if (player.isInCave() && (direction == 1) && chitCard.getAnimal().getAnimal() != animalType.REVERSE_DRAGON) {
            exitCave(player, board);
            if (steps > 1) {
                return movePlayerOnBoard(player, steps - 1, board, controller);
            }
        } else if (chitCard.getAnimal().getAnimal() == animalType.REVERSE_DRAGON  && !player.isInCave()) {
            System.out.println(player.getName() + " draws a Reverse Dragon card");
            return reverseCave(board, player);
        } else if (chitCard.getAnimal().getAnimal() != animalType.REVERSE_DRAGON) {
            return movePlayerOnBoard(player, steps * direction, board, controller);
        }
        return false;
    }

    private boolean reverseCave(Board board, Player player){
        // Get the keys from the CaveHashMap and sort them in reverse order
        List<String> keys = getDynamicReverseOrderedCaveKeys(board, player);

        // Find the player's current cell
        Cell currentCell = board.findPlayerCurrentCell(player);

        for (String key : keys) {
            Cave cave = board.getCaveHashMap().get(key);
            if (cave.getPlayersInside().isEmpty()) {
                // Found an unoccupied cave, move player
                if (currentCell != null) {
                    currentCell.removePlayer(player);
                }
                int cell_num = Integer.parseInt(key) * board.getCELL_PER_VOLCANO();
//                player.incrementSteps(Math.max(cell_num, currentCell.getCellNumber()) - Math.min(cell_num, currentCell.getCellNumber()));
                System.out.println((cell_num - currentCell.getCellNumber()) + " steps back");
                System.out.println(player.getSteps());
                player.setInCave(true);
                cave.addPlayer(player);
                player.setPositionNow(Integer.parseInt(key));
                player.incrementSteps(cell_num - currentCell.getCellNumber());
                System.out.println(player.getName() + " enters cave at position " + key);
                return false;
            }
        }
        return true;
    }

    private void exitCave(Player player, Board board) {
        player.setInCave(false);
        board.getCaveHashMap().get(Integer.toString(player.getPositionNow())).removePlayer(player);
        board.getVolcanoes().get(player.getPositionNow()).getCells().get(0).addPlayer(player);
        System.out.println(player.getName() + " exits cave at position " + player.getPositionNow());
        player.setPositionNow(player.getPositionNow());
    }

    private void enterCave(Player player, Board board) {
        player.setInCave(true);
        board.getVolcanoes().get(player.getPositionNow()).getCells().get(0).removePlayer(player);
        board.getCaveHashMap().get(Integer.toString(player.getPositionNow())).addPlayer(player);
    }

    private boolean movePlayerOnBoard(Player player, int steps, Board board, GameStateController controller) {
        Cell currentCell = board.findPlayerCurrentCell(player);
        System.out.println(currentCell.getCellNumber() + " is the current cell");

        if (currentCell == null) {
            return false;
        }
        int currentCellIndex = currentCell.getCellNumber();
        int newPosition = (currentCellIndex + steps) % (board.getCELL_PER_VOLCANO() * board.getVolcanoes().size());

        int newVolcanoIndex = newPosition / board.getCELL_PER_VOLCANO();
        int newCellIndexInVolcano = newPosition % board.getCELL_PER_VOLCANO();

        Cell newCell = board.getVolcanoes().get(newVolcanoIndex).getCells().get(newCellIndexInVolcano);
        if (newCell.getPlayersInside().isEmpty()){
            player.incrementSteps(steps);
            System.out.println(player.getName() + " moves to volcano " + newVolcanoIndex + " cell " + newCellIndexInVolcano+ "from cell " + currentCellIndex + " with " + steps + " steps");
            movePlayerToCell(player, currentCell, board.getVolcanoes().get(newVolcanoIndex).getCells().get(newCellIndexInVolcano), board, controller);
            return true;
        }
        System.out.println(newCell.getCellNumber() + " is occupied");
        return false;
    }

    private void movePlayerToCell(Player player, Cell fromCell, Cell toCell, Board board, GameStateController controller) {
        fromCell.removePlayer(player);
        toCell.addPlayer(player);

        if (fromCell.getCellNumber() > toCell.getCellNumber()) {
            player.incrementBackwardSteps(fromCell.getCellNumber() - toCell.getCellNumber());
        }
        checkForWinner(player, board, controller);
    }

    private void checkForWinner(Player player, Board board, GameStateController controller) {
        if (player.getSteps() == board.getVolcanoes().size() * board.getCELL_PER_VOLCANO()){
            enterCave(player, board);
            announceWinner(player, board, controller);
        } else if (player.getSteps() > board.getVolcanoes().size() * board.getCELL_PER_VOLCANO()) {
            Cell currentCell = board.findPlayerCurrentCell(player);
            for (Volcano volcano: board.getVolcanoes()) {
                if (volcano.getCells().get(0).getCellNumber() == currentCell.getCellNumber()) {
                    player.setPositionNow(volcano.getVolcanoNumber());
                }
            }
            player.setSteps(0); // This will empty the steps
            player.incrementSteps(board.findPlayerCurrentCell(player).getCellNumber());
            System.out.println(player.getName() + " moves to volcano " + player.getPositionNow() / board.getCELL_PER_VOLCANO() + " cell " + player.getPositionNow() % board.getCELL_PER_VOLCANO());
        }
        System.out.println(player.getSteps());
    }

    /**
     * Announces the winner of the game.
     *
     * @param winner the player who won the game
     * @param board the game board
     */
    private void announceWinner(Player winner, Board board, GameStateController controller) {
        System.out.println(winner.getName() + " wins!");  // Console log or use logging framework
        saveWinStats(controller);
        WinningScreenView.showWinningScreen(winner, board.getPlayerList());  // Method to display the winning scene
    }

    private void saveWinStats(GameStateController controller){
        // Define the relative file path
        String relativePath = "saves/winStats.json";

        // Create the directory if it does not exist
        File directory = new File("saves");
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Create the file
        File file = new File(relativePath);

        // Serialize the game state to JSON
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();
        String json = gson.toJson(controller);

        // Write the JSON to the file
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the list of cave keys in reverse order, starting from the cave just before the player's current position.
     *
     * @param board the game board
     * @param player the player
     * @return the list of cave keys in reverse order
     */
    private List<String> getDynamicReverseOrderedCaveKeys(Board board, Player player) {
        List<String> keys = new ArrayList<>(board.getCaveHashMap().keySet());
        Collections.sort(keys);

        int currentPosition = player.getPositionNow();
        List<String> orderedKeys = new ArrayList<>();

        for (int i = keys.size() - 1; i >= 0; i--) {
            int cavePosition = Integer.parseInt(keys.get(i));
            if (cavePosition < currentPosition) {
                orderedKeys.add(keys.get(i));
            }
        }

        for (int i = keys.size() - 1; i >= 0; i--) {
            int cavePosition = Integer.parseInt(keys.get(i));
            if (cavePosition >= currentPosition) {
                orderedKeys.add(keys.get(i));
            }
        }

        return orderedKeys;
    }
}