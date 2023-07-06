package com.example.scarasimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {



    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        HelloController controller = loader.getController();
        scene.setOnKeyPressed(controller::keyEvent);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SCARA Simulation");
        primaryStage.show();


    }



    public static void main(String[] args) {
        launch();
    }







}