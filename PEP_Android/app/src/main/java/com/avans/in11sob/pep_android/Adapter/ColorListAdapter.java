package com.avans.in11sob.pep_android.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Model.ProfileColor;
import com.avans.in11sob.pep_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ColorListAdapter extends BaseAdapter {

    List<ProfileColor> results;
    Context context;
    private static LayoutInflater inflater=null;

    public ColorListAdapter(Activity activity, List<ProfileColor> list) {
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
    public ProfileColor getItem(int position) {
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
        color.setText(results.get(position).getName());
        color.setBackgroundColor(Color.rgb(results.get(position).getRed(), results.get(position).getGreen(), results.get(position).getBlue()));

        return row;
    }
}
