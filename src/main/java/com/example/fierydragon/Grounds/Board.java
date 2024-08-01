package com.example.fierydragon.Grounds;

import com.example.fierydragon.Action.MoveAction;
import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Chits.ChitCard;
import com.example.fierydragon.Animals.*;
import com.example.fierydragon.Chits.ChitCardManager;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * The Board class represents the game board, managing players, cells, caves, and chit cards.
 */
public class Board {
    @Expose
    private static final int BOARD_SIZE = 24; // The size of the game board
    @Expose
    private static final int CELL_PER_VOLCANO = 3; // The number of cells per volcano
    @Expose
    private final ArrayList<Player> playerArrayList = new ArrayList<>(); // List of players in the game
    @Expose
    private final ArrayList<Volcano> volcanoes = new ArrayList<>(); // List of volcanoes on the board

    @Expose
    private final HashMap<String, Cave> caveHashMap = new HashMap<>(); // Map of caves on the board
    @Expose
    private final ChitCardManager chitCardManager; // Manager for chit cards
    private MoveAction moveAction; // Action handler for moving players


    /**
     * Constructs a Board object, initializing the players, board cells, caves, and chit cards.
     *
     * @param playerCount the number of players in the game
     * @param chitCardCount the number of chit cards to be used in the game
     */
    public Board(int playerCount, int chitCardCount) {
        ArrayList<Animal> collectionOfAnimals = new ArrayList<>();
        collectionOfAnimals.add(new Bat());
        collectionOfAnimals.add(new Spider());
        collectionOfAnimals.add(new Dragon());
        collectionOfAnimals.add(new Lizard());
        collectionOfAnimals.add(new DragonPirate());
        collectionOfAnimals.add(new ReverseDragon());

        chitCardManager = new ChitCardManager(collectionOfAnimals, chitCardCount);
        initializeBoard(collectionOfAnimals);
        initializePlayers(playerCount, playerArrayList, caveHashMap, volcanoes);
        moveAction = new MoveAction(); // Initialize MoveAction with this board
    }

    private void initializeBoard(ArrayList<Animal> collectionOfAnimals) {
        ArrayList<Animal> animalList = new ArrayList<>();
        ArrayList<Animal> animalListWithoutDragonPirate = new ArrayList<>(collectionOfAnimals);
        animalListWithoutDragonPirate.removeIf(animal -> animal.getAnimal() == animalType.DRAGON_PIRATE);
        animalListWithoutDragonPirate.removeIf(animal -> animal.getAnimal() == animalType.REVERSE_DRAGON);

        Random rand = new Random();
        for (int i = 0; i < BOARD_SIZE; i++) {
            int randomIndex = rand.nextInt(collectionOfAnimals.size());
            Animal selectedAnimal;
            if ((collectionOfAnimals.get(randomIndex).getAnimal() != animalType.DRAGON_PIRATE) &&
                    (collectionOfAnimals.get(randomIndex).getAnimal() != animalType.REVERSE_DRAGON)) {
                selectedAnimal = collectionOfAnimals.get(randomIndex);
            } else {
                int randomIndexWithoutDragonPirate = rand.nextInt(animalListWithoutDragonPirate.size());
                selectedAnimal = animalListWithoutDragonPirate.get(randomIndexWithoutDragonPirate);
            }
            animalList.add(selectedAnimal);
        }
        // Randomly place Thief animals on the board
        int numberOfThieves = 1;
        for (int i = 0; i < numberOfThieves; i++) {
            int randomPosition = rand.nextInt(BOARD_SIZE);
            animalList.set(randomPosition, new RobinPig());
        }
        setVolcanoes(animalList);
    }

    public static void initializePlayers(int playerCount, ArrayList<Player> playerArrayList, HashMap<String, Cave> caveHashMap, ArrayList<Volcano> volcanoes) {
        Random rand = new Random();

        for (int i = 0; i < playerCount; i++) {
            int position = (volcanoes.size() / playerCount) * i;
            Player newPlayer = new Player("Player" + i, position);
            playerArrayList.add(newPlayer);

            int volcanoIndex = CELL_PER_VOLCANO / 2; // Example index, adjust as necessary

            // Find a non-thief animal for the cave
            Animal animal = volcanoes.get(position).getCells().get(volcanoIndex).getAnimalLabel();
            while (animal.getAnimal() == animalType.THIEF) {
                int randomIndex = rand.nextInt(BOARD_SIZE);
                animal = volcanoes.get(position).getCells().get(randomIndex % CELL_PER_VOLCANO).getAnimalLabel();
            }

            caveHashMap.put(Integer.toString(position), new Cave(animal, newPlayer, position));
        }
    }


    private void setVolcanoes(ArrayList<Animal> animalList) {
        int cellsPerVolcano = animalList.size() / CELL_PER_VOLCANO; // Calculate the number of cells per volcano
        int currNum = 0;
        for (int i = 0; i < cellsPerVolcano; i++) {
            Volcano volcano = new Volcano(i);
            for (int j = 0; j < CELL_PER_VOLCANO; j++) {
                Cell cell = new Cell(animalList.get(i * CELL_PER_VOLCANO + j), currNum);
                System.out.println(cell.getAnimalLabel());
                volcano.addCell(cell);
                currNum++;
            }
            volcanoes.add(volcano);
        }
    }

    /**
     * Finds the current cell where the player is located.
     *
     * @param player the player to find
     * @return the cell containing the player, or null if not found
     */
    public Cell findPlayerCurrentCell(Player player) {
        for (int i = 0; i < this.getVolcanoes().size(); i++) {
            for (int j = 0; j < this.getVolcanoes().get(i).getCells().size(); j++) {
                Cell cell = this.getVolcanoes().get(i).getCells().get(j);
                if (cell.getPlayersInside().containsKey(player.getName())) {
                    return cell;
                }
            }
        }
        return null;
    }

    /**
     * Returns the MoveAction instance for handling player moves.
     *
     * @return the MoveAction instance
     */
    public MoveAction getMoveAction() {
        if (moveAction == null) {
            moveAction = new MoveAction();
        }
        return moveAction;
    }

    /**
     * Returns the list of players in the game.
     *
     * @return the list of players
     */
    public ArrayList<Player> getPlayerList() {
        return playerArrayList;
    }

    public ArrayList<Volcano> getVolcanoes() {
        return volcanoes;
    }

    /**
     * Returns the map of caves on the board.
     *
     * @return the map of caves
     */
    public HashMap<String, Cave> getCaveHashMap() {
        return caveHashMap;
    }

    /**
     * Returns the map of chit cards managed by the ChitCardManager.
     *
     * @return the map of chit cards
     */
    public HashMap<String, ChitCard> getChitCardHashMap() {
        return chitCardManager.getChitCardHashMap();
    }

    public void setMoveAction(MoveAction moveAction) {
        this.moveAction = moveAction;
    }


    public int getBOARD_SIZE() {
        return BOARD_SIZE;
    }
    public int getCELL_PER_VOLCANO() {
        return CELL_PER_VOLCANO;
    }
}
