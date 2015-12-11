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
import com.iwan_b.chummersr5.fragments.fragmentUtil.FactoryMethod;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;

import java.util.ArrayList;

public class MainContainer extends Fragment implements UpdateInterface, FactoryMethod {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();

    public static void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
            karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
        }
    }

    @Override
    public Fragment newInstance() {
        MainContainer main = new MainContainer();
        return main;
    }

    @Override
    public void update() {
        updateKarma();
        for(UpdateInterface child : childrenToUpdate){
            child.update();
        }
    }

    @Override
    public void updateParent() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mainstats_main, container, false);
        updateKarma();

        // TODO figure this out later
        // However, if we're being restored from a previous state,
        // then we don't need to do anything and should return or else
        // we could end up with overlapping fragments.
//        if (savedInstanceState != null) {
//            Log.i(ChummerConstants.TAG, "Saved instance was called!");
//        }


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        AttributeFragment attrFragment = AttributeFragment.newInstance(this);
        QualitiesFragment positiveQuals = QualitiesFragment.newInstance(this);
        QualitiesFragment negativeQuals = QualitiesFragment.newInstance(this);

        childrenToUpdate.add(attrFragment);
        childrenToUpdate.add(positiveQuals);
        childrenToUpdate.add(negativeQuals);

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