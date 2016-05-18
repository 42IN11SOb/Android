package com.avans.in11sob.pep_android.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.avans.in11sob.pep_android.Fragment.ColorDetailFragment;
import com.avans.in11sob.pep_android.Fragment.ColorMasterFragment;
import com.avans.in11sob.pep_android.Model.Profile;
import com.avans.in11sob.pep_android.Model.ProfileColor;
import com.avans.in11sob.pep_android.R;

public class ColorMasterActivity extends AppCompatActivity implements ColorMasterFragment.OnItemSelectedListener {

    private ProfileColor currentColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_master);
        Profile pro = Profile.getInstance();
        this.setTitle(pro.getSeason());
    }

    @Override
    public void onListItemSelected(ProfileColor color) {
        boolean dualPane = getResources().getBoolean(R.bool.dual_pane);
        currentColor = color;

        if (dualPane) {
            ColorDetailFragment detailFragment;
            detailFragment = (ColorDetailFragment) getFragmentManager().findFragmentById(R.id.detailFragment);
            detailFragment.setColor(currentColor);
            detailFragment.showDetails();
        } else {
            Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
            intent.putExtra(ColorDetailActivity.COLOR, currentColor);
            startActivity(intent);
        }
    }
}
