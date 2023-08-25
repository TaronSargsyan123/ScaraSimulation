package com.example.scarasimulation;

import com.example.scarasimulation.initialWindow.TitleBar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class SimApplication extends Application {
    public static Stage stage;




    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.initStyle(StageStyle.DECORATED);



        stage = primaryStage;
        primaryStage.setResizable(true);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("initial-window.fxml"));
        BorderPane root = loader.load();
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(SimApplication.class.getResourceAsStream("/images/logo.png")));
        stage.setHeight(630);
        stage.setWidth(800);

        // --- activate custom title bar ----
        customTitleBar(root);


        stage.setScene(scene);
        stage.setTitle("Welcome to ReviveSim");
        stage.show();


    }

    public static void openMainWindow(double innerLinkSize, double outerLinkSize, double height, String path) throws IOException { //
        stage.close();

        FXMLLoader loader = new FXMLLoader(SimApplication.class.getResource("main-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        SimController controller = loader.getController();
        controller.start(innerLinkSize, outerLinkSize, height, path);
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

    private void customTitleBar(BorderPane root){
        stage.initStyle(StageStyle.UNDECORATED);
        TitleBar titleBarCreator  = new TitleBar();
        BorderPane titleBar = titleBarCreator.createTitleBar(stage);
        root.setTop(titleBar);
    }





    public static void main(String[] args) {
        launch();
    }

}