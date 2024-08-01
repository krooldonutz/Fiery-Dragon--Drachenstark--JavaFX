package com.example.fierydragon.Animals;

/**
 * The Spider class represents a spider animal in the game, extending the Animal class.
 * It sets the animal type to SPIDER and initializes the resource and picture URL for the spider.
 */
public class Spider extends Animal {

    /**
     * Constructs a Spider object, setting the animal type to SPIDER.
     * It initializes the resource to the location of the spider image and sets the picture URL.
     */
    public Spider() {
        super(animalType.SPIDER);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Spider.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource.toString());
    }
}
