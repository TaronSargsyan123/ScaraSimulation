package com.example.scarasimulation;

import java.lang.Math;

public class Kinematics {
    private double L1 = 5;
    private double L2 = 5;

    public void setLinks(double innerLink, double outerLink){
        L1 = innerLink;
        L2 = outerLink;
    }

    public double[] forwardKinematics(double theta1, double theta2) {
        double x = L1 * Math.cos(Math.toRadians(theta1)) + L2 * Math.cos(Math.toRadians(theta1 + theta2));
        double y = L1 * Math.sin(Math.toRadians(theta1)) + L2 * Math.sin(Math.toRadians(theta1 + theta2));
        double[] position = { x, y };
        return position;
    }



    public double[] inverseKinematics(double x, double y) {
        double distance = Math.sqrt(x * x + y * y);
        if (distance > L1 + L2 || distance < Math.abs(L1 - L2)) {
            return null;
        }
        double d = (x * x + y * y - L1 * L1 - L2 * L2) / (2 * L1 * L2);
        if (1 - d * d < 0) {
            return null;
        }
        double theta2 = Math.toDegrees(Math.atan2(Math.sqrt(1 - d * d), d));
        double theta1 = Math.toDegrees(Math.atan2(y, x) - Math.atan2(L2 * Math.sin(Math.toRadians(theta2)), L1 + L2 * Math.cos(Math.toRadians(theta2))));
        double[] angles = { theta1, theta2 };
        return angles;
    }



}
