package com.avans.in11sob.pep_android.Api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 29-5-2016.
 */
public class Profile implements Serializable{

    private static Profile instance;

    public static Profile getInstance()
    {
        if (instance == null)
        {
            instance = new Profile();
        }
        return instance;
    }

    public static Profile getInstance(Profile profile)
    {
        if (instance == null)
        {
            instance = profile;
        }
        return instance;
    }

    public Profile()
    {
        getInstance(this);
    }

    public boolean success;
    public Data data;

    public class Data
    {
        @SerializedName("_id")
        public String id;

        public String username;
        public boolean email;
        public Role role;
        public Passport passport;

        public class Role
        {
            @SerializedName("_id")
            public String id;
            public String name;
        }

        public class Passport
        {
            @SerializedName("_id")
            public String id;
            public Season season;
            public Figure figure;
            public boolean mustResetPassword;
            public boolean active;
            public boolean male;

            public class Season
            {
                @SerializedName("_id")
                public String id;
                public String name;
                public ArrayList<Color> colors = new ArrayList<>();
            }

            public class Figure
            {
                @SerializedName("_id")
                public String id;
                public String title;
                public String advice;
                public String info;
                public String img;
                public List<String> donts;
                public List<String> dos;
            }
        }
    }
}
