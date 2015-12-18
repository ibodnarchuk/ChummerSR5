package com.iwan_b.chummersr5.fragments.MainStats;

import android.app.ActionBar.LayoutParams;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.FreeCounters;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.Quality;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.fragments.fragmentUtil.UpdateInterface;
import com.iwan_b.chummersr5.utility.ChummerConstants;
import com.iwan_b.chummersr5.utility.ChummerXML;

import java.util.ArrayList;

public class AttributeFragment extends Fragment implements UpdateInterface {
    // Whether the max attribute was used
    private boolean maxAttributeUsed = false;
    private View rootView;

    private MainContainer parentContainer;
    private ArrayList<AttributeOnClickListener> childrenToUpdate = new ArrayList<>();

    public static AttributeFragment newInstance(MainContainer main) {
        AttributeFragment f = new AttributeFragment();
        f.parentContainer = main;
        return f;
    }

    private void updateCounters() {
        TextView attrTextViewCounter = (TextView) rootView.findViewById(R.id.fragment_mainstats_attribute_Priority_Counter);
        TextView attrTextViewSpecCounter = (TextView) rootView.findViewById(R.id.fragment_mainstats_attribute_attrSpec_Counter);

        attrTextViewCounter.setText(FreeCounters.getCounters().getFreeAttributes() + " Attr Counter");
        attrTextViewSpecCounter.setText(FreeCounters.getCounters().getFreeSpecAttributes() + " Spec Counter");

        MainContainer.updateKarma();
    }

    @Override
    public void update() {
        for (AttributeOnClickListener child : childrenToUpdate) {
            child.update();
        }
    }

