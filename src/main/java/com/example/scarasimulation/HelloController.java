package com.example.scarasimulation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController {

    public Button up_button;
    public Button left_button;
    public Button right_button;
    public Button down_button;

    private Group mainGroup = new Group();

    private Camera camera = new PerspectiveCamera(true);


    private double subSceneWidth = 800;
    private double subSceneHeight = 600;
    private Group startGroup;


    private double x = -5;
    private double y = 4;

    private SCARAModel scaraModel;





    @FXML
    public VBox right_vbox;

    @FXML
    public AnchorPane main_anchor_pane;


    @FXML
    private void initialize() {





        scaraModel = new SCARAModel(camera, x, y);;
        setCameraPos(camera);
        startGroup = scaraModel.drawRobot(x, y);
        subSceneWidth = 1200;
        subSceneHeight = 700;

        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(-0);
        light.setTranslateY(-0);
        light.setTranslateZ(-0);

        startGroup.getChildren().add(light);
        mainGroup.getChildren().add(light);

        SubScene subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setFill(scaraModel.getBackgroundColor());
        subScene.setCamera(camera);

        mainGroup.getChildren().add(subScene);
        main_anchor_pane.getChildren().add(mainGroup);





        up_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y--;
                scaraModel.changeArmPos(x,y);
            }
        });
        down_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y++;
                scaraModel.changeArmPos(x,y);
            }
        });
        left_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x--;
                scaraModel.changeArmPos(x,y);
            }
        });
        right_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x++;
                scaraModel.changeArmPos(x,y);
            }
        });
    }




    private void setCameraPos(Camera camera){
        camera.getTransforms().addAll(
                new Rotate(-25, Rotate.X_AXIS),
                new Translate(0, 0, -(scaraModel.getInnerLinkSize() +scaraModel.getOuterLinkSize())*3)
        );

        Rotate rotation = new Rotate();
        rotation.setAxis(Rotate.Y_AXIS);
        rotation.setPivotX(0);
        rotation.setPivotY(0);
        rotation.setPivotZ(0);
        camera.getTransforms().add(rotation);

    }

    public void keyEvent(KeyEvent keyEvent){
        switch (keyEvent.getCode()) {
            case A -> {
                x--;
                scaraModel.changeArmPos(x, y);
            }
            case D -> {
                x++;
                scaraModel.changeArmPos(x, y);
            }
            case W -> {
                y--;
                scaraModel.changeArmPos(x, y);
            }
            case S -> {
                y++;
                scaraModel.changeArmPos(x, y);
            }
        }
    }




}