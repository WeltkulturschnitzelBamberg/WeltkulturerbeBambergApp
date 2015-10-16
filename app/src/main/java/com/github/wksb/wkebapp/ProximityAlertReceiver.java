package com.github.wksb.wkebapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import com.github.wksb.wkebapp.utilities.DebugUtils;

//TODO Documentation
/**
 * Created by michael on 12.06.15.
 */
public class ProximityAlertReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean entering = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);

        if (entering) {
            DebugUtils.toast(context, "Im entering " + intent.getStringExtra("name") + " (Lat: " + intent.getFloatExtra("lat", 0.0f) + ", Lng: " + intent.getFloatExtra("lng", 0.0f) + ")");
        } else {
            DebugUtils.toast(context, "Im doing something funny :D");
        }
    }
}