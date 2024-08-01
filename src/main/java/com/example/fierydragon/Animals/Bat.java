package com.example.fierydragon.Animals;

/**
 * The Bat class represents a bat animal in the game, extending the Animal class.
 * It sets the animal type to BAT and initializes the resource and picture URL for the bat.
 */
public class Bat extends Animal {

    /**
     * Constructs a Bat object, setting the animal type to BAT.
     * It initializes the resource to the location of the bat image and sets the picture URL.
     */
    public Bat() {
        super(animalType.BAT);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Bat.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource);
    }

}
