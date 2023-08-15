package com.example.scarasimulation.initialWindow;

import com.example.scarasimulation.Colors;
import com.example.scarasimulation.SimApplication;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TitleBar  {
    private double xOffset = 0;
    private double yOffset = 0;


    public BorderPane createTitleBar(Stage stage) {
        BorderPane titleBar = new BorderPane();
        titleBar.getStyleClass().add("title-bar");
        titleBar.setStyle("-fx-background-color: " + Colors.gray + ";");
        titleBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        ImageView imageView = new ImageView();
        Image logoImage = new Image(SimApplication.class.getResourceAsStream("/images/logo.png"));
        imageView.setImage(logoImage);
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        imageView.setTranslateX(-10);
        imageView.setTranslateY(10);

        Text label = new Text();
        label.setText("Revive sim");
        label.setFill(Color.valueOf(Colors.white));
        label.setFont(new Font(12));
        label.setTranslateY(10);
        label.setTranslateX(-20);

        Button minimizeButton = new Button();

        minimizeButton.setOnAction(event -> stage.setIconified(true));
        minimizeButton.setStyle("-fx-background-color: transparent;");

        Image imgMinimize = new Image(SimApplication.class.getResourceAsStream("/images/minimize.png"));
        ImageView viewMinimize = new ImageView(imgMinimize);
        viewMinimize.setFitHeight(11);
        viewMinimize.setPreserveRatio(true);

        minimizeButton.setGraphic(viewMinimize);



        Button maximizeButton = new Button();

        Image imgMaximize = new Image(SimApplication.class.getResourceAsStream("/images/maximize.png"));
        ImageView viewMaximize = new ImageView(imgMaximize);
        viewMaximize.setFitHeight(10);
        viewMaximize.setPreserveRatio(true);

        maximizeButton.setGraphic(viewMaximize);

        maximizeButton.setOnAction(event -> {
            if (stage.isMaximized()) {
                stage.setMaximized(false);
            } else {
                stage.setMaximized(true);
            }
        });
        maximizeButton.setStyle("-fx-background-color: transparent;");

        Button closeButton = new Button();

        Image imgClose = new Image(SimApplication.class.getResourceAsStream("/images/close.png"));
        ImageView viewClose = new ImageView(imgClose);
        viewClose.setFitHeight(10);
        viewClose.setPreserveRatio(true);

        closeButton.setGraphic(viewClose);

        closeButton.setOnAction(event -> stage.close());
        closeButton.setStyle("-fx-background-color: transparent;");












        closeButton.setTextFill(Color.valueOf(Colors.white));
        minimizeButton.setTextFill(Color.valueOf(Colors.white));
        maximizeButton.setTextFill(Color.valueOf(Colors.white));

        HBox buttonsHBox = new HBox();
        HBox labelHBox = new HBox();

        buttonsHBox.getChildren().addAll(closeButton, maximizeButton, minimizeButton);
        labelHBox.getChildren().addAll(label, imageView);

        // Add event handler for window dragging
        titleBar.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titleBar.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });



        titleBar.setLeft(buttonsHBox);
        titleBar.setRight(labelHBox);

        return titleBar;
    }

}
