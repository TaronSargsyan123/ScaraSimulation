package com.example.scarasimulation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class InitialWindow {

    public Button change_button;
    public TextField inner_link;
    public TextField outer_link;
    public TextField height;

    @FXML
    private void handleButtonAction() throws IOException {
        try {
            double innerLinkSize = Double.parseDouble(inner_link.getText());
            double outerLinkSize = Double.parseDouble(outer_link.getText());
            double heightSize = Double.parseDouble(height.getText());
            SimApplication.openMainWindow(innerLinkSize, outerLinkSize, heightSize);
        }catch (Exception e){
            System.out.println("invalid values");
        }


    }

    @FXML
    void initialize(){
        change_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    handleButtonAction();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
