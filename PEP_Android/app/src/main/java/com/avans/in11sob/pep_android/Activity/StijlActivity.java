package com.avans.in11sob.pep_android.Activity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Model.Profile;
import com.avans.in11sob.pep_android.R;

import java.io.InputStream;
import java.util.ArrayList;

public class StijlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stijl);

        myStyle();
    }

    private void myStyle() {
        Profile profile = Profile.getInstance();
        String imageURL = profile.getStyle().getImage();
        new DownloadImageTask((ImageView) findViewById(R.id.myStyleImage))
                .execute(imageURL);

        String adviceTitle = profile.getStyle().getTitle();
        TextView adviceTitleView = (TextView) findViewById(R.id.myAdviceTitle);
        adviceTitleView.setText(adviceTitle);

        String adviceInfo = profile.getStyle().getInfo();
        TextView adviceInfoView = (TextView) findViewById(R.id.myAdviceInfo);
        adviceInfoView.setText(adviceInfo);

        String adviceText = profile.getStyle().getAdvice();
        TextView adviceTextView = (TextView) findViewById(R.id.myAdviceText);
        adviceTextView.setText(adviceText);

        ArrayList<String> adviceDos = profile.getStyle().getDos();
        String _dos = "";
        for (String _do : adviceDos) {
            _dos = _dos + "- " + _do + "\n";
        }
        TextView adviceDosView = (TextView) findViewById(R.id.myAdviceDos);
        adviceDosView.setText(_dos);

        ArrayList<String> adviceDonts = profile.getStyle().getDonts();
        String _donts = "";
        for (String _dont : adviceDonts) {
            _donts = _donts + "- " + _dont + "\n";
        }
        TextView adviceDontsView = (TextView) findViewById(R.id.myAdviceDonts);
        adviceDontsView.setText(_donts);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

}
