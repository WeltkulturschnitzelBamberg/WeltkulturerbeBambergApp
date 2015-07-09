package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.WaypointsLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.utilities.DebugUtils;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.QuizzesLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.RouteLoader;
import android.content.Intent;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

/**
 * This activity is the launch activity of the World-Heritage-Application
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WelcomePageActivity extends Activity implements LoaderManager.LoaderCallbacks, AppCompatCallback{

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
        setContentView(R.layout.activity_welcome_page_app);

        onFirstLaunch();

        //TODO cohesion
        //let's create the delegate, passing the activity at both arguments (Activity, AppCompatCallback)
        delegate = AppCompatDelegate.create(this, this);
        //we need to call the onCreate() of the AppCompatDelegate
        delegate.onCreate(savedInstanceState);

        //we use the delegate to inflate the layout
        delegate.setContentView(R.layout.activity_welcome_page_app);

        //Finally, let's add the Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.welcome_toolbar);
        delegate.setSupportActionBar(toolbar);
    }

    /**
     * This methode executes only on the first start of the Application
     */
    private void onFirstLaunch() {
        // Check if this is the first launch of the Application
        if (getSharedPreferences("PREFERENCES", MODE_PRIVATE).getBoolean("IS_FIRST_LAUNCH", true)) {
            // Initialise Loaders
            getLoaderManager().initLoader(WaypointsLoader.LOADER_ID, null, this);
            getLoaderManager().initLoader(RouteLoader.LOADER_ID, null, this);
            getLoaderManager().initLoader(QuizzesLoader.LOADER_ID, null, this);
            getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit().putBoolean("IS_FIRST_LAUNCH", false).commit();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RouteLoader.LOADER_ID:
                // Return new RouteLoader to load routes in the database
                DebugUtils.toast(this, "Loading Routes in Database ...");
                return new RouteLoader(this);
            case QuizzesLoader.LOADER_ID:
                // Return new QuizzesLoader to load quizzes in the database
                DebugUtils.toast(this, "Loading Quizzes in Database ...");
                return new QuizzesLoader(this);
            case WaypointsLoader.LOADER_ID:
                // Return new WaypointsLoader to load waypoints in the database
                DebugUtils.toast(this, "Loading Waypoints in Database ...");
                return new WaypointsLoader(this);
            default:
                // There is no such Loader ID -> throw Exception
                throw new IllegalArgumentException("Loader not found. ID: " + id);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        //Not used
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        switch (loader.getId()) {
            case RouteLoader.LOADER_ID:
                DebugUtils.toast(this, "Routes loaded");
                getLoaderManager().destroyLoader(RouteLoader.LOADER_ID);
                break;
            case QuizzesLoader.LOADER_ID:
                DebugUtils.toast(this, "Quizzes loaded");
                getLoaderManager().destroyLoader(QuizzesLoader.LOADER_ID);
                break;
            case WaypointsLoader.LOADER_ID:
                DebugUtils.toast(this, "Waypoints loaded");
                getLoaderManager().destroyLoader(WaypointsLoader.LOADER_ID);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_score) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //TODO Documentation
    public void onBtnClickScore(View view) {
        Intent startScoreActivity = new Intent(this, ScoreActivity.class);
        startActivity(startScoreActivity);
    }

    //TODO Documentation
    public void onBtnClickStart(View view) {
        Intent startInstructionActivity = new Intent(this, InstructionsActivity.class);
        startActivity(startInstructionActivity);
    }

    //TODO Documentation
    public void onBtnClickContinue(View view) {
        //TODO start navigation activity from last waypoint
    }
}