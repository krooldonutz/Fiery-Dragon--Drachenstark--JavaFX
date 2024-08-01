package com.example.fierydragon.Grounds;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Animals.Animal;
import com.google.gson.annotations.Expose;

import java.util.HashMap;

/**
 * The Cell class represents a cell on the game board, which may contain an animal and players.
 */
public class Cell {
    @Expose
    private Animal animalLabel; // The animal label associated with the cell
    @Expose
    private HashMap<String, Player> playersInside = new HashMap<>(); // Players inside the cell
    @Expose
    private int cellNumber; // The cell number

    /**
     * Constructs a Cell object with the specified animal label.
     *
     * @param animalLabel the animal label associated with the cell
     */
    public Cell(Animal animalLabel, int cellNumber) {
        this.animalLabel = animalLabel;
        this.cellNumber = cellNumber;
    }

    /**
     * Returns the animal label associated with the cell.
     *
     * @return the animal label
     */
    public Animal getAnimalLabel() {
        return animalLabel;
    }

    /**
     * Sets the animal label associated with the cell.
     *
     * @param animalLabel the animal label to set
     */
    public void setAnimalLabel(Animal animalLabel) {
        this.animalLabel = animalLabel;
    }

    /**
     * Adds a player to the cell.
     *
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        playersInside.put(player.getName(), player);
    }

    /**
     * Removes a player from the cell.
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player) {
        playersInside.remove(player.getName());
    }

    /**
     * Returns the map of players inside the cell.
     *
     * @return the map of players
     */
    public HashMap<String, Player> getPlayersInside() {
        return playersInside;
    }

    /**
     * Sets the map of players inside the cell.
     *
     * @param playersInside the map of players to set
     */
    public void setPlayersInside(HashMap<String, Player> playersInside) {
        this.playersInside = playersInside;
    }

    public int getCellNumber() {
        return cellNumber;
    }
}
