package com.example.scarasimulation.scara;

import java.io.*;

public class ScaraDeserializer {

    private double innerLink;
    private double outerLink;
    private double column;
    private String name;
    private String path;


    public ScaraDeserializer(String path) throws IOException {
        this.path = path;
        File file = new File(path); //"src/main/resources/templates/standard_scara.txt"
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;


        while ((st = br.readLine()) != null) {
            st = st.replaceAll(" ", "");
            String[] arrOfStr = st.split(":", 2);

            switch (arrOfStr[0]) {
                case "Name" -> name = arrOfStr[1];
                case "InnerLink" -> innerLink = Double.parseDouble(arrOfStr[1]);
                case "OuterLink" -> outerLink = Double.parseDouble(arrOfStr[1]);
                case "Column" -> column = Double.parseDouble(arrOfStr[1]);
                default -> {
                }
            }
        }

    }


    public double getInnerLink(){
        return innerLink;
    }

    public double getOuterLink(){
        return outerLink;
    }

    public double getColumn(){
        return column;
    }

    public String getName(){
        return name;
    }

    public String getPath(){
        System.out.println(path);
        return path;
    }








}
