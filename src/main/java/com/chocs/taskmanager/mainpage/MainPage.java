package com.chocs.taskmanager.mainpage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainPage extends Application {
    public static void createMainScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainPage.class.getResource("main-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    @Override
    public void start(Stage stage) throws IOException {
        createMainScene(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}