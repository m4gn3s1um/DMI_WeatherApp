package com.example.dmi_weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DMIApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(DMIApplication.class.getResource("DMI-App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MDI Vejrudsigt!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}