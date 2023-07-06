package com.example.scarasimulation;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

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

    public SubScene subScene;

    @FXML
    public AnchorPane main_anchor_pane;


    @FXML
    private void initialize() {





        scaraModel = new SCARAModel(camera, x, y);;
        camera.setFarClip(200);
        setCameraPos(camera);
        startGroup = scaraModel.drawRobot(x, y);
        subSceneWidth = 1200;
        subSceneHeight = 700;
        subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setFill(scaraModel.getBackgroundColor());
        subScene.setCamera(camera);

        mainGroup.getChildren().add(subScene);


        mainGroup.getChildren().add(drawMenu(200, 300));


        main_anchor_pane.getChildren().add(mainGroup);






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
            }
            case D -> {
                x++;

            }
            case W -> {
                y--;

            }
            case S -> {
                y++;

            }

        }
        scaraModel.changeArmPos(x, y);
    }

    public SubScene betSubScene(){
        return subScene;
    }

    public void resizeSubScene() {
        double sceneWidth = subScene.getScene().getWidth();
        double sceneHeight = subScene.getScene().getHeight();
        subScene.setWidth(sceneWidth);
        subScene.setHeight(sceneHeight);
    }

    private VBox drawMenu(double width, double height){
        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");
        vBox.setPadding(new Insets(10, 5, 0, 5));
        vBox.setSpacing(10);
        Button button = new Button("Click me!!!!!");
        Button buttonTwo = new Button("Click me!!!!!");
        button.setPrefWidth(width);
        buttonTwo.setPrefWidth(width);
        vBox.getChildren().addAll(button, buttonTwo);
        vBox.setPrefWidth(width);
        vBox.setPrefHeight(height);
        return vBox;
    }





}