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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

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

    @NonNull
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
        holder.textViewDate.setText(formatDate(currentArticle.getWebPublicationDate()));
        holder.textViewAuthor.setText(currentArticle.getByLine());

        if (currentArticle.getThumbnail() != null)
            holder.imageViewThumbnail.setImageBitmap(currentArticle.getThumbnail());

        holder.webUrl = Uri.parse(currentArticle.getWebUrl());
    }

    // Return the total number of articles
    @Override
    public int getItemCount() {
        return newsArticles.size();
    }

    /**
     * Format the date returned from the JSON parsing to something more readable
     *
     * @param date string from JSON parsing
     * @return properly formatted date
     */
    private String formatDate(String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'", Locale.getDefault());

        // Set the time zone to where the articles are published
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

        try {
            // Parse the date and store as a Date object to calculate time since article was posted
            Date articleDate = simpleDateFormat.parse(date);

            // Grab an instance of the calendar to get the time
            Calendar calendar = Calendar.getInstance();

            // Calculate time since article was posted
            long currentTime = calendar.getTimeInMillis();
            long articleTime = currentTime - articleDate.getTime();

            // Only seconds ago
            if (articleTime < 60000)
                return (TimeUnit.MILLISECONDS.toSeconds(articleTime) + " " + contextToInflate.getString(R.string.time_seconds_ago));

                // 1 minute ago
            else if (articleTime < TimeUnit.MINUTES.toMillis(2))
                return (TimeUnit.MILLISECONDS.toMinutes(articleTime) + " " + contextToInflate.getString(R.string.time_minute_ago));

                // 1-59 minutes
            else if (articleTime < TimeUnit.HOURS.toMillis(1))
                return (TimeUnit.MILLISECONDS.toMinutes(articleTime) + " " + contextToInflate.getString(R.string.time_minutes_ago));

                // 1 hour
            else if (articleTime < TimeUnit.HOURS.toMillis(2))
                return (TimeUnit.MILLISECONDS.toHours(articleTime) + " " + contextToInflate.getString(R.string.time_hour_ago));

                // 2-23 hours
            else if (articleTime < TimeUnit.DAYS.toMillis(1))
                return (TimeUnit.MILLISECONDS.toHours(articleTime) + " " + contextToInflate.getString(R.string.time_hours_ago));

                // 1 day
            else if (articleTime < TimeUnit.DAYS.toMillis(2))
                return (TimeUnit.MILLISECONDS.toDays(articleTime) + " " + contextToInflate.getString(R.string.time_day_ago));

                // full date
            else {
                simpleDateFormat.applyPattern("MM/dd/yyyy");
                return simpleDateFormat.format(articleDate);
            }

        } catch (ParseException e) {
            return null;
        }
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
