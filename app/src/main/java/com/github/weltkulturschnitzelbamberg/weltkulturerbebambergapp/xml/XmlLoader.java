package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.xml;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by michael on 10.06.15.
 */
//TODO add documentation
public class XmlLoader {

    public static final int INDEX_TAG_NAME = 0;
    public static final int INDEX_TEXT = 1;
    public static final int INDEX_PARENT_ID = 2;

    private List<String> searchedTags;
    private List<String[]> foundTags;
    private String parentTag;

    public XmlLoader() {
    }

    public XmlLoader setSearchedTags(String... tagNames) {
        searchedTags = new ArrayList<>(Arrays.asList(tagNames));
        foundTags = new ArrayList<>();
        return this;
    }

    public XmlLoader setParentTag(String parent) {
        parentTag = parent;
        return this;
    }

    public XmlLoader load(Context context, String fileName) throws XmlPullParserException, IOException {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(false);
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(context.getAssets().open(fileName), null);

        int eventType = parser.getEventType();
        int parentID = 0;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            eventType = parser.next();
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (parentTag.equals(tagName)) {
                        parentID ++;
                    } else if (searchedTags.contains(tagName)) {
                        foundTags.add(new String[]{tagName, getText(parser), Integer.toString(parentID)});
                    }
                    break;
            }
        }
        return this;
    }

    public List<String[]> getResults() {
        return foundTags;
    }

    private String getText(XmlPullParser parser) throws XmlPullParserException, IOException{
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}