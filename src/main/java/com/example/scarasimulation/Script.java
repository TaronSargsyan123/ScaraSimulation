package com.example.scarasimulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Script {
    private String name;
    private String path;
    private File file;
    private FileWriter fileWriter;
    private BufferedWriter bufferedWriter;


    public Script(String name, String path) throws IOException {
        this.name = name;
        this.path = path;
        fileWriter = new FileWriter(path, true);
        bufferedWriter = new BufferedWriter(fileWriter);

        //  ---command types---
        //  move
        //  go_to_start

    }

    public void startScript() throws IOException {
        bufferedWriter.write("Script_start:" + name);
        bufferedWriter.newLine();
    }

    public void endScript() throws IOException {
        bufferedWriter.write("Script_end");
        bufferedWriter.newLine();
    }

    public void writeMoveCommand(double x, double y) throws IOException {
        bufferedWriter.write("Move:" + x + ":" + y);
        bufferedWriter.newLine();
    }

    public void readScriptFromPath(){

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
