package com.iwan_b.chummersr5.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.adapter.TabsPagerAdapter;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.fragmentUtil.FactoryMethodInterface;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import java.util.ArrayList;

public class SwipeFragmentHolder extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_swipe_fragmentholder);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        ArrayList<String> listOfTabs = new ArrayList<>();

        ArrayList<FactoryMethodInterface> tabsToCall = new ArrayList<>();
        listOfTabs.add("Attributes");
        tabsToCall.add(new com.iwan_b.chummersr5.fragments.MainStats.MainContainer());

        if (ShadowrunCharacter.getCharacter().getUserType().ordinal() >= ChummerConstants.userType.mystic_adept.ordinal()) {
            listOfTabs.add("Magic");
            tabsToCall.add(new com.iwan_b.chummersr5.fragments.Magic.MainContainer());
        }

        if (ShadowrunCharacter.getCharacter().getUserType() == ChummerConstants.userType.adept ||
                ShadowrunCharacter.getCharacter().getUserType() == ChummerConstants.userType.mystic_adept) {
            listOfTabs.add("Adept");
            tabsToCall.add(new com.iwan_b.chummersr5.fragments.Adept.MainContainer());
        }

        listOfTabs.add("Active Skills");
        tabsToCall.add(new com.iwan_b.chummersr5.fragments.ActiveSkill.MainContainer());

        listOfTabs.add("Knowledge Skills");
        tabsToCall.add(new com.iwan_b.chummersr5.fragments.KnowledgeSkill.MainContainer());

        // TODO this might not be the best thing to hardcode. Although I suppose the application should be light in usage anyways.
        pager.setOffscreenPageLimit(listOfTabs.size());

        final TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), listOfTabs.toArray(new String[listOfTabs.size()]), tabsToCall);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                UpdateInterface fragment = (UpdateInterface) adapter.instantiateItem(pager, position);
                if (fragment != null) {
                    fragment.update();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
    }


}