package com.example.android.bitbacklashnewsfeed;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SectionPlaystationFragment extends Fragment {

    // List of Articles
    List<NewsArticle> newsArticleList;

    // The Recycler view
    RecyclerView recyclerView;

    // Required empty constructor
    public SectionPlaystationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the view with the news_recycler
        View rootView = inflater.inflate(R.layout.news_recycler, container, false);

        // Grab the news_recyclerview from the layout and set fixed size and set the layout manager
        recyclerView = rootView.findViewById(R.id.news_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the newsArticleList
        newsArticleList = new ArrayList<>();

        newsArticleList.add(new NewsArticle("Games",
                "2018-07-15",
                "Google is blah blah blah",
                "https://google.com",
                "By Stephen Wood",
                "Thumbnail"));

        newsArticleList.add(new NewsArticle("Games",
                "2018-07-15",
                "Yahoo is blah blah blah",
                "https://yahoo.com",
                "By Stephen Wood",
                "Thumbnail"));

        newsArticleList.add(new NewsArticle("Games",
                "2018-07-15",
                "Microsoft and stuff................",
                "https://microsoft.com",
                "By Stephen Wood",
                "Thumbnail"));

        newsArticleList.add(new NewsArticle("Games",
                "2018-07-15",
                "Some other stuff and stuff, dude!",
                "https://live.com",
                "By Stephen Wood",
                "Thumbnail"));

        // Create the recycler view adapter
        NewsArticleRecyclerAdapter adapter = new NewsArticleRecyclerAdapter(getActivity(), newsArticleList);

        // Set the adapter on the recycler view
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}
