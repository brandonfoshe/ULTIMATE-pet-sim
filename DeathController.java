package com.example.petsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class DeathController {

    private Pet pet;

    @FXML
    private Label deathText;

    private void Screen(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setPet(Pet pet){
        this.pet = pet;
        deathText.setText("Your " + pet.getSpecies() + " " + pet.getName() + " has passed away. :(\n" +
                "If only you were better at playing games...\n" +
                "Click restart to start over with a new pet or exit to quit.");
    }

    @FXML
    private void restart(ActionEvent event) throws IOException{
        Screen("main-menu.fxml", event);
    }

    @FXML
    private void Exit(ActionEvent event){
        Platform.exit();
    }
}