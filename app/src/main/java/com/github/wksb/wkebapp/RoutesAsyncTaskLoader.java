package com.github.wksb.wkebapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.RoutesTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This AsyncTaskLoader loads the routes into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class RoutesAsyncTaskLoader extends AsyncTaskLoader {

    public RoutesAsyncTaskLoader(Context context) {
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
    private void writeRoutesToDatabase() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(getContext().getAssets().open("routes.json"), "UTF-8"));

        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray routes = (JSONArray) jsonObject.get("routes");
            for (int i = 0; i < routes.size(); i++) {
                JSONObject route = (JSONObject) routes.get(i);

                String name = null;
                if (route.get("name") instanceof String) name = (String) route.get("name");

                JSONArray waypoints = (JSONArray) route.get("waypoints");
                for (int k = 0; k < waypoints.size(); k++) {
                    JSONObject waypoint = (JSONObject) waypoints.get(k);

                    Long waypointId = 0L;
                    Long position = 0L;

                    if (waypoint.get("id") instanceof Long) waypointId = (long) waypoint.get("id");
                    if (waypoint.get("position") instanceof Long) position = (long) waypoint.get("position");

                    ContentValues values = new ContentValues();
                    values.put(RoutesTable.COLUMN_ROUTE_NAME, name);
                    values.put(RoutesTable.COLUMN_WAYPOINT_ID, waypointId);
                    values.put(RoutesTable.COLUMN_WAYPOINT_POSITION, position);

                    getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_ROUTES, values);
                }
            }
        }
    }
}