package com.example.petsimulator;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MainGameController {
    @FXML
    private Label imaginePet;
    @FXML
    private Label turnLabel;

    private Pet pet;

    private double diffLockMult;
    private double diffLossMult;
    private double diffGainMult;
    private int turnCounter;

    @FXML
    private ProgressBar energyBar, hungerBar, bathroomBar, healthBar, happinessBar, loyaltyBar;

    @FXML
    private Button feedButton, bathroomButton, treatButton, sleepButton, vetButton, playButton, abandonButton, skipButton;

    @FXML
    private StackPane feedButtonWrapper, bathroomButtonWrapper, treatButtonWrapper, sleepButtonWrapper,
            vetButtonWrapper, playButtonWrapper, abandonButtonWrapper, skipButtonWrapper;

    private final Map<Button, Integer> lockedButtons = new HashMap<>();
    private final Map<Button, Integer> lockDurations = new HashMap<>();
    private final Map<Button, Integer> energyCosts = new HashMap<>();

    private final Map<StackPane, Button> wrapperToButton = new HashMap<>();

    public void setPet(Pet pet) {
        this.pet = pet;
        updateLabel();
        updateProgressBars();
        updateButtonStates();
        updateAllTooltips();
    }

    public void setDifficulty(Difficulty difficulty) {

        switch (difficulty) {
            case EASY -> {
                diffLockMult = 0.75;
                diffLossMult = 0.8;
                diffGainMult = 1.2;
            }
            case MEDIUM -> {
                diffLockMult = 1.0;
                diffLossMult = 1.0;
                diffGainMult = 1.0;
            }
            case HARD -> {
                diffLockMult = 1.5;
                diffLossMult = 1.2;
                diffGainMult = 0.8;
            }
            case IMPOSSIBLE -> {
                diffLockMult = 3.0;
                diffLossMult = 2.0;
                diffGainMult = 0.5;
            }
        }

        //LOCK DURATIONS EACH BUTTON
        // needs an int num of turns, so I had to cast this to int
        lockDurations.put(feedButton, (int) (10 * diffLockMult));
        lockDurations.put(bathroomButton, (int) (8 * diffLockMult));
        lockDurations.put(treatButton, (int) (2 * diffLockMult));
        lockDurations.put(sleepButton, (int) (6 * diffLockMult));
        lockDurations.put(vetButton, (int) (35 * diffLockMult));
        lockDurations.put(playButton, (int) (1 * diffLockMult));
        lockDurations.put(abandonButton, 0);
        lockDurations.put(skipButton, 0);

        //ENERGY COSTS EACH BUTTON
        energyCosts.put(feedButton, 8);
        energyCosts.put(bathroomButton, 12);
        energyCosts.put(treatButton, 5);
        energyCosts.put(sleepButton, 0);
        energyCosts.put(vetButton, 30);
        energyCosts.put(playButton, 20);
        energyCosts.put(abandonButton, 0);
        energyCosts.put(skipButton, 0);

        updateButtonStates();
        updateAllTooltips();
    }

    @FXML
    public void initialize() {
        wrapperToButton.put(feedButtonWrapper, feedButton);
        wrapperToButton.put(bathroomButtonWrapper, bathroomButton);
        wrapperToButton.put(treatButtonWrapper, treatButton);
        wrapperToButton.put(sleepButtonWrapper, sleepButton);
        wrapperToButton.put(vetButtonWrapper, vetButton);
        wrapperToButton.put(playButtonWrapper, playButton);
        wrapperToButton.put(abandonButtonWrapper, abandonButton);
        wrapperToButton.put(skipButtonWrapper, skipButton);

        updateButtonStates();
        updateAllTooltips();
    }

    @FXML
    private void ButtonClick(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        int requiredEnergy = energyCosts.getOrDefault(clickedButton, 0);

        if (pet == null) return;

        if (pet.getEnergy() < requiredEnergy) {
            lockedButtons.put(clickedButton, 0);
            clickedButton.setDisable(true);
            updateTooltipForButton(clickedButton);
            return;
        }

        pet.loseEnergy(requiredEnergy * diffLossMult);

        int lockTime = lockDurations.getOrDefault(clickedButton, 0);
        if (lockTime > 0) {
            lockedButtons.put(clickedButton, lockTime);
            clickedButton.setDisable(true);
        }

        //BUTTON LOGIC HERE
        if (clickedButton == feedButton){
            //DO ACTION - gain 100 hunger, lose bathroom
            pet.gainHunger(100 * diffGainMult);
            pet.loseBathroom(15 * diffLossMult);
        }
        if (clickedButton == bathroomButton){
            //DO ACTION - gain 100 bathroom, lose hunger
            pet.gainBathroom(100 * diffGainMult);
            pet.loseHunger(5 * diffLossMult);
        }
        if (clickedButton == treatButton){
            //DO ACTION - raise happiness and hunger slightly, lower health and bathroom slightly
            pet.gainHappiness(5 * diffGainMult);
            pet.gainHunger(2 * diffGainMult);
            pet.loseHealth( 2 * diffLossMult);
            pet.loseBathroom(2 * diffLossMult);
        }
        if (clickedButton == sleepButton){
            //DO ACTION - lock all tiles, fall asleep and wake up when energy is above 95
            pet.setSleeping(true);
            toggleSleepMode();
        }
        if (clickedButton == vetButton){
            //DO ACTION - set health to 100, raise happiness and loyalty slightly
            pet.setHealth(100);
            pet.gainLoyalty(3 * diffGainMult);
            pet.gainHappiness(5 * diffGainMult);
        }
        if (clickedButton == playButton){
            //DO ACTION - raise happiness by a lot, loyalty by a little, lower hunger and energy a lot
            pet.gainHappiness(30 * diffGainMult);
            pet.gainLoyalty(2 * diffGainMult);
            pet.loseHunger(20 * diffLossMult);
        }
        if (clickedButton == abandonButton){
            //DO ACTION - call the player a monster and make them quit the game
            FXMLLoader loader = new FXMLLoader(getClass().getResource("abandon.fxml"));
            Parent root = loader.load();

            AbandonController newController = loader.getController();
            newController.setPet(this.pet);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        }
        // No action for skip button because it's a dumb useless button that does nothing

        nextTurn();
    }

    private void updateTooltipForButton(Button btn) {
        Tooltip tip;

        if (pet != null && pet.isSleeping() && btn != skipButton) {
            tip = new Tooltip("Your " + pet.getSpecies() + " is sleeping");
        } else {
            int requiredEnergy = energyCosts.getOrDefault(btn, 0);
            int lockRemaining = lockedButtons.getOrDefault(btn, 0);
            String lockText;

            if (btn.isDisable() && lockRemaining == 0 && pet.getEnergy() < requiredEnergy) {
                lockText = "Locked due to insufficient energy";
            } else if (lockRemaining > 0) {
                lockText = "Will unlock in " + lockRemaining + " turns";
            } else {
                lockText = "Locks for " + lockDurations.getOrDefault(btn, 0) + " turns after use";
            }

            tip = new Tooltip("Requires " + requiredEnergy + " energy\n" + lockText);
        }

        // First uninstall any old tooltip, then install the new one
        Tooltip.uninstall(btn, null);
        Tooltip.install(btn, tip);

        for (Map.Entry<StackPane, Button> entry : wrapperToButton.entrySet()) {
            if (entry.getValue() == btn) {
                Tooltip.uninstall(entry.getKey(), null);
                Tooltip.install(entry.getKey(), tip);
                break;
            }
        }
    }



    private void toggleSleepMode(){
        for(Button btn : energyCosts.keySet()){
            if (btn != skipButton){
                btn.setDisable(true);
                Tooltip.install(btn, new Tooltip("Your " + pet.getSpecies() + " is sleeping"));
            }
        }
    }

    private void untoggleSleepMode(){
        for (Button btn : energyCosts.keySet()){
            if (!lockedButtons.containsKey(btn)){
                btn.setDisable(false);
            }
        }
    }

    private void updateAllTooltips() {
        for (Button btn : energyCosts.keySet()) {
            updateTooltipForButton(btn);
        }
    }

    private void updateButtonStates() {
        if (pet == null) return;

        for (Map.Entry<StackPane, Button> entry : wrapperToButton.entrySet()) {
            Button btn = entry.getValue();
            int requiredEnergy = energyCosts.getOrDefault(btn, 0);
            boolean locked = lockedButtons.containsKey(btn) && lockedButtons.get(btn) > 0;
            boolean disable = locked || (pet.getEnergy() < requiredEnergy);
            btn.setDisable(disable);
        }
    }

    private void sleepDecay(){
        //Applies passive stat changes at half effectiveness while sleeping, no conditional effects
        // Also dogs gain 2 additional energy / turn when sleeping like I originally planned
        if (pet.getSpecies().equals("dog")) {
            pet.loseHunger((4 * diffLossMult) / 2);
            pet.loseBathroom((5 * diffLossMult) / 2);
            pet.gainEnergy((2 * diffGainMult));
        } else if (pet.getSpecies().equals("cat")) {
            pet.loseHunger((2 * diffLossMult) / 2);
            pet.loseBathroom((4 * diffLossMult) / 2);
        } else {
            pet.loseHunger((3 * diffLossMult) / 2);
            pet.loseBathroom((4 * diffLossMult) / 2);
        }
    }

    public void nextTurn(){
        if (pet.getHealth() <= 1) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("death.fxml"));
                Parent root = loader.load();

                DeathController controller = loader.getController();
                controller.setPet(pet);

                // Use turnLabel to get the current stage
                Stage stage = (Stage) turnLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (pet.getLoyalty() >= 99) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("victory.fxml"));
                Parent root = loader.load();

                VictoryController controller = loader.getController();
                controller.setPet(pet);
                controller.setTurnCount(turnCounter);

                // Use turnLabel to get the current stage
                Stage stage = (Stage) turnLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        turnCounter++;
        turnLabel.setText("Turn " + turnCounter);

        updateProgressBars();

        // If pet is exhausted auto trigger sleep button
        if (pet.getEnergy() <= 5 && !pet.isSleeping()) {
            pet.setSleeping(true);

            int duration = lockDurations.getOrDefault(sleepButton, 0);
            lockedButtons.put(sleepButton, duration);
            sleepButton.setDisable(true);

            toggleSleepMode();
        }

        if (pet.isSleeping()) {
            pet.gainEnergy(10 * diffGainMult);
            updateProgressBars();

            for (Button btn : energyCosts.keySet()) {
                if (btn != skipButton) {
                    btn.setDisable(true);
                }
                updateTooltipForButton(btn);
            }

            if (pet.getEnergy() >= 95) {
                pet.setSleeping(false);
                untoggleSleepMode();
                updateAllTooltips();
            }

            sleepDecay(); // always apply decay while sleeping
            return;
        }

        // Regular logic continues here (only if not sleeping)
        Iterator<Map.Entry<Button, Integer>> iterator = lockedButtons.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Button, Integer> entry = iterator.next();
            Button btn = entry.getKey();
            int turnsLeft = entry.getValue();

            if (turnsLeft > 0) {
                turnsLeft--;
                if (turnsLeft <= 0) {
                    btn.setDisable(false);
                    iterator.remove();
                } else {
                    entry.setValue(turnsLeft);
                }
            } else if (turnsLeft == 0) {
                int requiredEnergy = energyCosts.getOrDefault(btn, 0);
                if (pet.getEnergy() >= requiredEnergy) {
                    btn.setDisable(false);
                    iterator.remove();
                }
            }
            updateTooltipForButton(btn);
        }

        updateButtonStates();
        updateAllTooltips();

        // INDIVIDUAL PET STAT GAIN/LOSS
        if (pet.getSpecies().equals("dog")) {
            pet.loseHunger(4 * diffLossMult);
            pet.loseBathroom(5 * diffLossMult);
            pet.gainEnergy(2 * diffGainMult);
        }
        else if (pet.getSpecies().equals("cat")) {
            pet.loseHunger(2 * diffLossMult);
            pet.loseBathroom(4 * diffLossMult);
        }
        else {
            pet.loseHunger(3 * diffLossMult);
            pet.loseBathroom(4 * diffLossMult);
        }

        // CONDITIONAL STAT CHANGES
        //Hunger -> health/happiness/loyalty loss
        if (pet.getHunger() <= 1) {
            pet.loseHealth(8 * diffLossMult);
            pet.loseHappiness(12 * diffLossMult);
            pet.loseLoyalty(5 * diffLossMult);
        }
        else if (pet.getHunger() <= 10) {
            pet.loseHealth(3 * diffLossMult);
            pet.loseHappiness(8 * diffLossMult);
            pet.loseLoyalty(2 * diffLossMult);
        }
        else if (pet.getHunger() <= 20) {
            pet.loseHealth(1 * diffLossMult);
            pet.loseHappiness(3 * diffLossMult);
        }

        //happiness to loyalty loss/gain
        if (pet.getHappiness() >= 99) {
            pet.gainLoyalty(5 * diffGainMult);
        }
        else if (pet.getHappiness() >= 90) {
            pet.gainLoyalty(3 * diffGainMult);
        }
        else if (pet.getHappiness() >= 80) {
            pet.gainLoyalty(2 * diffGainMult);
        }
        else if (pet.getHappiness() >= 70) {
            pet.gainLoyalty(1 * diffGainMult);
        }
        else if (pet.getHappiness() <= 1) {
            pet.loseLoyalty(4 * diffLossMult);
        }
        else if (pet.getHappiness() <= 10) {
            pet.loseLoyalty(3 * diffLossMult);
        }
        else if (pet.getHappiness() <= 20) {
            pet.loseLoyalty(2 * diffLossMult);
        }
        else if (pet.getHappiness() <= 30) {
            pet.loseLoyalty(1 * diffLossMult);
        }

        //Bathroom -> health and happiness loss
        if(pet.getBathroom() <= 1){
            pet.loseHappiness(10 * diffLossMult);
            pet.loseHealth(1*diffLossMult);
        }
    }

    private void updateLabel() {
        if (pet == null) return;
        imaginePet.setText("Imagine " + pet.getName() + ", the " + pet.getDescriptor() + pet.getBreed() + " is sitting here.");
    }

    private void updateProgressBars() {
        if (pet == null) return;
        energyBar.setProgress(pet.getEnergy() / 100.0);
        hungerBar.setProgress(pet.getHunger() / 100.0);
        bathroomBar.setProgress(pet.getBathroom() / 100.0);
        healthBar.setProgress(pet.getHealth() / 100.0);
        happinessBar.setProgress(pet.getHappiness() / 100.0);
        loyaltyBar.setProgress(pet.getLoyalty() / 100.0);
    }
}
