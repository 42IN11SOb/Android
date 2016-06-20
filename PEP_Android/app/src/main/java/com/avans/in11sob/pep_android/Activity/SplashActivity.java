package com.avans.in11sob.pep_android.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avans.in11sob.pep_android.Api.Models.IsLoggedIn;
import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.ApiRequests;
import com.avans.in11sob.pep_android.Utilities.App;
import com.avans.in11sob.pep_android.Utilities.GsonGetRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences settings = getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE);
                String token = settings.getString("token", null);

                Log.e("Token", "Checking token: " + token);

                if (token == null || token.isEmpty()){
                    LoginActivity.startIntent(SplashActivity.this);
                } else {
                    final String url = "http://projectpep.herokuapp.com/users/loggedin?token=" + token;

                    final GsonGetRequest<IsLoggedIn> gsonGetRequest =
                            ApiRequests.checkLogin(
                                    new Response.Listener<IsLoggedIn>() {
                                        @Override
                                        public void onResponse(IsLoggedIn response) {
                                            if (response.success){

                                                Context context = getApplicationContext();
                                                SharedPreferences sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);

                                                final GsonGetRequest<Profile> gsonGetRequest =
                                                        ApiRequests.profile(
                                                                new Response.Listener<Profile>() {
                                                                    @Override
                                                                    public void onResponse(Profile response) {
                                                                        // The profile is automatic set within a Singleton class.
                                                                        // No further handling required here
                                                                    }
                                                                },
                                                                new Response.ErrorListener() {
                                                                    @Override
                                                                    public void onErrorResponse(VolleyError error) {

                                                                    }
                                                                },
                                                                sharedPrefs.getString("token", null)
                                                        );

                                                App.addRequest(gsonGetRequest, "profile");

                                                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                                startActivity(intent);
                                            } else {
                                                IntroductionActivity.startIntent(SplashActivity.this);
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            IntroductionActivity.startIntent(SplashActivity.this);
                                        }
                                    },
                                    token
                            );

                    App.addRequest(gsonGetRequest, "isLoggedIn");
                }
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
