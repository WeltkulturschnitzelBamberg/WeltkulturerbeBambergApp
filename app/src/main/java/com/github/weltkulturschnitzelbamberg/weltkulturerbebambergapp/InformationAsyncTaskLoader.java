package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.InformationTable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Michael on 15.07.2015.
 */
public class InformationAsyncTaskLoader extends AsyncTaskLoader {

    public static final int LOADER_ID = 3;

    public InformationAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeInformationsToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO Docuemntation
    private void writeInformationsToDatabase() throws IOException, ParserConfigurationException, SAXException {
        Document doc = loadFile();

        NodeList waypointInformations = doc.getElementsByTagName(Informations.TAG_WAYPOINT_INFORMATION);
        for (int i = 0; i < waypointInformations.getLength(); i++) {
            if (waypointInformations.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element waypointInformation = (Element) waypointInformations.item(i);

                String id = null;
                String image = null;
                String infoText = null;

                if (waypointInformation.getElementsByTagName(Informations.TAG_ID).getLength() > 0)
                    id = waypointInformation.getElementsByTagName(Informations.TAG_ID).item(0).getTextContent();
                if (waypointInformation.getElementsByTagName(Informations.TAG_IMAGE).getLength() > 0)
                    image = waypointInformation.getElementsByTagName(Informations.TAG_IMAGE).item(0).getTextContent();
                if (waypointInformation.getElementsByTagName(Informations.TAG_INFO_TEXT).getLength() > 0)
                    infoText = waypointInformation.getElementsByTagName(Informations.TAG_INFO_TEXT).item(0).getTextContent();

                ContentValues values = new ContentValues();
                values.put(InformationTable.COLUMN_INFORMATION_ID, id);
                values.put(InformationTable.COLUMN_IMAGE, image);
                values.put(InformationTable.COLUMN_INFO_TEXT, infoText);

                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_INFORMATION, values);
            }
        }
    }

    // TODO Documentation
    private Document loadFile() throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(getContext().getAssets().open(Informations.FILENAME));
        Document doc = documentBuilder.parse(inputSource);
        return doc;
    }

    private static class Informations {

        private static final String FILENAME = "information.xml";
        private static final String TAG_INFORMATIONS = "information";
        private static final String TAG_WAYPOINT_INFORMATION = "waypoint-information";
        private static final String TAG_ID = "id";
        private static final String TAG_IMAGE = "image";
        private static final String TAG_INFO_TEXT = "info-text";
    }
}