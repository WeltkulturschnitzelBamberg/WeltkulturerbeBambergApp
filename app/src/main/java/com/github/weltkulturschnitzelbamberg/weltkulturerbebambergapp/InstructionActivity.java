package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * This activity shows a greeting and instructions how to use the World-heritage-Application.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InstructionActivity extends Activity implements AppCompatCallback {
    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        //let's leave this empty, for now
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        // let's leave this empty, for now
    }
    private AppCompatDelegate delegate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_instructions);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.instruc_toolbar);
        delegate.setSupportActionBar(toolbar);
    }

    public void onBtnClickShortRoute(View view) {
        Intent startShortRoute = new Intent(this, NavigationActivity.class);
        startShortRoute.putExtra(NavigationActivity.ROUTE_CODE, NavigationActivity.CODE_ROUTE_SHORT);
        startActivity(startShortRoute);
    }

    public void onBtnClickLongRoute(View view) {
        Intent startLongRoute = new Intent(this, NavigationActivity.class);
        startLongRoute.putExtra(NavigationActivity.ROUTE_CODE, NavigationActivity.CODE_ROUTE_LONG);
        startActivity(startLongRoute);
    }
}
