module com.chocs.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.chocs.taskmanager.mainpage to javafx.fxml;
    exports com.chocs.taskmanager.mainpage;

    opens com.chocs.taskmanager.createtask to javafx.fxml;
    exports com.chocs.taskmanager.createtask;
}