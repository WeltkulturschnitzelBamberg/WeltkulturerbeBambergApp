package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.LongRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.ShortRouteTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WaypointsTable;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * This activity shows a GoogleMaps map on which a route between to waypoints is shown.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */

public class NavigationActivity extends FragmentActivity implements AppCompatCallback {

    private AppCompatDelegate delegate;

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    public static final String ROUTE_CODE = "route_code";
    public static final int CODE_ROUTE_ERROR = -1;
    public static final int CODE_ROUTE_SHORT = 0;
    public static final int CODE_ROUTE_LONG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_navigation);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_toolbar);
        delegate.setSupportActionBar(toolbar);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        loadRoute(getIntent().getIntExtra(ROUTE_CODE, CODE_ROUTE_ERROR));
    }

    private void loadRoute(int routeCode) {
        String[] projection;
        Uri tableUri;
        switch (routeCode) {
            case CODE_ROUTE_SHORT:
                projection = new String[]{ShortRouteTable.COLUMN_WAYPOINT_ID};
                tableUri = WeltkulturerbeContentProvider.URI_TABLE_SHORT_ROUTE;
                break;
            case CODE_ROUTE_LONG:
                projection = new String[]{LongRouteTable.COLUMN_WAYPOINT_ID};
                tableUri = WeltkulturerbeContentProvider.URI_TABLE_LONG_ROUTE;
                break;
            default:
                throw new IllegalArgumentException("No such Route found. Route Code: " + routeCode);
        }

        Cursor waypointIDs = getContentResolver().query(tableUri, projection, null, null, null);
        while (waypointIDs.moveToNext()) {
            projection = new String[]{WaypointsTable.COLUMN_NAME, WaypointsTable.COLUMN_LONGITUDE, WaypointsTable.COLUMN_LATITUDE};
            String selection = WaypointsTable.COLUMN_WAYPOINT_ID + "=?";
            String[] selectionArgs = {Integer.toString(waypointIDs.getInt(waypointIDs.getColumnIndex(ShortRouteTable.COLUMN_WAYPOINT_ID)))};
            Cursor waypoint = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS,
                    projection, selection, selectionArgs, null);
            while (waypoint.moveToNext()) {
                MarkerOptions marker = new MarkerOptions()
                        .title(waypoint.getString(waypoint.getColumnIndex(WaypointsTable.COLUMN_NAME)))
                        .position(new LatLng(waypoint.getFloat(waypoint.getColumnIndex(WaypointsTable.COLUMN_LATITUDE)),
                                waypoint.getFloat(waypoint.getColumnIndex(WaypointsTable.COLUMN_LONGITUDE))));
                mMap.addMarker(marker);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_score:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(NavigationActivity.this, ScoreActivity.class));
                return true;
            case R.id.action_start:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(NavigationActivity.this, WelcomePageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        //let's leave this empty, for now
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        // let's leave this empty, for now
    }
}
