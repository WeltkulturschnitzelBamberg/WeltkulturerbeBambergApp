package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.QuizzesTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.ShortRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.LongRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WaypointsTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WeltkulturerbeDatabaseHelper;

/**
 * This class provides access to the "Weltkulturerbe" database
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WeltkulturerbeContentProvider extends ContentProvider {

    // Weltkulturerbe Datenbank die vom ContentProvider erreichbar gemacht wird
    private static WeltkulturerbeDatabaseHelper sDatabaseHelper;

    private static final String AUTHORITY = "com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider";

    public static final Uri URI_TABLE_LONG_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + LongRouteTable.TABLE_LONG_ROUTE);
    private static final int CODE_TABLE_LONG_ROUTE = 1;
    private static final int CODE_TABLE_LONG_ROUTE_ROW = 2;

    public static final Uri URI_TABLE_SHORT_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + ShortRouteTable.TABLE_SHORT_ROUTE);
    private static final int CODE_TABLE_SHORT_ROUTE = 3;
    private static final int CODE_TABLE_SHORT_ROUTE_ROW = 4;

    public static final Uri URI_TABLE_WAYPOINTS = Uri.parse("content://" + AUTHORITY + "/" + WaypointsTable.TABLE_WAYPOINTS);
    private static final int CODE_TABLE_WAYPOINTS = 5;
    private static final int CODE_TABLE_WAYPOINTS_ROW = 6;

    public static final Uri URI_TABLE_QUIZZES = Uri.parse("content://" + AUTHORITY + "/" + QuizzesTable.TABLE_QUIZZES);
    private static final int CODE_TABLE_QUIZZES = 7;
    private static final int CODE_TABLE_QUIZZES_ROW = 8;

    /** UriMatcher that returns NO_MATCH if it matches the root URI **/
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        /** Add URI for the table "long_route" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, LongRouteTable.TABLE_LONG_ROUTE, CODE_TABLE_LONG_ROUTE);
        sUriMatcher.addURI(AUTHORITY, LongRouteTable.TABLE_LONG_ROUTE + "/#", CODE_TABLE_LONG_ROUTE_ROW);

        /** Add URI for the table "short_route" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, ShortRouteTable.TABLE_SHORT_ROUTE, CODE_TABLE_SHORT_ROUTE);
        sUriMatcher.addURI(AUTHORITY, ShortRouteTable.TABLE_SHORT_ROUTE + "/#", CODE_TABLE_SHORT_ROUTE_ROW);

        /** Add URI for the table "waypoints" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, WaypointsTable.TABLE_WAYPOINTS, CODE_TABLE_WAYPOINTS);
        sUriMatcher.addURI(AUTHORITY, WaypointsTable.TABLE_WAYPOINTS + "/#", CODE_TABLE_WAYPOINTS_ROW);

        /** Add URI for the table "quizzes.xml" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, QuizzesTable.TABLE_QUIZZES, CODE_TABLE_QUIZZES);
        sUriMatcher.addURI(AUTHORITY, QuizzesTable.TABLE_QUIZZES + "/#", CODE_TABLE_QUIZZES_ROW);
    }

    @Override
    public boolean onCreate() {
        sDatabaseHelper = new WeltkulturerbeDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        //TODO support more tables

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case CODE_TABLE_LONG_ROUTE:
                queryBuilder.setTables(LongRouteTable.TABLE_LONG_ROUTE);
                break;
            case CODE_TABLE_LONG_ROUTE_ROW:
                queryBuilder.setTables(LongRouteTable.TABLE_LONG_ROUTE);
                queryBuilder.appendWhere(LongRouteTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_TABLE_SHORT_ROUTE:
                queryBuilder.setTables(ShortRouteTable.TABLE_SHORT_ROUTE);
                break;
            case CODE_TABLE_SHORT_ROUTE_ROW:
                queryBuilder.setTables(ShortRouteTable.TABLE_SHORT_ROUTE);
                queryBuilder.appendWhere(ShortRouteTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_TABLE_WAYPOINTS:
                queryBuilder.setTables(WaypointsTable.TABLE_WAYPOINTS);
                break;
            case CODE_TABLE_WAYPOINTS_ROW:
                queryBuilder.setTables(WaypointsTable.TABLE_WAYPOINTS);
                queryBuilder.appendWhere(WaypointsTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_TABLE_QUIZZES:
                queryBuilder.setTables(QuizzesTable.TABLE_QUIZZES);
                break;
            case CODE_TABLE_QUIZZES_ROW:
                queryBuilder.setTables(QuizzesTable.TABLE_QUIZZES);
                queryBuilder.appendWhere(QuizzesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unkown URI: " + uri);
        }

        SQLiteDatabase database = sDatabaseHelper.getWritableDatabase();
        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase database = sDatabaseHelper.getWritableDatabase();
        long rowID;
        switch (uriType) {
            case CODE_TABLE_LONG_ROUTE:
                rowID = database.insert(LongRouteTable.TABLE_LONG_ROUTE, null, values);
                break;
            case CODE_TABLE_SHORT_ROUTE:
                rowID = database.insert(ShortRouteTable.TABLE_SHORT_ROUTE, null, values);
                break;
            case CODE_TABLE_WAYPOINTS:
                rowID = database.insert(WaypointsTable.TABLE_WAYPOINTS, null, values);
                break;
            case CODE_TABLE_QUIZZES:
                rowID = database.insert(QuizzesTable.TABLE_QUIZZES, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(AUTHORITY + "/" + rowID);
    }

    /** Functions delete(), update() and getType() aren't needed in this project **/
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }
}
