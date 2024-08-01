package com.example.fierydragon.Animals;

/**
 * The Dragon class represents a dragon animal in the game, extending the Animal class.
 * It sets the animal type to DRAGON and initializes the resource and picture URL for the dragon.
 */
public class Dragon extends Animal {

    /**
     * Constructs a Dragon object, setting the animal type to DRAGON.
     * It initializes the resource to the location of the dragon image and sets the picture URL.
     */
    public Dragon() {
        super(animalType.DRAGON);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Dragon.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource);
    }
}
