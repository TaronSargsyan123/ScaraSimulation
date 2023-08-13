package com.example.scarasimulation.initialWindow;

import com.example.scarasimulation.Colors;
import com.example.scarasimulation.scara.ScaraDeserializer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InitialWindow {

    public VBox vbox_test;
    public Button projects_button;
    public Button new_project_button;
    public Button learn_button;
    public Button settings_button;
    public ImageView search_image;
    public TextField search_text_field;

    private ViewProjectsMenu projectsViewGenerator = new ViewProjectsMenu();
    private ViewCreateProjectMenu createNewProjectMenu = new ViewCreateProjectMenu();
    private String tempSearchString;


    @FXML
    void initialize(){

        setEnableDefaultButton( projects_button, 15);
        configureButton(projects_button, projects_button, 15);
        configureButton(projects_button, new_project_button, 20);
        configureButton(projects_button, settings_button, 25);
        configureButton(projects_button, learn_button, 30);

        drawProjects();

        projects_button.setOnAction(event -> projectsButtonOnClick());
        new_project_button.setOnAction(event -> newProjectButtonOnClick());



    }

    private void drawProjects(){

        String fileName = "src/main/resources/projectsPaths.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                ScaraDeserializer deserializer = new ScaraDeserializer(line);
                projectsViewGenerator.addSCARAView(deserializer.getName(), deserializer.getInnerLink(), deserializer.getOuterLink(), deserializer.getColumn(), vbox_test);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void projectsButtonOnClick(){
        setEnableSearchField();
        vbox_test.getChildren().clear();
        drawProjects();
    }

    private void newProjectButtonOnClick(){
        setDisableSearchField();
        vbox_test.getChildren().clear();
        createNewProjectMenu.createNewProjectMenu(vbox_test);
    }


    private void configureButton(Button defaultEnabledButton, Button button, double y){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
                button.translateYProperty().set(y);
                button.translateXProperty().set(5);
                button.setTextFill(Color.valueOf(Colors.firstTextColor));
                if (defaultEnabledButton != button){

                    defaultEnabledButton.setStyle("-fx-background-color: " +"transperent" + ";");
                }
            }
        });

        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: " +"transperent" + ";"));

    }

    private void setEnableDefaultButton(Button defaultButton, double y){
        defaultButton.setStyle("-fx-background-color: " + Colors.darkBlue + ";");
        defaultButton.translateYProperty().set(y);
        defaultButton.translateXProperty().set(5);
        defaultButton.setTextFill(Color.valueOf(Colors.firstTextColor));
    }

    private void setDisableSearchField(){
        tempSearchString = search_text_field.getText().toString();
        search_text_field.clear();
        search_text_field.setEditable(false);
    }


    private void setEnableSearchField(){
        search_text_field.setText(tempSearchString);
        search_text_field.setEditable(true);
    }










}
