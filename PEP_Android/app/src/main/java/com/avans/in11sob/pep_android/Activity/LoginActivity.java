package com.avans.in11sob.pep_android.Activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avans.in11sob.pep_android.Api.Models.Login;
import com.avans.in11sob.pep_android.Model.User;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.ApiRequests;
import com.avans.in11sob.pep_android.Utilities.App;
import com.avans.in11sob.pep_android.Utilities.GsonPostRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;

public class LoginActivity extends Activity {

    private EditText mUsernameView;
    private EditText mPasswordView;

    private boolean loginBusy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameView = (EditText) findViewById(R.id.loginUsername);
        mPasswordView = (EditText) findViewById(R.id.loginPassword);

        Button mLoginButton = (Button) findViewById(R.id.loginButton);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        Button mGuestButton = (Button) findViewById(R.id.guestLoginButton);
        mGuestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                skipLogin();
            }
        });
    }

    Intent intent;

    private void skipLogin() {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void attemptLogin() {
        if (loginBusy)
            return;
        loginBusy = true;
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError("This password is too short");
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError("This field is required");
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            loginBusy = false;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            final GsonPostRequest<Login> gsonPostRequest =
                    ApiRequests.login(
                            new Response.Listener<Login>() {
                                @Override
                                public void onResponse(Login response) {

                                    SharedPreferences settings = getApplicationContext().getSharedPreferences("auth", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = settings.edit();

                                    editor.putString("token", response.data.token);
                                    editor.commit();
                                    Log.e("Token", "Token submitted: " + response.data.token);

                                    //User user = new User(getApplicationContext(), username, password, token);
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("LoginActivity", "On error triggered for some reason");
                                    mUsernameView.setError("");
                                    mPasswordView.setError("");
                                }
                            },
                            username,
                            password
                    );

            App.addRequest(gsonPostRequest, "Login");
        }
    }

    private boolean isEmailValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 4;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    public static void startIntent(Context c)
    {
        Intent intent = new Intent(c, LoginActivity.class);

        c.startActivity(intent);
    }
}

