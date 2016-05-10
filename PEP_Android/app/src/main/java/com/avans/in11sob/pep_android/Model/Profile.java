package com.avans.in11sob.pep_android.Model;

import java.util.List;

/**
 * Created by mikes on 10-5-2016.
 */
public class Profile {

    ProfileStyle profileStyle;
    String seasonName;
    List<ProfileColor> profileColors;

    public Profile() {

    }

    public void addColor(ProfileColor color) {
        profileColors.add(color);
    }

    public List<ProfileColor> getColors() {
        return profileColors;
    }

    public void addStyle(ProfileStyle style) {
        profileStyle = style;
    }

    public ProfileStyle getStyle() {
        return profileStyle;
    }

    public void setSeason(String seasonName) {
        this.seasonName = seasonName;
    }
}
