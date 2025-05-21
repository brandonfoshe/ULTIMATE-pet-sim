package com.example.petsimulator;

import java.util.Random;

public class Pet {
    protected String species;
    protected String name;
    protected String descriptor;
    protected String breed;

    private boolean sleeping = false;

    public boolean isSleeping(){
        return sleeping;
    }

    public void setSleeping(boolean sleeping){
        this.sleeping = sleeping;
    }

    //Generated getters and setters for STATS
    public double getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public double getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public double getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public double getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(int loyalty) {
        this.loyalty = loyalty;
    }

    //STATS
    private double energy;
    private double hunger;
    private double bathroom;
    private double health;
    private double happiness;
    private double loyalty;

    public Pet(String name, String species, String descriptor, String breed){
        this.name = name;
        this.species = species;
        this.descriptor = descriptor;
        this.breed = breed;

        //Starting STAT values
        this.hunger = new Random().nextInt(60,90); // Start hunger from 60-90
        this.bathroom = new Random().nextInt(60,90); //Bathroom
        this.health = new Random().nextInt(80,100); // Start health from 80-100
        this.happiness = new Random().nextInt(40, 60); // Start hapiness from 40-60
        this.loyalty = 0; //Loyalty starts fixed at 0

        if(species.equals("dog")){
            this.energy = new Random().nextInt(75,100); //75 to 100 starting energy if dog
        }
        else{this.energy = new Random().nextInt(40, 80);} // energy starts 40-80 if anything else
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getSpecies(){
        return species;
    }
    public String getDescriptor(){
        return descriptor;
    }
    public String getBreed(){
        return breed;
    }

    //STAT Gainers and decrementers

    public void gainHappiness(double amount){
        if (species.equals("cat")){
            amount *= 0.8;
        }
        this.happiness = Math.min(this.happiness + amount, 100);
    }
    public void gainHunger(double amount){
        this.hunger = Math.min(this.hunger + amount, 100);
    }
    public void gainEnergy(double amount){
        this.energy = Math.min(this.energy + amount, 100);
    }
    public void gainHealth(double amount){
        this.health = Math.min(this.health + amount, 100);
    }
    public void gainBathroom(double amount){
        this.bathroom = Math.min(this.bathroom + amount, 100);
    }
    public void gainLoyalty(double amount){
        this.loyalty = Math.min(this.loyalty + amount, 100);
    }

    public void loseHappiness(double amount){
        this.happiness = Math.max(this.happiness - amount, 0);
    }
    public void loseHunger(double amount){
        this.hunger = Math.max(this.hunger - amount, 0);
    }
    public void loseEnergy(double amount){
        this.energy = Math.max(this.energy - amount, 0);
    }
    public void loseHealth(double amount){
        this.health = Math.max(this.health - amount, 0);
    }
    public void loseBathroom(double amount){
        this.bathroom = Math.max(this.bathroom - amount, 0);
    }
    public void loseLoyalty(double amount){
        this.loyalty = Math.max(this.loyalty - amount, 0);
    }
}
