package com.chocs.taskmanager.mainpage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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

	private Connection conn;

	@FXML
	public void initialize() {
		conn = connect();
		read();
	}

    @FXML
    protected void onCreateButtonClick(ActionEvent event) throws IOException {
        CreateController.create((Stage)((Node)event.getSource()).getScene().getWindow(), conn);
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

	private Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "java", "password");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}