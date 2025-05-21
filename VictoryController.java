package com.example.petsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class VictoryController {

    private Pet pet;

    @FXML
    private Label victoryText;
    private int turnCount;

    private void Screen(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setPet(Pet pet){
        this.pet = pet;
        victoryText.setText("Congratulations, you reached max loyalty with " + pet.getName() + " after " + turnCount + " turns!" +
                "\nYou can restart to play with a new pet or exit to quit the game.");
    }

    public void setTurnCount(int turnCount) {
        this.turnCount = turnCount;
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