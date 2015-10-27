package com.github.wksb.wkebapp.activity;

import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.RouteSegmentsTable;
import com.github.wksb.wkebapp.database.RoutesTable;
import com.github.wksb.wkebapp.database.WaypointsTable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity shows a GoogleMaps map on which a route between to waypoints is shown.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */

public class NavigationActivity extends FragmentActivity {

    private GoogleMap mMap;

    // This Object contains the current Route with all its RouteSegments
    private Route mRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_activity_navigation);

        // Set up Map and Route if they don't exist
        if (mMap == null) setUpMap();
        if (mRoute == null) setUpRoute();

        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putBoolean("IS_IN_PROGRESS", true).commit(); // Set the Tour to being in progress
        mRoute.getRouteSegments().get(getSharedPreferences("TOUR", MODE_PRIVATE).getInt("PROGRESS", 1)-1).init(mMap); // Load the n-th Segment in the current Route, depending on the progress. Load Segment 0 as default
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set up Map and Route if they don't exist
        if (mMap == null) setUpMap();
        if (mRoute == null) setUpRoute();

        // TODO Improve this
        setTitle("Progress: " + getSharedPreferences("TOUR", MODE_PRIVATE).getInt("PROGRESS", 0) + "/" + mRoute.getRouteSegments().size());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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

    //TODO Dont animate camera on onResume()
    /**
     * This function sets up the GoogleMaps v2 Map
     */
    private void setUpMap() {
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                .getMap();

        // Move Camera to Bamberg
        LatLng BAMBERG = new LatLng(49.898814, 10.890764);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BAMBERG, 12f));
        mMap.setMyLocationEnabled(true);
    }

    //TODO Documentation
    private void setUpRoute() {

        String[] projection = {RoutesTable.COLUMN_ROUTE_SEGMENT_ID, RoutesTable.COLUMN_ROUTE_SEGMENT_POSITION};
        String selection = RoutesTable.COLUMN_ROUTE_NAME + "=?";
        String[] selectionArgs = {getSharedPreferences("TOUR", MODE_PRIVATE).getString("ROUTE_NAME", "")};

        mRoute = new Route(getSharedPreferences("TOUR", MODE_PRIVATE).getString("ROUTE_NAME", ""));

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
                mRoute.addRouteSegment(new RouteSegment(fromWaypointID, toWaypointID, filename));
            }
        }
    }

    private class Route {

        private final String name;
        private final List<RouteSegment> routeSegmentsList;

        public Route(String name) {
            this.name = name;
            this.routeSegmentsList = new ArrayList<>();
        }

        public void addRouteSegment(RouteSegment routeSegment) {
            routeSegmentsList.add(routeSegment);
        }

        public List<RouteSegment> getRouteSegments() {
            return this.routeSegmentsList;
        }
    }

    private class RouteSegment {

        private int fromWaypointID;
        private int toWaypointID;
        private Waypoint fromWaypoint;
        private Waypoint toWaypoint;
        private String filename;

        public RouteSegment(int fromWaypointID, int toWaypointID, String filename) {
            this.fromWaypointID = fromWaypointID;
            this.toWaypointID = toWaypointID;
            this.filename = filename;
        }

        /**
         * Initialises this RouteSegment. Adds Markers for the Start and Destination, the Polyline connecting the two Markers and a Proximity for the Destination
         * @param map The Map on which to add the Markers and the Polyline
         */
        public void init(GoogleMap map) {
            // Get the from and to Waypoints, if they don't already exist
            if (fromWaypoint == null) fromWaypoint = new Waypoint(fromWaypointID);
            if (toWaypoint == null) toWaypoint = new Waypoint(toWaypointID);

            // Set the current Quiz to the Quiz of the current destination Waypoint
            getSharedPreferences("TOUR", MODE_PRIVATE).edit().putInt("CURRENT_QUIZ_ID", toWaypoint.getQuizID()).commit();

            // Clear the Map from all Markers, polylines, overlays, etc.
            map.clear();

            // Add the Marker for the from and to Waypoint to the map
            addMarkersToMap(map);

            // Add the Polyline showing the Route to the Map
            addPolylineToMape(map);

            // Add the proximity alert at the destination Waypoint
            addProximityAlert();

            // Zoom to the Route Segment on the Map
            mMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng((fromWaypoint.getLatitude() + toWaypoint.getLatitude()) / 2, (fromWaypoint.getLongitude() + toWaypoint.getLongitude()) / 2), 14));
        }

        /**
         * Add the Marker for the from and to Waypoints to a {@link GoogleMap}
         * @param map The {@link GoogleMap} to which the Markers are added
         */
        private void addMarkersToMap(GoogleMap map) {
            MarkerOptions fromWapointMarker = new MarkerOptions()
                    .title(fromWaypoint.getName())
                    .position(new LatLng(fromWaypoint.getLatitude(), fromWaypoint.getLongitude()));
            MarkerOptions toWapointMarker = new MarkerOptions()
                    .title(toWaypoint.getName())
                    .position(new LatLng(toWaypoint.getLatitude(), toWaypoint.getLongitude()));
            map.addMarker(fromWapointMarker);
            map.addMarker(toWapointMarker);
        }

        /**
         * Add a proximity alert at the destination Waypoint
         */
        private void addProximityAlert() {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            Intent proximityAlert = new Intent();
            proximityAlert.setAction("com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.PROXIMITY_ALERT");
            proximityAlert.putExtra("waypoint-name", toWaypoint.getName());
            proximityAlert.putExtra("quiz-id", toWaypoint.getQuizID());

            int detectionRadius = 40; // The radius around the central point in which to send an proximity alert
            int expirationTime = -1; // The time in milliseconds it takes this proximity alert to expire (-1 = no expiration)
            PendingIntent pendingIntent = PendingIntent.getBroadcast(NavigationActivity.this, 0, proximityAlert, PendingIntent.FLAG_UPDATE_CURRENT); // TODO Previous Proximity Alerts have to be removed

            locationManager.addProximityAlert(toWaypoint.getLatitude(), toWaypoint.getLongitude(), detectionRadius, expirationTime, pendingIntent);
        }

        private void addPolylineToMape(GoogleMap map) {
            List<LatLng> points = new ArrayList<>();

            try {
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new InputStreamReader(getAssets().open(filename)));

                if (obj instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) obj;

                    for (String coordinates : ((String)jsonObject.get("polyline")).split(",0.0")) {
                        String longitude = coordinates.substring(0, coordinates.indexOf(","));
                        String latitude = coordinates.substring(coordinates.indexOf(",") +1);

                        LatLng point = new LatLng(Float.parseFloat(latitude), Float.parseFloat(longitude));
                        points.add(point);
                    }
                }
            } catch (ParseException | IOException e) {
                e.printStackTrace();
            }

            PolylineOptions polyline = new PolylineOptions();
            polyline.addAll(points);
            polyline.color(getResources().getColor(R.color.PrimaryColor));
            polyline.width(25);
            polyline.geodesic(true);

            map.addPolyline(polyline);
        }
    }

    private class Waypoint {

        private float latitude;
        private float longitude;
        private String name;
        private int quizID;

        public Waypoint(int waypointID) {
            // The parameter for the SQLite query
            String[] projection = {WaypointsTable.COLUMN_LATITUDE, WaypointsTable.COLUMN_LONGITUDE, WaypointsTable.COLUMN_NAME, WaypointsTable.COLUMN_QUIZ_ID};
            String selection = WaypointsTable.COLUMN_WAYPOINT_ID + "=?";
            String[] selectionArgs = {""+waypointID};

            Cursor cursor = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_WAYPOINTS, projection, selection, selectionArgs, null);
            if (cursor.moveToNext()) {
                latitude = cursor.getFloat(cursor.getColumnIndex(WaypointsTable.COLUMN_LATITUDE));
                longitude = cursor.getFloat(cursor.getColumnIndex(WaypointsTable.COLUMN_LONGITUDE));
                name = cursor.getString(cursor.getColumnIndex(WaypointsTable.COLUMN_NAME));
                quizID = cursor.getInt(cursor.getColumnIndex(WaypointsTable.COLUMN_QUIZ_ID));
            }
        }

        /**
         * Get the Name of this Wapoint
          * @return The Name of this Waypoint
         */
        public String getName() {
            return name;
        }

        /**
         * Get the Latitude of this Waypoint
         * @return The Latitude of this Waypoint
         */
        public float getLatitude() {
            return latitude;
        }

        /**
         * Get the Longitude of this Waypoint
         * @return The Waypoint of this Waypoint
         */
        public float getLongitude() {
            return longitude;
        }

        /**
         * Get the ID of the Quiz about this Waypoint
         * @return The ID of the Quiz about this Waypoint
         */
        public int getQuizID() {
            return quizID;
        }
    }
}