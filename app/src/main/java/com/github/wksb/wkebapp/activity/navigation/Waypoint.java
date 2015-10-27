package com.github.wksb.wkebapp.activity.navigation;

import android.app.Activity;
import android.database.Cursor;

import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.WaypointsTable;

/**
 * Created by Michael on 27.10.2015.
 */
public class Waypoint {

    private float latitude;
    private float longitude;
    private String name;
    private int quizID;

    public Waypoint(Activity activity, int waypointID) {
        // The parameter for the SQLite query
        String[] projection = {WaypointsTable.COLUMN_LATITUDE, WaypointsTable.COLUMN_LONGITUDE, WaypointsTable.COLUMN_NAME, WaypointsTable.COLUMN_QUIZ_ID};
        String selection = WaypointsTable.COLUMN_WAYPOINT_ID + "=?";
        String[] selectionArgs = {""+waypointID};

        Cursor cursor = activity.getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS, projection, selection, selectionArgs, null);
        if (cursor.moveToNext()) {
            latitude = cursor.getFloat(cursor.getColumnIndex(WaypointsTable.COLUMN_LATITUDE));
            longitude = cursor.getFloat(cursor.getColumnIndex(WaypointsTable.COLUMN_LONGITUDE));
            name = cursor.getString(cursor.getColumnIndex(WaypointsTable.COLUMN_NAME));
            quizID = cursor.getInt(cursor.getColumnIndex(WaypointsTable.COLUMN_QUIZ_ID));
        }
    }

    /**
     * Get the Name of this Wapoint
     * @return The Name of this Waypoint
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Latitude of this Waypoint
     * @return The Latitude of this Waypoint
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Get the Longitude of this Waypoint
     * @return The Waypoint of this Waypoint
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Get the ID of the Quiz about this Waypoint
     * @return The ID of the Quiz about this Waypoint
     */
    public int getQuizID() {
        return quizID;
    }
}
