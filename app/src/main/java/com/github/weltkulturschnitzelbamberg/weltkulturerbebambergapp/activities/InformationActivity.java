package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activities;

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

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;

/**
 * This activity shows information about a world-heritage.
 * The shown content depends on the world-heritage.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InformationActivity extends Activity implements AppCompatCallback {

    private AppCompatDelegate delegate;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO cohesion
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_information);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.info_toolbar);
        delegate.setSupportActionBar(toolbar);

        //TODO Remove onClickListener
        Button btn_info_continue = (Button) findViewById(R.id.btn_info_continue);
         btn_info_continue.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 startActivity(new Intent(InformationActivity.this, ActivityFrage2.class));
             }
         });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

              switch (item.getItemId()) {
            case R.id.action_score:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(InformationActivity.this, ScoreActivity.class));
                return true;
            case R.id.action_start:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(InformationActivity.this, WelcomePageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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