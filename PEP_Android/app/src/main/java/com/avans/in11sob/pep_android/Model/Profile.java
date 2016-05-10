package com.avans.in11sob.pep_android.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mikes on 10-5-2016.
 */
public class Profile {

    private static Profile instance;

    public static Profile getInstance()
    {
        if (instance == null)
        {
            // Create the instance
            instance = new Profile();
        }
        // Return the instance
        return instance;
    }

    ProfileStyle profileStyle;
    String seasonName;
    ArrayList<ProfileColor> profileColors;

    public Profile() {
        profileColors = new ArrayList<ProfileColor>();
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
