package com.example.petsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.petsimulator.Difficulty;

import java.io.IOException;

public class InstructionsController {

    @FXML
    private Label petInfoLabel;
    @FXML
    private Label Instructions;
    @FXML
    private TextField nameInput;

    private Difficulty difficulty;
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    private Pet pet;

    public void setPet(Pet pet) {
        this.pet = pet;
        petInfoLabel.setText("Your " + pet.getSpecies() + " is a " + pet.getDescriptor() + pet.getBreed());
        Instructions.setText("In this game, you try to keep your " + pet.getSpecies() + " alive as long as possible by " +
                "doing various actions that increase its stats. For a detailed description of each button click the help" +
                " button below, otherwise you can name your " + pet.getSpecies() + " in the box below and select your " +
                "difficulty to start the game. Try to reach max loyalty with your " + pet.getSpecies() + " as fast as " +
                "possible for the best score.");
    }

    @FXML
    private void StartGame(ActionEvent event) throws IOException {
        String name = nameInput.getText().trim();
        if (!name.isEmpty()) {
            pet.setName(name);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-game.fxml"));
        Parent root = loader.load();
        MainGameController controller = loader.getController();
        controller.setPet(pet);
        controller.setDifficulty(difficulty);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void startEasy(ActionEvent event) throws IOException{
        difficulty = Difficulty.EASY;
        StartGame(event);
    }

    @FXML
    private void startMedium(ActionEvent event) throws IOException{
        difficulty = Difficulty.MEDIUM;
        StartGame(event);
    }

    @FXML
    private void startHard(ActionEvent event) throws IOException{
        difficulty = Difficulty.HARD;
        StartGame(event);
    }

    @FXML
    private void startExtreme(ActionEvent event) throws IOException{
        difficulty = Difficulty.IMPOSSIBLE;
        StartGame(event);
    }

    @FXML
    private void help(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("help.fxml"));
        Parent root = loader.load();
        SelectionController controller = loader.getController();
        controller.setPet(pet);
        controller.setDifficulty(difficulty);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void BackMainMenu(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-menu.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
