module com.example.worldinspace {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.worldinspace to javafx.fxml;
    exports com.example.worldinspace;
}