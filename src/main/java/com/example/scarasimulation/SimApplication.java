package com.example.scarasimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;

import java.io.IOException;


public class SimApplication extends Application {



    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        SimController controller = loader.getController();
        scene.setOnKeyPressed(controller::keyEvent);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> controller.resizeSubScene());
        scene.heightProperty().addListener((observable, oldValue, newValue) -> controller.resizeSubScene());

        primaryStage.setMinHeight(500);
        primaryStage.setMinWidth(500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SCARA Robot sim");
        primaryStage.show();


    }



    public static void main(String[] args) {
        launch();
    }







}