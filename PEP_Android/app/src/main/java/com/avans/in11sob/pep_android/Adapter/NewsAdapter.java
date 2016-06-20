package com.avans.in11sob.pep_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avans.in11sob.pep_android.Activity.NewsActivity;
import com.avans.in11sob.pep_android.Api.Models.News;
import com.avans.in11sob.pep_android.Api.Models.NewsCollection;
import com.avans.in11sob.pep_android.R;
import com.avans.in11sob.pep_android.Utilities.URLImageParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 20-6-2016.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;

    List<News> newsCollection;

    public NewsAdapter(Context context, NewsCollection newsCollection){
        this.context = context;

        List<News> tempNews = new ArrayList<>();
        for (News news : newsCollection.data){
            if (news.published){
                tempNews.add(news);
            }
        }

        this.newsCollection = tempNews;
    }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.newsitem, viewGroup, false);
        NewsViewHolder nvh = new NewsViewHolder(v);
        return  nvh;
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int i){
        holder.title.setText(newsCollection.get(i).title);

        final Context self = context;

        holder.cv.setTag(i);
        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int counter = (int) v.getTag();

                Intent intent = new Intent(self, NewsActivity.class);
                intent.putExtra("news", newsCollection.get(counter));
                self.startActivity(intent);
            }
        });

        URLImageParser p = new URLImageParser(holder.content, context);
        holder.content.setText(Html.fromHtml(newsCollection.get(i).content, p, null));
        holder.content.setMovementMethod(LinkMovementMethod.getInstance());
        holder.content.setMaxLines(5);
    }

    @Override
    public int getItemCount() {
        return newsCollection.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView title;
        TextView content;

        NewsViewHolder(View itemView){
            super(itemView);
            cv          = (CardView) itemView.findViewById(R.id.stijl);
            title       = (TextView) itemView.findViewById(R.id.title);
            content     = (TextView) itemView.findViewById(R.id.content);
        }
    }
}
