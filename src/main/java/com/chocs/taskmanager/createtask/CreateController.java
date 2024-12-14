package com.chocs.taskmanager.createtask;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

import com.chocs.taskmanager.mainpage.MainPage;
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
    private Task task = new Task();
    private Connection conn;

    @FXML
    MenuButton typeMenu, subjectMenu;

    @FXML
    TextField textField;

    @FXML
    protected void onTypeMenuChanged(ActionEvent event) throws SQLException {
        String type = ((MenuItem) event.getSource()).getText();

        ResultSet result = query("SELECT * FROM TaskTypes WHERE Nome = '" + type + "';");

        if(result != null && result.next()) {
            task.setTypeId(result.getInt("ID_type"));
        } else {
            System.out.println("Type not found.");
        }

        typeMenu.setText(type);
    }

    @FXML
    protected void onSubjectMenuChanged(ActionEvent event) throws SQLException {
        String subject = ((MenuItem) event.getSource()).getText();

        ResultSet result = query("SELECT * FROM Subjects WHERE Nome = '" + subject + "';");

        if(result != null && result.next()) {
            task.setSubjectId(result.getInt("ID_subject"));
        } else {
            System.out.println("Subject not found.");
        }

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
        if(task.getDescription() == null || task.getSubjectId() == 0 ||
           task.getTypeId() == 0        || task.getDate() == null) {
            ((Button) event.getSource()).setText("Fill all values first!");
            return;
        }

        System.out.println(task);
        conn.createStatement().executeUpdate("INSERT INTO Tasks (ID_type, ID_subject, Descript, TaskDate) values (" + task.getTypeId() + ", "
                                                 + task.getSubjectId() + ", '" + task.getDescription() + "', '" + task.getDate() + "');");

        task = new Task();
        onBackButtonPressed(event);
    }

    private ResultSet query(String sql) throws SQLException {
        if(conn == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/task_manager", "java", "password");
        }

        return conn.createStatement().executeQuery(sql);
    }
}