package com.example.scarasimulation.initialWindow;

import com.example.scarasimulation.Colors;
import com.example.scarasimulation.SimApplication;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class ViewProjectsMenu {
    private LettersImageGenerator lettersImageGenerator = new LettersImageGenerator();

    public void addSCARAView(String name, double innerLinkSize, double outerLinkSize, double column, VBox parent, String path){
        HBox hBox = new HBox();

        VBox textVbox = new VBox();
        hBox.setPadding(new Insets(0, 10, 10, 10));
        textVbox.setPadding(new Insets(0, 10, 10, 10));
        ImageView imageView = new ImageView();

        Text textOne = new Text();
        textOne.setText(name);
        textOne.setFont(Font.font(12));
        textOne.setFill(Color.valueOf(Colors.firstTextColor));

        Text textTwo = new Text();

        textTwo.setText("Inner link " + innerLinkSize + ", outer link " + outerLinkSize + ", column " + column);
        textTwo.setFont(Font.font(12));
        textTwo.setFill(Color.valueOf(Colors.secondTextColor));

        textVbox.getChildren().addAll(textOne, textTwo);

        imageView.setImage(lettersImageGenerator.generateLettersImage(name));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);

        hBox.getChildren().addAll(imageView, textVbox);


        parent.getChildren().add(hBox);

        hBox.setOnMouseExited(event -> hBox.setStyle("-fx-background-color: " + Colors.darkGray + ";"));

        hBox.setOnMouseEntered(event -> hBox.setStyle("-fx-background-color: " + Colors.gray + ";"));

        hBox.setOnMouseClicked(mouseEvent -> {
            try {
                SimApplication.openMainWindow(innerLinkSize, outerLinkSize, column, path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }
}
