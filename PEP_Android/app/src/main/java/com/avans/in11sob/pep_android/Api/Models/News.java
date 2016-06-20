package com.avans.in11sob.pep_android.Api.Models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 20-6-2016.
 */
public class News implements Serializable {
    @SerializedName("_id")
    public String id;
    public String content;
    public String title;
    @SerializedName("publish")
    public Boolean published;
}
