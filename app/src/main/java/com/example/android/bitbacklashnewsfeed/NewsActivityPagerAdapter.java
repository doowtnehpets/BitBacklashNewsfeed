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
                bundle.putString("url", "https://content.guardianapis.com/search?show-fields=thumbnail%2Cbyline&section=games&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            case 1:
                // Sports
                bundle.putString("url", "https://content.guardianapis.com/search?section=sport&show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            case 2:
                // Culture
                bundle.putString("url", "https://content.guardianapis.com/search?section=culture&show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            case 3:
                // Politics
                bundle.putString("url", "https://content.guardianapis.com/search?section=politics&show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            case 4:
                // Books
                bundle.putString("url", "https://content.guardianapis.com/search?section=books&show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            case 5:
                // Technology
                bundle.putString("url", "https://content.guardianapis.com/search?section=technology&show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
                break;
            default:
                // News
                bundle.putString("url", "https://content.guardianapis.com/search?show-fields=thumbnail%2Cbyline&api-key=85a473fc-b895-4de9-a6c5-16ae66dab850");
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
