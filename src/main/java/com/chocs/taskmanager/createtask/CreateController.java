package com.chocs.taskmanager.createtask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

import com.chocs.taskmanager.mainpage.MainPage;
import com.chocs.taskmanager.model.Task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateController {
    Task task = new Task();

    @FXML
    MenuButton typeMenu, subjectMenu;

    @FXML
    TextField textField;

    @FXML
    protected void onTypeMenuChanged(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        task.setType(item.getText());

        typeMenu.setText(item.getText());
    }

    @FXML
    protected void onSubjectMenuChanged(ActionEvent event) {
        MenuItem item = (MenuItem) event.getSource();
        task.setSubject(item.getText());

        subjectMenu.setText(item.getText());
    }

    @FXML
    protected void onDescriptionChanged(ActionEvent event) {
        String description = ((TextField) event.getSource()).getText();

        if(description.length() > 63) {
            description = description.substring(0, 63);
            textField.setText(description);
        }

        task.setDescription(description);
    }

    @FXML
    protected void onDatePickerChanged(ActionEvent event) {
        LocalDate date = ((DatePicker) event.getSource()).getValue();

        if (date != null && date.isAfter(LocalDate.now())) {
            task.setDate(date);
        }
    }

    @FXML
    protected void onBackButtonPressed(ActionEvent event) throws IOException {
        MainPage.createScene((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    protected void onSubmitButtonPressed(ActionEvent event) throws IOException {
        if(task.getDescription() == null || task.getSubject() == null || task.getSubject().isEmpty() ||
           task.getType() == null        || task.getType().isEmpty()  || task.getDate() == null) {
            ((Button) event.getSource()).setText("Fill all values!");
        }
        
        try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager");
	        conn.createStatement().executeQuery("INSERT INTO Tasks values (");
        } catch (SQLException e) {
			e.printStackTrace();
		}

        
        task = new Task();
        onBackButtonPressed(event);
    }
}
