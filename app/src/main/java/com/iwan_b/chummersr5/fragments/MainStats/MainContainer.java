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
			TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
			karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.newcharacterinput, container, false);

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

		fragmentTransaction.add(R.id.attributeFragment, attrFragment);
		fragmentTransaction.add(R.id.positiveQualitiesFragment, positiveQuals);
		fragmentTransaction.add(R.id.NegativeQualitiesFragment, negativeQuals);
		fragmentTransaction.commit();

		return rootView;
	}

}