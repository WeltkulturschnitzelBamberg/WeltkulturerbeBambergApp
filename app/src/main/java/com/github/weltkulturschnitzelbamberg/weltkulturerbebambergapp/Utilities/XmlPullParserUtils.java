package com.github.orangeutan.androidxmlloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * This class provides functions to ease the use of the {@link XmlPullParser}
 *
 * @author OrangeUtan
 * @version 1.0
 * @since 2015-03-04
 */
public final class XmlPullParserUtils {

    /**
     * Private Constructor to prevent instantiation. If called it throws a IllegalAccessException
     * to discourage to instantiate this class
     */
    private XmlPullParserUtils()throws IllegalAccessException{
        throw new IllegalAccessException(XmlPullParserUtils.class.getName() + " should not be instantiated!");
    }

    /**
     * Moves the parser to the next tag with the searched tag name. <p>
     * If the tag was found this function returns true, otherwise it
     * will return false and the parser stops at the end of the document.<p>
     * If <b>stayInParentTag</b> is true, the parser won't search for tags with the searched
     * tag name outside of the parent tag. If the parser hereupon doesn't find another tag
     * with the searched tag name inside the parent tag it stops at start tag of the tag following the
     * parent tag
     * Assuming you are searching for <b>childTag</b> and start at <b>parentTag</b>:
     *
     * <pre>
     * {@code <parentTag>
     *           <childTag></childTag>
     *           <childTag></childTag>
     *   </parentTag>
     *   <followingTag></followingTag>
     *
     *   XmlPullParserUtils.moveToTag(parser, "childTag", true) -> childTag -> returns true
     *   XmlPullParserUtils.moveToTag(parser, "childTag", true) -> childTag -> return true
     *   XmlPullParserUtils.moveToTag(parser, "childTag", true) -> followingTag -> returns false}
     *
     * @param parser {@link XmlPullParser} in which to search for the next tag
     * @param name The name of the searched tag
     * @param stayInParentTag If true the parseer won't search for the searched tag outside the parent tag
     * @return The result if it found the searched tag
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static boolean goToTag(XmlPullParser parser, String name, boolean stayInParentTag) throws XmlPullParserException, IOException{
        nextTag(parser);
        int depth = parser.getDepth();
        while (!name.equals(parser.getName())) {
            nextTag(parser);
            /**
             * If the parser left the parent tag (is at the same depth as the parent tag)
             * and the parser should not search outside the parent tag (stayInParentTag = true),
             * quit the search
             */
            if (depth > parser.getDepth() && stayInParentTag) return false;
            /**
             * If the parser reaches the end of the document it returns false
             * to show it couldn't find anymore tags with the searched name
             */
            if (parser.getEventType() == XmlPullParser.END_DOCUMENT) return false;
        }
        return true;
    }

    /**
     * Moves the parser to the start of the next tag. Returns true if done successfully and false
     * if there are no more tags.
     * The parser can be anywhere in the document.
     * @param parser {@link XmlPullParser} in which to move to the next tag
     * @return The result if it was possible to move to the next tag
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static boolean nextTag(XmlPullParser parser) throws XmlPullParserException, IOException{
        int eventType = parser.next();
        while (eventType != XmlPullParser.START_TAG) {
            eventType = parser.next();
            /**
             * If the parser reaches the end of the document it returns false to show there are no more tags
             */
            if (eventType == XmlPullParser.END_DOCUMENT) return false;
        }
        return true;
    }

    /**
     * Skips the current tag including its nested tags. Parser has to be at the start tag
     * @param parser {@link XmlPullParser} in which to skip the current tag
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static void skipTag(XmlPullParser parser) throws XmlPullParserException, IOException{
        /**
         * If the parser is located at a start tag it moves through the nested tags until the tag is closed
         */
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                case XmlPullParser.END_TAG:
                    depth--;
            }
        }
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            nextTag(parser);
        }
    }

    /**
     * Skips the tag following the current tag including its nested tags. If the current
     * tag is the last childTag of the current parentTag, it moves to the start of the next tag
     * following the current parentTag
     * @param parser {@link XmlPullParser} in which to skip the next tag
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static void skipNextTag(XmlPullParser parser) throws XmlPullParserException, IOException{
        nextTag(parser);
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
                case XmlPullParser.END_TAG:
                    depth--;
            }
        }
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            nextTag(parser);
        }
    }

    /**
     * Returns the text of the current tag and afterwards moves to the end tag.
     * The parser has to be at the start tag
     * @param parser {@link XmlPullParser} in which to get the text of the current tag
     * @return The text in the current text
     * @throws XmlPullParserException
     * @throws IOException
     */
    public static String getText(XmlPullParser parser) throws XmlPullParserException, IOException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}