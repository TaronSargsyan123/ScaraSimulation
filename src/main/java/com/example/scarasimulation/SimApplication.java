package com.example.scarasimulation;

import com.example.scarasimulation.initialWindow.TitleBar;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.*;


import java.io.IOException;


public class SimApplication extends Application {
    public static Stage stage;
    private TitleBar titleBarCreator  = new TitleBar();




    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // Create a custom title bar
        BorderPane titleBar = titleBarCreator.createTitleBar(primaryStage);
        stage = primaryStage;
        stage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("initial-window.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(SimApplication.class.getResourceAsStream("/images/logo.png")));
        stage.setHeight(630);
        stage.setWidth(800);

        root.setTop(titleBar);

        stage.setScene(scene);
        //ikscene.getStylesheets().add(getClass().getResource("titleBarStyle.css").toExternalForm());
        stage.setTitle("Welcome to ReviveSim");
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
        stage.getIcons().add(new Image(SimApplication.class.getResourceAsStream("/images/logo.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Revive sim");
        stage.setScene(scene);
        stage.show();

    }





    public static void main(String[] args) {
        launch();
    }

}