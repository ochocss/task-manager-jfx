package com.chocs.taskmanager.taskpane;

import com.chocs.taskmanager.createtask.CreateController;
import com.chocs.taskmanager.mainpage.MainController;
import com.chocs.taskmanager.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskController {
	@FXML
	private Label type, subject, description, date, daysLeft;
	@FXML
	private Button deleteButton, updateButton;

	private Task task;
	private Connection conn;
	private MainController parent;

	public TaskController(Task task, Connection conn, MainController parent) {
		this.task = task;
		this.conn = conn;
		this.parent = parent;
	}

	@FXML
	public void initialize() {
		deleteButton.setOnAction(t -> delete());
		updateButton.setOnAction(t -> update(t));
		try {
			ResultSet r = conn.createStatement().executeQuery("SELECT Nome FROM TaskTypes WHERE ID_type = " + task.getTypeId());
			r.next();
			type.setText(r.getString(1));

			r = conn.createStatement().executeQuery("SELECT Nome FROM Subjects WHERE ID_subject = " + task.getSubjectId());
			r.next();
			subject.setText(r.getString(1));

			description.setText(task.getDescription());
			date.setText(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(task.getDate()));
			daysLeft.setText(task.getDate().getDayOfYear() - LocalDate.now().getDayOfYear() + " days left");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void delete() {
		try {
			conn.createStatement().executeUpdate("DELETE FROM Tasks WHERE ID_task = " + task.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		parent.reload();
    }

	@FXML
	public void update(ActionEvent event) {
        try {
			CreateController.create((Stage)((Node)event.getSource()).getScene().getWindow(),
                                  conn, task.getId(), type.getText(), subject.getText(), task.getDescription(), task.getDate());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
