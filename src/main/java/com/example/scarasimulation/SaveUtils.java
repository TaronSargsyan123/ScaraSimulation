package com.example.scarasimulation;

public class SaveUtils {


    public String getDownloadsFolderPath(){
        String downloads = System.getProperty("user.home")+"/Downloads/";
        return downloads;

    }

    public String getHomeFolderPath(){
        String home = System.getProperty("user.home");
        return home;
    }
}
