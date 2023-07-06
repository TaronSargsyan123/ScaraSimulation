package com.example.scarasimulation;

import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class SCARAModel {

    private final Color innerLinkColor = Color.rgb(255, 190, 10);
    private final Color outerLinkColor = Color.rgb(0, 206, 209);
    private final Color backgroundColor = Color.rgb(213,213,213);
    private final Color standColor = Color.rgb(85,173,122);
    private final Color planeColor = Color.rgb(255, 255, 255);

    private Camera camera;

    private Group armGroup = new Group();
    private Group outerLinkGroup = new Group();

    private double armGroupAngle = 0;
    private double outerLinkGroupAngle = 0;

    private final Kinematics kinematics = new Kinematics();

    private double x;
    private  double y;

    private Group root = new Group();

    private double innerLinkSize = 11;
    private double outerLinkSize = 10;


    private final double innerLinkRadius = innerLinkSize / 100 *15;
    private final double outerLinkRadius = outerLinkSize / 100 *15;



    public SCARAModel(Camera camera, double x, double y){
        this.camera = camera;
        this.x = x;
        this.y = y;
        kinematics.setLinks(innerLinkSize, outerLinkSize);
    }

    public SCARAModel(Camera camera, double x, double y, double innerLinkSize, double outerLinkSize){
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.innerLinkSize = innerLinkSize;
        this.outerLinkSize = outerLinkSize;
        kinematics.setLinks(innerLinkSize, outerLinkSize);
    }

    public Group drawRobot(double x, double y){


        Cylinder plane = new Cylinder(10, 0.1);
        Cylinder cylinder = new Cylinder(2.5, 5);

        Cylinder innerLinkStartCylinder = new Cylinder(innerLinkRadius, 1);
        Box innerLink = new Box(innerLinkSize,1,innerLinkRadius*2);
        Cylinder innerLinkEndCylinder = new Cylinder(innerLinkRadius, 1);

        Cylinder outerLinkStartCylinder = new Cylinder(outerLinkRadius, 1);
        Box outerLink = new Box(outerLinkSize,1,outerLinkRadius*2);
        Cylinder outerLinkEndCylinder = new Cylinder(outerLinkRadius, 1);

        cylinder.setMaterial(new PhongMaterial(standColor));
        plane.setMaterial(new PhongMaterial(planeColor));
        outerLinkStartCylinder.setMaterial(new PhongMaterial(outerLinkColor));
        outerLink.setMaterial(new PhongMaterial(outerLinkColor));
        outerLinkEndCylinder.setMaterial(new PhongMaterial(outerLinkColor));

        innerLinkStartCylinder.setMaterial(new PhongMaterial(innerLinkColor));
        innerLink.setMaterial(new PhongMaterial(innerLinkColor));
        innerLinkEndCylinder.setMaterial(new PhongMaterial(innerLinkColor));


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

        root.getChildren().add(camera);
        root.getChildren().add(plane);
        root.getChildren().add(cylinder);
        root.getChildren().add(armGroup);

        return root;
    }

    public Group refreshRobot(){
        return root;
    }




    public void changeArmPos(double x, double y){
        double[] angles = kinematics.inverseKinematics(x, y);
        double armGroupDeltaAngle = angles[0]- armGroupAngle;
        Rotate rotateInnerLink = new Rotate(armGroupDeltaAngle, 0, -4, 0, Rotate.Y_AXIS);
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
        armGroup.getTransforms().add(rotateInnerLink);

        Rotate rotateOuterLink = new Rotate(angles[1], innerLinkSize, -4, 0, Rotate.Y_AXIS);
        outerLinkGroup.getTransforms().add(rotateOuterLink);
        armGroupAngle =  angles[0];
        outerLinkGroupAngle = angles[1];

    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setInnerLinkSize(double innerLinkSize) {
        this.innerLinkSize = innerLinkSize;
    }

    public void setOuterLinkSize(double outerLinkSize) {
        this.outerLinkSize = outerLinkSize;
    }

    public double getInnerLinkSize(){
        return innerLinkSize;
    }

    public double getOuterLinkSize(){
        return outerLinkSize;
    }

    public Color getBackgroundColor(){
        return backgroundColor;
    }


}
