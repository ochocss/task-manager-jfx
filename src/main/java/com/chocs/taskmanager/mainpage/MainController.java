package com.chocs.taskmanager.mainpage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import com.chocs.taskmanager.createtask.CreateController;
import com.chocs.taskmanager.model.Task;

import com.chocs.taskmanager.taskpane.TaskController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private VBox taskBox;


	@FXML
	public void initialize() {
		conn = connect();
		read();
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

	public void reload() {
		taskBox.getChildren().clear();
		read();
	}

	private void read() {
		try {
			ResultSet r = Objects.requireNonNull(connect()).createStatement().executeQuery("SELECT * FROM Tasks");

			while(r.next()) {
				Task task = new Task(r.getInt("ID_task"),
						r.getInt("ID_type"),
						r.getInt("ID_subject"),
						r.getString("Descript"),
						r.getDate("TaskDate").toLocalDate());

				FXMLLoader fxmlLoader = new FXMLLoader(TaskController.class.getResource("task.fxml"));
				TaskController taskController = new TaskController(task, conn, this);
				fxmlLoader.setController(taskController);

				taskBox.getChildren().add(fxmlLoader.load());
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}

}