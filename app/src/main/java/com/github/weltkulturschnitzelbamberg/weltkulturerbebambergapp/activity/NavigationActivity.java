package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.RoutesTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.WaypointsTable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows a GoogleMaps map on which a route between to waypoints is shown.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */

public class NavigationActivity extends FragmentActivity implements AppCompatCallback {

    private AppCompatDelegate mDelegate;

    private GoogleMap mMap;

    private static final LatLng BAMBERG = new LatLng(49.898814, 10.890764);

    // This Object contains the current Route with all its Waypoints
    private Route mRoute;

    // Definition of the Tags used in Intents send to this Activity
    public static final String TAG_PACKAGE = NavigationActivity.class.getPackage().getName();
    /** This TAG tags the CODE for the Route which is to be loaded within an Intent send to this Activity. Use {@link NavigationActivity#FLAG_ROUTE_CODE_ERROR} to indicate an error occurred or the route doesn't exist */
    public static final String TAG_ROUTE_CODE = TAG_PACKAGE + "route_code";
    /** FLAG for the Route code within an Intent send to this Activity, tagged with {@link NavigationActivity#TAG_ROUTE_CODE}, which indicates the Route doesn't exist*/
    public static final int FLAG_ROUTE_CODE_ERROR = -1;
    /** FLAG for the Route code within an Intent send to this Activity, tagged with {@link NavigationActivity#TAG_ROUTE_CODE}, which indicates the short Route is to be loaded*/
    public static final int FLAG_ROUTE_CODE_SHORT = 0;
    /** FLAG for the Route code within an Intent send to this Activity, tagged with {@link NavigationActivity#TAG_ROUTE_CODE}, which indicates the long Route is to be loaded*/
    public static final int FLAG_ROUTE_CODE_LONG = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO cohesion
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        mDelegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        mDelegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        mDelegate.setContentView(R.layout.activity_navigation);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.navigation_toolbar);
        mDelegate.setSupportActionBar(toolbar);
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

    //TODO Dont animate camera on onResume()
    /**
     * This function sets up the GoogleMaps v2 Map
     */
    private void setUpMap() {
        // Move Camera to Bamberg
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BAMBERG, 12f));
        mMap.setMyLocationEnabled(true);
        loadRoute(getIntent().getIntExtra(TAG_ROUTE_CODE, FLAG_ROUTE_CODE_ERROR));
        addMarkers();
        addProximityAlerts();
    }

    //TODO Documentation
    private void loadRoute(int routeCode) {
        String[] projection = {RoutesTable.COLUMN_WAYPOINT_ID};
        String selection = RoutesTable.COLUMN_ROUTE_NAME + "=?";
        String[] selectionArgs;
        switch (routeCode) {
            case FLAG_ROUTE_CODE_SHORT:
                selectionArgs = new String[]{"Short Route"};
                mRoute = new Route("Short Route");
                break;
            case FLAG_ROUTE_CODE_LONG:
                selectionArgs = new String[]{"Long Route"};
                mRoute = new Route("Long Route");
                break;
            default:
                throw new IllegalArgumentException("No such Route found. Route Code: " + routeCode);
        }

        Cursor waypointIDs = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_ROUTES,
                projection, selection, selectionArgs, null);
        while (waypointIDs.moveToNext()) {
            String[] projection2 = new String[]{WaypointsTable.COLUMN_NAME, WaypointsTable.COLUMN_LONGITUDE, WaypointsTable.COLUMN_LATITUDE};
            String selection2 = WaypointsTable.COLUMN_WAYPOINT_ID + "=?";
            String[] selectionArgs2 = {Integer.toString(waypointIDs.getInt(waypointIDs.getColumnIndex(RoutesTable.COLUMN_WAYPOINT_ID)))};
            Cursor waypoint = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS,
                    projection2, selection2, selectionArgs2, null);
            while (waypoint.moveToNext()) {
                Float latitude = waypoint.getFloat(waypoint.getColumnIndex(WaypointsTable.COLUMN_LATITUDE));
                Float longitude = waypoint.getFloat(waypoint.getColumnIndex(WaypointsTable.COLUMN_LONGITUDE));
                String title = waypoint.getString(waypoint.getColumnIndex(WaypointsTable.COLUMN_NAME));
                mRoute.addWaypoint(latitude, longitude, title);
            }
        }
    }

    // TODO Documentation
    private void addMarkers() {
        for (Route.Waypoint waypoint : mRoute.getWaypoints()) {
            MarkerOptions marker = new MarkerOptions()
                    .title(waypoint.getTitle())
                    .position(new LatLng(waypoint.getLatitude(), waypoint.getLongitude()));
            mMap.addMarker(marker);
        }
    }

    private void addProximityAlerts() {
        LocationManager locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        for (Route.Waypoint waypoint : mRoute.getWaypoints()) {
            Intent intent = new Intent();
            intent.setAction("com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.PROXIMITY_ALERT");
            intent.putExtra("name", waypoint.getTitle());
            intent.putExtra("lat", waypoint.getLatitude());
            intent.putExtra("lng", waypoint.getLongitude());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            locManager.addProximityAlert(waypoint.getLatitude(), waypoint.getLongitude(), 40, -1, pendingIntent);
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

    private class Route {

        private final String name;
        private final List<Waypoint> waypoints;

        public Route(String name) {
            this.name = name;
            this.waypoints = new ArrayList<>();
        }

        public void addWaypoint(Float latitude, Float longitude, String title) {
            waypoints.add(new Waypoint(latitude, longitude, title));
        }

        public List<Waypoint> getWaypoints() {
            return this.waypoints;
        }

        private class Waypoint {

            private final float latitude;
            private final float longitude;
            private final String title;

            public Waypoint(Float latitude, Float longitude, String title) {
                this.latitude = latitude;
                this.longitude = longitude;
                this.title = title;
            }

            public Float getLatitude() {
                return latitude;
            }

            public Float getLongitude() {
                return longitude;
            }

            public String getTitle() {
                return title;
            }
        }
    }
}