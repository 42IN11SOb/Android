package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.avans.in11sob.pep_android.Adapter.NewsAdapter;
import com.avans.in11sob.pep_android.Api.Models.News;
import com.avans.in11sob.pep_android.Api.Models.NewsCollection;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.ApiRequests;
import com.avans.in11sob.pep_android.Utilities.App;
import com.avans.in11sob.pep_android.Utilities.GsonGetRequest;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);

        final Context self = this;

        final GsonGetRequest<NewsCollection> gsonGetRequest =
                ApiRequests.news(
                        new Response.Listener<NewsCollection>() {
                            @Override
                            public void onResponse(NewsCollection response) {
                                NewsAdapter adapter = new NewsAdapter(self, response);
                                rv.setAdapter(adapter);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }
                );

        App.addRequest(gsonGetRequest, "profile");

    }
}
