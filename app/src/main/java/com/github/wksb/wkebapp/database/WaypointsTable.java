package com.github.wksb.wkebapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "waypoints" in the Database {@link WeltkulturerbeDatabaseHelper} which
 * contains different Waypoints. Each Waypoint has a unique ID, a name, a Longitude, a Latitude and a the
 * ID of a Quiz. The corresponding Quiz is saved inside the {@link QuizzesTable} with the same ID in its
 * Column {@link QuizzesTable#COLUMN_QUIZ_ID}
 * In this Table all waypoints are saved
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class WaypointsTable {

    /** Name of the table in the database **/
    public static final String TABLE_WAYPOINTS = "waypoints";

    /** Name of the Column containing the ID (index) of each Row **/
    public static final String COLUMN_ID = "_id";
    /** Name of the Column containing the ID of each Waypoint **/
    public static final String COLUMN_WAYPOINT_ID = "waypoint_id";
    /** Name of the Column containing the name of each Waypoint **/
    public static final String COLUMN_NAME = "name";
    /** Name of the Column containing the Longitude of each Waypoint **/
    public static final String COLUMN_LONGITUDE = "longitude";
    /** Name of the Column containing the Latitude of each Waypoint **/
    public static final String COLUMN_LATITUDE = "latitude";
    /** Name of the Column containing the ID of the Quiz belonging to each Waypoint **/
    public static final String COLUMN_QUIZ_ID = "quizID";

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private WaypointsTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_WAYPOINTS
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WAYPOINT_ID + " INTEGER NOT NULL,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_LONGITUDE + " REAL NOT NULL,"
            + COLUMN_LATITUDE + " REAL NOT NULL,"
            + COLUMN_QUIZ_ID + " INTEGER"
            + ");";

    /**
     * Add this Table to a SQLite Database
     * @param database {@link SQLiteDatabase} The Database you want to add this Table to
     */
    public static void addToDatabase(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}