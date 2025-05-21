package com.example.petsimulator;

import java.util.Random;

public class Dog extends Pet{
    public Dog(String name, String descriptor, String breed){
        super(name, "dog", descriptor, breed);
    }

    public static Dog randomDog(){
        String[] names = {"Buddy", "Bella", "Cooper", "Charlie", "Max"}; //Default name when no name is given
        String[] descriptors = {"small ", "medium ", "large "};
        String[] breeds = {"Bulldog", "Labrador", "Poodle", "German Shepherd", "Golden Retriever"};
        String randomName = names[new Random().nextInt(names.length)];
        String randomDescriptor = descriptors[new Random().nextInt(descriptors.length)];
        String randomBreed = breeds[new Random().nextInt(breeds.length)];
        int luck  = new Random().nextInt(0,100);
        if (luck == 0){
            randomName = "Goldie";
            randomDescriptor = "";
            randomBreed = "Legendary Golden Dog";
        }
        return new Dog(randomName, randomDescriptor, randomBreed);
    }
}
