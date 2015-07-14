package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

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
public class RouteAsyncTaskLoader extends AsyncTaskLoader {

    public static final int LOADER_ID = 1;

    public RouteAsyncTaskLoader(Context context) {
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

    // TODO Documentation
    private void writeRoutesToDatabase() throws IOException, ParserConfigurationException, SAXException {
        Document doc = loadFile();

        // Get a List of all Routes within the XML Document 'doc'
        NodeList routes = doc.getElementsByTagName(Routes.TAG_ROUTE);
        for (int i = 0; i < routes.getLength(); i++) {
            // If t?he Route in position 'i' in the List of all Routes 'routes' is a Element Node, continue
            if (routes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                // 'route' is the Route in position 'i' in the List of all Routes 'routes'
                Element route = (Element) routes.item(i);

                // Name of the current Route
                String routeName = null;
                // If the current Route has a "Name" Tag, 'routeName' is set to its value
                if (route.getElementsByTagName(Routes.TAG_NAME).getLength() > 0) routeName = route.getElementsByTagName(Routes.TAG_NAME).item(0).getTextContent();

                // Get a List of all Waypoints within the current Route 'route'
                NodeList waypoints = route.getElementsByTagName(Routes.TAG_WAYPOINT);
                for (int k = 0; k < waypoints.getLength(); k++) {
                    // If the Waypoint in position 'k' in the List of all Waypoints 'waypoints' within the current Route 'route' is a Element Node, continue
                    if (waypoints.item(k).getNodeType() == Node.ELEMENT_NODE) {
                        // 'waypoint' is the Waypoint in position 'k' in the List of all Waypoints 'waypoints' within the current Route 'route'
                        Element waypoint = (Element) waypoints.item(k);

                        // Position of the current Waypoint in the current Route
                        String position = null;
                        // ID of the Waypoint in this position
                        String waypointID = null;

                        // If the current Waypoint has a "Position" Tag, 'position' is set to its value
                        if (waypoint.getElementsByTagName(Routes.TAG_POSITION).getLength() > 0)
                            position = waypoint.getElementsByTagName(Routes.TAG_POSITION).item(0).getTextContent();
                        // If the current Waypoint has a "WaypointID" Tag, 'position' is set to its value
                        if (waypoint.getElementsByTagName(Routes.TAG_WAYPOINT_ID).getLength() >0)
                            waypointID = waypoint.getElementsByTagName(Routes.TAG_WAYPOINT_ID).item(0).getTextContent();

                        // Content values containing the individual values of the current waypoint
                        ContentValues values = new ContentValues();
                        // 'routeName' will be inserted into the "RouteName" Column
                        values.put(RoutesTable.COLUMN_ROUTE_NAME, routeName);
                        // 'position' will be inserted into the "WaypointPosition" Column
                        values.put(RoutesTable.COLUMN_WAYPOINT_POSITION, position);
                        // 'waypointID' will be inserted into the "WaypointID" Column
                        values.put(RoutesTable.COLUMN_WAYPOINT_ID, waypointID);

                        // Instert new Waypoint into the Routes Table in the SQL Database
                        getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_ROUTES, values);
                    }
                }
            }
        }
    }

    // TODO Documentation
    private Document loadFile() throws IOException, ParserConfigurationException, SAXException{
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(getContext().getAssets().open(Routes.FILENAME));
        Document doc = docBuilder.parse(inputSource);
        doc.getDocumentElement().normalize();
        return doc;
    }

    //TODO Documentation
    private static class Routes {

        private static final String FILENAME = "routes.xml";
        private static final String TAG_ROUTE = "route";
        private static final String TAG_NAME = "name";
        private static final String TAG_WAYPOINT = "waypoint";
        private static final String TAG_POSITION = "position";
        private static final String TAG_WAYPOINT_ID = "id";
    }
}