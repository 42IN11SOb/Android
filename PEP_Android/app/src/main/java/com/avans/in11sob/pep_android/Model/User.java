package com.avans.in11sob.pep_android.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by mikes on 16-4-2016.
 */
public class User {

    private final Context context;
    private final SharedPreferences sharedPrefs;

    private String username = "";
    private String password = "";
    private String token = "";
    private String email = "";

    public User (Context context, String _username, String _password, String _token) {
        this.context = context;
        this.username = _username;
        this.password = _password;
        this.token = _token;

        sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("token", token);
        editor.apply();
    }

    public void getUserPref() {
        Gson gson = new Gson();
        String username = sharedPrefs.getString("username", "");
        String password = sharedPrefs.getString("password", "");
        String token = sharedPrefs.getString("token", "");
    }

    public String getToken() {
        return token;
    }
}
