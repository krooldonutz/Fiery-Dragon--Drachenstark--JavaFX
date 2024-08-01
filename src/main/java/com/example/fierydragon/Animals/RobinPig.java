package com.example.fierydragon.Animals;

public class RobinPig extends Animal{
    public RobinPig() {
        super(animalType.THIEF);
        this.resource = getClass().getClassLoader().getResource("com/example/fierydragon/Animals/Robin.png").toExternalForm();
        assert resource != null;
        setPictureURL(resource);
    }
}
