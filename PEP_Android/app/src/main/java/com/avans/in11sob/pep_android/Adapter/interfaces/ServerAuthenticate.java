package com.avans.in11sob.pep_android.Adapter.interfaces;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Mark on 4-5-2016.
 */
public class ServerAuthenticate {

    private static boolean loggedin;
    private final String baseURL = "http://projectpep.herokuapp.com";
    private final String loginExtention = "/users/login";
    //private Boolean loggedin = false;

    private static String authToken;

    public static String userSignIn(String userName, String userPass, String mAuthTokenType)
    {
        try {
            URL url = new URL(baseURL + loginExtention);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            String urlParams = "username=" + userName + "&password=" + userPass;
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            conn.setFixedLengthStreamingMode(urlParams.getBytes().length);
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(15000);
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);

            // SEND REQUEST:
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.write(urlParams);
            out.close();

            Scanner instream = new Scanner(conn.getInputStream());
            String response = "";
            while (instream.hasNextLine()) {
                response += (instream.nextLine());
            }
            Log.d("LOGINACTIVITY", "doInBackground: " + response);
            JSONObject json = new JSONObject(response);
            loggedin = json.getBoolean("success");
            Log.d("Check", "" +loggedin);
            JSONObject jsonData  = new JSONObject(json.getString("data"));
            authToken = jsonData.getString("token");

            conn.disconnect();
        } catch (Exception e) {
            Log.e("Login", "Failed to send HTTP GET request.");
            authToken = "";
        }

        return authToken;
    }
}
