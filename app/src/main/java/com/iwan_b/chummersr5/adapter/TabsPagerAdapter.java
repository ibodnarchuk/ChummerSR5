package com.iwan_b.chummersr5.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	// Tab titles
	public static final String[] tabs = { "Attributes", "Active Skills", "Knowledge Skills" };

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			return com.iwan_b.chummersr5.fragments.MainStats.MainContainer.newInstance(index);
		case 1:
			return com.iwan_b.chummersr5.fragments.ActiveSkill.MainContainer.newInstance(index);
		case 2:
			return com.iwan_b.chummersr5.fragments.KnowledgeSkill.MainContainer.newInstance(index);
		}

		return null;
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
