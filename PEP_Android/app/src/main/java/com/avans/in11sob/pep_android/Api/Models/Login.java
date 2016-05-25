package com.avans.in11sob.pep_android.Api.Models;

import java.io.Serializable;

/**
 * Created by Mark on 25-5-2016.
 */
public class Login implements Serializable
{
    public boolean success;
    public Data data;

    public class Data
    {
        public boolean success;
        public String message;
        public String token;
    }
}
