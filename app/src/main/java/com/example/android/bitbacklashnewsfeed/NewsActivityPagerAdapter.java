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
                return "Games";
            case 1:
                return "Sports";
            case 2:
                return "Culture";
            case 3:
                return "Politics";
            case 4:
                return "Books";
            case 5:
                return "Technology";
            default:
                return "News";
        }
    }

    // Return the correct section for the current position
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();

        switch (position) {
            case 0:
                // Games
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_GAMES));
                break;
            case 1:
                // Sports
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_SPORTS));
                break;
            case 2:
                // Culture
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_CULTURE));
                break;
            case 3:
                // Politics
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_POLITICS));
                break;
            case 4:
                // Books
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_BOOKS));
                break;
            case 5:
                // Technology
                bundle.putString("url", GuardianUrlBuilder.buildUrl(GuardianUrlBuilder.SECTION_TECHNOLOGY));
                break;
            default:
                // News
                bundle.putString("url", GuardianUrlBuilder.buildUrl(null));
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
