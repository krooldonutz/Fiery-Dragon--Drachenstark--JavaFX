package com.example.fierydragon.Animals;

public class ReverseDragon extends Animal{
    public ReverseDragon() {
        super(animalType.REVERSE_DRAGON);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/ReverseDragon.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource.toString());
    }
}
