package com.example.petsimulator;

import java.util.Random;

public class Cat extends Pet{
    public Cat(String name, String descriptor, String breed){
        super(name, "cat", descriptor, breed);
    }

    public static Cat randomCat(){
        String[] names = {"Whiskers", "Samantha", "Mittens", "Soft Paws", "Kitty"}; //Default name when no name is given
        String[] descriptors = {"cuddly ", "cute ", "fat "};
        String[] breeds = {"Calico", "Tabby", "Bengal", "Siamese", "Persian"};
        String randomName = names[new Random().nextInt(names.length)];
        String randomDescriptor = descriptors[new Random().nextInt(descriptors.length)];
        String randomBreed = breeds[new Random().nextInt(breeds.length)];
        int luck  = new Random().nextInt(0,100); // 1 in 100
        if (luck == 0){
            randomName = "Queen of the Universe"; //My girlfriend named this one
            randomDescriptor = "";
            randomBreed = "Legendary Golden Cat";
        }
        return new Cat(randomName, randomDescriptor, randomBreed);
    }
}
