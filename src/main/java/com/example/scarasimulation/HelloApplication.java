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
        Cylinder lOneCylinder = new Cylinder(1, 1);
        cylinder.setMaterial(new PhongMaterial(Color.BLUE));
        plane.setMaterial(new PhongMaterial(Color.ORANGE));
        target.setMaterial(new PhongMaterial(Color.RED));
        plane.translateYProperty().set(2.5);
        cylinderDesign.translateYProperty().set(-3);
        target.translateYProperty().set(-3.5);
        lOneGroup.getChildren().addAll(lOne, cylinderDesign, lOneCylinder);





        Camera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, -5, -80)
        );


        Translate translateLOne = new Translate(5, -3, 0);
        lOne.getTransforms().add(translateLOne);

        Translate translateLOneCylinder = new Translate(10, -4, 0);
        lOneCylinder.getTransforms().add(translateLOneCylinder);


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
                Rotate rotat = new Rotate(10, 0, 0, 0, Rotate.Y_AXIS);
                lOneGroup.getTransforms().add(rotat);


            }else if (e.getCode() == KeyCode.D){
                Rotate rotat = new Rotate(-10, 0, 0, 0, Rotate.Y_AXIS);
                lOneGroup.getTransforms().add(rotat);
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