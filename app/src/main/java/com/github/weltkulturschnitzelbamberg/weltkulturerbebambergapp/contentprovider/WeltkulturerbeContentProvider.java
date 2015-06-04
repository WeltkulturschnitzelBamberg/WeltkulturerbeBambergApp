package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WeltkulturerbeDatabaseHelper;

//TODO Documentation schreiben
/**
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WeltkulturerbeContentProvider extends ContentProvider {

    //TODO Content Provider schreiben

    // Weltkulturerbe Datenbank die vom ContentProvider erreichbar gemacht wird
    private WeltkulturerbeDatabaseHelper mDatabase;

    private static final String AUTHORITY = "com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider";


    @Override
    public boolean onCreate() {
        mDatabase = new WeltkulturerbeDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

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
