package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "information" in the Database {@link WeltkulturerbeDatabaseHelper} which
 * contains different Information about different waypoints. Each Information has a unique ID, a reference
 * to an Image and a text providing information about a waypoint.
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class InformationTable {

    /** Name of the table in the database **/
    public static final String TABLE_INFORMATION = "information";

    /** Name of the Column containing the ID (index) of each Row **/
    public static final String COLUMN_ID = "_id";
    /** Name of the Column containing the ID of each Information **/
    public static final String COLUMN_INFORMATION_ID = "information_id";
    /** Name of the Column containing the Image of each Information **/ //TODO How are images referenced/saved?
    public static final String COLUMN_IMAGE = "image";
    /** Name of the Column containing the Text of each Information **/
    public static final String COLUMN_INFO_TEXT = "info_text";

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private InformationTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** SQL command to create the table **/
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_INFORMATION
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_INFORMATION_ID + " INTEGER NOT NULL,"
            + COLUMN_IMAGE + " INTEGER,"
            + COLUMN_INFO_TEXT + " TEXT"
            + ");";

    public static void addToDatabase(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}