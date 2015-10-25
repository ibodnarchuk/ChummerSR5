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
		Log.i(ChummerConstants.TAG, newCharacter.toString());

		setContentView(R.layout.fragment_test);
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
						switch(name.toLowerCase()) {
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

						switch(name.toLowerCase()){
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
