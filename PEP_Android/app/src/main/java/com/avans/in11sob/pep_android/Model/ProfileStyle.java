package com.avans.in11sob.pep_android.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProfileStyle implements Serializable {

    private String info;
    private String image;
    private String advice;
    private String title;
    private ArrayList<String> donts;
    private ArrayList<String> dos;

    public ProfileStyle() {
        donts = new ArrayList<String>();
        dos = new ArrayList<String>();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getDonts() {
        return donts;
    }

    public void setDonts(ArrayList<String> donts) {
        this.donts = donts;
    }

    public void addDonts(String donts) {
        this.donts.add(donts);
    }

    public ArrayList<String> getDos() {
        return dos;
    }

    public void setDos(ArrayList<String> dos) {
        this.dos = dos;
    }

    public void addDos(String dos) {
        this.dos.add(dos);
    }
}
