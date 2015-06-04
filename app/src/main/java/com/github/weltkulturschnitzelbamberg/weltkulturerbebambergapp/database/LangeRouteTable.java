package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Diese Klasse beschreibt den Table 'LangeRouteTable' für die Database
 * {@link WeltkulturerbeDatabaseHelper}
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class LangeRouteTable {

    /** Privater Konstruktor um eine Instantiierung zu verhindern. Falls diese Klasse
     * instantiiert wird wirft sie eine {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private LangeRouteTable() throws IllegalAccessException {
        throw new IllegalAccessException("'LangeRouteTable' should not be instantiated");
    }

    /** Name des Tables in der Datenbank **/
    private static final String TABLE_LANGE_ROUTE = "lange_route";

    /** Namen der Zeilen im Table**/
    private static final String COLUMN_WAYPOINT_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LATITUDE = "latitude";

    /** SQL Erzeugungs-Befehl für den Table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_LANGE_ROUTE
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