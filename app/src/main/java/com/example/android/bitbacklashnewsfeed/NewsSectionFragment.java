package com.example.android.bitbacklashnewsfeed;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

public class NewsSectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<NewsArticle>> {

    // Swipe refresh layout
    SwipeRefreshLayout swipeRefreshLayout;
    // The Recycler view
    RecyclerView recyclerView;
    // Empty text view
    TextView emptyTextView;
    // No of articles
    int noOfArticles;
    // Order by
    String orderBy;
    // Guardian API URL for articles, set it to a default if no section is chosen
    private String guardian_url;

    // Required empty constructor
    public NewsSectionFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view with the news_recycler
        View rootView = inflater.inflate(R.layout.news_recycler, container, false);

        // Grab the swipe refresh layout
        swipeRefreshLayout = rootView.findViewById(R.id.news_recycler_swiperefresh);
        swipeRefreshLayout.setRefreshing(true);

        // Grab the news_recyclerview from the layout
        recyclerView = rootView.findViewById(R.id.news_recyclerview);

        // Grab the empty text view to hide or show those if data loads
        emptyTextView = rootView.findViewById(R.id.news_recycler_emptyview);

        // Set up swipe refresh
        setupSwipeRefresh();

        // If connected to the internet start loading the articles else show error message
        if (connectedToInternet()) getLoaderManager().initLoader(1, null, this);
        else {
            swipeRefreshLayout.setRefreshing(false);
            emptyTextView.setText(R.string.no_internet);
            emptyTextView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<NewsArticle>> onCreateLoader(int i, Bundle bundle) {

        // Load the shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        // Get no of articles setting
        noOfArticles = Integer.parseInt(sharedPreferences.getString(getString(R.string.settings_no_of_articles_key),
                getString(R.string.settings_no_of_articles_default)));

        // Get orderBy setting
        orderBy = sharedPreferences.getString(getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));

        // Check if a string was supplied for a different URL and use that instead if so
        String suppliedSection = getArguments().getString("section");
        if (suppliedSection != null && !suppliedSection.isEmpty())
            guardian_url = GuardianUrlBuilder.buildUrl(suppliedSection, noOfArticles);
        else guardian_url = GuardianUrlBuilder.buildUrl(null, noOfArticles);

        return new NewsArticleLoader(getContext(), guardian_url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<NewsArticle>> loader, List<NewsArticle> newsArticles) {

        // Stop swipe refreshing animation
        swipeRefreshLayout.setRefreshing(false);

        // Hide the error message if it came up
        emptyTextView.setVisibility(View.GONE);

        // Set the recycler adapter if newsArticles isn't null, else show show 1 of 2 error messages
        if (newsArticles != null && !newsArticles.isEmpty()) {
            // If order by is set to oldest, reverse the order of the returned articles
            if (orderBy.equals(getString(R.string.settings_order_by_oldest_value)))
                Collections.reverse(newsArticles);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            NewsArticleRecyclerAdapter newsArticleRecyclerAdapter = new NewsArticleRecyclerAdapter(getContext(), newsArticles);
            recyclerView.setAdapter(newsArticleRecyclerAdapter);
        } else if (!connectedToInternet()) {
            emptyTextView.setText(R.string.no_internet);
            emptyTextView.setVisibility(View.VISIBLE);

        } else {
            emptyTextView.setText(R.string.no_articles_found);
            emptyTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<NewsArticle>> loader) {
    }

    /**
     * Checks if device is connected to the internet
     *
     * @return true if connected or connecting, false if no connection
     */
    private boolean connectedToInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) return false;
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }


    /**
     * restart the loader if internet is connected
     */
    private void restartLoader() {
        if (connectedToInternet()) {
            swipeRefreshLayout.setRefreshing(true);
            getLoaderManager().restartLoader(1, null, this);
        }

        else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sets up the swipe to refresh
     */
    private void setupSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!getLoaderManager().hasRunningLoaders()) restartLoader();
            }
        });
    }
}
