package com.example.android.bitbacklashnewsfeed;

import android.content.Context;
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
                return "Playstation";
            default:
                return "Playstation";
        }
    }

    // Return the correct section for the current position
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SectionPlaystationFragment();
            default:
                return new SectionPlaystationFragment();
        }
    }

    // Return the number of tabs
    @Override
    public int getCount() {
        return 1;
    }
}
