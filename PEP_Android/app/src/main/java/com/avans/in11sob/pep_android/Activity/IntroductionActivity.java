package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.avans.in11sob.pep_android.R;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
    }

    public void buttonClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.nonVisitedPEP:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.visitedPEP:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    public static void startIntent(Context c)
    {
        Intent intent = new Intent(c, IntroductionActivity.class);
        c.startActivity(intent);
    }
}
