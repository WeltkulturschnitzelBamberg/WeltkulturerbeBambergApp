package com.github.wksb.wkebapp.activity.navigation;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.activity.QuizActivity;
import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.RouteSegmentsTable;
import com.github.wksb.wkebapp.database.RoutesTable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

/**
 * This activity shows a GoogleMaps map on which a route between to waypoints is shown.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */

public class NavigationActivity extends AppCompatActivity {

    // Title of the ActionBar
    private TextView mActionBarTitle;

    // The Google Maps Fragment
    private GoogleMap mMap;

    // This Object contains the current Route with all its RouteSegments
    private Route mRoute;

    // Components of the Navigation Drawer
    private DrawerLayout mDrawerLayout;
    private ListView mLvWaypoints;  // ListView in the Drawer containign a List of Waypoints
    private ActionBarDrawerToggle mDrawerToggle;  // Button in ActionBar toggling the DrawerLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Set up Map and Route if they don't exist
        if (mMap == null) setUpMap();
        if (mRoute == null) setUpRoute();

        // Set up the Action Bar
        setUpActionBar();

        // Set up the Navigation Drawer
        setUpDrawer();

        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putBoolean("IS_IN_PROGRESS", true).commit(); // Set the Tour to being in progress
        mRoute.getRouteSegments().get(getSharedPreferences("TOUR", MODE_PRIVATE).getInt("PROGRESS", 1) - 1).init(this, mMap); // Load the n-th Segment in the current Route, depending on the progress. Load Segment 0 as default
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set up Map and Route if they don't exist
        if (mMap == null) setUpMap();
        if (mRoute == null) setUpRoute();

        // TODO Improve this
        mActionBarTitle.setText(String.format("Progress: %d / %d", getSharedPreferences("TOUR", MODE_PRIVATE).getInt("PROGRESS", 0), mRoute.getRouteSegments().size()));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Synchronize the Drawer Toggle Button with the Navigation Drawer
        mDrawerToggle.syncState(); // Sync State, for example after switching from Landscape to Portrait Mode
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Check if the Drawer Toggle Button was pressed
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        Intent startQuiz = new Intent(this, QuizActivity.class);
        switch (item.getItemId()) {
            case R.id.action_navigation_waypoint_1:
                // No Quiz
                return true;
            case R.id.action_navigation_waypoint_2:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 3);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_3:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 8);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_4:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 9);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_5:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 7);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_6:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 4);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_7:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 5);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_8:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 1);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_9:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 6);
                startActivity(startQuiz);
                return true;
            case R.id.action_navigation_waypoint_10:
                startQuiz.putExtra(QuizActivity.TAG_QUIZ_ID, 2);
                startActivity(startQuiz);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
    }

    private void setUpActionBar() {
        if (getSupportActionBar() == null)return;

        // Use Custom ActionBar Layout and Display BackButton
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

        // Set Custom ActionBar Layout
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        mActionBarTitle = (TextView) findViewById(R.id.actionbar_title);
    }

    private void setUpDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navlayout_navigation);
        mLvWaypoints = (ListView) findViewById(R.id.lv_navigation);

        // Configure Drawer Toggle Button in Action Bar
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.waypoint_1, R.string.waypoint_2);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    //TODO Dont animate camera on onResume()
    /**
     * This function sets up the GoogleMaps v2 Map
     */
    private void setUpMap() {
        // Get the Map Fragment
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        // Move Camera to Bamberg
        LatLng BAMBERG = new LatLng(49.898814, 10.890764);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BAMBERG, 12f));

        // Show Location on Maps
        mMap.setMyLocationEnabled(true);
    }

    //TODO Documentation
    private void setUpRoute() {

        // Query Arguments
        String[] projection = {RoutesTable.COLUMN_ROUTE_SEGMENT_ID, RoutesTable.COLUMN_ROUTE_SEGMENT_POSITION};
        String selection = RoutesTable.COLUMN_ROUTE_NAME + "=?";
        String[] selectionArgs = {getSharedPreferences("TOUR", MODE_PRIVATE).getString("ROUTE_NAME", "")};

        // Give the Route a Name
        mRoute = new Route(getSharedPreferences("TOUR", MODE_PRIVATE).getString("ROUTE_NAME", ""));

        // Query for Route Segments
        Cursor routeSegments = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_ROUTES,
                projection, selection, selectionArgs, null);
        while (routeSegments.moveToNext()) {
            projection = new String[]{RouteSegmentsTable.COLUMN_START_WAYPOINT_ID, RouteSegmentsTable.COLUMN_END_WAYPOINT_ID, RouteSegmentsTable.COLUMN_KML_FILENAME};
            selection = RouteSegmentsTable.COLUMN_SEGMENT_ID + "=?";
            selectionArgs = new String[]{""+routeSegments.getInt(routeSegments.getColumnIndex(RoutesTable.COLUMN_ROUTE_SEGMENT_ID))};

            Cursor routeSegment = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_ROUTE_SEGMENTS, projection, selection, selectionArgs, null);
            if (routeSegment.moveToNext()) {
                int fromWaypointID = routeSegment.getInt(routeSegment.getColumnIndex(RouteSegmentsTable.COLUMN_START_WAYPOINT_ID));
                int toWaypointID = routeSegment.getInt(routeSegment.getColumnIndex(RouteSegmentsTable.COLUMN_END_WAYPOINT_ID));
                String filename = routeSegment.getString(routeSegment.getColumnIndex(RouteSegmentsTable.COLUMN_KML_FILENAME));

                // Add a new Route Segment to the current Route
                mRoute.addRouteSegment(new RouteSegment(fromWaypointID, toWaypointID, filename));
            }
        }
    }
}