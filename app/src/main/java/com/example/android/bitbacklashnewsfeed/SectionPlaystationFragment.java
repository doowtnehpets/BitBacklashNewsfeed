package com.example.android.bitbacklashnewsfeed;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

public class SectionPlaystationFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    // Guardian API URL for playstation articles
    private static String PLAYSTATION_URL = "https://content.guardianapis.com/search?show-fields=thumbnail%2Cbyline&section=games&q=playstation&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850";

    // The Recycler view
    RecyclerView recyclerView;

    // NewsArticleRecyclerAdapter
    private NewsArticleRecyclerAdapter newsArticleRecyclerAdapter;

    // The Progress Bar
    ProgressBar progressBar;

    // Required empty constructor
    public SectionPlaystationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view with the news_recycler
        View rootView = inflater.inflate(R.layout.news_recycler, container, false);

        // Grab the news_recyclerview from the layout and set fixed size and set the layout manager
        recyclerView = rootView.findViewById(R.id.news_recyclerview);

        progressBar = rootView.findViewById(R.id.news_recycler_progressbar);

        getLoaderManager().initLoader(1, null, this);

        return rootView;
    }

    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        return new NewsArticleLoader(getContext(), PLAYSTATION_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {

        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        // Set the recycler adapter
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsArticleRecyclerAdapter = new NewsArticleRecyclerAdapter(getContext(), newsArticles);
        recyclerView.setAdapter(newsArticleRecyclerAdapter);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsArticle>> loader) {

    }
}
