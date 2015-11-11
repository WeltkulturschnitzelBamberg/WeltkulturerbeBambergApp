package com.github.wksb.wkebapp.activity.navigation;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.location.LocationManager;

import com.github.wksb.wkebapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
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
 * Created by Michael on 27.10.2015.
 */
public class RouteSegment {

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
    public void init(Activity activity, GoogleMap map) {
        // Get the from and to Waypoints, if they don't already exist
        if (fromWaypoint == null) fromWaypoint = new Waypoint(activity, fromWaypointID);
        if (toWaypoint == null) toWaypoint = new Waypoint(activity, toWaypointID);

        // Set the current Quiz to the Quiz of the current destination Waypoint
        activity.getSharedPreferences("TOUR", Activity.MODE_PRIVATE).edit().putInt("CURRENT_QUIZ_ID", toWaypoint.getQuizID()).commit();

        // Clear the Map from all Markers, polylines, overlays, etc.
        map.clear();

        // Add the Marker for the from and to Waypoint to the map
        addMarkersToMap(map);

        // Add the Polyline showing the Route to the Map
        addPolylineToMape(activity, map);

        // Add the proximity alert at the destination Waypoint
        addProximityAlert(activity);

        // Zoom to the Route Segment on the Map
        map.animateCamera(CameraUpdateFactory
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
    private void addProximityAlert(Activity activity) {
        LocationManager locationManager = (LocationManager) activity.getSystemService(Activity.LOCATION_SERVICE);
        Intent proximityAlert = new Intent();
        proximityAlert.setAction("com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.PROXIMITY_ALERT");
        proximityAlert.putExtra("waypoint-name", toWaypoint.getName());
        proximityAlert.putExtra("quiz-id", toWaypoint.getQuizID());

        int detectionRadius = 40; // The radius around the central point in which to send an proximity alert
        int expirationTime = -1; // The time in milliseconds it takes this proximity alert to expire (-1 = no expiration)
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, 0, proximityAlert, PendingIntent.FLAG_UPDATE_CURRENT); // TODO Previous Proximity Alerts have to be removed

        locationManager.addProximityAlert(toWaypoint.getLatitude(), toWaypoint.getLongitude(), detectionRadius, expirationTime, pendingIntent);
    }

    private void addPolylineToMape(Activity activity, GoogleMap map) {
        List<LatLng> points = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new InputStreamReader(activity.getAssets().open(filename)));

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
        polyline.color(activity.getResources().getColor(R.color.PrimaryColor));
        polyline.width(25);
        polyline.geodesic(true);

        map.addPolyline(polyline);
    }
}
