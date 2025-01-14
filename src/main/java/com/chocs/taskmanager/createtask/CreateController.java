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
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CreateController {
    private Task task = new Task();
    private Connection conn;
    private int id;
    private boolean isUpdating;

    @FXML private MenuButton typeMenu, subjectMenu;
    @FXML private TextField descriptionTextfield;
    @FXML private DatePicker datePicker;
    @FXML private Button submitButton;

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

    private void setType(String type) throws SQLException {
        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM TaskTypes WHERE Nome = '" + type + "';");

        if(result.next()) {
            task.setTypeId(result.getInt("ID_type"));
        } else {
            System.out.println("Type not found.");
        }

        typeMenu.setText(type);
    }

    private void setSubject(String subject) throws SQLException {
        ResultSet result = conn.createStatement().executeQuery("SELECT * FROM Subjects WHERE Nome = '" + subject + "';");

        if(result.next()) {
            task.setSubjectId(result.getInt("ID_subject"));
        } else {
            System.out.println("Subject not found.");
        }
    }

    @FXML
    protected void onTypeMenuChanged(ActionEvent event) {
        String type = ((MenuItem) event.getSource()).getText();
        typeMenu.setText(type);
    }

    @FXML
    protected void onSubjectMenuChanged(ActionEvent event) throws SQLException {
        String subject = ((MenuItem) event.getSource()).getText();
        subjectMenu.setText(subject);
    }

    @FXML
    protected void onDescriptionChanged(ActionEvent event) {
        String description = ((TextField) event.getSource()).getText();

        if(description.length() > 63) {
            description = description.substring(0, 63);
            descriptionTextfield.setText(description);
        }
    }

    @FXML
    protected void onDatePickerChanged(ActionEvent event) {
        LocalDate date = ((DatePicker) event.getSource()).getValue();

        if (date == null) {
            System.out.println("Date is null.");
            return;
        }

        task.setDate(date);
    }

    @FXML
    protected void onBackButtonPressed(ActionEvent event) throws IOException {
        MainPage.createMainScene((Stage)((Node)event.getSource()).getScene().getWindow());
    }

    @FXML
    protected void onMouseExitedButton() {
        submitButton.setText("Submit");
    }

    @FXML
    protected void onSubmitButtonPressed(ActionEvent event) throws IOException, SQLException {
        String temp = typeMenu.getText();
        if(temp.equals("Select type...") || temp.isEmpty() || temp.isBlank()) {
            submitButton.setText("Fill all values first!");
            return;
        }
        setType(temp);

        temp = subjectMenu.getText();
        if(temp.equals("Select subject...") || temp.isEmpty() || temp.isBlank()) {
            submitButton.setText("Fill all values first!");
            return;
        }
        setSubject(temp);

        temp = descriptionTextfield.getText();
        if(temp.equals("Insert description...") || temp.isEmpty() || temp.isBlank()) {
            submitButton.setText("Fill all values first!");
            return;
        }
        task.setDescription(temp);

        LocalDate date = datePicker.getValue();
        if(date == null) {
            submitButton.setText("Fill all values first!");
            return;
        }
        task.setDate(date);

        if(isUpdating) {
            conn.createStatement().executeUpdate("UPDATE Tasks SET ID_type = " + task.getTypeId() + ", ID_subject = " + task.getSubjectId()
                                                     + ", Descript = '" + task.getDescription() + "', TaskDate = '" + task.getDate() +
                                                     "' WHERE ID_task = " + id + ";");
        } else {
            conn.createStatement().executeUpdate("INSERT INTO Tasks (ID_type, ID_subject, Descript, TaskDate) values (" + task.getTypeId() + ", "
                                                     + task.getSubjectId() + ", '" + task.getDescription() + "', '" + task.getDate() + "');");
        }

        onBackButtonPressed(event);
    }
}
