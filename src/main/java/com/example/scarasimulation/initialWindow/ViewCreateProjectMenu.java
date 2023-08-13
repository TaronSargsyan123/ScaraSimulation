package com.example.scarasimulation.initialWindow;

import com.example.scarasimulation.Colors;
import com.example.scarasimulation.SaveUtils;
import com.example.scarasimulation.SimApplication;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;

import java.io.File;
import java.util.ArrayList;

import static com.example.scarasimulation.SimApplication.stage;

public class ViewCreateProjectMenu {
    private HBox innerLinkHBox;
    private HBox outerLinkHBox;
    private HBox columnHBox;
    private String path;


    public void createNewProjectMenu(VBox parent){
        SaveUtils saveUtils = new SaveUtils();
        innerLinkHBox = generateHBox("Inner link", 10, 40, 22);
        outerLinkHBox = generateHBox("Outer link", 10, 50, 20);
        columnHBox = generateHBox("Column", 10, 60, 30);

        HBox locationHBox = new HBox();
        HBox choseTypeHBox = new HBox();
        HBox nameHBox = new HBox();

        path = saveUtils.getDownloadsFolderPath();

        Text locationText = new Text();
        locationText.setText("Location");
        locationText.setFill(Color.valueOf(Colors.firstTextColor));
        locationText.setTranslateX(10);

        Image img = new Image(SimApplication.class.getResourceAsStream("/images/folder.png"));
        ImageView view = new ImageView(img);
        view.setFitHeight(18);
        view.setFitWidth(18);
        view.setTranslateX(25);
        view.setTranslateY(2);




        TextField locationTextField = new TextField();
        locationTextField.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");
        locationTextField.setText(path);


        locationHBox.setPrefWidth(parent.getPrefWidth());

        locationTextField.setPrefWidth(locationHBox.getPrefWidth() - locationText.getWrappingWidth() - 90);
        locationTextField.setTranslateX(15);

        locationHBox.getChildren().addAll(locationText, locationTextField, view);

        Text nameText = new Text();
        nameText.setText("Name");
        nameText.setFill(Color.valueOf(Colors.firstTextColor));
        nameText.setTranslateX(10);

        TextField nameTextField = new TextField();
        nameTextField.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");

        nameHBox.getChildren().addAll(nameText, nameTextField);
        nameHBox.setTranslateY(10);
        nameTextField.setTranslateX(30);




        Text choseTypeText = new Text();
        choseTypeText.setText("Type");
        choseTypeText.setFill(Color.valueOf(Colors.firstTextColor));

        ComboBox comboBox = new ComboBox();
        comboBox.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");



        comboBox.setTranslateX(25);

        choseTypeHBox.getChildren().addAll(choseTypeText, comboBox);
        choseTypeHBox.setTranslateY(20);
        choseTypeHBox.setTranslateX(10);

        parent.getChildren().addAll(locationHBox, nameHBox, choseTypeHBox);

        Button createButton = new Button();
        createButton.setText("Create");
        createButton.setTranslateY(70);
        createButton.setPrefWidth(60);
        createButton.setTranslateX(locationTextField.getPrefWidth());
        createButton.setStyle("-fx-background-color: " + Colors.lightBlue + "; -fx-text-fill: " + Colors.firstTextColor + ";");

        parent.getChildren().add(createButton);


        comboBox.getItems().add("SCARA");
        comboBox.getSelectionModel().selectFirst();
        changeComboBoxTextColor(comboBox);


        checkInitialComboBoxValue(comboBox, parent);


        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue == "SCARA"){
                    drawCreateSCARAMenu(parent);
                }

                // write here all robots types
            }
        });

        createButton.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createButton.setStyle("-fx-background-color: " + Colors.darkBlue + "; -fx-text-fill: " + Colors.firstTextColor + ";");
            }
        });

        createButton.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                createButton.setStyle("-fx-background-color: " + Colors.lightBlue + "; -fx-text-fill: " + Colors.firstTextColor + ";");
            }
        });

        createButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                TextField innerLinkTextField = (TextField) innerLinkHBox.getChildren().get(1);
                TextField outerLinkTextField = (TextField) outerLinkHBox.getChildren().get(1);
                TextField columnTextField = (TextField) columnHBox.getChildren().get(1);
                String name;
                if (nameTextField.getText().isEmpty()){
                    name = "unnamed";
                }else {
                    name = nameTextField.getText().toString();
                }
                try {
                    createButtonClick(saveUtils, path, name, Double.valueOf(innerLinkTextField.getText().toString()),
                            Double.valueOf(outerLinkTextField.getText().toString()), Double.valueOf(columnTextField.getText().toString()));

                    SimApplication.openMainWindow(Double.valueOf(innerLinkTextField.getText().toString()), Double.valueOf(outerLinkTextField.getText().toString()), Double.valueOf(columnTextField.getText().toString()));
                    saveUtils.addPathToProjectPaths(path  + name + ".txt");
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        view.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                File selectedDirectory = directoryChooser.showDialog(stage);

                if(selectedDirectory == null){
                    System.out.println("Directory not selected");
                }else{
                    path = selectedDirectory.getAbsolutePath() +"/";
                    locationTextField.setText(path);
                    System.out.println(selectedDirectory.getAbsolutePath());
                }
            }
        });

    }











    public HBox generateHBox(String text, double x, double y, double padding){
        HBox hBox = new HBox();
        hBox.setTranslateX(x);
        hBox.setTranslateY(y);

        Text viewText = new Text();
        viewText.setText(text);
        viewText.setFill(Color.valueOf(Colors.firstTextColor));
        viewText.setTranslateY(2);


        TextField textField = new TextField();
        textField.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");
        textField.setTranslateX(padding);
        textField.setPromptText("10 (default)");

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        hBox.getChildren().addAll(viewText, textField);
        return hBox;
    }

    private void drawCreateSCARAMenu(VBox parent){

        parent.getChildren().add(3, innerLinkHBox);
        parent.getChildren().add(4, outerLinkHBox);
        parent.getChildren().add(5, columnHBox);

    }

    private void changeComboBoxTextColor(ComboBox comboBox){
        comboBox.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<String>() {
                            {
                                super.setPrefWidth(100);
                            }
                            @Override public void updateItem(String item,
                                                             boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    setTextFill(Color.valueOf(Colors.firstTextColor));
                                    BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(Colors.gray), null, null);
                                    Background background = new Background(backgroundFill);
                                    setBackground(background);
                                    setOnMouseEntered(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(Colors.lightGray), null, null);
                                            Background background = new Background(backgroundFill);
                                            setBackground(background);
                                        }
                                    });

                                    setOnMouseExited(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                            BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf(Colors.gray), null, null);
                                            Background background = new Background(backgroundFill);
                                            setBackground(background);
                                        }
                                    });


                                }
                                else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }

                });
    }

    private void checkInitialComboBoxValue(ComboBox comboBox, VBox parent){
        if (comboBox.getValue() == "SCARA"){
            drawCreateSCARAMenu(parent);
        }
        //write here all types
    }

    private void createButtonClick(SaveUtils saveUtils, String path, String name, double innerLink, double outerLink, double column){
        saveUtils.saveSCARAProject(path, name, innerLink, outerLink, column);

    }



}
