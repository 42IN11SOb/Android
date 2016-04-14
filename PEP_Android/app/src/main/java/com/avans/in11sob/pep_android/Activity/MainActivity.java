package com.avans.in11sob.pep_android.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avans.in11sob.pep_android.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
}
