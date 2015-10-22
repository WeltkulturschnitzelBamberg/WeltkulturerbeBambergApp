package com.github.wksb.wkebapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.RouteSegmentsTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This AsyncTaskLoader loads the route-segments into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-10-18
 */
public class RouteSegmentsAsyncTaskLoader extends AsyncTaskLoader{

    public RouteSegmentsAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeRouteSegmentsToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeRouteSegmentsToDatabase() throws IOException, ParseException{
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(getContext().getAssets().open("route-segments.json"), "UTF-8"));

        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray routeSegments = (JSONArray) jsonObject.get("route-segments");
            for (int i=0; i < routeSegments.size(); i++) {
                JSONObject routeSegment = (JSONObject) routeSegments.get(i);

                Long segmentID = -1L;
                Long startWaypointID = -1L;
                Long endWaypointID = -1L;
                String kmlFilename = null;

                if (routeSegment.get("segment-id") instanceof Long) segmentID = (long) routeSegment.get("segment-id");
                if (routeSegment.get("start-waypoint-id") instanceof Long) startWaypointID = (long) routeSegment.get("start-waypoint-id");
                if (routeSegment.get("end-waypoint-id") instanceof Long) endWaypointID = (long) routeSegment.get("end-waypoint-id");
                if (routeSegment.get("kml-filename") instanceof String) kmlFilename = (String) routeSegment.get("kml-filename");

                ContentValues values = new ContentValues();
                values.put(RouteSegmentsTable.COLUMN_SEGMENT_ID, segmentID);
                values.put(RouteSegmentsTable.COLUMN_START_WAYPOINT_ID, startWaypointID);
                values.put(RouteSegmentsTable.COLUMN_END_WAYPOINT_ID, endWaypointID);
                values.put(RouteSegmentsTable.COLUMN_KML_FILENAME, kmlFilename);

                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_ROUTE_SEGMENTS, values);
            }
        }
    }
}
