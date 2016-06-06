package com.avans.in11sob.pep_android.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avans.in11sob.pep_android.Adapter.ColorListAdapter;
import com.avans.in11sob.pep_android.Api.Models.Profile;
import com.avans.in11sob.pep_android.Api.Models.Color;
import com.avans.in11sob.pep_android.Model.User;
import com.avans.in11sob.pep_android.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ColorMasterFragment extends Fragment {

    private OnItemSelectedListener listener;
    ColorListAdapter adapter;
    ListView listview;
    List<Color> colors = new ArrayList<>();

    public ColorMasterFragment() {

    }

    public interface OnItemSelectedListener {
        public void onListItemSelected(Color color);
    }

    public static ColorMasterFragment newInstance() {
        ColorMasterFragment fragment = new ColorMasterFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        loadData();
        View view = inflater.inflate(R.layout.fragment_color_master, container, false);
        listview = (ListView) view.findViewById(R.id.colorMasterView);

        adapter = new ColorListAdapter(this.getActivity(), colors);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Color c = adapter.getItem(position);
                listener.onListItemSelected(c);
                Bundle data = new Bundle();
                getActivity().setTitle(c.color.name);
                data.putInt("colorPosition", position);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);

        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnItemSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public void loadData(){
//        colors = ProfileColor.getColors(); // old way to get static colors
        Profile profile = Profile.getInstance();
        Log.e("Colors", "colors lengte is: " + profile.data.passport.season.colors.toArray().length);
        colors = profile.data.passport.season.colors;
    }
}
