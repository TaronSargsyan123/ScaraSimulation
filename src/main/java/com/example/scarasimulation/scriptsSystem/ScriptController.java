package com.example.scarasimulation.scriptsSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class ScriptController {
    private String name;
    private String path;
    private ArrayList<String> scriptsName = new ArrayList<>();



    public ScriptController(String name, String path) throws IOException {
        this.name = name;
        this.path = path;


        //  ---command types---
        //  move
        //  go_to_start

    }

    public ScriptController(String path){
        this.path = path;

    }


    public void startScript(){
                try {
                    FileWriter fileWriter = new FileWriter(path, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Script_start:" + name);
                    bufferedWriter.newLine();
                    bufferedWriter.close();

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
    }

    public void endScript() throws IOException {


        FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);


        bufferedWriter.newLine();
        bufferedWriter.write("Script_end");
        bufferedWriter.newLine();
        bufferedWriter.close();
        fileWriter.close();
    }

    public void writeMoveCommand(double x, double y) throws IOException {
        FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("Move:" + x + ":" + y);
        bufferedWriter.close();
        fileWriter.close();

    }

//    public void readScriptFromPath() throws IOException {
//
//        fileWriter = new FileWriter(path, true);
//        bufferedWriter = new BufferedWriter(fileWriter);
//        fileReader =  new FileReader(path);
//        bufferedReader = new BufferedReader(fileReader);
//
//        Boolean flag = false;
//        String line;
//        while ((line = bufferedReader.readLine()) != null) {
//            String[] parts = line.split(":");
//
//            if (parts[0] == "Script_start:"){
//                flag = true;
//                setName(parts[1]);
//            }
//
//
//
//        }
//
//        fileWriter.close();
//        fileReader.close();
//        bufferedWriter.close();
//        bufferedReader.close();
//    }
//
    public ArrayList<String> getScriptsName() throws IOException {

        FileWriter fileWriter = new FileWriter(path, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        FileReader fileReader =  new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(fileReader);


        scriptsName.clear();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(":");



            if (Objects.equals(parts[0], "Script_start")){

                scriptsName.add(parts[1]);
            }
        }

        fileWriter.close();
        fileReader.close();
        bufferedWriter.close();
        bufferedReader.close();

        return scriptsName;


    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
