package com.example.scarasimulation.scriptsSystem;

import com.example.scarasimulation.scriptsSystem.ScriptController;
import javafx.scene.Group;

import java.io.IOException;
import java.util.ArrayList;

public class ViewScriptsList {
    private ArrayList<String> scriptsArray = new ArrayList<>();
    private String path;

    public void setPath(String path){
        this.path = path;
    }
    public Group getScriptsList(String path) throws IOException {
        this.path = path;
        ScriptController scriptController = new ScriptController(path);
        scriptsArray = scriptController.getScriptsName();

        for (String element : scriptsArray) {
            System.out.println(element);
        }

        return null;
    }

    public void createNewScript(String name) throws IOException {
        ScriptController scriptController = new ScriptController(name, path);
        scriptController.startScript();
        scriptController.writeMoveCommand(5,5);
        scriptController.endScript();

    }




}
