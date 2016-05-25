package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avans.in11sob.pep_android.Model.Profile;
import com.avans.in11sob.pep_android.Model.ProfileColor;
import com.avans.in11sob.pep_android.Model.ProfileStyle;
import com.avans.in11sob.pep_android.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PassportActivity extends AppCompatActivity {

    private RequestProfileTask mColorTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);

        Context context = this;
        SharedPreferences sharedPrefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Profile profile = Profile.getInstance();
        if (profile.getSeason() == null) {
            mColorTask = new RequestProfileTask(sharedPrefs.getString("token", ""));
            mColorTask.execute();
        }
    }

    public void buttonClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.stijl:
                intent = new Intent(this, StijlActivity.class);
                startActivity(intent);
                break;
            case R.id.kleurenPalet:
                intent = new Intent(this, ColorMasterActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    private class RequestProfileTask extends AsyncTask<Void, Void, Boolean> {
        private final String baseURL = "http://projectpep.herokuapp.com";
        private final String URLExtention = "/users/profile?token=";
        private String token;


        RequestProfileTask(String token) {
            this.token = token;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                URL url = new URL(baseURL + URLExtention + token);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                int responseCode = conn.getResponseCode();
                Scanner instream = new Scanner(conn.getInputStream());
                String response = "";
                while (instream.hasNextLine()) {
                    response += (instream.nextLine());
                }
                Log.e("Passport", response);

                Profile mProfile = Profile.getInstance();
                JSONObject json = new JSONObject(response);
                JSONObject jsonData = new JSONObject(json.getString("data"));
                JSONObject jsonPassport = new JSONObject(jsonData.getString("passport"));
                JSONObject jsonSeason = new JSONObject(jsonPassport.getString("season"));
                JSONObject jsonFigure = new JSONObject(jsonPassport.getString("figure"));

                String seasonName = jsonSeason.getString("name");
                mProfile.setSeason(seasonName);
                JSONArray seasonColors = jsonSeason.getJSONArray("colors");

                for(int i = 0; i < seasonColors.length(); i++) {
                    JSONObject colorsColor = seasonColors.getJSONObject(i);
                    JSONObject colorsColor1 = colorsColor.getJSONObject("color");
                    String colorId = colorsColor1.getString("_id");
                    Integer colorB = colorsColor1.getInt("b");
                    Integer colorG = colorsColor1.getInt("g");
                    Integer colorR = colorsColor1.getInt("r");
                    String colorName = colorsColor1.getString("name");
                    ProfileColor mColor = new ProfileColor(colorId, colorName, colorR, colorG, colorB);
                    mProfile.addColor(mColor);
                }

                ProfileStyle mStyle = new ProfileStyle();
                String figureTitle = jsonFigure.getString("title");
                mStyle.setTitle(figureTitle);
                String figureAdvice = jsonFigure.getString("advice");
                mStyle.setAdvice(figureAdvice);
                String figureInfo = jsonFigure.getString("info");
                mStyle.setInfo(figureInfo);
                String figureImage = jsonFigure.getString("img");
                mStyle.setImage(figureImage);
                JSONArray figureDonts = jsonFigure.getJSONArray("donts");
                for(int i = 0; i < figureDonts.length(); i++) {
                    mStyle.addDonts(figureDonts.getString(i));
                }
                JSONArray figureDos = jsonFigure.getJSONArray("dos");
                for(int i = 0; i < figureDos.length(); i++) {
                    mStyle.addDos(figureDos.getString(i));
                }
                mProfile.addStyle(mStyle);

                return true;
            } catch (Exception e) {
                Log.e("Login", "Failed to send HTTP GET request.");
                return false;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        protected void onPostExecute(final Boolean succes) {
            mColorTask = null;

            if(succes) {

            } else {

            }
        }
    }
}
