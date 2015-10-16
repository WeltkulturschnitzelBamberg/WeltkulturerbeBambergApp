package com.github.wksb.wkebapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.wksb.wkebapp.R;

/**
 * This activity shows a greeting and instructions how to use the World-heritage-Application.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InstructionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

    //TODO Documentation
    public void onBtnClickShortRoute(View view) {
        Intent startShortRoute = new Intent(this, NavigationActivity.class);
        startShortRoute.putExtra(NavigationActivity.TAG_ROUTE_CODE, NavigationActivity.FLAG_ROUTE_CODE_SHORT);
        startActivity(startShortRoute);
    }

    //TODO Documentation
    public void onBtnClickLongRoute(View view) {
        Intent startLongRoute = new Intent(this, NavigationActivity.class);
        startLongRoute.putExtra(NavigationActivity.TAG_ROUTE_CODE, NavigationActivity.FLAG_ROUTE_CODE_LONG);
        startActivity(startLongRoute);
    }
}