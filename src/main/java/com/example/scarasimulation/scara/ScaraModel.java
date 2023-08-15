package com.example.scarasimulation.scara;

import com.example.scarasimulation.Colors;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PointLight;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ScaraModel {


    private static final int PLANE_SIZE = 2000;
    private static final int TEXTURE_SIZE = 20;

    private final Color innerLinkColor = Color.rgb(255, 190, 10);
    private final Color outerLinkColor = Color.rgb(0, 206, 209);
    private final Color backgroundColor = Color.valueOf(Colors.black);
    private final Color standColor = Color.rgb(85,173,122);

    private Camera camera;

    private Group armGroup = new Group();
    private Group outerLinkGroup = new Group();

    private double armGroupAngle = 0;
    private double outerLinkGroupAngle = 0;

    private final ScaraKinematics SCARAKinematics = new ScaraKinematics();

    private double x;
    private  double y;

    private Group root = new Group();

    private double innerLinkSize = 11;
    private double outerLinkSize = 10;

    private double cylinderHeight = 5;
    private double cylinderRadius = 2.5;


    private double innerLinkRadius = innerLinkSize / 100 *15;
    private double outerLinkRadius = outerLinkSize / 100 *15;

    private Cylinder plane;
    private Cylinder cylinder;
    private Cylinder innerLinkStartCylinder;
    private Box innerLink;
    private Cylinder innerLinkEndCylinder;
    private Cylinder outerLinkStartCylinder;
    private Box outerLink;
    private Cylinder outerLinkEndCylinder;

    public ScaraModel(Camera camera, double x, double y){
        this.camera = camera;
        this.x = x;
        this.y = y;
        SCARAKinematics.setLinks(innerLinkSize, outerLinkSize);
    }

    public ScaraModel(Camera camera, double x, double y, double innerLinkSize, double outerLinkSize){
        this.camera = camera;
        this.x = x;
        this.y = y;
        this.innerLinkSize = innerLinkSize;
        this.outerLinkSize = outerLinkSize;
        SCARAKinematics.setLinks(innerLinkSize, outerLinkSize);
    }

    public Group drawRobot(double x, double y){

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseMap(createTexture());

        plane = new Cylinder(100, 0.1);
        cylinder = new Cylinder(cylinderRadius, cylinderHeight);

        innerLinkStartCylinder = new Cylinder(innerLinkRadius, 1);
        innerLink = new Box(innerLinkSize,1,innerLinkRadius*2);
        Box innerLinkLine = new Box(innerLinkSize,1,innerLinkRadius*2);
        innerLinkLine.setDrawMode(DrawMode.LINE);
        innerLinkEndCylinder = new Cylinder(innerLinkRadius, 1);

        outerLinkStartCylinder = new Cylinder(outerLinkRadius, 1);
        outerLink = new Box(outerLinkSize,1,outerLinkRadius*2);
        Box outerLinkLine = new Box(outerLinkSize,1,outerLinkRadius*2);
        outerLinkLine.setDrawMode(DrawMode.LINE);
        outerLinkEndCylinder = new Cylinder(outerLinkRadius, 1);


        cylinder.setMaterial(new PhongMaterial(standColor));
        plane.setMaterial(material);
        outerLinkStartCylinder.setMaterial(new PhongMaterial(outerLinkColor));
        outerLink.setMaterial(new PhongMaterial(outerLinkColor));
        outerLinkLine.setMaterial(new PhongMaterial(Color.valueOf(Colors.black)));
        outerLinkEndCylinder.setMaterial(new PhongMaterial(outerLinkColor));

        innerLinkStartCylinder.setMaterial(new PhongMaterial(innerLinkColor));
        innerLink.setMaterial(new PhongMaterial(innerLinkColor));
        innerLinkLine.setMaterial(new PhongMaterial(Color.valueOf(Colors.black)));
        innerLinkEndCylinder.setMaterial(new PhongMaterial(innerLinkColor));



        plane.translateYProperty().set(2.5);
        innerLinkStartCylinder.translateYProperty().set(-3);
        innerLinkEndCylinder.translateYProperty().set(-3);
        innerLinkEndCylinder.translateXProperty().set(innerLinkSize);


        outerLink.translateYProperty().set(-4);
        outerLink.translateXProperty().set(innerLinkSize + outerLinkSize/2);
        outerLinkLine.translateYProperty().set(-4);
        outerLinkLine.translateXProperty().set(innerLinkSize + outerLinkSize/2);
        outerLinkEndCylinder.translateYProperty().set(-4);
        outerLinkEndCylinder.translateXProperty().set(innerLinkSize + outerLinkSize);


        Translate translateLOne = new Translate(innerLinkSize/2, -3, 0);
        innerLink.getTransforms().add(translateLOne);
        innerLinkLine.getTransforms().add(translateLOne);


        Translate translateLOneCylinder = new Translate(innerLinkSize, -4, 0);
        outerLinkStartCylinder.getTransforms().add(translateLOneCylinder);

        outerLinkGroup.getChildren().addAll(outerLinkStartCylinder, outerLink, outerLinkEndCylinder, outerLinkLine);
        armGroup.getChildren().addAll(innerLink, innerLinkStartCylinder, innerLinkEndCylinder, outerLinkGroup, innerLinkLine);

        PointLight pointLight = new PointLight(Color.WHITE);
        pointLight.setTranslateX(0);
        pointLight.setTranslateY(-5);
        pointLight.setTranslateZ(-10);

        // Create an AmbientLight
        AmbientLight ambientLight = new AmbientLight(Color.WHITE);
        ambientLight.setLightOn(true);

        setPos(x, y);






        root.getChildren().addAll(camera, plane, cylinder, armGroup, ambientLight);

        return root;
    }

    public void refreshRobot(){
        innerLinkRadius = innerLinkSize / 100 *15;
        outerLinkRadius = outerLinkSize / 100 *15;

        innerLinkStartCylinder.setRadius(innerLinkRadius);
        innerLinkEndCylinder.setRadius(innerLinkRadius);
        outerLinkStartCylinder.setRadius(outerLinkRadius);
        outerLinkEndCylinder.setRadius(outerLinkRadius);

        this.innerLink.setWidth(innerLinkSize);
        this.outerLink.setWidth(outerLinkSize);
        cylinder.setHeight(cylinderHeight);
        cylinder.setRadius(cylinderRadius);

        plane.translateYProperty().set(2.5);
        innerLinkStartCylinder.setLayoutY(-3);
        innerLinkEndCylinder.setLayoutY(-3);
        innerLinkEndCylinder.translateXProperty().set(innerLinkSize);

        outerLink.translateYProperty().set(-4);
        outerLink.translateXProperty().set(innerLinkSize + outerLinkSize/2);
        outerLinkEndCylinder.translateYProperty().set(-4);
        outerLinkEndCylinder.translateXProperty().set(innerLinkSize + outerLinkSize);


        Translate translateLOne = new Translate(innerLinkSize/2, -3, 0);
        innerLink.getTransforms().add(translateLOne);

        Translate translateLOneCylinder = new Translate(innerLinkSize, -4, 0);
        outerLinkStartCylinder.getTransforms().add(translateLOneCylinder);
    }




    public void changeArmPos(double x, double y){
        SCARAKinematics.setLinks(innerLinkSize, outerLinkSize);
        double[] angles = SCARAKinematics.inverseKinematics(x, y);
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
        SCARAKinematics.setLinks(innerLinkSize, outerLinkSize);
        double[] angles = SCARAKinematics.inverseKinematics(tempX, tempY);
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

    public double getCylinderHeight() {
        return cylinderHeight;
    }

    public void setCylinderHeight(double cylinderHeight) {
        this.cylinderHeight = cylinderHeight;
    }

    public double getCylinderRadius() {
        return cylinderRadius;
    }

    public void setCylinderRadius(double cylinderRadius) {
        this.cylinderRadius = cylinderRadius;
    }

    public Color getBackgroundColor(){
        return backgroundColor;
    }

    private javafx.scene.image.Image createTexture() {
        int tileSize = TEXTURE_SIZE;
        int numTiles = PLANE_SIZE / tileSize;

        javafx.scene.image.WritableImage writableImage = new javafx.scene.image.WritableImage(
                numTiles * tileSize, numTiles * tileSize);
        javafx.scene.image.PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < numTiles; i++) {
            for (int j = 0; j < numTiles; j++) {
                int color = (i + j) % 2 == 0 ? 255 : 0;
                for (int x = 0; x < tileSize; x++) {
                    for (int y = 0; y < tileSize; y++) {
                        if (color == 255){
                            pixelWriter.setColor(i * tileSize + x, j * tileSize + y,
                                    javafx.scene.paint.Color.valueOf(Colors.lightGray));
                        } else {
                            pixelWriter.setColor(i * tileSize + x, j * tileSize + y,
                                    javafx.scene.paint.Color.valueOf(Colors.gray));
                        }

                    }
                }
            }
        }

        return writableImage;
    }
}
