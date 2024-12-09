package com.chocs.taskmanager.createtask;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

import com.chocs.taskmanager.mainpage.MainPage;
import com.chocs.taskmanager.model.Subject;
import com.chocs.taskmanager.model.Task;

import com.chocs.taskmanager.model.TaskTypes;
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
    Connection conn;

    @FXML
    MenuButton typeMenu, subjectMenu;

    @FXML
    TextField textField;

    @FXML
    protected void onTypeMenuChanged(ActionEvent event) {
        String type = ((MenuItem) event.getSource()).getText();
        switch (type) {
            case "Test": task.setType(TaskTypes.TEST);
            case "Homework": task.setType(TaskTypes.HOMEWORK);
            default: task.setType(TaskTypes.OTHERS);
        }

        typeMenu.setText(type);
    }

    @FXML
    protected void onSubjectMenuChanged(ActionEvent event) throws SQLException {
        String subject = ((MenuItem) event.getSource()).getText();
        task.setSubject(Objects.requireNonNull(query("SELECT * FROM Subjects WHERE Nome = " + subject)).getObject(1, Subject.class));

        subjectMenu.setText(subject);
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
    protected void onSubmitButtonPressed(ActionEvent event) throws IOException, SQLException {
        if(task.getDescription() == null || task.getSubject() == null ||
           task.getType() == null        || task.getDate() == null) {
            ((Button) event.getSource()).setText("Fill all values first!");
        }
        
        query("INSERT INTO Tasks values (" + task.getId() + ", " + task.getType() + ", " + task.getSubject().getId() + ", '"
                + task.getDescription() + "', '" + task.getDate() + "');");
        
        task = new Task();
        onBackButtonPressed(event);
    }

    private ResultSet query(String sql) throws SQLException {
        if(conn == null) {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager");
        }

        return conn.createStatement().executeQuery(sql);
    }
}
