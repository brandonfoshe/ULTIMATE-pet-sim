package com.example.petsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.application.Platform;
import java.util.Random;

import java.io.IOException;

public class AbandonController {

    private Pet pet;
    public String abandonMessage;

    @FXML
    private Label abandonText;

    public void setPet(Pet pet){
        this.pet = pet;
        int abandonMethod = new Random().nextInt(0,3);
        if (abandonMethod == 0){
            abandonMessage = " on the side of the road";
        }
        if (abandonMethod == 1){
            abandonMessage = " in a dumpster";
        }
        if (abandonMethod == 2){
            abandonMessage = " in a box somewhere";
        }
        abandonText.setText("You abandoned " + pet.getName() + abandonMessage);
    }

    @FXML
    private void Exit(ActionEvent event){
        Platform.exit();
    }
}