package com.github.wksb.wkebapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.github.wksb.wkebapp.database.InformationTable;
import com.github.wksb.wkebapp.database.QuizzesTable;
import com.github.wksb.wkebapp.database.RoutesTable;
import com.github.wksb.wkebapp.database.WaypointsTable;
import com.github.wksb.wkebapp.database.WeltkulturerbeDatabaseHelper;

/**
 * This class provides access to the "Weltkulturerbe" database
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WeltkulturerbeContentProvider extends ContentProvider {

    // Weltkulturerbe Datenbank die vom ContentProvider erreichbar gemacht wird
    private static WeltkulturerbeDatabaseHelper sDatabaseHelper;

    private static final String AUTHORITY = WeltkulturerbeContentProvider.class.getName();

    public static final Uri URI_TABLE_WAYPOINTS = Uri.parse("content://" + AUTHORITY + "/" + WaypointsTable.TABLE_WAYPOINTS);
    private static final int CODE_TABLE_WAYPOINTS = 5;
    private static final int CODE_TABLE_WAYPOINTS_ROW = 6;

    public static final Uri URI_TABLE_QUIZZES = Uri.parse("content://" + AUTHORITY + "/" + QuizzesTable.TABLE_QUIZZES);
    private static final int CODE_TABLE_QUIZZES = 7;
    private static final int CODE_TABLE_QUIZZES_ROW = 8;

    public static final Uri URI_TABLE_ROUTES = Uri.parse("content://" + AUTHORITY + "/" + RoutesTable.TABLE_ROUTES);
    private static final int CODE_TABLE_ROUTES = 9;
    private static final int CODE_TABLE_ROUTES_ROW = 10;

    public static final Uri URI_TABLE_INFORMATION = Uri.parse("content://" + AUTHORITY + "/" + InformationTable.TABLE_INFORMATION);
    private static final int CODE_TABLE_INFORMATION = 11;
    private static final int CODE_TABLE_INFORMATION_ROW = 12;

    // UriMatcher that returns NO_MATCH if it matches the root URI
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        // Add URI for the table "waypoints" and for its rows
        sUriMatcher.addURI(AUTHORITY, WaypointsTable.TABLE_WAYPOINTS, CODE_TABLE_WAYPOINTS);
        sUriMatcher.addURI(AUTHORITY, WaypointsTable.TABLE_WAYPOINTS + "/#", CODE_TABLE_WAYPOINTS_ROW);

        // Add URI for the table "quizzes" and for its rows
        sUriMatcher.addURI(AUTHORITY, QuizzesTable.TABLE_QUIZZES, CODE_TABLE_QUIZZES);
        sUriMatcher.addURI(AUTHORITY, QuizzesTable.TABLE_QUIZZES + "/#", CODE_TABLE_QUIZZES_ROW);

        // Add URI for the table "routes" and for its rows
        sUriMatcher.addURI(AUTHORITY, RoutesTable.TABLE_ROUTES, CODE_TABLE_ROUTES);
        sUriMatcher.addURI(AUTHORITY, RoutesTable.TABLE_ROUTES + "/#" , CODE_TABLE_ROUTES_ROW);

        // Add URI for the table "information" and for its rows
        sUriMatcher.addURI(AUTHORITY, InformationTable.TABLE_INFORMATION, CODE_TABLE_INFORMATION);
        sUriMatcher.addURI(AUTHORITY, InformationTable.TABLE_INFORMATION + "/#", CODE_TABLE_INFORMATION_ROW);
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
            case CODE_TABLE_ROUTES:
                queryBuilder.setTables(RoutesTable.TABLE_ROUTES);
                break;
            case CODE_TABLE_ROUTES_ROW:
                queryBuilder.setTables(RoutesTable.TABLE_ROUTES);
                queryBuilder.appendWhere(RoutesTable.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_TABLE_INFORMATION:
                queryBuilder.setTables(InformationTable.TABLE_INFORMATION);
                break;
            case CODE_TABLE_INFORMATION_ROW:
                queryBuilder.setTables(InformationTable.TABLE_INFORMATION);
                queryBuilder.appendWhere(InformationTable.COLUMN_ID + "=" + uri.getLastPathSegment());
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
            case CODE_TABLE_WAYPOINTS:
                rowID = database.insert(WaypointsTable.TABLE_WAYPOINTS, null, values);
                break;
            case CODE_TABLE_QUIZZES:
                rowID = database.insert(QuizzesTable.TABLE_QUIZZES, null, values);
                break;
            case CODE_TABLE_ROUTES:
                rowID = database.insert(RoutesTable.TABLE_ROUTES, null, values);
                break;
            case CODE_TABLE_INFORMATION:
                rowID = database.insert(InformationTable.TABLE_INFORMATION, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(AUTHORITY + "/" + rowID);
    }

    // Functions delete(), update() and getType() aren't needed in this project
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
