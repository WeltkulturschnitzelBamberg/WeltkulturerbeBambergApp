package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.InformationAsyncTaskLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.RouteAsyncTaskLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.WaypointsAsyncTaskLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.utilities.DebugUtils;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.QuizzesAsyncTaskLoader;

import android.content.Intent;
import android.view.View;

/**
 * This activity is the launch activity of the World-Heritage-Application
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WelcomePageActivity extends Activity implements LoaderManager.LoaderCallbacks{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_app);

        onFirstLaunch();
    }

    /**
     * This methode executes only on the first start of the Application
     */
    private void onFirstLaunch() {
        // Check if this is the first launch of the Application
        if (getSharedPreferences("PREFERENCES", MODE_PRIVATE).getBoolean("IS_FIRST_LAUNCH", true)) {
            // Initialise Loaders
            getLoaderManager().initLoader(WaypointsAsyncTaskLoader.LOADER_ID, null, this);
            getLoaderManager().initLoader(RouteAsyncTaskLoader.LOADER_ID, null, this);
            getLoaderManager().initLoader(QuizzesAsyncTaskLoader.LOADER_ID, null, this);
            getLoaderManager().initLoader(InformationAsyncTaskLoader.LOADER_ID, null, this);
            getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit().putBoolean("IS_FIRST_LAUNCH", false).commit();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case RouteAsyncTaskLoader.LOADER_ID:
                // Return new RouteAsyncTaskLoader to load routes in the database
                DebugUtils.toast(this, "Loading Routes in Database ...");
                return new RouteAsyncTaskLoader(this);
            case QuizzesAsyncTaskLoader.LOADER_ID:
                // Return new QuizzesAsyncTaskLoader to load quizzes in the database
                DebugUtils.toast(this, "Loading Quizzes in Database ...");
                return new QuizzesAsyncTaskLoader(this);
            case WaypointsAsyncTaskLoader.LOADER_ID:
                // Return new WaypointsAsyncTaskLoader to load waypoints in the database
                DebugUtils.toast(this, "Loading Waypoints in Database ...");
                return new WaypointsAsyncTaskLoader(this);
            case InformationAsyncTaskLoader.LOADER_ID:
                // Return new InformationAsyncTaskLoader to load the information about the waypoints in the database
                DebugUtils.toast(this, "Loading Information about the Waypoints in the Database ...");
                return new InformationAsyncTaskLoader(this);
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
            case RouteAsyncTaskLoader.LOADER_ID:
                DebugUtils.toast(this, "Routes loaded");
                getLoaderManager().destroyLoader(RouteAsyncTaskLoader.LOADER_ID);
                break;
            case QuizzesAsyncTaskLoader.LOADER_ID:
                DebugUtils.toast(this, "Quizzes loaded");
                getLoaderManager().destroyLoader(QuizzesAsyncTaskLoader.LOADER_ID);
                break;
            case WaypointsAsyncTaskLoader.LOADER_ID:
                DebugUtils.toast(this, "Waypoints loaded");
                getLoaderManager().destroyLoader(WaypointsAsyncTaskLoader.LOADER_ID);
                break;
            case InformationAsyncTaskLoader.LOADER_ID:
                DebugUtils.toast(this, "Information about Waypoints loaded");
                getLoaderManager().destroyLoader(InformationAsyncTaskLoader.LOADER_ID);
                break;
        }
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
        Intent start = new Intent(this, InformationActivity.class);
        start.putExtra(InformationActivity.TAG_INFORMATION_ID, 3);
        startActivity(start);
    }
}