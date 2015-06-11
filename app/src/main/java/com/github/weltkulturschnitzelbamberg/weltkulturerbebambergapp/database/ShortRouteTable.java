package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "short_route" in the Database
 * {@link WeltkulturerbeDatabaseHelper}
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class ShortRouteTable {

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private ShortRouteTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + ShortRouteTable.class.getName() + "' should not be instantiated");
    }

    /** Name of the table in the database **/
    public static final String TABLE_SHORT_ROUTE = "kurze_route";

    /** Namens of the columns inside the table**/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WAYPOINT_ID = "waypoint_id";

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_SHORT_ROUTE
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_WAYPOINT_ID + " INTEGER NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }

    public static void onUpgrade() {
        //TODO onUpgrade() schreiben
    }
}