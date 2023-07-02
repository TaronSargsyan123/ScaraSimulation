package com.example.scarasimulation;

import javafx.animation.Animation;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);

        Group lOneGroup = new Group();


        Cylinder cylinder = new Cylinder(2.5, 5);
        Cylinder cylinderDesign = new Cylinder(2.5, 1);
        Sphere target = new Sphere(0.2);
        Cylinder plane = new Cylinder(10, 0.1);
        Box lOne = new Box(10,1,5);
        cylinder.setMaterial(new PhongMaterial(Color.BLUE));
        plane.setMaterial(new PhongMaterial(Color.ORANGE));
        target.setMaterial(new PhongMaterial(Color.RED));
        plane.translateYProperty().set(2.5);
        cylinderDesign.translateYProperty().set(-3);
        lOne.translateYProperty().set(-3);
        target.translateYProperty().set(-3.5);
        lOneGroup.getChildren().add(lOne);
        lOneGroup.getChildren().add(cylinderDesign);




        Camera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, -5, -80)
        );


        Translate translate = new Translate(5, 0, 0);
        lOne.getTransforms().add(translate);




        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(plane);
        root.getChildren().add(cylinder);
        root.getChildren().add(lOneGroup);
        root.getChildren().add(target);




        Scene scene = new Scene(root, 1000, 1000, true);
        scene.setFill(Color.web("#4287f5"));
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.A) {

                Rotate rotate = new Rotate();
                rotate.setAxis(Rotate.Y_AXIS);
                rotate.setPivotX(0);  // X coordinate of the pivot point
                rotate.setPivotY(0);     // Y coordinate of the pivot point
                rotate.setPivotZ(0);
                lOne.getTransforms().add(rotate);
                lOne.setRotationAxis(Rotate.Y_AXIS);
                lOne.setRotate(lOne.getRotate()-10);

            }else if (e.getCode() == KeyCode.D){
                Rotate rotate = new Rotate();
                rotate.setAxis(Rotate.Y_AXIS);
                rotate.setPivotX(0);  // X coordinate of the pivot point
                rotate.setPivotY(0);     // Y coordinate of the pivot point
                rotate.setPivotZ(0);
                lOne.getTransforms().add(rotate);
                lOne.setRotationAxis(Rotate.Y_AXIS);
                lOne.setRotate(lOne.getRotate()+10);

            }else if (e.getCode() == KeyCode.UP){
                target.translateZProperty().set(target.getTranslateZ() + 1);

            }else if (e.getCode() == KeyCode.DOWN){
                target.translateZProperty().set(target.getTranslateZ() - 1);

            }else if (e.getCode() == KeyCode.RIGHT){
                target.translateXProperty().set(target.getTranslateX() + 1);

            }else if (e.getCode() == KeyCode.LEFT){
                target.translateXProperty().set(target.getTranslateX() - 1);
            }

        });
    }
    public static void main(String[] args) {
        launch();
    }
}