package com.example.fierydragon.Animals;

import com.google.gson.annotations.Expose;

/**
 * The Animal class represents an abstract concept of an animal, encapsulating details
 * about the type of animal, a URL to the animal's picture, and a resource identifier.
 */
public abstract class Animal {
    @Expose
    private animalType animal; // The type of the animal
    private String pictureURL; // URL to a picture of the animal
    public String resource; // A resource identifier related to the animal

    /**
     * Constructs an Animal with the specified animal type.
     *
     * @param animal the type of the animal
     */
    public Animal(animalType animal) {
        setAnimal(animal);
    }

    /**
     * Returns the type of the animal.
     *
     * @return the type of the animal
     */
    public animalType getAnimal() {
        return animal;
    }

    /**
     * Sets the type of the animal.
     *
     * @param animal the type of the animal to set
     */
    public void setAnimal(animalType animal) {
        this.animal = animal;
    }

    /**
     * Returns the URL to the picture of the animal.
     *
     * @return the picture URL
     */
    public String getPictureURL() {

        return pictureURL;
    }

    /**
     * Sets the URL to the picture of the animal.
     *
     * @param pictureURL the picture URL to set
     */
    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    /**
     * Returns a string representation of the animal, which is the string
     * representation of the animal type.
     *
     * @return a string representation of the animal
     */
    @Override
    public String toString() {
        return animal.toString();
    }
}
