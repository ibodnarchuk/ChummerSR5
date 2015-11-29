package com.iwan_b.chummersr5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	// Tab titles
	public static String[] tabs = { "Attributes", "Active Skills", "Knowledge Skills" };

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return new com.iwan_b.chummersr5.fragments.MainStats.MainContainer();
		case 1:
			return new com.iwan_b.chummersr5.fragments.ActiveSkill.MainContainer();
		case 2:
			return new com.iwan_b.chummersr5.fragments.KnowledgeSkill.MainContainer();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return tabs.length;
	}

}
