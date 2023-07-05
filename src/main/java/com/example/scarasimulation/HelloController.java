package com.example.scarasimulation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class HelloController {

    public Button up_button;
    public Button left_button;
    public Button right_button;
    public Button down_button;

    Group armGroup = new Group();
    Group outerLinkGroup = new Group();

    double anchorPaneWidth;
    double anchorPaneHeight;

    Sphere target = new Sphere(0.2);

    private double subSceneWidth = 800;
    private double subSceneHeight = 600;

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

    @FXML
    public VBox right_vbox;

    @FXML
    public AnchorPane main_anchor_pane;

    public double getAnchorPaneWidth(){
        anchorPaneWidth = main_anchor_pane.getWidth();
        return anchorPaneWidth;
    }

    public double getAnchorPaneHeight(){
        anchorPaneHeight = main_anchor_pane.getHeight();
        return anchorPaneHeight;
    }



    @FXML
    private void handleKeyReleased(KeyEvent e) {

    }

    @FXML
    private void initialize() {
        Group startGroup = drawRobot(x, y);
        subSceneWidth = 1200;
        subSceneHeight = 700;

        SubScene subScene = new SubScene(startGroup, subSceneWidth, subSceneHeight, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.BLACK);
        subScene.setCamera(camera);



        setCameraPos(camera);
        kinematics.setLinks(innerLinkSize, outerLinkSize);


        mainGroup.getChildren().add(subScene);
        main_anchor_pane.getChildren().add(mainGroup);

        up_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y--;
                changeArmPos();
            }
        });
        down_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                y++;
                changeArmPos();
            }
        });
        left_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x--;
                changeArmPos();
            }
        });
        right_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                x++;
                changeArmPos();
            }
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