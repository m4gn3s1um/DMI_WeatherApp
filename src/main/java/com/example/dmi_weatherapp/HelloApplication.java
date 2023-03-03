package com.example.dmi_weatherapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("DMI-App.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("MDI Vejrudsigt!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    //Create object for strategy pattern

    /*// Create animal
    Animal rr = new Animal ("roadrunner");
    Animal c = new Animal ("Coyote");
    // change strategies...
    //rr.setStrategy(new NormalPattern());

    //run
    //rr.run(23);

    // change strategy to 2
    //rr.setStrategy(new ZigZagPattern());

    //run
    //rr.run(12);*/

}