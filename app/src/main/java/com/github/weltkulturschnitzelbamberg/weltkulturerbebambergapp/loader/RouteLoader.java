package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.loader;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.LongRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.ShortRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WaypointsTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.xml.XmlLoader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * This Loader loads the waypoints and routes into the {@link com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WeltkulturerbeDatabaseHelper}
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class RouteLoader extends AsyncTaskLoader {

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
            loadWaypointsInDatabase();
            loadRoutesInDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadWaypointsInDatabase() throws XmlPullParserException, IOException {
        XmlLoader xmlLoader = new XmlLoader().setParentTag("waypoint").setSearchedTags("id", "name", "longitude", "latitude")
                .load(getContext(), "waypoints.xml");
        for (List<String[]> waypointTag : xmlLoader.getResultsByParentTag()) {
            ContentValues values = new ContentValues();
            for (String[] childTag : waypointTag) {
                switch (childTag[XmlLoader.INDEX_TAG_NAME]) {
                    case "id":
                        values.put(WaypointsTable.COLUMN_WAYPOINT_ID, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "name":
                        values.put(WaypointsTable.COLUMN_NAME, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "longitude":
                        values.put(WaypointsTable.COLUMN_LONGITUDE, Float.parseFloat(childTag[XmlLoader.INDEX_TEXT]));
                        break;
                    case "latitude":
                        values.put(WaypointsTable.COLUMN_LATITUDE, Float.parseFloat(childTag[XmlLoader.INDEX_TEXT]));
                        break;
                }
            }
            getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS, values);
        }
    }

    private void loadRoutesInDatabase() throws XmlPullParserException, IOException{
        XmlLoader xmlLoader = new XmlLoader().setParentTag("route").setSearchedTags("name", "waypoint-id").load(getContext(), "routes.xml");
        for (List<String[]> routeTag : xmlLoader.getResultsByParentTag()) {
            String routeName = "";
            for (String[] childTag : routeTag) {
                switch (childTag[XmlLoader.INDEX_TAG_NAME]) {
                    case "name":
                        routeName = childTag[XmlLoader.INDEX_TEXT];
                        break;
                    case "waypoint-id":
                        ContentValues values = new ContentValues();
                        switch (routeName) {
                            case "Long Route":
                                values.put(LongRouteTable.COLUMN_WAYPOINT_ID, Integer.parseInt(childTag[XmlLoader.INDEX_TEXT]));
                                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_LONG_ROUTE, values);
                                break;
                            case "Short Route":
                                values.put(ShortRouteTable.COLUMN_WAYPOINT_ID, Integer.parseInt(childTag[XmlLoader.INDEX_TEXT]));
                                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_SHORT_ROUTE, values);
                                break;
                        }
                        break;
                }
            }
        }
    }
}