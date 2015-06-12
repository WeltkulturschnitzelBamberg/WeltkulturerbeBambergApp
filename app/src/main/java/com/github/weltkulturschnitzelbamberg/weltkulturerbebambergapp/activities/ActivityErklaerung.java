package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;

public class ActivityErklaerung extends Activity implements AppCompatCallback {

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        //let's leave this empty, for now
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        // let's leave this empty, for now
    }
    private AppCompatDelegate delegate;

    //meine Oberflächenelemente
    private TextView textViewErklärung;
    private Button buttonKurzeRoute;
    private Button buttonLangeRoute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_activity_erklaerung);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.erklaerung_toolbar);
        delegate.setSupportActionBar(toolbar);

        buttonKurzeRoute = (Button) findViewById(R.id.buttonKurzeRoute);
        buttonLangeRoute = (Button) findViewById(R.id.buttonLangeRoute);
        textViewErklärung = (TextView) findViewById(R.id.textViewErklärung);
    }

    //Route gewählt
    public void RouteGewählt(View view)
    {
        if(view.getId() == R.id.buttonLangeRoute)
        {
            Intent Frage1 = new Intent(this, ActivityFrage1.class);
            startActivity(Frage1);
        }
        else
        {
            Intent Frage2 = new Intent(this, ActivityFrage2.class);
            startActivity(Frage2);
        }
    }
}
