module com.example.dmi_weatherapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires MaterialFX;


    opens com.example.dmi_weatherapp to javafx.fxml;
    exports com.example.dmi_weatherapp;
    exports com.example.dmi_weatherapp.Strategy;
    opens com.example.dmi_weatherapp.Strategy to javafx.fxml;
}