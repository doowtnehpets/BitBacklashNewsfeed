package com.example.android.bitbacklashnewsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsArticleRecyclerAdapter extends RecyclerView.Adapter<NewsArticleRecyclerAdapter.NewsArticleViewHolder> {

    // Context to inflate
    private Context contextToInflate;

    // List of articles
    private List<NewsArticle> newsArticles;

    // Constructor
    public NewsArticleRecyclerAdapter(Context contextToInflate, List<NewsArticle> newsArticles) {
        this.contextToInflate = contextToInflate;
        this.newsArticles = newsArticles;
    }

    @Override
    public NewsArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate and return the view holder
        LayoutInflater inflater = LayoutInflater.from(contextToInflate);
        View view = inflater.inflate(R.layout.news_article, null);
        return new NewsArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsArticleViewHolder holder, int position) {
        // Get the NewsArticle at the current position
        NewsArticle currentArticle = newsArticles.get(position);

        // Bind the data to the viewholder views
        holder.textViewTitle.setText(currentArticle.getWebTitle());
        holder.textViewDate.setText(currentArticle.getWebPublicationDate());
        holder.textViewSection.setText(currentArticle.getSectionId());
        holder.textViewAuthor.setText(currentArticle.getByLine());
        holder.webUrl = Uri.parse(currentArticle.webUrl);
    }

    // Return the total number of articles
    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    // View holder for a particular article
    class NewsArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViewThumbnail;
        TextView textViewTitle, textViewDate, textViewSection, textViewAuthor;
        Uri webUrl;

        public NewsArticleViewHolder(View itemView) {
            super(itemView);

            // Load items from the CardLayout
            imageViewThumbnail = itemView.findViewById(R.id.article_thumbnail);
            textViewTitle = itemView.findViewById(R.id.article_title);
            textViewDate = itemView.findViewById(R.id.article_date);
            textViewSection = itemView.findViewById(R.id.article_section);
            textViewAuthor = itemView.findViewById(R.id.article_author);

            // Set the onClick listener
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Open URL when item is clicked
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, webUrl);
            contextToInflate.startActivity(websiteIntent);
        }
    }
}
