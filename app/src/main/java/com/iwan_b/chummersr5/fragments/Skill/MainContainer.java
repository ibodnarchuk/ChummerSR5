package com.iwan_b.chummersr5.fragments.Skill;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iwan_b.chummersr5.R;
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
	private static View rootView;

	public static void updateKarma() {
		if (rootView != null) {
			TextView attrKarmaCounter = (TextView) rootView.findViewById(R.id.karmaCounter);
			attrKarmaCounter.setText("" + ShadowrunCharacter.getKarma());
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.skillsfragment, container, false);

		ArrayList<Skill> rs = readSkillsXML("skills/skills.xml");

		for (Skill s : rs) {
			Log.i(ChummerConstants.TAG, s.toString());
		}

		return rootView;
	}

	private ArrayList<Skill> parseXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
		ArrayList<Skill> Skills = new ArrayList<>();
		Skill tempSkill = null;

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

				if (name.equalsIgnoreCase("skill")) {
					tempSkill = new Skill();
					// int attCount = parser.getAttributeCount();
					// TODO example of how to get the string
					// String p = getString(R.string.type);
				} else if (tempSkill != null) {
					String s = parser.nextText();

					switch(name.toLowerCase()){
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
					}

				}
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();
				if (name.equalsIgnoreCase("skill")) {
					Skills.add(tempSkill);
					tempSkill = null;
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