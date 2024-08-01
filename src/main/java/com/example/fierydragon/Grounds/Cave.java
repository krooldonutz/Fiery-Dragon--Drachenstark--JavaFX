package com.example.fierydragon.Grounds;

import com.example.fierydragon.Actors.Player;
import com.example.fierydragon.Animals.Animal;
import com.google.gson.annotations.Expose;

import java.util.HashMap;

/**
 * The Cave class represents a cave on the game board, which may contain an animal and players.
 */
public class Cave {
    @Expose
    private Animal animalLabel; // The animal label associated with the cave
    @Expose
    private final HashMap<String, Player> playersInside = new HashMap<>(); // Players inside the cave
    @Expose
    private final int cavePosition; // The position of the cave on the board
    @Expose
    private final String caveURL; // The URL of the cave image

    /**
     * Constructs a Cave object with the specified animal label, player, and cave position.
     *
     * @param animalLabel the animal label associated with the cave
     * @param player the player inside the cave
     * @param cavePosition the position of the cave on the board
     */
    public Cave(Animal animalLabel, Player player, int cavePosition) {
        this.animalLabel = animalLabel;
        this.playersInside.put(player.getName(), player);
        this.cavePosition = cavePosition;
        this.caveURL = getClass().getClassLoader().getResource("com/example/fierydragon/Grounds/Cave.png").toString();
        System.out.println(cavePosition);
    }

    /**
     * Returns the animal label associated with the cave.
     *
     * @return the animal label
     */
    public Animal getAnimalLabel() {
        return animalLabel;
    }

    /**
     * Removes a player from the cave.
     *
     * @param player the player to remove
     */
    public void removePlayer(Player player){
        playersInside.remove(player.getName());
    }

    /**
     * Adds a player to the cave.
     *
     * @param player the player to add
     */
    public void addPlayer(Player player){
        playersInside.put(player.getName(), player);
    }

    /**
     * Returns the position of the cave on the board.
     *
     * @return the cave position
     */
    public int getCavePosition() {
        return cavePosition;
    }

    /**
     * Returns the map of players inside the cave.
     *
     * @return the map of players
     */
    public HashMap<String, Player> getPlayersInside() {
        return playersInside;
    }

    /**
     * Checks if the cave is empty (contains no players).
     *
     * @return true if the cave is empty, false otherwise
     */
    public boolean isEmpty(){
        return playersInside.isEmpty();
    }

    /**
     * Returns the URL of the cave image.
     *
     * @return the cave image URL
     */
    public String getCaveURL() {
        if (caveURL == null) {
            return getClass().getClassLoader().getResource("com/example/fierydragon/Grounds/Cave.png").toString();
        }
        return caveURL;
    }
}
