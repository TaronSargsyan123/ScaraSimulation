package com.example.scarasimulation;


import com.example.scarasimulation.initialWindow.ViewProjectsMenu;
import com.example.scarasimulation.scara.ScaraDeserializer;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class SearchEngine {
    private ViewProjectsMenu projectsViewGenerator = new ViewProjectsMenu();


    //clear vbox read project names from file contains with searchTerm and draw projects in vbox
    public void searchName(String searchTerm, VBox vBox, String fileName, ArrayList<String> projectNamesArray) {
        vBox.getChildren().clear();
        for (String name : projectNamesArray) {
            if (name.toLowerCase().contains(searchTerm.toLowerCase())) {


                try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        ScaraDeserializer deserializer = new ScaraDeserializer(line);
                        if (Objects.equals(deserializer.getName(), name)) {
                            projectsViewGenerator.addSCARAView(deserializer.getName(), deserializer.getInnerLink(), deserializer.getOuterLink(), deserializer.getColumn(), vBox, deserializer.getPath());
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }







}
