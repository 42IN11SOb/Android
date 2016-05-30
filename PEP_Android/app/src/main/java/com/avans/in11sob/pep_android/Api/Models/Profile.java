package com.avans.in11sob.pep_android.Api.Models;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mark on 29-5-2016.
 */
public class Profile implements Serializable {
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
                public List<Color> colors;

                public class Color
                {
                    @SerializedName("_id")
                    public String id;
                    public String name;
                    public int r;
                    public int g;
                    public int b;
                }
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
