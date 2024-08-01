package com.example.fierydragon.Chits;

import com.example.fierydragon.Animals.animalType;
import com.example.fierydragon.Animals.Animal;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * The ChitCardManager class is responsible for managing the initialization and storage
 * of ChitCards used in the game. It generates ChitCards with random steps and animals.
 */
public class ChitCardManager {
    @Expose
    private final HashMap<String, ChitCard> chitCardHashMap = new HashMap<>();
    @Expose
    private final ArrayList<Animal> animalArrayList;

    /**
     * Constructor for ChitCardManager.
     *
     * @param animalArrayList The list of animals to be used for creating ChitCards.
     * @param chitCardCount   The number of ChitCards to be created.
     */
    public ChitCardManager(ArrayList<Animal> animalArrayList, int chitCardCount) {
        this.animalArrayList = animalArrayList;
        initializeChitCards(chitCardCount);
    }

    /**
     * Initializes the ChitCards with random steps and animals.
     *
     * @param chitCardCount The number of ChitCards to be created.
     */
    private void initializeChitCards(int chitCardCount) {
        ArrayList<Animal> shuffledAnimals = new ArrayList<>(animalArrayList);
        int i = 0;
        Random rand = new Random();
        while (i < chitCardCount) {
            Collections.shuffle(shuffledAnimals);
            for (Animal animal : shuffledAnimals) {
                int steps;
                if (i >= chitCardCount) {
                    break;
                }
                if (animal.getAnimal() == animalType.DRAGON_PIRATE) {
                    steps = rand.nextInt(2) + 1;
                } else if (animal.getAnimal() == animalType.REVERSE_DRAGON) {
                    steps = 0;
                } else {
                    steps = rand.nextInt(3) + 1;
                }
                // Randomly set the number of steps for the animal (1, 2, or 3 steps)
                chitCardHashMap.put(Integer.toString(i), new ChitCard(animal, i, steps));
                i++;
            }
        }
    }

    /**
     * Gets the HashMap of ChitCards.
     *
     * @return The HashMap of ChitCards.
     */
    public HashMap<String, ChitCard> getChitCardHashMap() {
        return chitCardHashMap;
    }
}
