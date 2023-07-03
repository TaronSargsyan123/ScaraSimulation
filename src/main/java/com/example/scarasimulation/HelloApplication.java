package com.example.scarasimulation;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    double innerLinkSize = 5;
    double outerLinkSize = 5;


    double innerLinkRadius = innerLinkSize / 100 *15;
    double outerLinkRadius = outerLinkSize / 100 *15;

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;
    private final Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
    private final Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);

    private Camera camera = new PerspectiveCamera(true);

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);

        Kinematics kinematics = new Kinematics();


        kinematics.setLinks(innerLinkSize, outerLinkSize);





        Group armGroup = new Group();
        Group outerLinkGroup = new Group();



        Sphere target = new Sphere(0.2);
        Cylinder plane = new Cylinder(10, 0.1);
        Cylinder cylinder = new Cylinder(2.5, 5);

        Cylinder innerLinkStartCylinder = new Cylinder(innerLinkRadius, 1);
        Box innerLink = new Box(innerLinkSize,1,innerLinkRadius*2);
        Cylinder innerLinkEndCylinder = new Cylinder(innerLinkRadius, 1);

        Cylinder outerLinkStartCylinder = new Cylinder(outerLinkRadius, 1);
        Box outerLink = new Box(outerLinkSize,1,outerLinkRadius*2);
        Cylinder outerLinkEndCylinder = new Cylinder(outerLinkRadius, 1);

        cylinder.setMaterial(new PhongMaterial(Color.BLUE));
        plane.setMaterial(new PhongMaterial(Color.ORANGE));
        target.setMaterial(new PhongMaterial(Color.RED));
        outerLinkStartCylinder.setMaterial(new PhongMaterial(Color.RED));
        outerLink.setMaterial(new PhongMaterial(Color.RED));
        outerLinkEndCylinder.setMaterial(new PhongMaterial(Color.RED));


        plane.translateYProperty().set(2.5);
        innerLinkStartCylinder.translateYProperty().set(-3);
        innerLinkEndCylinder.translateYProperty().set(-3);
        innerLinkEndCylinder.translateXProperty().set(innerLinkSize);
//        target.translateYProperty().set(-5);
//        target.translateXProperty().set(innerLinkSize + outerLinkSize);

        outerLink.translateYProperty().set(-4);
        outerLink.translateXProperty().set(innerLinkSize + outerLinkSize/2);
        outerLinkEndCylinder.translateYProperty().set(-4);
        outerLinkEndCylinder.translateXProperty().set(innerLinkSize + outerLinkSize);


        Translate translateLOne = new Translate(innerLinkSize/2, -3, 0);
        innerLink.getTransforms().add(translateLOne);

        Translate translateLOneCylinder = new Translate(innerLinkSize, -4, 0);
        outerLinkStartCylinder.getTransforms().add(translateLOneCylinder);



        outerLinkGroup.getChildren().addAll(outerLinkStartCylinder, outerLink, outerLinkEndCylinder);
        armGroup.getChildren().addAll(innerLink, innerLinkStartCylinder, innerLinkEndCylinder, outerLinkGroup);

        int x = 5;
        int y = 0;

//        Translate translateTarget = new Translate(x, -3, -y);
//        target.getTransforms().add(translateTarget);

        //ok

        double[] angles1 = kinematics.inverseKinematics(x, y); // Specify the desired end effector position
        Rotate rotate1 = new Rotate(angles1[0], 0, -4, 0, Rotate.Y_AXIS);
        System.out.println("Joint Angles: (" + angles1[0] + ", " + angles1[1] + ")");
        armGroup.getTransforms().add(rotate1);


        //not ok
//        System.out.println(angles1[0]);
//        System.out.println(Math.sin(Math.toRadians(angles1[0])));
//        Translate translateTarget = new Translate(Math.cos(Math.toRadians(angles1[0])) * innerLinkSize, -5, -Math.sin(Math.toRadians(angles1[0])) * innerLinkSize);
//        target.getTransforms().add(translateTarget);

//        Rotate outerLinkGroupRotate = new Rotate(0, Math.cos(Math.toRadians(angles1[0])) * innerLinkSize, 0, -Math.sin(Math.toRadians(angles1[0])) * innerLinkSize, Rotate.Y_AXIS);
//        outerLinkGroup.getTransforms().add(outerLinkGroupRotate);

        Translate translateTarget = new Translate(x, -5, -y);
        target.getTransforms().add(translateTarget);

        Rotate rotate = new Rotate(angles1[1], innerLinkSize, -4, 0, Rotate.Y_AXIS);
        outerLinkGroup.getTransforms().add(rotate);





        setCameraPos(camera);





        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(plane);
        root.getChildren().add(cylinder);
        root.getChildren().add(armGroup);
        root.getChildren().add(target);


        Scene scene = new Scene(root, 1000, 700, true);
        scene.setFill(Color.web("#4287f5"));
        scene.setCamera(camera);


        primaryStage.setScene(scene);
        primaryStage.show();


//        scene.setOnKeyPressed(e -> {
//            if (e.getCode() == KeyCode.A) {
//                Rotate rotate = new Rotate(10, 0, 0, 0, Rotate.Y_AXIS);
//                armGroup.getTransforms().add(rotate);
//
//
//            }else if (e.getCode() == KeyCode.D){
//                Rotate rotate = new Rotate(-10, 0, 0, 0, Rotate.Y_AXIS);
//                armGroup.getTransforms().add(rotate);
//
//            }else if (e.getCode() == KeyCode.UP){
////                target.translateZProperty().set(target.getTranslateZ() + 1);
////                double[] angles = kinematics.inverseKinematics(target.getTranslateX(), target.getTranslateZ()); // Specify the desired end effector position
////                Rotate rotate = new Rotate(angles[0], innerLinkSize, -4, 0, Rotate.Y_AXIS);
////                System.out.println("Joint Angles: (" + angles[0] + ", " + angles[1] + ")");
////                armGroup.getTransforms().add(rotate);
//
//            }else if (e.getCode() == KeyCode.DOWN){
////                target.translateZProperty().set(target.getTranslateZ() - 1);
////                System.out.println(target.getTranslateX());
////                double[] angles = kinematics.inverseKinematics(target.getTranslateX()/2, target.getTranslateZ());
////                System.out.println(angles[0]);
////                Rotate rotate = new Rotate(angles[0], 0, -4, 0, Rotate.Y_AXIS);
////                armGroup.getTransforms().add(rotate);
//
//            }else if (e.getCode() == KeyCode.RIGHT){
////                target.translateXProperty().set(target.getTranslateX() + 1);
////                Rotate rotate = new Rotate(-10, innerLinkSize, -4, 0, Rotate.Y_AXIS);
////                outerLinkGroup.getTransforms().add(rotate);
//
//            }else if (e.getCode() == KeyCode.LEFT){
////                target.translateXProperty().set(target.getTranslateX() - 1);
////                Rotate rotate = new Rotate(10, innerLinkSize, -4, 0, Rotate.Y_AXIS);
////                outerLinkGroup.getTransforms().add(rotate);
//            }
//
//        });



    }

    private void setCameraPos(Camera camera){
        camera.getTransforms().addAll(
                //-(innerLinkSize + outerLinkSize)*3.5
                new Rotate(-90, Rotate.X_AXIS),
                new Translate(0, 0, -80)
        );
    }


    public static void main(String[] args) {
        launch();
    }
}