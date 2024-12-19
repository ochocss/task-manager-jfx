package com.chocs.taskmanager.taskpane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TaskController {
	@FXML
	public Label id, typeId, subjectId, description, date, daysLeft;

	public TaskController() {}

	public TaskController(String id, String typeId, String subjectId, String description, LocalDate date) throws IOException {
		this.id.setText(id);
		this.typeId.setText(typeId);
		this.subjectId.setText(subjectId);
		this.description.setText(description);
		this.date.setText(date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		this.daysLeft.setText(String.valueOf(LocalDate.now().getDayOfYear() - date.getDayOfYear()));
		FXMLLoader.load(Objects.requireNonNull(TaskController.class.getResource("task.fxml")));
	}
}
