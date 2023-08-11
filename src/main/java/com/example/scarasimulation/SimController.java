package com.example.scarasimulation;

import com.example.scarasimulation.scara.ScaraModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class SimController {


    private Group mainGroup = new Group();

    private Camera camera = new PerspectiveCamera(true);


    private double subSceneWidth = 800;
    private double subSceneHeight = 600;
    private Group startGroup;

    private double x = -5;
    private double y = 4;

    private double startX = x;
    private double startY = y;

    private double innerLinkSize;
    private double outerLinkSize;

    private ScaraModel scaraModel;
    private VBox vBox = new VBox();

    private TextField xTextField = new TextField();
    private TextField yTextField = new TextField();

    private TextField innerLinkSizeTextField = new TextField();
    private TextField outerLinkSizeTextField = new TextField();

    private TextField standHeightTextField = new TextField();
    private TextField standRadiusTextField = new TextField();



    @FXML
    public VBox right_vbox;

    public SubScene subScene;

    @FXML
    public AnchorPane main_anchor_pane;


    @FXML
    private void initialize() {
//        scaraModel = new SCARAModel(camera, x, y); //, innerLinkSize, outerLinkSize);;
//        camera.setFarClip(200);
//        setCameraPos(camera);
//        startGroup = scaraModel.drawRobot(x, y);
//        subSceneWidth = 1200;
//        subSceneHeight = 700;
//        subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
//        subScene.setFill(scaraModel.getBackgroundColor());
//        subScene.setCamera(camera);
//        mainGroup.getChildren().add(subScene);
//        mainGroup.getChildren().add(drawMenu(185, subSceneHeight));
//        main_anchor_pane.getChildren().add(mainGroup);
    }

    public void start(double innerLinkSize, double outerLinkSize, double height){
        this.innerLinkSize = innerLinkSize;
        this.outerLinkSize = outerLinkSize;

        if (this.innerLinkSize != 0 && this.outerLinkSize != 0){
            scaraModel = new ScaraModel(camera, x, y, innerLinkSize, outerLinkSize);
        }else {
            scaraModel = new ScaraModel(camera, x, y);
        }
        camera.setFarClip(200);
        setCameraPos(camera);
        startGroup = scaraModel.drawRobot(x, y);
        subSceneWidth = 1200;
        subSceneHeight = 700;
        subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setFill(scaraModel.getBackgroundColor());
        subScene.setCamera(camera);
        mainGroup.getChildren().add(subScene);
        mainGroup.getChildren().add(drawMenu(185, subSceneHeight));
        main_anchor_pane.getChildren().add(mainGroup);

    }





    private void setCameraPos(Camera camera){
        camera.getTransforms().addAll(
                new Rotate(-25, Rotate.X_AXIS),
                new Translate(0, 0, -(scaraModel.getInnerLinkSize() +scaraModel.getOuterLinkSize())*3)
        );

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
        xTextField.setText(String.valueOf(x));
        yTextField.setText(String.valueOf(y));
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
        vBox.setPrefHeight(sceneHeight);

    }

    private VBox drawMenu(double width, double height){

        vBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;");
        vBox.setPadding(new Insets(10, 5, 0, 5));
        vBox.setSpacing(10);

        innerLinkSizeTextField.setPrefWidth(90);
        HBox hbIl = new HBox(new Label("Inner link size:"), innerLinkSizeTextField);
        hbIl.setSpacing(10);

        outerLinkSizeTextField.setPrefWidth(87);
        HBox hbOl = new HBox(new Label("Outer link size:"), outerLinkSizeTextField);
        hbOl.setSpacing(10);

        standHeightTextField.setPrefWidth(95);
        HBox hbSh = new HBox(new Label("Stand height:"), standHeightTextField);
        hbSh.setSpacing(10);

        standRadiusTextField.setPrefWidth(96);
        HBox hbSr = new HBox(new Label("Stand radius:"), standRadiusTextField);
        hbSr.setSpacing(10);

        innerLinkSizeTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scaraModel.setInnerLinkSize(Double.parseDouble(innerLinkSizeTextField.getText()));
                scaraModel.refreshRobot();
            }
        });




        xTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x = Double.parseDouble(xTextField.getText());
                scaraModel.changeArmPos(x, y);

            }
        });

        yTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y = Double.parseDouble(yTextField.getText());
                scaraModel.changeArmPos(x, y);
            }
        });
        xTextField.setText(String.valueOf(x));
        yTextField.setText(String.valueOf(y));


        HBox hbX = new HBox(new Label("X:"), xTextField);
        hbX.setSpacing(10);

        HBox hbY = new HBox(new Label("Y:"), yTextField);
        hbY.setSpacing(10);

        Button button = new Button("Go to start pos");
        button.setPrefWidth(width);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                scaraModel.changeArmPos(startX, startY);
                x = startX;
                y = startY;
                xTextField.setText(String.valueOf(x));
                yTextField.setText(String.valueOf(y));
            }
        });


        VBox confGroup = new VBox(new Separator(Orientation.HORIZONTAL), hbIl, hbOl, new Separator(Orientation.HORIZONTAL), hbSh, hbSr);

        Button simulationButton = new Button("Simulation");
        Button confButton = new Button("Scene config");
        VBox simulationGroup = new VBox(new Separator(Orientation.HORIZONTAL), hbX, hbY, new Separator(Orientation.HORIZONTAL), button);

        simulationButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                vBox.getChildren().add(simulationGroup);
                vBox.getChildren().remove(confGroup);
            }
        });

        confButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //vBox.getChildren().remove(simulationGroup);
                //vBox.getChildren().add(confGroup);

            }
        });

        HBox hbButtons = new HBox(simulationButton, confButton);
        hbButtons.setSpacing(10);
        hbButtons.setPrefWidth(height);




        vBox.getChildren().addAll(hbButtons, simulationGroup);
        vBox.setPrefWidth(width);
        vBox.setPrefHeight(height);
        return vBox;
    }







}