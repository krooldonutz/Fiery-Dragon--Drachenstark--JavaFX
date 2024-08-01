package com.example.fierydragon.Animals;

/**
 * The Lizard class represents a lizard animal in the game, extending the Animal class.
 * It sets the animal type to LIZARD and initializes the resource and picture URL for the lizard.
 */
public class Lizard extends Animal {

    /**
     * Constructs a Lizard object, setting the animal type to LIZARD.
     * It initializes the resource to the location of the lizard image and sets the picture URL.
     */
    public Lizard() {
        super(animalType.LIZARD);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Lizard.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource.toString());
    }
}
