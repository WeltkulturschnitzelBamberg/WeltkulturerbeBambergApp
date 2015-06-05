package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.Utilities.DebugUtils;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.ShortRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.LongRouteTable;
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

    private static final Uri URI_TABLE_LONG_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + LongRouteTable.TABLE_LONG_ROUTE);
    private static final int CODE_TABLE_LONG_ROUTE = 1;
    private static final int CODE_TABLE_LONG_ROUTE_ROW = 2;

    private static final Uri URI_TABLE_SHORT_ROUTE = Uri.parse("content://" + AUTHORITY + "/" + ShortRouteTable.TABLE_SHORT_ROUTE);
    private static final int CODE_TABLE_SHORT_ROUTE = 3;
    private static final int CODE_TABLE_SHORT_ROUTE_ROW = 4;

    /** UriMatcher that returns NO_MATCH if it matches the root URI **/
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        /** Add URI for the table "long_route" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, LongRouteTable.TABLE_LONG_ROUTE, CODE_TABLE_LONG_ROUTE);
        sUriMatcher.addURI(AUTHORITY, LongRouteTable.TABLE_LONG_ROUTE + "/#", CODE_TABLE_LONG_ROUTE_ROW);

        /** Add URI for the table "short_route" and for its rows **/
        sUriMatcher.addURI(AUTHORITY, ShortRouteTable.TABLE_SHORT_ROUTE, CODE_TABLE_SHORT_ROUTE);
        sUriMatcher.addURI(AUTHORITY, ShortRouteTable.TABLE_SHORT_ROUTE + "/#", CODE_TABLE_SHORT_ROUTE_ROW);
    }

    @Override
    public boolean onCreate() {
        sDatabaseHelper = new WeltkulturerbeDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(LongRouteTable.TABLE_LONG_ROUTE);
        //TODO support more tables

        int uriType = sUriMatcher.match(uri);
        switch (uriType) {
            case CODE_TABLE_LONG_ROUTE:
                break;
            case CODE_TABLE_LONG_ROUTE_ROW:
                queryBuilder.appendWhere(LongRouteTable.COLUMN_WAYPOINT_ID + "=" + uri.getLastPathSegment());
                break;
            case CODE_TABLE_SHORT_ROUTE:
                break;
            case CODE_TABLE_SHORT_ROUTE_ROW:
                queryBuilder.appendWhere(ShortRouteTable.COLUMN_WAYPOINT_ID + "=" + uri.getLastPathSegment());
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
        long rowID = 0;
        switch (uriType) {
            case CODE_TABLE_LONG_ROUTE:
                rowID = database.insert(LongRouteTable.TABLE_LONG_ROUTE, null, values);
                break;
            case CODE_TABLE_SHORT_ROUTE:
                rowID = database.insert(ShortRouteTable.TABLE_SHORT_ROUTE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        DebugUtils.toast(getContext(), "Neuen Wegpunkt hinzugefuegt. ID: " + rowID);
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
