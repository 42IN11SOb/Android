package com.avans.in11sob.pep_android.Activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Api.Models.News;
import com.avans.in11sob.pep_android.R;
import com.squareup.okhttp.internal.Util;

public class NewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        News news = (News) getIntent().getSerializableExtra("news");

        TextView title  = (TextView) findViewById(R.id.title);
        WebView webView = (WebView) findViewById(R.id.content);

        title.setText(news.title);
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadData(news.content, "text/html; charset=UTF-8", null);

    }
}
