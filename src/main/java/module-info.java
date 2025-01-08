module com.chocs.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.chocs.taskmanager.mainpage to javafx.fxml;
    exports com.chocs.taskmanager.mainpage;

    opens com.chocs.taskmanager.createtask to javafx.fxml;
    exports com.chocs.taskmanager.createtask;

    opens com.chocs.taskmanager.taskpane to javafx.fxml;
    exports com.chocs.taskmanager.taskpane;
}