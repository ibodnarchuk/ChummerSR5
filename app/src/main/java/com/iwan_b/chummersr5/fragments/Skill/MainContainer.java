package com.iwan_b.chummersr5.fragments.Skill;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainContainer extends Fragment {
	// Whether the max attribute was used
	private boolean maxInitialSkillUsed = false;

	private static View rootView;

	Integer displaySkillCounter = 0;
	Integer displaySkillGroupCounter = 0;


	public static void updateKarma() {
		if (rootView != null) {
			TextView karmaCounterTxtView = (TextView) rootView.findViewById(R.id.karmaCounter);
			karmaCounterTxtView.setText(ShadowrunCharacter.getKarma() + " Karma");
		}
	}

	private void updateCounters() {
		TextView freeSkillsTxtView = (TextView) rootView.findViewById(R.id.freeSkills);
		freeSkillsTxtView.setText(String.valueOf(displaySkillCounter));

		TextView freeSkillGroupTxt = (TextView) rootView.findViewById(R.id.freeSkillGroups);
		freeSkillGroupTxt.setText(String.valueOf(displaySkillGroupCounter));

		updateKarma();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.skillsfragment, container, false);

		// TODO change the hardcode containskey
		if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill")) {
			int freeSkill = 0;
			for(Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill")) {
				freeSkill += m.getAmount();
			}

			displaySkillCounter = freeSkill;
		}

		// TODO change the hardcode containskey
		if (ShadowrunCharacter.getCharacter().getModifiers().containsKey("skill_group")) {
			int freeSkillGroup = 0;
			for(Modifier m : ShadowrunCharacter.getCharacter().getModifiers().get("skill_group")) {
				freeSkillGroup += m.getAmount();
			}

			displaySkillGroupCounter = freeSkillGroup;
		}

		updateCounters();

		ArrayList<Skill> Skills = readSkillsXML("skills/skills.xml");

		TableLayout SkillsTableLayout = (TableLayout) rootView.findViewById(R.id.SkillsTableLayout);

		// Loop through each skill and create a row for them
		for (final Skill currentSkill : Skills) {
//			Log.i(ChummerConstants.TAG, currentSkill.toString());

			TableRow newTableRow = new TableRow(rootView.getContext());
			newTableRow.setGravity(Gravity.CENTER_VERTICAL);

			TextView titleTxtView = new TextView(rootView.getContext());
			Button subButton = new Button(rootView.getContext());
			TextView skillDisplayTxtView = new TextView(rootView.getContext());
			Button addButton = new Button(rootView.getContext());
			TextView extraInfo = new TextView(rootView.getContext());


			// Title of the attribute
			titleTxtView.setText(currentSkill.getName());
			TableRow.LayoutParams lp = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
			lp.setMargins(0, 0, 5, 0);
			titleTxtView.setLayoutParams(lp);
			newTableRow.addView(titleTxtView);

			// Subtract Button
			subButton.setText("-");
			TableRow.LayoutParams lp2 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
			subButton.setLayoutParams(lp2);
			newTableRow.addView(subButton);

			TableRow.LayoutParams lp3 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
			lp3.setMargins(20, 20, 20, 20);

			skillDisplayTxtView.setLayoutParams(lp3);
			// TODO change the hardcoded values
			skillDisplayTxtView.setText("0");
			skillDisplayTxtView.setGravity(1);
			skillDisplayTxtView.setMinWidth(50);

			newTableRow.addView(skillDisplayTxtView);

			// Addition Button
			addButton.setText("+");
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
			addButton.setLayoutParams(lp4);
			newTableRow.addView(addButton);

			// Extra Info
			extraInfo.setText("");
			newTableRow.addView(extraInfo);

			if(currentSkill.getSpec() != null) {
				Spinner spinner = new Spinner(rootView.getContext());
				ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, currentSkill.getSpec()); //selected item will look like a spinner set from XML
				spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				// Set an empty thing at the beginning.
				// TODO decide if the empty tag should be here or in the XML
				spinnerArrayAdapter.insert("Specialization",0);
				spinner.setAdapter(spinnerArrayAdapter);

				TableRow.LayoutParams lp5 = new TableRow.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
				spinner.setLayoutParams(lp5);

				newTableRow.addView(spinner);
			}


			// Whether the skill is special only
			// TODO skip adding if the skill is only meant for magic/technomancer
			boolean spec = currentSkill.getMagicOnly() || currentSkill.getTechnomancerOnly();

			subButton.setOnClickListener(new SkillOnClickListener(currentSkill.getName(), skillDisplayTxtView, extraInfo, false));
			addButton.setOnClickListener(new SkillOnClickListener(currentSkill.getName(), skillDisplayTxtView, extraInfo, true));

			SkillsTableLayout.addView(newTableRow);
		}

		return rootView;
	}


	private class SkillOnClickListener implements View.OnClickListener {
		// Which textfield to modify
		private TextView skillDisplayTxtView;
		// Whether to add or subtract
		private boolean isAddition;

		// Limits on the attribute
		private int baseSkill = 0;
		private int maxSkill = 12;

		// How much karma was used
		private int karmaUsed = 0;

		// Name of the attribute being altered
		private String skillName;

		// Display all the modifiers that affect the attribute
		private TextView extraInfo;

		public SkillOnClickListener(final String skillName,final TextView skillDisplayTxtView, final TextView extraInfo,
									 final boolean isAddition) {
			this.skillName = skillName;
			this.skillDisplayTxtView = skillDisplayTxtView;
			this.extraInfo = extraInfo;
			this.isAddition = isAddition;
		}

		@Override
		public void onClick(View v) {
			// Current rating of the skill
			int currentRating = Integer.valueOf(skillDisplayTxtView.getText().toString());

			// Current value left for attributes
			Integer skillCounter, groupSkillCounter;

			skillCounter = displaySkillCounter;
			groupSkillCounter = displaySkillGroupCounter;

			// Current amount of karma left
			Integer karmaUnused = ShadowrunCharacter.getKarma();

			// Get the karma count for this attribute
			if (skillDisplayTxtView.getTag() != null) {
				karmaUsed = (Integer) skillDisplayTxtView.getTag();
			}

			int max_skill_mod = 0;

			if(isAddition) {
				if(currentRating < maxSkill + max_skill_mod) {
					if (!(maxInitialSkillUsed && currentRating + 1 == maxSkill + max_skill_mod)) {
						// Use the free skills first, then karma
						if (skillCounter - 1 >= 0) {
							// Test if they used karma on this specific item before or not
							if (karmaUsed > 0) {
								Toast toast = Toast.makeText(getActivity().getApplicationContext(),
										"You already used karma on this. Can't use points afterwards.",
										Toast.LENGTH_SHORT);
								toast.show();
							} else {
								currentRating++;
								skillCounter--;
							}
						} else {
							// Forumula for karma needed to advance to next level is: (New Rating x 2)
							if ((currentRating + 1) * 2 <= karmaUnused) {
								karmaUsed += (currentRating + 1) * 2;
								karmaUnused -= (currentRating + 1) * 2;

								// Set the karma count for the subtraction button to know
								skillDisplayTxtView.setTag(karmaUsed);

								currentRating++;
							}
						}

						if (currentRating == maxSkill + max_skill_mod) {
							maxInitialSkillUsed = true;
						}
					}
				}
				// Subtraction
			}else {
				if(currentRating > baseSkill) {
					// We make it false because we are going to remove one now
					if (currentRating == maxSkill + max_skill_mod) {
						maxInitialSkillUsed = false;
					}

					// Get the karma count for this attribute
					if (skillDisplayTxtView.getTag() != null) {
						karmaUsed = (Integer) skillDisplayTxtView.getTag();
					} else {
						karmaUsed = 0;
					}

					// Forumula for karma needed to advance to next level is: (New Rating x 2)
					if(karmaUsed > 0) {
						karmaUsed -= (currentRating) * 2;
						karmaUnused += (currentRating) * 2;
						currentRating--;
					}else{
						// No karma was used
						currentRating--;
						skillCounter++;
					}
					skillDisplayTxtView.setTag(karmaUsed);
				}
			}

			// Update the displays with all the new stuff.
			skillDisplayTxtView.setText(String.valueOf(currentRating));

			displaySkillCounter = skillCounter;
			displaySkillGroupCounter = groupSkillCounter;

			ShadowrunCharacter.setKarma(karmaUnused);
			updateCounters();

		}
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
								tempSkill.setName(s);
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