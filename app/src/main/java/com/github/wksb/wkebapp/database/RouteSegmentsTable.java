package com.github.wksb.wkebapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "route-segments" in the Database {@link WeltkulturerbeDatabaseHelper} which contains
 * different route-segments. Each route-segment contains a unique route-segment-id, the id of its start-waypoint, the id of its end-waypoint
 * and the filename of the KML-File containing the route-segment.
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-10-18
 */
public final class RouteSegmentsTable {

    /** Name of the table in the database **/
    public static final String TABLE_ROUTE_SEGMENTS = "route_segments";

    /** Name of the Column containing the ID (index) of each Row **/
    public static final String COLUMN_ID = "_id";
    /** Name of the Column containing the ID of each Route-Segment **/
    public static final String COLUMN_SEGMENT_ID = "segment_id";
    /** Name of the Column containing the ID of each start-waypoint **/
    public static final String COLUMN_START_WAYPOINT_ID = "start_waypoint_id";
    /** Name of the Column containing the ID of each end-waypoint **/
    public static final String COLUMN_END_WAYPOINT_ID = "end_waypoint_id";
    /** Name of the Column containing the filename of each KML-File **/
    public static final String COLUMN_KML_FILENAME = "kml_filename";

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private RouteSegmentsTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_ROUTE_SEGMENTS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SEGMENT_ID + " INTEGER NOT NULL,"
            + COLUMN_START_WAYPOINT_ID + " INTEGER NOT NULL,"
            + COLUMN_END_WAYPOINT_ID + " INTEGER NOT NULL,"
            + COLUMN_KML_FILENAME + " TEXT NOT NULL"
            + ");";

    /**
     * Add this Table to a SQLite Database
     * @param database {@link SQLiteDatabase} The Database you want to add this Table to
     */
    public static void addToDatabase(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}
