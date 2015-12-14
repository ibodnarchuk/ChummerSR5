package com.iwan_b.chummersr5.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.adapter.TabsPagerAdapter;
import com.iwan_b.chummersr5.data.Attribute;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.PriorityCounters;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.fragmentUtil.FactoryMethodInterface;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerMethods;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SwipeFragmentHolder extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildChar();

        setContentView(R.layout.activity_swipe_fragmentholder);

        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        final ViewPager pager = (ViewPager) findViewById(R.id.pager);

        ArrayList<String> listOfTabs = new ArrayList<>();

        ArrayList<FactoryMethodInterface> tabsToCall = new ArrayList<>();
        listOfTabs.add("Attributes");
        tabsToCall.add(new com.iwan_b.chummersr5.fragments.MainStats.MainContainer());

        if(ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic() > 0) {
            listOfTabs.add("Magic");
            tabsToCall.add(new com.iwan_b.chummersr5.fragments.Magic.MainContainer());
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


    private void buildChar() {
        ShadowrunCharacter newCharacter = ShadowrunCharacter.getCharacter();

        String metastring = PriorityCounters.getCounters().getMeta().getMetaTypeName();

        // TODO make a metatype class
        final Attribute attrs = readXML("metatypes/" + metastring + ".xml");

        attrs.setBaseMagic(0);
        attrs.setBaseRes(0);
        attrs.setMaxMagic(6);
        attrs.setMaxRes(6);

        // Test if they are mundane or not
        if (PriorityCounters.getCounters().getMagic().getStats() != 0) {
            // Find out if they are a mage or technomancer
            if (PriorityCounters.getCounters().getMagic().getMagicType().equalsIgnoreCase("magic")) {
                attrs.setBaseMagic((int) PriorityCounters.getCounters().getMagic().getStats());
                attrs.setMagic((int) PriorityCounters.getCounters().getMagic().getStats());
            } else {
                attrs.setBaseRes((int) PriorityCounters.getCounters().getMagic().getStats());
                attrs.setRes((int) PriorityCounters.getCounters().getMagic().getStats());
            }
        }

        newCharacter.setAttributes(attrs);
//        Log.i(ChummerConstants.TAG, newCharacter.toString());
    }

    private Attribute parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        Attribute attr = null;
        Modifier m = null;
        boolean mod = false;

        int eventType = parser.getEventType();
        // TODO change all the hardcoded xml properties used further down
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    name = parser.getName();
                    // Log.i(ChummerConstants.TAG, "START_DOCUMENT " + name);
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("metatype")) {
                        attr = new Attribute();

                        int attCount = parser.getAttributeCount();
                        // TODO example of how to get the string
                        String p = getString(R.string.type);
                    } else if (attr != null) {
                        if (name.equalsIgnoreCase("mod")) {
                            m = new Modifier();
                            mod = true;
                        }

                        if (mod) {
                            switch (name.toLowerCase()) {
                                case "name":
                                    m.setName(parser.nextText());
                                    break;
                                case "amount":
                                    Integer i;
                                    // TODO figure if this is the best place. Currently
                                    // throws an error if the user doesn't put anything
                                    // between the brackets in the xml file
                                    try {
                                        i = Integer.valueOf(parser.nextText());
                                    } catch (NumberFormatException e) {
                                        i = 0;
                                    }
                                    m.setAmount(i);
                                    break;
                                case "displaytext":
                                    m.setDisplayText(parser.nextText());
                                    break;
                                case "summary":
                                    m.setSummary(parser.nextText());
                                    break;
                                case "book":
                                    m.setBook(parser.nextText());
                                    break;
                                case "page":
                                    m.setPage(parser.nextText());
                                    break;
                            }

                        } else {
                            String s = parser.nextText();
                            Integer i;

                            switch (name.toLowerCase()) {
                                case "race":
                                    attr.setRace(s);
                                    break;
                            /* Base stats and starting stats */
                                case "basebody":
                                    i = Integer.valueOf(s);
                                    attr.setBaseBody(i);
                                    attr.setBody(i);
                                    break;
                                case "baseagi":
                                    i = Integer.valueOf(s);
                                    attr.setBaseAgi(i);
                                    attr.setAgi(i);
                                    break;
                                case "baserea":
                                    i = Integer.valueOf(s);
                                    attr.setBaseRea(i);
                                    attr.setRea(i);
                                    break;
                                case "basestr":
                                    i = Integer.valueOf(s);
                                    attr.setBaseStr(i);
                                    attr.setStr(i);
                                    break;
                                case "basewil":
                                    i = Integer.valueOf(s);
                                    attr.setBaseWil(i);
                                    attr.setWil(i);
                                    break;
                                case "baselog":
                                    i = Integer.valueOf(s);
                                    attr.setBaseLog(i);
                                    attr.setLog(i);
                                    break;
                                case "baseintu":
                                    i = Integer.valueOf(s);
                                    attr.setBaseInt(i);
                                    attr.setIntu(i);
                                    break;
                                case "basecha":
                                    i = Integer.valueOf(s);
                                    attr.setBaseCha(i);
                                    attr.setCha(i);
                                    break;
                                case "baseedge":
                                    i = Integer.valueOf(s);
                                    attr.setBaseEdge(i);
                                    attr.setEdge(i);
                                    break;
                            /* Max Stats here */
                                case "maxbody":
                                    i = Integer.valueOf(s);
                                    attr.setMaxBody(i);
                                    break;
                                case "maxagi":
                                    i = Integer.valueOf(s);
                                    attr.setMaxAgi(i);
                                    break;
                                case "maxrea":
                                    i = Integer.valueOf(s);
                                    attr.setMaxRea(i);
                                    break;
                                case "maxstr":
                                    i = Integer.valueOf(s);
                                    attr.setMaxStr(i);
                                    break;
                                case "maxwil":
                                    i = Integer.valueOf(s);
                                    attr.setMaxWil(i);
                                    break;
                                case "maxlog":
                                    i = Integer.valueOf(s);
                                    attr.setMaxLog(i);
                                    break;
                                case "maxintu":
                                    i = Integer.valueOf(s);
                                    attr.setMaxInt(i);
                                    break;
                                case "maxcha":
                                    i = Integer.valueOf(s);
                                    attr.setMaxCha(i);
                                    break;
                                case "maxedge":
                                    i = Integer.valueOf(s);
                                    attr.setMaxEdge(i);
                                    break;
                                case "ess":
                                    i = Integer.valueOf(s);
                                    attr.setEss(i);
                                    break;
                            }

                        }

                    }
                    // Log.i(ChummerConstants.TAG, "START_TAG " + name);
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("mod")) {
                        ChummerMethods.addModstoChar(m, ShadowrunCharacter.getCharacter());
                        m = null;
                        mod = false;
                    }
                    if (name.equalsIgnoreCase("metatype")) {
                        return attr;
                    }

                    // Log.i(ChummerConstants.TAG, "END_TAG " + name);
            }

            eventType = parser.next();
        }

        return attr;
    }

    /**
     * @param fileLocation of the file to parse
     * @return an Array of PriorityTable data
     */
    private Attribute readXML(final String fileLocation) {
        Attribute attr = null;
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = getApplicationContext().getAssets().open(fileLocation);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            attr = parseXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return attr;
    }
}