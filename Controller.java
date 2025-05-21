package com.example.petsimulator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.application.Platform;

import java.io.IOException;

public class Controller {

    private void Screen(String fxmlFile, ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void Start(ActionEvent event) throws IOException {
        Screen("pet-select.fxml", event);
    }

    @FXML
    private void ToDo(ActionEvent event) throws IOException {
        Screen("to-do.fxml", event);
    }

    @FXML
    private void Bugs(ActionEvent event) throws IOException {
        Screen("bugs.fxml", event);
    }

    @FXML
    private void Exit(ActionEvent event) {
        Platform.exit();
    }
}