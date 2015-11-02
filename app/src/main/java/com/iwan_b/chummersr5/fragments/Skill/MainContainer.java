package com.iwan_b.chummersr5.fragments.Skill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.data.Skill;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

public class MainContainer extends Fragment {
	// Whether the max attribute was used
	private boolean maxInitialSkillUsed = false;

	private static View rootView;

	public static void updateKarma() {
		if (rootView != null) {
			TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
			karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
		}
	}

	private void updateCounters(final Integer skillCounter,final Integer groupSkillCounter) {
		TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
		freeSkillsTxtView.setText(String.valueOf(skillCounter));

		TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroups);
		freeSkillGroupTxt.setText(String.valueOf(groupSkillCounter));

		updateKarma();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.skillsfragment, container, false);

		int freeSkill = 0;
		int freeSkillGroup = 0;
		// TODO change the hardcode containskey
		if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill")) {

			for(Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill")) {
				freeSkill += m.getAmount();
			}
		}

		// TODO change the hardcode containskey
		if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill_group")) {
			for(Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill_group")) {
				freeSkillGroup += m.getAmount();
			}
		}

		updateCounters(freeSkill, freeSkillGroup);

		ArrayList<Skill> skillsAvailable = readSkillsXML("skills/skills.xml");

		Collections.sort(skillsAvailable);

		TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillsTableLayout);
		SkillTableRow genSkillTableRow = new SkillTableRow(rootView);

		ArrayList<String> skillGroup = new ArrayList<>();


		for (final Skill currentSkill : skillsAvailable) {
			// Add the skill group listed
			if(currentSkill.getGroupName() != null && !currentSkill.getGroupName().isEmpty()){
				// Don't allow duplicates
				if(!skillGroup.contains(currentSkill.getGroupName())) {
					skillGroup.add(currentSkill.getGroupName());
				}
			}

			if(currentSkill.getMagicOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseMagic() > 0){
				SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
			} else if(currentSkill.getTechnomancerOnly() && ShadowrunCharacter.getCharacter().getAttributes().getBaseRes() > 0){
				SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
			} else if(!currentSkill.getMagicOnly() && !currentSkill.getTechnomancerOnly()){
				SkillsTableLayout.addView(genSkillTableRow.createRow(currentSkill));
			}
		}

		TableLayout SkillsGroupTableLayout = (TableLayout) rootView.findViewById(R.id.SkillGroupsTableLayout);

		SkillGroupTableRow genSkillGroupTableRow = new SkillGroupTableRow(rootView, skillsAvailable);
		// Add skill groups
		for(final String sGroup: skillGroup){
			SkillsGroupTableLayout.addView(genSkillGroupTableRow.createRow(sGroup));
		}


		return rootView;
	}

	private ArrayList<Skill> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Skill> Skills = new ArrayList<>();
		Skill tempSkill = null;
		boolean spec = false;

		int eventType = parser.getEventType();
		// TODO change all the hardcoded xml properties used further down
		while (eventType != XmlPullParser.END_DOCUMENT) {
			String name;
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:
				name = parser.getName();
				break;
			case XmlPullParser.START_TAG:
				name = parser.getName();
//				Log.i(ChummerConstants.TAG, "START_TAG " + name);
				if (name.equalsIgnoreCase("skill")) {
					tempSkill = new Skill();
				} else if (tempSkill != null) {
					if(name.equalsIgnoreCase("spec")){
						spec = true;
					}

					if(spec) {
						switch (name.toLowerCase()) {
							case "item":
								if (tempSkill.getSpec() == null) {
									tempSkill.setSpec(new ArrayList<String>());
								}
								tempSkill.getSpec().add(parser.nextText());
								break;
						}
					} else{
					String s = parser.nextText();
						switch (name.toLowerCase()) {
							case "name":
								tempSkill.setSkillName(s);
								break;
							case "book":
								tempSkill.setBook(s);
								break;
							case "page":
								tempSkill.setPage(s);
								break;
							case "summary":
								tempSkill.setSummary(s);
								break;
							case "magiconly":
								if (s.equalsIgnoreCase("true")) {
									tempSkill.setMagicOnly(true);
								}
								break;
							case "techonly":
								if (s.equalsIgnoreCase("true")) {
									tempSkill.setTechnomancerOnly(true);
								}
								break;
							case "attr":
								tempSkill.setAttrName(s);
								break;
							case "defaultable":
								tempSkill.setIsDefaultable(Boolean.getBoolean(s));
								break;
							case "group":
								tempSkill.setGroupName(s);
								break;
						}}

					}
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();
//				Log.i(ChummerConstants.TAG, "END_TAG " + name);

				switch(name.toLowerCase()){
					case "spec":
						spec = false;
						break;
					case "skill":
						Skills.add(tempSkill);
						tempSkill = null;
						break;
				}
			}

			eventType = parser.next();
		}

		return Skills;
	}

	/**
	 * @param fileLocation
	 *            of the file to parse
	 * @return an Array of PriorityTable data
	 */
	private ArrayList<Skill> readSkillsXML(final String fileLocation) {
		ArrayList<Skill> skills = new ArrayList<>();
		try {
			XmlPullParserFactory pullParserFactory;

			pullParserFactory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = pullParserFactory.newPullParser();

			InputStream in_s = getActivity().getApplicationContext().getAssets().open(fileLocation);

			parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
			parser.setInput(in_s, null);

			skills = parseXML(parser);

		} catch (XmlPullParserException e) {
			Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
		} catch (IOException e) {
			Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
		}

		return skills;
	}

}