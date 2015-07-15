package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "informations" in the Database {@link WeltkulturerbeDatabaseHelper}.
 * In this Table all information about the waypoints are saved
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InformationTable {

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private InformationTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** Name of the table in the database **/
    public static final String TABLE_INFORMATION = "information";

    /** Names of the columns inside the table **/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INFORMATION_ID = "information_id";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_INFO_TEXT = "info_text";

    /** SQL command to create the table **/
    public static final String SQL_CREATE = "CREATE TABLE " + TABLE_INFORMATION
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_INFORMATION_ID + " INTEGER NOT NULL,"
            + COLUMN_IMAGE + " INTEGER,"
            + COLUMN_INFO_TEXT + " TEXT"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }

    public static void onUpgrade() {
        //TODO onUpgrade() schreiben
    }
}