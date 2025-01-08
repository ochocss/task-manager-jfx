package com.chocs.taskmanager.taskpane;

import com.chocs.taskmanager.mainpage.MainController;
import com.chocs.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskController {
	@FXML
	private Label typeId, subjectId, description, date, daysLeft;
	@FXML
	private Button deleteButton;

	private Task task;
	private Connection conn;
	private MainController parent;

	public TaskController(Task task, Connection conn, MainController parent) {
		this.task = task;
		this.conn = conn;
		this.parent = parent;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@FXML
	public void initialize() {
		deleteButton.setOnAction(t -> delete());
		try {
			ResultSet r = conn.createStatement().executeQuery("SELECT Nome FROM TaskTypes WHERE ID_type = " + task.getTypeId());
			r.next();
			typeId.setText(r.getString(1));

			r = conn.createStatement().executeQuery("SELECT Nome FROM Subjects WHERE ID_subject = " + task.getSubjectId());
			r.next();
			subjectId.setText(r.getString(1));

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
}
