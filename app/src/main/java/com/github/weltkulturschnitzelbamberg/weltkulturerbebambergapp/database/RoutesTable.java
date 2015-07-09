package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "routes" in the Database {@link WeltkulturerbeDatabaseHelper}. In this Table all routes are saved
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class RoutesTable {

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private RoutesTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** Name of the table in the database **/
    public static final String TABLE_ROUTES = "routes";

    /** Names of the columns inside the table **/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ROUTE_NAME = "route_name";
    public static final String COLUMN_WAYPOINT_ID = "waypoint_id";

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_ROUTES
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ROUTE_NAME + " TEXT NOT NULL,"
            + COLUMN_WAYPOINT_ID + " INTEGER NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }

    public static void onUpgrade() {
        //TODO onUpgrade() schreiben
    }
}