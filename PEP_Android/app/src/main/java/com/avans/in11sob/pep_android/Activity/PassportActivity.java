package com.avans.in11sob.pep_android.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avans.in11sob.pep_android.R;

public class PassportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
    }

    public void buttonClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.stijl:
                intent = new Intent(this, StijlActivity.class);
                startActivity(intent);
                break;
            case R.id.kleurenPalet:
                intent = new Intent(this, KleurenPaletActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
