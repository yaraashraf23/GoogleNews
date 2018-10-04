package com.example.youssef.googlenews.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

//import com.example.youssef.googlenews.News;
import com.example.youssef.googlenews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyHolder> {

    private Context context;
    private List<NewsModel> list;
    public static ArrayList<String> arr;

    public NewsAdapter(Context context, List<NewsModel> list) {

        this.context = context;
        this.list = list;
    }
    @Override
    public NewsAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        NewsAdapter.MyHolder myHolder = new NewsAdapter.MyHolder(LayoutInflater.from(context).inflate(
                R.layout.custome_listview, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(NewsAdapter.MyHolder holder, final int position) {

        holder.txtName.setText(list.get(position).getArticles().get(position).getSource().getName());
        holder.txttime.setText(list.get(position).getArticles().get(position).getPublishedAt());

        try {
            Picasso.get().load(list.get(position).getArticles().get(position).getUrlToImage()).into(holder.imageViewNews);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        holder.toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                if (b) {

                    arr = new ArrayList<>();
                    arr.add(list.get(position).getArticles().get(position).getUrl());
                    for (int i = 0; i < arr.size(); i++) {
                        Toast.makeText(context,"item saved",Toast.LENGTH_LONG).show();
                    }
                } else {

                    arr.remove(list.get(position).getArticles().get(position).getUrl());
                    for (int i = 0; i < arr.size(); i++) {
                        Toast.makeText(context,"item notsaved",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getArticles().get(position).getUrl())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView txtName, txttime;
        ImageView imageViewNews;
        ToggleButton toggle_btn;

        public MyHolder(View itemView) {
            super(itemView);

            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txttime = (TextView) itemView.findViewById(R.id.txttime);
            imageViewNews = (ImageView) itemView.findViewById(R.id.imageViewNews);
            toggle_btn = (ToggleButton) itemView.findViewById(R.id.toggle_btn);
        }
    }
}
