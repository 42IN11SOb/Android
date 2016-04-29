package com.avans.in11sob.pep_android.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileColor implements Serializable {

    String id;
    String name;
    Integer red;
    Integer green;
    Integer blue;

    public ProfileColor(String _id, String _name, Integer _red, Integer _green, Integer _blue) {
        this.id = _id;
        this.name = _name;

        this.red = _red;
        this.green = _green;
        this.blue = _blue;
    }

    public static ArrayList<ProfileColor> getColors() {
        ArrayList<ProfileColor> colors = new ArrayList<ProfileColor>();
//        colors.add(new ProfileColor("1", "Rood", "FF0000"));
        colors.add(new ProfileColor("1", "Rood", 255,0,0));
//        colors.add(new ProfileColor("2", "Oranje", "FF8000"));
        colors.add(new ProfileColor("2", "Oranje", 255, 128, 0));
//        colors.add(new ProfileColor("3", "Geel", "FFFF00"));
        colors.add(new ProfileColor("3", "Geel", 255, 255, 0));
//        colors.add(new ProfileColor("4", "Groen", "00FF00"));
        colors.add(new ProfileColor("4", "Groen", 0, 255, 0));
//        colors.add(new ProfileColor("5", "Blauw", "0000FF"));
        colors.add(new ProfileColor("5", "Blauw", 0, 0, 255));
//        colors.add(new ProfileColor("6", "Violet", "AA00FF"));
        colors.add(new ProfileColor("6", "Violet", 170, 0, 255));
        return colors;
    }

    public String getName() {
        return name;
    }

    public Integer getRed() {
        return red;
    }

    public Integer getGreen() {
        return green;
    }

    public Integer getBlue() {
        return blue;
    }

    @Override
    public String toString() {
        return getName();
    }
}
