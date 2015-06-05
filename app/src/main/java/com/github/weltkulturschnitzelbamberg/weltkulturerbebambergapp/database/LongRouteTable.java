package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "long_route" in the Database
 * {@link WeltkulturerbeDatabaseHelper}
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class LongRouteTable {

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private LongRouteTable() throws IllegalAccessException {
        throw new IllegalAccessException("'LongRouteTable' should not be instantiated");
    }

    /** Name of the table in the database **/
    public static final String TABLE_LONG_ROUTE = "lange_route";

    /** Names of the columns inside the table **/
    private static final String COLUMN_WAYPOINT_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_LONG_ROUTE
            + "("
            + COLUMN_WAYPOINT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT NOT NULL,"
            + COLUMN_LONGITUDE + " REAL NOT NULL,"
            + COLUMN_LATITUDE + " REAL NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }

    public static void onUpgrade() {
        //TODO onUpgrade() schreiben
    }
}