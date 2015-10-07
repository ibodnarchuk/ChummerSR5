package com.iwan_b.chummersr5.utility;

import android.app.Activity;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChummerXML {

    /**
     * @param fileLocation of the file to parse
     * @return an Array of PriorityTable data
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static ArrayList<String> readStringXML(final Activity activity, final String fileLocation) {
        ArrayList<String> attributes = new ArrayList<String>();
        try {
            XmlPullParserFactory pullParserFactory;

            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = activity.getApplicationContext().getAssets().open(fileLocation);

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            attributes = parseStringXML(parser);

        } catch (XmlPullParserException e) {
            Log.d(ChummerConstants.TAG, "XmlPullParserException: " + e.getMessage());
        } catch (IOException e) {
            Log.d(ChummerConstants.TAG, "IOException: " + e.getMessage());
        }

        return attributes;
    }

    private static ArrayList<String> parseStringXML(final XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<String> attributes = new ArrayList<String>();

        int eventType = parser.getEventType();
        // TODO change all the hardcoded xml properties used further down
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    name = parser.getName();
                    // Log.i(ChummerConstants.TAG, "START_DOCUMENT " + name);
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("data")) {
                        attributes.add(parser.nextText());
                    }
                    // Log.i(ChummerConstants.TAG, "START_TAG " + name);
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    // Log.i(ChummerConstants.TAG, "END_TAG " + name);
                    break;
            }

            eventType = parser.next();
        }
        return attributes;
    }
}