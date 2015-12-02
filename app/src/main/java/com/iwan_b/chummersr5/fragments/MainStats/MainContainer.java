package com.iwan_b.chummersr5.fragments.MainStats;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;

public class MainContainer extends Fragment {
	private static View rootView;

	public static void updateKarma() {
		if (rootView != null) {
			TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
			karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
		}
	}

	public static Fragment newInstance(int index) {
		MainContainer main = new MainContainer();
		Bundle args = new Bundle();
		args.putInt("index", index);
		main.setArguments(args);
		return main;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_mainstats_main, container, false);

		updateKarma();

		// TODO figure this out later
		// // However, if we're being restored from a previous state,
		// // then we don't need to do anything and should return or else
		// // we could end up with overlapping fragments.
		// if (savedInstanceState != null) {
		// return;
		// }

		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		AttributeFragment attrFragment = new AttributeFragment();
		QualitiesFragment positiveQuals = new QualitiesFragment();
		QualitiesFragment negativeQuals = new QualitiesFragment();

		Bundle dataBundle = new Bundle();

		dataBundle.putString("FileLocation", "qualities/positive.xml");
		dataBundle.putBoolean("isPositive", true);
		positiveQuals.setArguments(dataBundle);

		dataBundle = new Bundle();
		dataBundle.putString("FileLocation", "qualities/negative.xml");
		dataBundle.putBoolean("isPositive", false);
		negativeQuals.setArguments(dataBundle);

		fragmentTransaction.add(R.id.fragment_mainstats_main_attribute_Fragment, attrFragment);
		fragmentTransaction.add(R.id.fragment_mainstats_main_positiveQualities_Fragment, positiveQuals);
		fragmentTransaction.add(R.id.fragment_mainstats_main_NegativeQualities_Fragment, negativeQuals);
		fragmentTransaction.commit();

		return rootView;
	}

}