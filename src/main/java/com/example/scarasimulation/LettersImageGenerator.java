package com.example.scarasimulation;

import javafx.scene.image.WritableImage;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LettersImageGenerator {

    public WritableImage generateLettersImage(String word){
        WritableImage image = null;

        if (word.length() >= 2) {
            String firstLetter = word.substring(0, 1);
            String secondLetter = word.substring(1, 2);

            int width = 40;
            int height = 40;
            BufferedImage capture = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = capture.createGraphics();

            Color startColor = Colors.firstColor;
            Color endColor = Colors.secondColor;

            int startX = 10, startY = 20, endX = 30, endY = 40;

            GradientPaint gradient = new GradientPaint(startX, startY, startColor, endX, endY, endColor);

            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, width, height);
            g2d.setColor(Color.decode(Colors.darkGray));
            g2d.setFont(new Font("Sagoe", Font.PLAIN, 30));
            g2d.drawString(firstLetter, 5, 30);
            g2d.drawString(secondLetter, 25, 30);
            g2d.dispose();

            image = javafx.embed.swing.SwingFXUtils.toFXImage(capture, null);
        } else {
            System.out.println("The input string is too short to get two letters.");
        }

        return image;

    }





}
