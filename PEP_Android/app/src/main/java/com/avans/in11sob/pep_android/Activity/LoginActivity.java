package com.avans.in11sob.pep_android.Activity;


import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avans.in11sob.pep_android.Adapter.Authenticator;
import com.avans.in11sob.pep_android.Model.User;
import com.avans.in11sob.pep_android.R;

import org.json.JSONObject;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    public static final String ARG_ACCOUNT_TYPE             = "com.pep_android";
    public static final String ARG_AUTH_TYPE                = "pepprofile";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT    = "";

    private EditText mUsernameView;
    private EditText mPasswordView;

    private UserLogin mAuthTask = null;

    private Authenticator authenticator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authenticator = new Authenticator(getApplicationContext());

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
        if(mAuthTask != null) {
            return;
        }
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
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.

            mAuthTask = new UserLogin(username, password);
            mAuthTask.execute((Void) null);

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

    public class UserLogin extends AsyncTask<Void, Void, Boolean> {
        private final String username;
        private final String password;
        private final String baseURL = "http://projectpep.herokuapp.com";
        private final String loginExtention = "/users/login";
        private Boolean loggedin = false;
        private String token = "";

        UserLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication againt API

            try {
                URL url = new URL(baseURL + loginExtention);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                String urlParams = "username=" + username + "&password=" + password;
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
                Log.d("LOGINACTIVITY", "doInBackground " + response);
                JSONObject json = new JSONObject(response);
                loggedin = json.getBoolean("success");
                Log.d("Check", "" +loggedin);
                token = json.getString("token");
                Log.d("Check", token);

                conn.disconnect();
                return loggedin;
            } catch (Exception e) {
                Log.e("Login", "Failed to send HTTP GET request.");
                return false;
            }
        }

        protected void onPostExecute(final Boolean succes) {
            mAuthTask = null;

            if(succes) {
                // save user info
                User user = new User(getApplicationContext(), username, password, token);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                mUsernameView.setError("");
                mPasswordView.setError("");
            }
        }

    }
}

