package com.avans.in11sob.pep_android.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.avans.in11sob.pep_android.R;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /****** Create Thread that will sleep for 1 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 1 seconds
                    sleep(100000);

                    // After 1 seconds redirect to another intent
                    Intent intent = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(intent);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }
}
