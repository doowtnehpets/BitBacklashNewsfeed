package com.example.android.bitbacklashnewsfeed;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.TextView;

import java.util.List;

public class NewsSectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    // The Recycler view
    RecyclerView recyclerView;
    // The Progress Bar
    ProgressBar progressBar;
    // Empty text view
    TextView emptyTextView;
    // Guardian API URL for articles, set it to a default if no section is chosen
    private String guardian_url = "https://content.guardianapis.com/search?show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850";

    // Required empty constructor
    public NewsSectionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Check if a string was supplied for a different URL and use that instead if so
        String suppliedUrl = getArguments().getString("url");
        if (suppliedUrl != null && !suppliedUrl.isEmpty()) guardian_url = suppliedUrl;

        // Inflate the view with the news_recycler
        View rootView = inflater.inflate(R.layout.news_recycler, container, false);

        // Grab the news_recyclerview from the layout and set fixed size and set the layout manager
        recyclerView = rootView.findViewById(R.id.news_recyclerview);

        // Grab the progress bar and empty text view to hide or show those if data loads
        progressBar = rootView.findViewById(R.id.news_recycler_progressbar);
        emptyTextView = rootView.findViewById(R.id.news_recycler_emptyview);

        // If connected to the internet start loading the articles else show error message
        if(connectedToInternet()) getLoaderManager().initLoader(1, null, this);
        else{
            progressBar.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet);
        }

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {
        return new NewsArticleLoader(getContext(), guardian_url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {

        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        // Set the recycler adapter if newsArticles isn't null, else show no articles found message
        if(newsArticles != null && !newsArticles.isEmpty()){
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            NewsArticleRecyclerAdapter newsArticleRecyclerAdapter = new NewsArticleRecyclerAdapter(getContext(), newsArticles);
            recyclerView.setAdapter(newsArticleRecyclerAdapter);
        } else {
            emptyTextView.setText(R.string.no_articles_found);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsArticle>> loader) {

    }

    /**
     * Checks if device is connected to the internet
     * @return true if connected or connecting, false if no connection
     */
    private boolean connectedToInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager == null) return false;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }
}
