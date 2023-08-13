package com.example.scarasimulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveUtils {


    public String getDownloadsFolderPath(){
        String downloads = System.getProperty("user.home")+"\\Downloads\\";
        return downloads;

    }

    public String getHomeFolderPath(){
        String home = System.getProperty("user.home");
        return home;
    }

    public void saveSCARAProject(String pathStr, String name, double innerLink, double outerLink, double column){
        Path path = Paths.get(pathStr + name + ".txt");
        String str = "Name : " + name + "\n" + "InnerLink : " + innerLink + "\n" + "OuterLink : " + outerLink + "\n" + "Column : " + column + "\n" + "Type : SCARA";
        try {
            Files.writeString(path, str, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addPathToProjectPaths(String path){
        String filePath = "src/main/resources/projectsPaths.txt";

        try (FileWriter fileWriter = new FileWriter(filePath, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(path);
            bufferedWriter.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
