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
import javafx.scene.paint.Color;
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

    public SubScene subScene;

    @FXML
    public AnchorPane main_anchor_pane;


    @FXML
    private void initialize() {

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


    public void resizeSubScene() {
        double sceneWidth = subScene.getScene().getWidth();
        double sceneHeight = subScene.getScene().getHeight();
        subScene.setWidth(sceneWidth);
        subScene.setHeight(sceneHeight);
        vBox.setPrefHeight(sceneHeight);

    }



    private VBox drawMenu(double width, double height){

        vBox.setStyle("-fx-background-color: " + Colors.gray + ";");
        vBox.setPadding(new Insets(10, 5, 0, 5));
        vBox.setSpacing(10);

        setXTextField();
        setYTextField();

        Label labelX = getCordLabel("X:");
        Label labelY = getCordLabel("Y:");

        HBox hBoxX = new HBox(labelX, xTextField);

        hBoxX.setSpacing(10);

        HBox hBoxY = new HBox(labelY, yTextField);
        hBoxY.setTranslateY(10);
        hBoxY.setSpacing(10);

        Button startPosButton = getStartPosButton(width);

        VBox simulationGroup = new VBox(hBoxX, hBoxY, startPosButton);

        vBox.getChildren().addAll(simulationGroup);
        vBox.setPrefWidth(width);
        vBox.setPrefHeight(height);
        return vBox;
    }












    private Button getStartPosButton(double width) {
        Button button = new Button("Go to start pos");
        button.setStyle("-fx-background-color: " + Colors.lightBlue + ";" + "-fx-text-fill:" + Colors.white + ";");
        button.setTranslateY(20);
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
        return button;
    }

    private Label getCordLabel(String text){
        Label label = new Label();
        label.setText(text);
        label.setTextFill(Color.valueOf(Colors.firstTextColor));
        label.setTranslateY(5);

        return label;
    }

    private void setXTextField(){
        xTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x = Double.parseDouble(xTextField.getText());
                scaraModel.changeArmPos(x, y);

            }
        });

        xTextField.setText(String.valueOf(x));
        xTextField.setStyle("-fx-background-color: " + Colors.gray +";" + "; -fx-border-color: " + Colors.lightGray + "; -fx-text-fill: " + Colors.firstTextColor + ";");

    }





    private void setYTextField(){
        yTextField.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y = Double.parseDouble(yTextField.getText());
                scaraModel.changeArmPos(x, y);
            }
        });

        yTextField.setText(String.valueOf(y));
        yTextField.setStyle("-fx-background-color: " + Colors.gray +";" + "; -fx-border-color: " + Colors.lightGray + "; -fx-text-fill: " + Colors.firstTextColor + ";");

    }


}