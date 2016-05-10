package com.avans.in11sob.pep_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avans.in11sob.pep_android.R;
import com.google.gson.Gson;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PassportActivity extends AppCompatActivity {

    private RequestProfileTask mColorTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);

        Context context = this;
        SharedPreferences sharedPrefs = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        Log.d("Passport", "Kom ik hier");
        mColorTask = new RequestProfileTask(sharedPrefs.getString("token", ""));
        mColorTask.execute();
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
            InputStream is = null;
            Gson gson = new Gson();

            try {
                URL url = new URL(baseURL + URLExtention + token);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.connect();
                int response = conn.getResponseCode();
                is = conn.getInputStream();

                BufferedReader bR = new BufferedReader(  new InputStreamReader(is));
                String line = "";
                StringBuilder sb = new StringBuilder();

                while((line =  bR.readLine()) != null){
                    sb.append(line);
                    Log.e("StijlActivity:", line);

                }
                is.close();

                JSONObject obj =  new JSONObject(sb.toString());
//                gson = gson.fromJson(responseStrBuilder); /*json result*/



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
                // save user colors
//                User user = new User(getApplicationContext(), username, password, token);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            } else {

            }
        }
    }

//    private String requestColor(String color_url) throws IOException {
//        InputStream is = null;
//
//        try {
//            URL url = new URL(color_url);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /* milliseconds */);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//
//            // Starts the query
//            conn.connect();
//            int response = conn.getResponseCode();
//            is = conn.getInputStream();
//
//
//            BufferedReader bR = new BufferedReader(  new InputStreamReader(is));
//            String line = "";
//            StringBuilder responseStrBuilder = new StringBuilder();
//            while((line =  bR.readLine()) != null){
//                responseStrBuilder.append(line);
//            }
//            is.close();
//            // Convert the InputStream into a string
//            return responseStrBuilder.toString();
//
//        } finally {
//            if (is != null) {
//                is.close();
//            }
//        }
//    }

//    public boolean isConnected(){
//        ConnectivityManager connMgr = (ConnectivityManager) this.getSystemService(Activity.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
//        if (networkInfo != null && networkInfo.isConnected())
//            return true;
//        else
//            return false;
//    }

}
