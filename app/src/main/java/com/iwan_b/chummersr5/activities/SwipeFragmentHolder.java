package com.iwan_b.chummersr5.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.iwan_b.chummersr5.R;
import com.iwan_b.chummersr5.data.Attribute;
import com.iwan_b.chummersr5.data.Modifier;
import com.iwan_b.chummersr5.data.PriorityTable;
import com.iwan_b.chummersr5.data.ShadowrunCharacter;
import com.iwan_b.chummersr5.utility.ChummerConstants;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.iwan_b.chummersr5.data.Counters.getCounters;

public class SwipeFragmentHolder extends FragmentActivity {

	private ShadowrunCharacter newCharacter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_test);

		newCharacter = ShadowrunCharacter.getCharacter();

		Intent i = getIntent();

		getCounters().setMeta((PriorityTable) i.getSerializableExtra("meta"));
		getCounters().setAttr((PriorityTable) i.getSerializableExtra("attr"));
		getCounters().setMagic((PriorityTable) i.getSerializableExtra("magic"));
		getCounters().setSkill((PriorityTable) i.getSerializableExtra("skill"));
		getCounters().setRes((PriorityTable) i.getSerializableExtra("res"));

		addModstoChar(getCounters().getMeta().getMods());
		addModstoChar(getCounters().getAttr().getMods());
		addModstoChar(getCounters().getMagic().getMods());
		addModstoChar(getCounters().getSkill().getMods());
		addModstoChar(getCounters().getRes().getMods());

		String metastring = getCounters().getMeta().getMetaTypeName();

		// TODO make a metatype class
		final Attribute attrs = readXML("metatypes/" + metastring + ".xml");

		attrs.setBaseMagic(0);
		attrs.setBaseRes(0);
		attrs.setMaxMagic(6);
		attrs.setMaxRes(6);

		// Test if they are mundane or not
		if (getCounters().getMagic().getStats() != 0) {
			// Find out if they are a mage or technomancer
			if (getCounters().getMagic().getMagicType().equalsIgnoreCase("magic")) {
				attrs.setBaseMagic((int) getCounters().getMagic().getStats());
			} else {
				attrs.setBaseRes((int) getCounters().getMagic().getStats());
			}
		}

		newCharacter.setAttributes(attrs);
	}

	/**
	 * Adds modifiers to the ShadowrunCharacter. Any duplicate modifiers are
	 * added instead of overwritten.
	 * 
	 * @param mods
	 *            The Arraylist of mods to add
	 */
	private void addModstoChar(final ArrayList<Modifier> mods) {
		if (mods != null) {
			for (Modifier m : mods) {
				addModstoChar(m);
			}
		}
	}

	private void addModstoChar(final Modifier m) {
		// Log.i(ChummerConstants.TAG, m.getName());
		if (newCharacter.getModifiers().containsKey(m.getName())) {
			newCharacter.getModifiers().get(m.getName()).add(m);
		} else {
			ArrayList<Modifier> temp = new ArrayList<>();
            temp.add(m);
			newCharacter.getModifiers().put(m.getName(), temp);
		}
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
						if (name.equalsIgnoreCase("name")) {
							m.setName(parser.nextText());
						} else if (name.equalsIgnoreCase("amount")) {
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
						} else if (name.equalsIgnoreCase("displayText")) {
							m.setDisplayText(parser.nextText());
						} else if (name.equalsIgnoreCase("summary")) {
							m.setSummary(parser.nextText());
						} else if (name.equalsIgnoreCase("book")) {
							m.setBook(parser.nextText());
						} else if (name.equalsIgnoreCase("page")) {
							m.setPage(parser.nextText());
						}
					} else {
						String s = parser.nextText();
						Integer i;
						// Super long else if statement because no switches
						// allowed
						if (name.equalsIgnoreCase("race")) {
							attr.setRace(s);
						}
						/* Base stats and starting stats */
						else if (name.equalsIgnoreCase("basebody")) {
							i = Integer.valueOf(s);
							attr.setBaseBody(i);
							attr.setBody(i);
						} else if (name.equalsIgnoreCase("baseagi")) {
							i = Integer.valueOf(s);
							attr.setBaseAgi(i);
							attr.setAgi(i);
						} else if (name.equalsIgnoreCase("baserea")) {
							i = Integer.valueOf(s);
							attr.setBaseRea(i);
							attr.setRea(i);
						} else if (name.equalsIgnoreCase("basestr")) {
							i = Integer.valueOf(s);
							attr.setBaseStr(i);
							attr.setStr(i);
						} else if (name.equalsIgnoreCase("basewil")) {
							i = Integer.valueOf(s);
							attr.setBaseWil(i);
							attr.setWil(i);
						} else if (name.equalsIgnoreCase("baselog")) {
							i = Integer.valueOf(s);
							attr.setBaseLog(i);
							attr.setLog(i);
						} else if (name.equalsIgnoreCase("baseintu")) {
							i = Integer.valueOf(s);
							attr.setBaseInt(i);
							attr.setIntu(i);
						} else if (name.equalsIgnoreCase("basecha")) {
							i = Integer.valueOf(s);
							attr.setBaseCha(i);
							attr.setCha(i);
						} else if (name.equalsIgnoreCase("baseedge")) {
							i = Integer.valueOf(s);
							attr.setBaseEdge(i);
							attr.setEdge(i);
						}
						/* Max Stats here */
						else if (name.equalsIgnoreCase("maxbody")) {
							i = Integer.valueOf(s);
							attr.setMaxBody(i);
						} else if (name.equalsIgnoreCase("maxagi")) {
							i = Integer.valueOf(s);
							attr.setMaxAgi(i);
						} else if (name.equalsIgnoreCase("maxrea")) {
							i = Integer.valueOf(s);
							attr.setMaxRea(i);
						} else if (name.equalsIgnoreCase("maxstr")) {
							i = Integer.valueOf(s);
							attr.setMaxStr(i);
						} else if (name.equalsIgnoreCase("maxwil")) {
							i = Integer.valueOf(s);
							attr.setMaxWil(i);
						} else if (name.equalsIgnoreCase("maxlog")) {
							i = Integer.valueOf(s);
							attr.setMaxLog(i);
						} else if (name.equalsIgnoreCase("maxintu")) {
							i = Integer.valueOf(s);
							attr.setMaxInt(i);
						} else if (name.equalsIgnoreCase("maxcha")) {
							i = Integer.valueOf(s);
							attr.setMaxCha(i);
						} else if (name.equalsIgnoreCase("maxedge")) {
							i = Integer.valueOf(s);
							attr.setMaxEdge(i);
						} else if (name.equalsIgnoreCase("ess")) {
							i = Integer.valueOf(s);
							attr.setEss(i);
						}

					}

				}
				// Log.i(ChummerConstants.TAG, "START_TAG " + name);
				break;
			case XmlPullParser.END_TAG:
				name = parser.getName();

				if (name.equalsIgnoreCase("mod")) {
					addModstoChar(m);
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
	 * @param fileLocation
	 *            of the file to parse
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
