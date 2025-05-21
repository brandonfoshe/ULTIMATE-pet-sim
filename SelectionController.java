package com.example.petsimulator;

import com.sun.source.tree.ContinueTree;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectionController {

    private Pet pet;
    private Difficulty difficulty;
    public void setPet(Pet pet) {
        this.pet = pet;
    }
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }


    @FXML
    private void catSelection(ActionEvent event) throws IOException {
        Cat cat = Cat.randomCat();
        Continue(cat, event);
    }

    @FXML
    private void dogSelection(ActionEvent event) throws IOException {
        Dog dog = Dog.randomDog();
        Continue(dog, event);
    }

    private void Continue(Pet pet, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructions.fxml"));
        Parent root = loader.load();

        InstructionsController controller = loader.getController();
        controller.setPet(pet);

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

    @FXML
    private void BackInstructions(ActionEvent event) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("instructions.fxml"));
        Parent root = loader.load();

        InstructionsController controller = loader.getController();
        controller.setPet(pet);
        controller.setDifficulty(difficulty);

        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }
}
