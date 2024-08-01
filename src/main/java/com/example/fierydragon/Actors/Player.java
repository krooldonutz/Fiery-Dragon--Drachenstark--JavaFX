package com.example.fierydragon.Actors;
import com.google.gson.annotations.Expose;

/**
 * The Player class represents a player in the game, encapsulating details
 * about the player's name, position on the board, whether the player is in a cave,
 * the number of steps taken, and the player's color.
 */
public class Player {
    @Expose
    private final String name; // The name of the player
    @Expose
    private int initialPosition; // The initial position of the player on the board
    @Expose
    private boolean isInCave = true; // If the player is in a cave
    @Expose
    private int positionNow; // The current position of the player on the board
    @Expose
    private int steps = 0; // The number of steps taken by the player
    @Expose
    private int wrongClick = 0; // The number of wrong moves made by the player
    @Expose
    private int backwardSteps = 0; // The number of backward steps taken by the player


    /**
     * Constructs a Player with the specified name and initial position.
     *
     * @param name            the name of the player
     * @param initialPosition the initial position of the player on the board
     */
    public Player(String name, int initialPosition) {
        this.name = name;
        this.initialPosition = initialPosition;
        this.steps = 0;
        this.positionNow = initialPosition;
    }

    /**
     * Returns the name of the player.
     *
     * @return the name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the initial position of the player on the board.
     *
     * @return the initial position of the player
     */
    public int getInitialPosition() {
        return initialPosition;
    }

    /**
     * Sets the initial position of the player on the board.
     *
     * @param initialPosition the initial position to set
     */
    public void setInitialPosition(int initialPosition) {
        this.initialPosition = initialPosition;
    }

    /**
     * Returns the current position of the player on the board.
     *
     * @return the current position of the player
     */
    public int getPositionNow() {
        return positionNow;
    }

    /**
     * Sets the current position of the player on the board.
     *
     * @param positionNow the current position to set
     */
    public void setPositionNow(int positionNow) {
        this.positionNow = positionNow;
    }

    /**
     * Returns whether the player is in a cave.
     *
     * @return true if the player is in a cave, false otherwise
     */
    public boolean isInCave() {
        return isInCave;
    }

    /**
     * Sets whether the player is in a cave.
     *
     * @param inCave true if the player is in a cave, false otherwise
     */
    public void setInCave(boolean inCave) {
        isInCave = inCave;
    }


    /**
     * Returns the number of steps taken by the player.
     *
     * @return the number of steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Increments the number of steps taken by the player by the specified amount.
     *
     * @param steps the number of steps to add
     */
    public void incrementSteps(int steps) {
        this.steps += steps;
    }

    public void incrementWrongClick() {
        this.wrongClick++;
    }

    public int getWrongClick() {
        return wrongClick;
    }

    public void incrementBackwardSteps(int steps) {
        this.backwardSteps += 1;
    }

    public int getBackwardSteps() {
        return backwardSteps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public double getWrongClickRatio(){
        return (double) wrongClick / steps;
    }

    public int getTotalMoves() {
        return this.steps + this.backwardSteps + this.wrongClick;
    }

    public int getSuccessfulMoves() {
        return this.steps - this.wrongClick;
    }

    public int getFailedMoves() {
        return this.wrongClick;
    }

    public double getSuccessRate() {
        return (double) getSuccessfulMoves() / getTotalMoves();
    }

    public double getFailureRate() {
        return (double) getFailedMoves() / getTotalMoves();
    }
}
