package com.avans.in11sob.pep_android.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import com.avans.in11sob.pep_android.Model.ProfileColor;
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
    List<ProfileColor> colors = new ArrayList<ProfileColor>();

    public ColorMasterFragment() {

    }

    public interface OnItemSelectedListener {
        public void onListItemSelected(ProfileColor color);
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
                ProfileColor c = adapter.getItem(position);
                listener.onListItemSelected(c);
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
        colors = ProfileColor.getColors();
//        String url = getResources().getString(R.string.BASE_URL) + getResources().getString(R.string.GETCOLORS);
        //String url = "";
        //if(isConnected()){
        //    new RequestColorTask().execute(url);
        //} else {
        //    // show alert user is not connected to internet
//
        //    new AlertDialog.Builder(getActivity())
        //            .setTitle("No connection")
        //            .setMessage("An internet connection is required to load content")
        //            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        //                public void onClick(DialogInterface dialog, int which) {
        //                    // do nothing
        //                }
        //            })
        //            .setIcon(android.R.drawable.ic_dialog_alert)
        //            .show();
        //}

    }

    private class RequestColorTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {
            // params comes from the execute() call: params[0] is the url.
            try {
                Log.d("LIST_FRAGMENT", urls[0]);
                return requestColor(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String results) {
            Log.d("LIST_FRAGMENT", results);

            JSONArray result= null;
            try {
                result = new JSONArray(results);
//                Log.d("INTRO", results);
                for(int i=0; i<result.length(); i++){
                    JSONObject color = result.getJSONObject(i);

                    ProfileColor color1 = new ProfileColor(color.getString("_id"),color.getString("name"), color.getInt("r"), color.getInt("g"), color.getInt("b"));
                    colors.add(color1);
                }

                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private String requestColor(String color_url) throws IOException{
        InputStream is = null;

        try {
            URL url = new URL(color_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            is = conn.getInputStream();


            BufferedReader bR = new BufferedReader(  new InputStreamReader(is));
            String line = "";
            StringBuilder responseStrBuilder = new StringBuilder();
            while((line =  bR.readLine()) != null){
                responseStrBuilder.append(line);
            }
            is.close();
            // Convert the InputStream into a string
            return responseStrBuilder.toString();

        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
