package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.RoutesTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.utilities.DebugUtils;

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
 * This AsyncTaskLoader loads the routes into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class RouteLoader extends AsyncTaskLoader {

    public static final int LOADER_ID = 1;

    public RouteLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeRoutesToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeRoutesToDatabase() throws IOException, ParserConfigurationException, SAXException {
        Document doc = loadFile();

        NodeList routesList = doc.getElementsByTagName(Routes.TAG_ROUTE);
        for (int i = 0; i < routesList.getLength(); i++) {
            Node routeNode = routesList.item(i);
            if (routeNode.getNodeType() == Node.ELEMENT_NODE) {
                Element route = (Element) routeNode;
                String routeName = route.getElementsByTagName(Routes.TAG_NAME).item(0).getTextContent();
                NodeList waypointIDList = route.getElementsByTagName(Routes.TAG_WAYPOINT_ID);
                for (int k = 0; k < waypointIDList.getLength(); k++) {
                    ContentValues values = new ContentValues();
                    DebugUtils.log(routeName);
                    values.put(RoutesTable.COLUMN_ROUTE_NAME, routeName);
                    values.put(RoutesTable.COLUMN_WAYPOINT_ID, waypointIDList.item(k).getTextContent());
                    getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_ROUTES, values);
                }
            }
        }
    }

    private Document loadFile() throws IOException, ParserConfigurationException, SAXException{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(getContext().getAssets().open(Routes.FILENAME));
        Document doc = docBuilder.parse(inputSource);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static class Routes {

        private static final String FILENAME = "routes.xml";
        private static final String TAG_ROUTE = "route";
        private static final String TAG_NAME = "name";
        private static final String TAG_WAYPOINT_ID = "waypoint-id";
    }
}