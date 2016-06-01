package com.avans.in11sob.pep_android.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.avans.in11sob.pep_android.Fragment.ColorDetailFragment;
import com.avans.in11sob.pep_android.Api.Models.Color;
import com.avans.in11sob.pep_android.R;

public class ColorDetailActivity extends AppCompatActivity {

    public static final String COLOR = "color";
    ColorDetailFragment fragmentColorDetail;

    private Color color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getResources().getBoolean(R.bool.dual_pane)) {
            finish();
            return;
        }
        setContentView(R.layout.activity_color_detail);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            Color _color = (Color) extras.getSerializable(COLOR);
            this.color = _color;
            this.setTitle(color.color.name);

            ColorDetailFragment detailFragment = (ColorDetailFragment) getFragmentManager()
                    .findFragmentById(R.id.detailFragment);

            detailFragment.setColor(_color);
            detailFragment.showDetails();
        }
    }
}
