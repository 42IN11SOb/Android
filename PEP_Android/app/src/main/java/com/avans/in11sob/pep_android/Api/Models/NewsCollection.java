package com.avans.in11sob.pep_android.Api.Models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mark on 20-6-2016.
 */
public class NewsCollection implements Serializable {
    public boolean success;
    public ArrayList<News> data = new ArrayList<>();
}
