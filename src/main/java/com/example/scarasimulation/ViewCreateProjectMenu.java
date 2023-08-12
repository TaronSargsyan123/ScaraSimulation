package com.example.scarasimulation;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

public class ViewCreateProjectMenu {
    public void createNewProjectMenu(VBox parent){
        SaveUtils saveUtils = new SaveUtils();

        HBox locationHBox = new HBox();
        HBox choseTypeHBox = new HBox();

        Text locationText = new Text();
        locationText.setText("Location");
        locationText.setFill(Color.valueOf(Colors.firstTextColor));
        locationText.setTranslateX(10);

        TextField locationTextField = new TextField();
        locationTextField.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");
        locationTextField.setText(saveUtils.getDownloadsFolderPath());


        locationHBox.setPrefWidth(parent.getPrefWidth());

        locationTextField.setPrefWidth(locationHBox.getPrefWidth() - locationText.getWrappingWidth() - 60);
        locationTextField.setTranslateX(15);

        locationHBox.getChildren().addAll(locationText, locationTextField);

        Text choseTypeText = new Text();
        choseTypeText.setText("Type");
        choseTypeText.setFill(Color.valueOf(Colors.firstTextColor));

        ComboBox comboBox = new ComboBox();
        comboBox.setStyle("-fx-background-color: " + Colors.gray + "; -fx-prompt-text-fill: " + Colors.firstTextColor + "; -fx-text-fill: " + Colors.firstTextColor + "; -fx-border-color: " + Colors.lightGray + "; -fx-border-width: 1px;");



        comboBox.setTranslateX(25);

        choseTypeHBox.getChildren().addAll(choseTypeText, comboBox);
        choseTypeHBox.setTranslateY(10);
        choseTypeHBox.setTranslateX(10);

        parent.getChildren().addAll(locationHBox, choseTypeHBox);

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
        parent.getChildren().add(2, generateHBox("Inner link", 10, 40, 22));
        parent.getChildren().add(3, generateHBox("Outer link", 10, 50, 20));
        parent.getChildren().add(4, generateHBox("Column", 10, 60, 30));

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



}
