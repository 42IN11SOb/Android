package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.ApiRequests;
import com.avans.in11sob.pep_android.Utilities.App;
import com.avans.in11sob.pep_android.Utilities.GsonGetRequest;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        SharedPreferences sharedPrefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE);

        final GsonGetRequest<Profile> gsonGetRequest =
                ApiRequests.profile(
                        new Response.Listener<Profile>() {
                            @Override
                            public void onResponse(Profile response) {

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
    }

    public void buttonClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.scanner:
                intent = new Intent(this, ScannerActivity.class);
                startActivity(intent);
                break;
            case R.id.passport:
                intent = new Intent(this, PassportActivity.class);
                startActivity(intent);
                break;
            case R.id.news:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
            case R.id.overig:
                intent = new Intent(this, OverigActivity.class);
                startActivity(intent);
            default:
                break;
        }
    }

    private static final int TIME_DELAY = 2000;
    private static long back_pressed;
    @Override
    public void onBackPressed() {
        if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast toast = Toast.makeText(MainActivity.this, "Press once again to exit!", Toast.LENGTH_SHORT);
            toast.show();
        }
        back_pressed = System.currentTimeMillis();
    }

}
