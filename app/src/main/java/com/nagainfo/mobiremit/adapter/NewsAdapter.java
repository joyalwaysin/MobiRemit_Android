package com.nagainfo.mobiremit.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nagainfo.mobiremit.R;
import com.nagainfo.mobiremit.model.News.NewsResultItem;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.BranchViewHolder> {

    List<NewsResultItem> news;
    Context context;


    public static class BranchViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ratesLayout;
        TextView news_title;
        TextView news_date;
        TextView news_content;


        public BranchViewHolder(View v) {
            super(v);
            ratesLayout = (LinearLayout) v.findViewById(R.id.news_layout);

            news_title = (TextView) v.findViewById(R.id.news_title);
            news_date = (TextView) v.findViewById(R.id.news_date);
            news_content = (TextView) v.findViewById(R.id.news_content);
        }
    }

    public NewsAdapter(Context context, List<NewsResultItem> news) {
        this.context = context;
        this.news = news;
    }

    @Override
    public NewsAdapter.BranchViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.news_list_item, parent, false);
        return new BranchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(BranchViewHolder holder, final int position) {
        holder.news_title.setText(news.get(position).getTitle());
        holder.news_date.setText(news.get(position).getCreatedDate());
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N) {
            holder.news_content.setText(Html.fromHtml(news.get(position).getMatter(), Html.FROM_HTML_MODE_LEGACY));
        }
        else {
            holder.news_content.setText(Html.fromHtml(news.get(position).getMatter()));
        }
//        holder.news_content.setText(news.get(position).getMatter());
    }

    @Override
    public int getItemCount() {
        return news.size();
    }


}