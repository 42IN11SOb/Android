package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.ApiRequests;
import com.avans.in11sob.pep_android.Utilities.App;
import com.avans.in11sob.pep_android.Utilities.GsonGetRequest;

public class PassportActivity extends AppCompatActivity {

    //private RequestProfileTask mColorTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        //mColorTask = new RequestProfileTask(sharedPrefs.getString("token", ""));
        //mColorTask.execute();
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
}
