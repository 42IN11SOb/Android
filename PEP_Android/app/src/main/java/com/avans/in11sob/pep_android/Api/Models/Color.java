package com.avans.in11sob.pep_android.Api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Mark on 31-5-2016.
 */
public class Color implements Serializable
{
    public IColor color;

    public class IColor implements Serializable{
        @SerializedName("_id")
        public String id;
        public String name;
        public int r;
        public int g;
        public int b;

        @Override
        public String toString() {
            return name;
        }
    }
}