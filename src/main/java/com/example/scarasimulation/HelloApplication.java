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
    Group armGroup = new Group();
    Group outerLinkGroup = new Group();

    Sphere target = new Sphere(0.2);

    private int subSceneWidth = 800;
    private int subSceneHeight = 600;

    double armGroupAngle = 0;
    double outerLinkGroupAngle = 0;

    double x = -5;
    double y = 4;

    Kinematics kinematics = new Kinematics();


    double innerLinkSize = 7;
    double outerLinkSize = 5;


    double innerLinkRadius = innerLinkSize / 100 *15;
    double outerLinkRadius = outerLinkSize / 100 *15;

    private Group mainGroup = new Group();

    private Camera camera = new PerspectiveCamera(true);


    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        setCameraPos(camera);

        kinematics.setLinks(innerLinkSize, outerLinkSize);
        Group startGroup = drawRobot(x, y);

        SubScene subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera);

        mainGroup.getChildren().add(subScene);





        Scene scene = new Scene(mainGroup, 1000, 700, true);
        scene.setFill(Color.web("#4287f5"));


        primaryStage.setScene(scene);
        primaryStage.show();




        scene.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.UP){
                y--;
                changeArmPos();




            }else if (e.getCode() == KeyCode.DOWN){
                y++;
                changeArmPos();




            }else if (e.getCode() == KeyCode.RIGHT){
                x++;
                changeArmPos();



            }else if (e.getCode() == KeyCode.LEFT){
                x--;
                changeArmPos();
            }
            System.out.println(x + ", " + y);

        });



    }

    private Group drawRobot(double x, double y){


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

        setPos(x, y);



        Group root = new Group();
        root.getChildren().add(camera);
        root.getChildren().add(plane);
        root.getChildren().add(cylinder);
        root.getChildren().add(armGroup);
        root.getChildren().add(target);

        return root;
    }


    private void setCameraPos(Camera camera){
        camera.getTransforms().addAll(
                //-(innerLinkSize + outerLinkSize)*3.5
                new Rotate(-55, Rotate.X_AXIS),
                new Translate(0, 0, -(innerLinkSize + outerLinkSize)*4)
        );
    }

    private void changeArmPos(){
        double[] angles = kinematics.inverseKinematics(x, y);
        double armGroupDeltaAngle = angles[0]- armGroupAngle;
        Rotate rotateInnerLink = new Rotate(armGroupDeltaAngle, 0, -4, 0, Rotate.Y_AXIS);
        System.out.println("Joint Angles: (" + angles[0] + ", " + angles[1] + ")");
        armGroup.getTransforms().add(rotateInnerLink);
        double outerLinkGroupDeltaAngle = angles[1]- outerLinkGroupAngle;
        Rotate rotateOuterLink = new Rotate(outerLinkGroupDeltaAngle, innerLinkSize, -4, 0, Rotate.Y_AXIS);
        outerLinkGroup.getTransforms().add(rotateOuterLink);
        armGroupAngle = angles[0];
        outerLinkGroupAngle = angles[1];
    }


    public static void main(String[] args) {
        launch();
    }

    private void setPos(double tempX, double tempY){
        double[] angles = kinematics.inverseKinematics(tempX, tempY);
        Rotate rotateInnerLink = new Rotate(angles[0], 0, -4, 0, Rotate.Y_AXIS);
        System.out.println("Joint Angles: (" + angles[0] + ", " + angles[1] + ")");
        armGroup.getTransforms().add(rotateInnerLink);

        Rotate rotateOuterLink = new Rotate(angles[1], innerLinkSize, -4, 0, Rotate.Y_AXIS);
        outerLinkGroup.getTransforms().add(rotateOuterLink);
        armGroupAngle =  angles[0];
        outerLinkGroupAngle = angles[1];

    }
}