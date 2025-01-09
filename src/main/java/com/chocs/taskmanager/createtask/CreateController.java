package com.chocs.taskmanager.createtask;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Objects;

import com.chocs.taskmanager.mainpage.MainPage;
import com.chocs.taskmanager.model.Task;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateController {
    private Task task = new Task();
    private Connection conn;
    private int id;
    private boolean isUpdating;

    @FXML private MenuButton typeMenu, subjectMenu;
    @FXML private TextField descriptionTextfield;
    @FXML private DatePicker datePicker;

    public static void create(Stage stage, Connection conn) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(CreateController.class.getResource("create-scene.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        ((CreateController) fxmlLoader.getController()).setConnection(conn);

        stage.setTitle("Create new task");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void create(Stage stage, Connection conn, int id, String type, String subject, String description, LocalDate date) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Objects.requireNonNull(CreateController.class.getResource("create-scene.fxml")));
        Scene scene = new Scene(fxmlLoader.load());

        ((CreateController) fxmlLoader.getController()).setConnection(conn);
        ((CreateController) fxmlLoader.getController()).setTask(id, type, subject, description, date);

        stage.setTitle("Create new task");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void setTask(int id, String type, String subject, String description, LocalDate date) {
        this.id = id;
        typeMenu.setText(type);
        subjectMenu.setText(subject);
        descriptionTextfield.setText(description);
        datePicker.setValue(date);

        isUpdating = true;
    }

    private void setConnection(Connection conn) {
        this.conn = conn;
    }

    @FXML
    protected void onTypeMenuChanged(ActionEvent event) throws SQLException {
        String type = ((MenuItem) event.getSource()).getText();

        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM TaskTypes WHERE Nome = '" + type + "';");

        if(result.next()) {
            task.setTypeId(result.getInt("ID_type"));
        } else {
            System.out.println("Type not found.");
        }

        typeMenu.setText(type);
    }

    @FXML
    protected void onSubjectMenuChanged(ActionEvent event) throws SQLException {
        String subject = ((MenuItem) event.getSource()).getText();

        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Subjects WHERE Nome = '" + subject + "';");

        if(result.next()) {
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
            descriptionTextfield.setText(description);
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
        MainPage.createMainScene((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    protected void onSubmitButtonPressed(ActionEvent event) throws IOException, SQLException {
        if(task.getDescription() == null || task.getSubjectId() == 0 ||
           task.getTypeId() == 0        || task.getDate() == null) {
            ((Button) event.getSource()).setText("Fill all values first!");
            return;
        }
        if(isUpdating) {
            System.out.println("UPDATE Tasks SET ID_type = " + task.getTypeId() + ", ID_subject = " + task.getSubjectId()
                    + ", Descript = " + task.getDescription() + ", TaskDate = '" + task.getDate() +
                    "' WHERE ID_task = " + id + ";");
            conn.createStatement().executeUpdate("UPDATE Tasks SET ID_type = " + task.getTypeId() + ", ID_subject = " + task.getSubjectId()
                                                     + ", Descript = '" + task.getDescription() + "', TaskDate = '" + task.getDate() +
                                                     "' WHERE ID_task = " + id + ";"); // que porra ???
        } else {
            conn.createStatement().executeUpdate("INSERT INTO Tasks (ID_type, ID_subject, Descript, TaskDate) values (" + task.getTypeId() + ", "
                                                     + task.getSubjectId() + ", '" + task.getDescription() + "', '" + task.getDate() + "');");
        }

        onBackButtonPressed(event);
    }
}
