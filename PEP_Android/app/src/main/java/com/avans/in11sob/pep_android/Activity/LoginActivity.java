package com.avans.in11sob.pep_android.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.avans.in11sob.pep_android.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "test", "test"
    };

    private EditText mUsernameView;
    private EditText mPasswordView;

    private UserLogin mAuthTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mUsernameView = (EditText) findViewById(R.id.loginUsername);

        mPasswordView = (EditText) findViewById(R.id.loginPassword);

//        UserLogin mLogin = new UserLogin();
//        mLogin.execute();

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
        Toast.makeText(LoginActivity.this, "Skipping login", Toast.LENGTH_SHORT).show();
        intent = new Intent(this, PassportActivity.class);
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
        return password.length() > 4;
    }

    public class UserLogin extends AsyncTask<Void, Void, Boolean> {
        private final String username;
        private final String password;
        private final String baseURL = "http://bartimeus-temp.herokuapp.com";
        private final String loginExtention = "/users/login";


        UserLogin(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication againt API

            try {
                Log.e("HOI", "kom ik heir");
                // Simulate network access.
//                Thread.sleep(2000);
                URL url = new URL(baseURL + loginExtention);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                String urlParams = "?username=" + username + "&password=" + password;
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
//                conn.setDoInput(true);
                conn.setDoOutput(true);
                Log.e("HOI", "kom ik heir2");
                DataOutputStream dStream = new DataOutputStream(conn.getOutputStream());
                dStream.writeBytes(urlParams);
                dStream.flush();
                dStream.close();
                int responseCode = conn.getResponseCode();
                Log.e("HOI", "kom ik heir3");
//                final StringBuilder output = new StringBuilder("Request URL " + url);
                String output = "Request URL " + url;
//                output.append(System.getProperty("line.separator") + "Request Parameters " + urlParams);
                output += System.getProperty("line.separator") + "Request Parameters " + urlParams;
//                output.append(System.getProperty("line.separator") + "Response Code " + responseCode);
                Log.e("HOI", "kom ik heir4");
                output += System.getProperty("line.separator") + "Response Code " + responseCode;
//                output.append(System.getProperty("line.separator") + "Type " + "POST");
//                output += System.getProperty("line.separator") + "Type " + "POST";
                Log.e("HOI", "kom ik heir4");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                Log.e("HOI", "kom ik heir4");

                String line = "";
                Log.e("HOI", "kom ik heir5");
                StringBuilder responseOutput = new StringBuilder();
                System.out.println("Output=====================" + br);
                while((line = br.readLine()) != null) {
                    responseOutput.append(line);
                }
                Log.e("HOI", "kom ik heir6");
                br.close();
                Log.e("HOI", "kom ik heir7");
                output += System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString();

                System.out.println(output);

//                Toast.makeText(LoginActivity.this, "Redirecting...", Toast.LENGTH_SHORT);

                Log.e("HOI", "kom ik heir8");
//                Toast.makeText(LoginActivity.this, "Wrong username/password", Toast.LENGTH_SHORT);

            } catch (Exception e) {
                Log.e("Login", "Failed to send HTTP GET request.");
                Toast.makeText(LoginActivity.this, "Failed to send HTTP GET request", Toast.LENGTH_SHORT).show();
                return false;
            }

            // TODO: give wrong username/password message
            return true;
        }

    }

}

