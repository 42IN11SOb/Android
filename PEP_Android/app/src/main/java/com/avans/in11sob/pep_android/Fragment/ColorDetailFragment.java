package com.avans.in11sob.pep_android.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.Api.Models.Color;
import com.avans.in11sob.pep_android.R;

import java.util.ArrayList;


public class ColorDetailFragment extends Fragment {

    private Color color;

    public ColorDetailFragment() {
    }

    public static ColorDetailFragment newInstance() {
        return new ColorDetailFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_color_detail, container, false);

        return view;
    }

    public void setColor(Color c) {
        color = c;
    }

    public void showDetails(){
        TextView background = (TextView) getView().findViewById(R.id.frBackgroundColor);
//        background.setBackgroundColor(Color.parseColor("#"+color.getCode()));
        background.setBackgroundColor(android.graphics.Color.rgb(color.color.r, color.color.g, color.color.b));
    }
}