package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;

/**
 * This activity shows a greeting and instructions how to use the World-heritage-Application.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InstructionsActivity extends Activity implements AppCompatCallback {

    private AppCompatDelegate delegate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO cohesion
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

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        //let's leave this empty, for now
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        // let's leave this empty, for now
    }
}
