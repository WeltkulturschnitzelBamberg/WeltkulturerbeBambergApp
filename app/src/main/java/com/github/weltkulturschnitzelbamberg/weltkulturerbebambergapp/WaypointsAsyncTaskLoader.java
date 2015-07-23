package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WaypointsTable;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This AsyncTaskLoader loads the waypoints into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WaypointsAsyncTaskLoader extends AsyncTaskLoader{

    public static final int LOADER_ID = 0;
    private static final String PACKAGE = WaypointsAsyncTaskLoader.class.getPackage().getName();
    private static final String INT_NOT_DEFINED = "" + -1;

    public WaypointsAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeWaypointsToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeWaypointsToDatabase() throws IOException, ParseException, JSONException{
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(getContext().getAssets().open("waypoints.json"), "UTF-8"));


        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;

            // Get the array of waypoints
            JSONArray waypoints = (JSONArray) jsonObject.get("waypoints");
            for (int i = 0; i<waypoints.size(); i++) {
                JSONObject waypoint = (JSONObject) waypoints.get(i);

                long id = 0L;
                String name = null;
                long quizID = 0L;
                double latitude = 0L;
                double longitude = 0L;

                if (waypoint.get("id") instanceof Long) id = (long) waypoint.get("id");
                if (waypoint.get("name") instanceof String) name = (String) waypoint.get("name");
                if (waypoint.get("quizID") instanceof Long) quizID = (long) waypoint.get("quizID");
                if (waypoint.get("latitude") instanceof Double) latitude = (double) waypoint.get("latitude");
                if (waypoint.get("longitude") instanceof Double) longitude = (double) waypoint.get("longitude");

                ContentValues values = new ContentValues();
                values.put(WaypointsTable.COLUMN_WAYPOINT_ID, id);
                values.put(WaypointsTable.COLUMN_NAME, name);
                values.put(WaypointsTable.COLUMN_QUIZ_ID, quizID);
                values.put(WaypointsTable.COLUMN_LATITUDE, latitude);
                values.put(WaypointsTable.COLUMN_LONGITUDE, longitude);

                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS, values);
            }
        }
    }
}