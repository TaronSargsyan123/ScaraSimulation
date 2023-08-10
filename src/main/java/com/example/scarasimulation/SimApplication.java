package com.example.scarasimulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


public class SimApplication extends Application {
    static Stage stage;



    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        stage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("initial-window.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        stage.setMinHeight(500);
        stage.setMinWidth(500);
        stage.setScene(scene);
        stage.setTitle("SCARA Robot sim");
        stage.show();


    }

    public static void openMainWindow(double innerLinkSize, double outerLinkSize, double height) throws IOException { //
        stage.close();

        FXMLLoader loader = new FXMLLoader(SimApplication.class.getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        SimController controller = loader.getController();
        controller.start(innerLinkSize, outerLinkSize, height);
        scene.setOnKeyPressed(controller::keyEvent);
        scene.widthProperty().addListener((observable, oldValue, newValue) -> controller.resizeSubScene());
        scene.heightProperty().addListener((observable, oldValue, newValue) -> controller.resizeSubScene());
        stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Другая форма");
        stage.setScene(scene);
        stage.show();

    }



    public static void main(String[] args) {
        launch();
    }







}