    @Override
    public void updateParent() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mainstats_attribute, container, false);

        // Display the counters
        updateCounters();

        ArrayList<String> attributes = ChummerXML.readStringXML(getActivity(), "data/AttributesFull.xml");

        TableLayout attrTableLayout = (TableLayout) rootView.findViewById(R.id.fragment_mainstats_attribute_attribute_TableLayout);

        // Loop through each attribute and create a row for them
        for (final String attrName : attributes) {
            // TODO figure out if it is better to create the rows here, or elsewhere.
            TableRow newTableRow = new TableRow(rootView.getContext());
            newTableRow.setGravity(Gravity.CENTER_VERTICAL);

            TextView titleTxtView = new TextView(rootView.getContext());
            Button subButton = new Button(rootView.getContext());
            TextView attrDisplayTxtView = new TextView(rootView.getContext());
            Button addButton = new Button(rootView.getContext());
            TextView extraInfo = new TextView(rootView.getContext());

            // Default stats
            int baseStat = ChummerConstants.baseStat;
            int maxStat = ChummerConstants.maxStat;
            boolean spec = false;

            // Title of the attribute
            titleTxtView.setText(attrName);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 5, 0);
            titleTxtView.setLayoutParams(lp);
            newTableRow.addView(titleTxtView);

            // Subtract Button
            subButton.setText("-");
            TableRow.LayoutParams lp2 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            subButton.setLayoutParams(lp2);
            newTableRow.addView(subButton);


            Resources res = getResources();

            switch (attrName.toLowerCase()) {
                case "body":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseBody();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxBody();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "agility":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseAgi();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxAgi();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "reaction":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseRea();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxRea();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "strength":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseStr();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxStr();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "will":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseWil();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxWil();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "logic":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseLog();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxLog();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "intuition":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseInt();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxInt();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "charisma":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseCha();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxCha();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    break;
                case "edge":
                    baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseEdge();
                    maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxEdge();
                    attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    spec = true;
                    break;
                case "magic":
                    if (ShadowrunCharacter.getCharacter().getUserType().ordinal() >= ChummerConstants.userType.mystic_adept.ordinal()) {
                        baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic();
                        maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxMagic();
                        attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    } else {
                        // Skip and not add to the table
                        continue;
                    }
                    spec = true;
                    break;
                case "technomancer":
                    if (ShadowrunCharacter.getCharacter().getUserType() == ChummerConstants.userType.technomancer) {
                        baseStat = ShadowrunCharacter.getCharacter().getAttributes().getBaseRes();
                        maxStat = ShadowrunCharacter.getCharacter().getAttributes().getMaxRes();
                        attrDisplayTxtView.setText(res.getString(R.string.attrText, baseStat, maxStat));
                    } else {
                        // Skip and not add to the table
                        continue;
                    }
                    spec = true;
                    break;
            }

            TableRow.LayoutParams lp3 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp3.setMargins(20, 20, 20, 20);
            attrDisplayTxtView.setLayoutParams(lp3);
            attrDisplayTxtView.setGravity(1);
            attrDisplayTxtView.setMinWidth(50);
            newTableRow.addView(attrDisplayTxtView);

            // Addition Button
            addButton.setText("+");
            TableRow.LayoutParams lp4 = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            addButton.setLayoutParams(lp4);
            newTableRow.addView(addButton);

            // Extra Info
            extraInfo.setText("");
            newTableRow.addView(extraInfo);

            // The amount used for each row
            final ArrayList<Integer> pointHistory = new ArrayList<>();

            AttributeOnClickListener subOnClickListener = new AttributeOnClickListener(attrName, attrDisplayTxtView, extraInfo, baseStat, maxStat,
                    false, spec, pointHistory);

            AttributeOnClickListener addOnClickListener = new AttributeOnClickListener(attrName, attrDisplayTxtView, extraInfo, baseStat, maxStat,
                    true, spec, pointHistory);

            childrenToUpdate.add(subOnClickListener);
            childrenToUpdate.add(addOnClickListener);

            subButton.setOnClickListener(subOnClickListener);
            addButton.setOnClickListener(addOnClickListener);

            attrTableLayout.addView(newTableRow);
        }

        return rootView;
    }

    private class AttributeOnClickListener implements OnClickListener {
        // Which textfield to modify
        private TextView attrDisplayTxtView;
        // Whether to add or subtract
        private boolean isAddition;

        // Limits on the attribute
        private int baseAttr;
        private int maxAttr;

        // Whether the attribute is special or not
        private boolean isSpec;

        // Name of the attribute being altered
        private String attrName;

        // Display all the modifiers that affect the attribute
        private TextView extraInfo;

        private ArrayList<Integer> pointHistory;

        public AttributeOnClickListener(final String attrName, final TextView attrDisplayTxtView,
                                        final TextView extraInfo, final int baseAttr, final int maxAttr, final boolean isAddition,
                                        final boolean isSpec, final ArrayList<Integer> pointHistory) {
            this.attrName = attrName;
            this.attrDisplayTxtView = attrDisplayTxtView;
            this.isAddition = isAddition;

            this.baseAttr = baseAttr;
            this.maxAttr = maxAttr;

            this.isSpec = isSpec;

            this.extraInfo = extraInfo;

            this.pointHistory = pointHistory;
        }

        public void update() {
            int max_attr_mod = getMods();

            maxAttributeUsed = getCurrentRating() == maxAttr + max_attr_mod;

            // Update the displays with all the new stuff.
            attrDisplayTxtView.setText(getResources().getString(R.string.attrText, getCurrentRating(), (maxAttr + max_attr_mod)));
        }

        private int getCurrentRating() {
            // TODO figure out a better way to call these methods
            switch (attrName.toLowerCase()) {
                case "body":
                    return ShadowrunCharacter.getCharacter().getAttributes().getBody();
                case "agility":
                    return ShadowrunCharacter.getCharacter().getAttributes().getAgi();
                case "reaction":
                    return ShadowrunCharacter.getCharacter().getAttributes().getRea();
                case "strength":
                    return ShadowrunCharacter.getCharacter().getAttributes().getStr();
                case "will":
                    return ShadowrunCharacter.getCharacter().getAttributes().getWil();
                case "logic":
                    return ShadowrunCharacter.getCharacter().getAttributes().getLog();
                case "intuition":
                    return ShadowrunCharacter.getCharacter().getAttributes().getIntu();
                case "charisma":
                    return ShadowrunCharacter.getCharacter().getAttributes().getCha();
                case "edge":
                    return ShadowrunCharacter.getCharacter().getAttributes().getEdge();
                case "resonance":
                    return ShadowrunCharacter.getCharacter().getAttributes().getRes();
                case "magic":
                    return ShadowrunCharacter.getCharacter().getAttributes().getMagic();
            }
            return 0;
        }

        private void setCurrentRating(final int rating) {
            // TODO figure out a better way to call these methods
            switch (attrName.toLowerCase()) {
                case "body":
                    ShadowrunCharacter.getCharacter().getAttributes().setBody(rating);
                    break;
                case "agility":
                    ShadowrunCharacter.getCharacter().getAttributes().setAgi(rating);
                    break;
                case "reaction":
                    ShadowrunCharacter.getCharacter().getAttributes().setRea(rating);
                    break;
                case "strength":
                    ShadowrunCharacter.getCharacter().getAttributes().setStr(rating);
                    break;
                case "will":
                    ShadowrunCharacter.getCharacter().getAttributes().setWil(rating);
                    break;
                case "logic":
                    ShadowrunCharacter.getCharacter().getAttributes().setLog(rating);
                    break;
                case "intuition":
                    ShadowrunCharacter.getCharacter().getAttributes().setIntu(rating);
                    break;
                case "charisma":
                    ShadowrunCharacter.getCharacter().getAttributes().setCha(rating);
                    break;
                case "edge":
                    ShadowrunCharacter.getCharacter().getAttributes().setEdge(rating);
                    break;
                case "resonance":
                    ShadowrunCharacter.getCharacter().getAttributes().setRes(rating);
                    break;
                case "magic":
                    ShadowrunCharacter.getCharacter().getAttributes().setMagic(rating);

                    Float powerPoints = FreeCounters.getCounters().getPowerPoints();
                    if (isAddition) {
                        powerPoints++;
                    } else {
                        powerPoints--;
                    }
                    FreeCounters.getCounters().setPowerPoints(powerPoints);
                    break;
            }
        }

        private boolean wasKarmaUsed() {
            for (int temp : pointHistory) {
                if (temp > 0) {
                    return true;
                }
            }
            return false;
        }

        private int getMods() {
            extraInfo.setText("");
            int max_attr_mod = 0;
            // TODO find a better way to apply modifiers
            if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("max_attr_" + attrName)) {
                for (Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("max_attr_" + attrName)) {
                    max_attr_mod += m.getAmount();
                }
            }

            for (Quality q : ShadowrunCharacter.getCharacter().getPositiveQualities()) {
                for (Modifier m : q.getMods()) {
                    if (m.getName().equalsIgnoreCase("max_attr_" + attrName)) {
                        max_attr_mod += m.getAmount();
                        extraInfo.setText(q.getName());
                    }
                }
            }
            return max_attr_mod;
        }

        @Override
        public void onClick(View v) {
            // Current rating of the attribute
            Integer currentRating = getCurrentRating();

            // Current value left for attributes
            Integer attrCounter;

            if (isSpec) {
                attrCounter = FreeCounters.getCounters().getFreeSpecAttributes();
            } else {
                attrCounter = FreeCounters.getCounters().getFreeAttributes();
            }

            // Current amount of karma left
            Integer karmaUnused = ShadowrunCharacter.getKarma();

            int max_attr_mod = getMods();

            if (isAddition) {
                // TODO add a karma thing as well as modifiers to this rule so the user can go above the max
                if (currentRating < maxAttr + max_attr_mod) {
                    if (isSpec) {
                        // Use the attributes first, then karma
                        if (attrCounter - 1 >= 0) {
                            currentRating++;
                            attrCounter--;
                            pointHistory.add(ChummerConstants.attrPointUsed);
                            setCurrentRating(currentRating);
                        } else {
                            // See if they have enough karma to buy the next rating
                            if ((currentRating + 1) * 5 <= karmaUnused) {
                                // How much karma is spent
                                pointHistory.add((currentRating + 1) * 5);
                                karmaUnused -= (currentRating + 1) * 5;

                                currentRating++;
                                setCurrentRating(currentRating);
                            }
                        }
                    } else {
                        if (!(maxAttributeUsed && currentRating + 1 == maxAttr + max_attr_mod)) {
                            // Use the attributes first, then karma
                            if (attrCounter - 1 >= 0) {
                                currentRating++;
                                attrCounter--;
                                pointHistory.add(ChummerConstants.attrPointUsed);
                                setCurrentRating(currentRating);
                            } else {
                                // See if they have enough karma to buy the next rating
                                if ((currentRating + 1) * 5 <= karmaUnused) {
                                    // How much karma is spent
                                    pointHistory.add((currentRating + 1) * 5);
                                    karmaUnused -= (currentRating + 1) * 5;

                                    currentRating++;
                                    setCurrentRating(currentRating);
                                }

                            }
                        }

                        if (currentRating == maxAttr + max_attr_mod) {
                            maxAttributeUsed = true;
                        }
                    }
                }
                // Subtraction
            } else {
                if (currentRating > baseAttr && !pointHistory.isEmpty()) {
                    // We make it false because we are going to remove one now
                    if (currentRating == maxAttr + max_attr_mod) {
                        maxAttributeUsed = false;
                    }

                    // Test to see if karma was used to increase this attr.
                    if (wasKarmaUsed()) {
                        karmaUnused += currentRating * 5;
                    } else {
                        attrCounter++;
                    }
                    currentRating--;
                    pointHistory.remove(pointHistory.size() - 1);
                    setCurrentRating(currentRating);
                }
            }

            // Update the displays with all the new stuff.
            attrDisplayTxtView.setText(getResources().getString(R.string.attrText, currentRating.toString(), (maxAttr + max_attr_mod)));

            if (isSpec) {
                FreeCounters.getCounters().setFreeSpecAttributes(attrCounter);
            } else {
                FreeCounters.getCounters().setFreeAttributes(attrCounter);
            }

            ShadowrunCharacter.setKarma(karmaUnused);
            updateCounters();
        }
    }

}