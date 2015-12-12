package com.iwan_b.chummersr5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iwan_b.chummersr5.fragments.fragmentUtil.FactoryMethodInterface;

import java.util.ArrayList;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    // Tab titles
    private static String[] tabs;
    private static ArrayList<FactoryMethodInterface> tabsToDisplay;

    public TabsPagerAdapter(FragmentManager fm, String[] tabs, ArrayList<FactoryMethodInterface> tabsToDisplay) {
        super(fm);
        TabsPagerAdapter.tabs = tabs;
        TabsPagerAdapter.tabsToDisplay = tabsToDisplay;
    }

    @Override
    public Fragment getItem(int index) {
        return tabsToDisplay.get(index).newInstance();
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return tabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
