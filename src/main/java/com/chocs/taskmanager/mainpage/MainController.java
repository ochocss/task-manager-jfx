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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainController {
	@FXML
	private VBox taskBox;
	@FXML
	private MenuButton orderFieldMenu, orderTypeMenu;

	private Connection conn;
	private String currentOrderField = "TaskDate", currentOrderType = "";

	@FXML
	public void initialize() {
		conn = connect();
		read();
	}

	@FXML
	protected void onOrderFieldSelected(ActionEvent event) {
		String order = ((MenuItem) event.getSource()).getText();
		orderFieldMenu.setText(order);

		if(order.equals("Order by type") && !currentOrderField.equals("ID_type")) {
			currentOrderField = "ID_type";
			reload();
			return;
		}
		if(order.equals("Order by subject") && !currentOrderField.equals("ID_subject")) {
			currentOrderField = "ID_subject";
			reload();
			return;
		}
		if(order.equals("Order by date") && !currentOrderField.equals("TaskDate")) {
			currentOrderField = "TaskDate";
			reload();
		}
	}

	@FXML
	protected void onOrderTypeSelected(ActionEvent event) {
		String order = ((MenuItem) event.getSource()).getText();
		orderTypeMenu.setText(order);

		if(order.equals("Ascending order")) {
			currentOrderType = "";
			reload();
		} else {
			currentOrderType = "DESC";
			reload();
		}
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
			ResultSet r = Objects.requireNonNull(connect()).createStatement().executeQuery("SELECT * FROM Tasks ORDER BY " +
					currentOrderField + " " + currentOrderType + ";");

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