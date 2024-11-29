package com.chocs.taskmanager.mainpage;

import java.io.IOException;
import java.util.Objects;

import com.chocs.taskmanager.createtask.CreateController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private ScrollPane scrollPane;

    @FXML
    protected void onCreateButtonClick(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(CreateController.class.getResource("create-scene.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(CreateController.class.getResource("createscene.css")).toExternalForm());
        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setTitle("Create new task");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }
}