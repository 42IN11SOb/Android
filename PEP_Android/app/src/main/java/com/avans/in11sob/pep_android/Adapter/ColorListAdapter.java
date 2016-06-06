package com.avans.in11sob.pep_android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.Api.Models.Color;
import com.avans.in11sob.pep_android.R;

import java.util.List;

public class ColorListAdapter extends BaseAdapter {

    List<Color> results;
    Context context;
    private static LayoutInflater inflater=null;

    public ColorListAdapter(Activity activity, List<Color> list) {
        results=list;
        context=activity;
        inflater = (LayoutInflater)context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Color getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;

        if(row==null){
            row=inflater.inflate(R.layout.element_color_master, parent, false);
        }

        TextView color = (TextView) row.findViewById(R.id.colorButton);
        color.setText(results.get(position).color.name);
        color.setBackgroundColor(android.graphics.Color.rgb(results.get(position).color.r, results.get(position).color.g, results.get(position).color.b));

        return row;
    }
}
