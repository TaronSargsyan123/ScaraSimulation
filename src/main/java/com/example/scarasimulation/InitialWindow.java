package com.example.scarasimulation;

import com.example.scarasimulation.scara.ScaraDeserializer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;

public class InitialWindow {

    public VBox vbox_test;
    public Button projects_button;
    public Button new_project_button;
    public Button learn_button;
    public Button settings_button;

    private LettersImageGenerator lettersImageGenerator = new LettersImageGenerator();

//    public Button change_button;
//    public TextField inner_link;
//    public TextField outer_link;
//    public TextField height;






    @FXML
    private void changeWindow(double innerLinkSize, double outerLinkSize, double column) throws IOException {
        try {
            SimApplication.openMainWindow(innerLinkSize, outerLinkSize, column);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @FXML
    void initialize(){
        configureButtons();

        try {
            ScaraDeserializer deserializer = new ScaraDeserializer("src/main/resources/templates/standard_scara.txt");
            addScaraView(deserializer.getName(), deserializer.getInnerLink(), deserializer.getOuterLink(), deserializer.getColumn());
            addScaraView(deserializer.getName(), deserializer.getInnerLink(), deserializer.getOuterLink(), deserializer.getColumn());
            addScaraView(deserializer.getName(), deserializer.getInnerLink(), deserializer.getOuterLink(), deserializer.getColumn());

        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private void addScaraView(String name, double innerLinkSize, double outerLinkSize, double column){
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


        vbox_test.getChildren().add(hBox);

        hBox.setOnMouseExited(event -> hBox.setStyle("-fx-background-color: " + Colors.darkGray + ";"));

        hBox.setOnMouseEntered(event -> hBox.setStyle("-fx-background-color: " + Colors.gray + ";"));

        hBox.setOnMouseClicked(mouseEvent -> {
            try {
                changeWindow(innerLinkSize, outerLinkSize, column);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }

    private void configureButtons(){

        projects_button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
        projects_button.translateYProperty().set(15);
        projects_button.translateXProperty().set(5);
        projects_button.setTextFill(Color.valueOf(Colors.firstTextColor));

        projects_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                projects_button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
                projects_button.translateYProperty().set(15);
                projects_button.translateXProperty().set(5);
                projects_button.setTextFill(Color.valueOf(Colors.firstTextColor));
            }
        }


        );
        projects_button.setOnMouseExited(event -> projects_button.setStyle("-fx-background-color: " +"transperent" + ";"));

        new_project_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                new_project_button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
                new_project_button.translateYProperty().set(20);
                new_project_button.translateXProperty().set(5);
                new_project_button.setTextFill(Color.valueOf(Colors.firstTextColor));
                projects_button.setStyle("-fx-background-color: " +"transperent" + ";");
            }
        });

        new_project_button.setOnMouseExited(event -> new_project_button.setStyle("-fx-background-color: " +"transperent" + ";"));

        settings_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                settings_button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
                settings_button.translateYProperty().set(25);
                settings_button.translateXProperty().set(5);
                settings_button.setTextFill(Color.valueOf(Colors.firstTextColor));
                projects_button.setStyle("-fx-background-color: " +"transperent" + ";");
            }
        });

        settings_button.setOnMouseExited(event -> settings_button.setStyle("-fx-background-color: " +"transperent" + ";"));

        learn_button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                learn_button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
                learn_button.translateYProperty().set(30);
                learn_button.translateXProperty().set(5);
                learn_button.setTextFill(Color.valueOf(Colors.firstTextColor));
                projects_button.setStyle("-fx-background-color: " +"transperent" + ";");
            }
        });

        learn_button.setOnMouseExited(event -> learn_button.setStyle("-fx-background-color: " +"transperent" + ";"));

    }

}
