package com.example.fierydragon.Chits;

import com.example.fierydragon.Animals.Animal;
import com.google.gson.annotations.Expose;

/**
 * The ChitCard class represents a chit card used in the game, which contains an animal and steps.
 */
public class ChitCard {
    @Expose
    private Animal animal; // The animal associated with the chit card
    @Expose
    private int steps; // The number of steps indicated on the chit card
    @Expose
    private int ID; // The ID of the chit card
    @Expose
    private String pictureURL; // The URL of the picture associated with the chit card

    /**
     * Constructs a ChitCard object with the specified animal, ID, and steps.
     *
     * @param animal the animal associated with the chit card
     * @param ID the ID of the chit card
     * @param steps the number of steps indicated on the chit card
     */
    public ChitCard(Animal animal, int ID, int steps){
        this.animal = animal;
        this.ID = ID;
        this.steps = steps;
        this.pictureURL = getClass().getClassLoader().getResource( "com/example/fierydragon/Animals/question.png").toString();
    }

    /**
     * Returns the animal associated with the chit card.
     *
     * @return the animal
     */
    public Animal getAnimal() {
        return animal;
    }

    /**
     * Returns the number of steps indicated on the chit card.
     *
     * @return the number of steps
     */
    public int getSteps() {
        return steps;
    }

    /**
     * Returns the ID of the chit card.
     *
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Returns the URL of the picture associated with the chit card.
     *
     * @return the picture URL
     */
    public String getPictureURL() {
        if (pictureURL != null){
            getClass().getClassLoader().getResource( "com/example/fierydragon/Animals/question.png").toString();
        }
        return pictureURL;
    }
}
