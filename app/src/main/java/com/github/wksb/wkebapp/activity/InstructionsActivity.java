package com.github.wksb.wkebapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.activity.navigation.NavigationActivity;

/**
 * This activity shows a greeting and instructions how to use the World-heritage-Application.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        // Set up theActionBar
        setUpActionBar();
    }

    private void setUpActionBar() {
        if (getSupportActionBar() == null)return;

        // Use Custom ActionBar Layout and Display BackButton
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

        // Set Custom ActionBar Layout
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
    }

    /**
     * Reset the previous Tour
     */
    private void resetPreviousTour() {
        // Reset SharedPreferences
        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putBoolean("IS_IN_PROGRESS", false).commit();
        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putInt("PROGRESS", 1).commit();
        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putInt("CURRENT_QUIZ_ID", -1).commit();
        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putString("ROUTE_NAME", null).commit();
    }

    //TODO Documentation
    public void onBtnClickShortRoute(View view) {
        resetPreviousTour();

        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putString("ROUTE_NAME", "short").commit();
        Intent startShortRoute = new Intent(this, NavigationActivity.class);
        startActivity(startShortRoute);
    }

    //TODO Documentation
    public void onBtnClickLongRoute(View view) {
        resetPreviousTour();

        getSharedPreferences("TOUR", MODE_PRIVATE).edit().putString("ROUTE_NAME", "long").commit();
        Intent startLongRoute = new Intent(this, NavigationActivity.class);
        startActivity(startLongRoute);
    }
}