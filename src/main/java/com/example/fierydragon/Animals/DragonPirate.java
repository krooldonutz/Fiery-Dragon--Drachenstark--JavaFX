package com.example.fierydragon.Animals;

/**
 * The DragonPirate class represents a dragon pirate animal in the game, extending the Animal class.
 * It sets the animal type to DRAGON_PIRATE and initializes the resource and picture URL for the dragon pirate.
 */
public class DragonPirate extends Animal {

    /**
     * Constructs a DragonPirate object, setting the animal type to DRAGON_PIRATE.
     * It initializes the resource to the location of the dragon pirate image and sets the picture URL.
     */
    public DragonPirate() {
        super(animalType.DRAGON_PIRATE);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Skeleton.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource.toString());
    }
}
