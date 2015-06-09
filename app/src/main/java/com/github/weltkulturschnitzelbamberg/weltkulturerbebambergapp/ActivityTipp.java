package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.content.Intent;

public class ActivityTipp extends Activity implements AppCompatCallback {

    @Override
    public void onSupportActionModeStarted(ActionMode mode) {
        //let's leave this empty, for now
    }

    @Override
    public void onSupportActionModeFinished(ActionMode mode) {
        // let's leave this empty, for now
    }
    private AppCompatDelegate delegate;

    private TextView textViewTipp;
    private Button buttonZurück;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_activity_tipp);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.tipp_toolbar);
        delegate.setSupportActionBar(toolbar);
        textViewTipp = (TextView) findViewById(R.id.textViewTipp);
        buttonZurück = (Button) findViewById(R.id.buttonZurück);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void ZurückButtonGeklickt(View view)
    {
        Intent Frage = new Intent(this, ActivityFrage1.class);
        startActivity(Frage);
    }
}
