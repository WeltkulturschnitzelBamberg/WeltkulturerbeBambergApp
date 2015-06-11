package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;


public class ActivityFrage1 extends Activity implements AppCompatCallback {

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
    private TextView textViewFrage1;
    private Button buttonRichtigeAntwort;
    private Button buttonFalscheAntwortEins;
    private Button buttonWeiter;
    private Button buttonHilfe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_activity_frage1);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.frage1_toolbar);
        delegate.setSupportActionBar(toolbar);
        //Oberflächenelemente definieren
        textViewFrage1 = (TextView) findViewById(R.id.textViewFrage1);
        buttonRichtigeAntwort = (Button) findViewById(R.id.buttonRichtigeAntwort);
        buttonFalscheAntwortEins = (Button) findViewById(R.id.buttonFalscheAntwortEins);
        buttonWeiter = (Button) findViewById(R.id.buttonWeiter);
        buttonHilfe = (Button) findViewById(R.id.buttonHilfe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_score:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(ActivityFrage1.this, ScoreActivity.class));
                return true;
            case R.id.action_start:
                if (item.isChecked()) item.setChecked(false);
                else item.setChecked(true);
                startActivity(new Intent(ActivityFrage1.this, WelcomePageActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void FrageBeantwortet(View view) {
        if (view.getId() == R.id.buttonRichtigeAntwort)
        {
            //wenn richtige Antwort, dann grün färben
            buttonRichtigeAntwort.setBackgroundColor(Color.GREEN);
        }
        else
        {
            //wenn falsche Antwort, gedrückten Button rot färben, richtige Antwort grün färben
            ((Button)view).setBackgroundColor(Color.RED);
            buttonRichtigeAntwort.setBackgroundColor(Color.GREEN);
        }
        //Antwortbuttons nicht mehr anklicken
        buttonRichtigeAntwort.setClickable(false);
        buttonFalscheAntwortEins.setClickable(false);
        //Weiterbutton sichtbar und anklickbar machen
        buttonWeiter.setClickable(true);
        buttonWeiter.setVisibility(View.VISIBLE);
    }

    public void naechsteFrage(View view)
    {
        Intent Frage2 = new Intent(this, InformationActivity.class);
        startActivity(Frage2);

    }

    public void TippAufrufen(View view)
    {
        Intent Tipp = new Intent(this, ActivityTipp.class);
        startActivity(Tipp);
    }
}
