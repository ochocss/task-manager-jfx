package com.chocs.taskmanager.mainpage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Objects;

import com.chocs.taskmanager.createtask.CreateController;
import com.chocs.taskmanager.database.DatabaseUtils;
import com.chocs.taskmanager.model.Task;

import com.chocs.taskmanager.taskpane.TaskController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private VBox taskBox;
	
	public MainController() throws SQLException, IOException {
		ResultSet r = Objects.requireNonNull(DatabaseUtils.connect()).createStatement().executeQuery("SELECT * FROM Tasks");
		
		while(r.next()) {
			Task task = new Task(r.getInt("ID_task"), 
					r.getInt("ID_type"),
					r.getInt("ID_subject"),
					r.getString("Descript"),
					LocalDate.ofInstant(r.getDate("TaskDate").toInstant(), ZoneId.systemDefault()));
			Pane taskPane = FXMLLoader.load(Objects.requireNonNull(TaskController.class.getResource("task.fxml")));
			
			taskBox.getChildren().add(taskPane);
		}
	}
	
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