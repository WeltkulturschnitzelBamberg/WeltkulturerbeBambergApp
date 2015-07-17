package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.LeadingMarginSpan;
import android.text.style.TypefaceSpan;

import org.xml.sax.XMLReader;

/**
 * Created by Michael on 17.07.2015.
 */
public class CustomHtmlTagHandler implements Html.TagHandler{
    private static final String TAG_INDENT = "indent";
    private static final String TAG_LINE = "l";

    @Override
    public void handleTag(final boolean opening, final String tag, Editable output, final XMLReader xmlReader) {
        if (tag.equalsIgnoreCase(TAG_INDENT)) {
            handleTagIndent(opening, output);
        } else if (tag.equalsIgnoreCase(TAG_LINE)) {
            handleTagLine(opening, output);
        }
    }

    private Object getLast(Editable text, Class kind) {
        Object[] objs = text.getSpans(0, text.length(), kind);
        if(objs.length == 0) {
            return null;
        } else {
            for (int i=objs.length; i > 0; i--) {
                if(text.getSpanFlags(objs[i-1]) == Spannable.SPAN_MARK_MARK) {
                    return objs[i-1];
                }
            }
            return null;
        }
    }

    private void handleTagIndent(boolean opening, Editable output) {
        if (opening) {
            if (output.length() >= 1) {
                output.insert(output.length(), "\n");
            }
            output.setSpan(new TypefaceSpan("monospace"), output.length(), output.length(), Spanned.SPAN_MARK_MARK);
        } else {
            Object obj = getLast(output, TypefaceSpan.class);
            int start = output.getSpanStart(obj);
            output.removeSpan(obj);
            if (!output.toString().endsWith("\n")) {
                output.append("\n");
            }
            output.setSpan(new LeadingMarginSpan.Standard(150), start, output.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    private void handleTagLine(boolean opening, Editable output) {
        if (opening) {
            if (output.length() >= 1) {
                output.append("\n");
            }
        }
    }
}
