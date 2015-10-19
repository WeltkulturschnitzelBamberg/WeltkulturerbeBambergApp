package com.github.wksb.wkebapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "routes" in the Database {@link WeltkulturerbeDatabaseHelper} which
 * contains different routes with multiple waypoints. Each row of the table has a column with the ID
 * of a Waypoint, the name of a Route that Waypoint is a part of and the Waypoints position (order/sequence)
 * in that Route. The Waypoint corresponding to the Waypoint ID in each Row is saved inside the
 * {@link WaypointsTable} with the same ID in its Column {@link WaypointsTable#COLUMN_WAYPOINT_ID}
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class RoutesTable {

    /** Name of the table in the database **/
    public static final String TABLE_ROUTES = "routes";

    /** Name of the Column containing the ID (index) of each Row **/
    public static final String COLUMN_ID = "_id";
    /** Name of the Column containing the name of the Route **/
    public static final String COLUMN_ROUTE_NAME = "route_name";
    /** Name of the Column containing the ID of the Route-Segment **/
    public static final String COLUMN_ROUTE_SEGMENT_ID = "route_segment_id";
    /** Name of the Column containing the position of the Route-Segment in the Route **/
    public static final String COLUMN_ROUTE_SEGMENT_POSITION = "route_segment_position";

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private RoutesTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_ROUTES
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ROUTE_NAME + " TEXT NOT NULL,"
            + COLUMN_ROUTE_SEGMENT_POSITION + " INTEGER NOT NULL,"
            + COLUMN_ROUTE_SEGMENT_ID + " INTEGER NOT NULL"
            + ");";

    /**
     * Add this Table to a SQLite Database
     * @param database {@link SQLiteDatabase} The Database you want to add this Table to
     */
    public static void addToDatabase(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}