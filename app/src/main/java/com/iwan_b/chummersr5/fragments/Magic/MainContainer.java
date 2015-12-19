package com.iwan_b.chummersr5.fragments.Magic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.FragmentUtil.FactoryMethodInterface;
import com.iwan_b.chummersr5.fragments.FragmentUtil.UpdateInterface;

import java.util.ArrayList;


public class MainContainer extends Fragment implements UpdateInterface, FactoryMethodInterface {
    private static View rootView;

    private ArrayList<UpdateInterface> childrenToUpdate = new ArrayList<>();


    @Override
    public Fragment newInstance() {
        MainContainer main = new MainContainer();
        return main;
    }

    @Override
    public void update() {
        updateCounters();
        for (UpdateInterface child : childrenToUpdate) {
            child.update();
        }
    }

    @Override
    public void updateParent() {
    }

    public void updateKarma() {
        if (rootView != null) {
            TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karma_counter);
            karmaCounterTxtView.setText(String.valueOf(ShadowrunCharacter.getKarma()));
        }
    }

    public void updateFreeSpellCounter() {
        if (rootView != null) {
            TextView freeSpellCounterTxtView = (TextView) rootView.findViewById(R.id.fragment_magic_main_free_spells_counter);
            freeSpellCounterTxtView.setText(String.valueOf(FreeCounters.getCounters().getFreeSpells()));
        }
    }

    public void updateCounters() {
        updateKarma();
        updateFreeSpellCounter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_magic_main, container, false);

        SpellList spells = new SpellList(rootView);
        RitualList rituals = new RitualList(rootView);
        AlchemyList alchmey = new AlchemyList(rootView);

        return rootView;
    }


}