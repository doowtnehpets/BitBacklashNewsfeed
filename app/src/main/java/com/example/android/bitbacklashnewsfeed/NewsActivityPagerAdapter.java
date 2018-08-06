package com.example.android.bitbacklashnewsfeed;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsActivityPagerAdapter extends FragmentPagerAdapter {
    // Context
    Context context;

    public NewsActivityPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    // Return the title for the current section
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return context.getString(R.string.title_games);
            case 1:
                return context.getString(R.string.title_sports);
            case 2:
                return context.getString(R.string.title_culture);
            case 3:
                return context.getString(R.string.title_politics);
            case 4:
                return context.getString(R.string.title_books);
            case 5:
                return context.getString(R.string.title_technology);
            default:
                return context.getString(R.string.title_news);
        }
    }

    // Return the correct section for the current position
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                // Games
                bundle.putString("section", GuardianUrlBuilder.SECTION_GAMES);
                break;
            case 1:
                // Sports
                bundle.putString("section", GuardianUrlBuilder.SECTION_SPORTS);
                break;
            case 2:
                // Culture
                bundle.putString("section", GuardianUrlBuilder.SECTION_CULTURE);
                break;
            case 3:
                // Politics
                bundle.putString("section", GuardianUrlBuilder.SECTION_POLITICS);
                break;
            case 4:
                // Books
                bundle.putString("section", GuardianUrlBuilder.SECTION_BOOKS);
                break;
            case 5:
                // Technology
                bundle.putString("section", GuardianUrlBuilder.SECTION_TECHNOLOGY);
                break;
            default:
                // News
                break;
        }

        // Attach the bundle to the fragment and return that
        NewsSectionFragment newsSectionFragment = new NewsSectionFragment();
        newsSectionFragment.setArguments(bundle);
        return newsSectionFragment;
    }

    // Return the number of tabs
    @Override
    public int getCount() {
        return 6;
    }
}
