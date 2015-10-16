package com.github.wksb.wkebapp.activity.welcomepage;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.github.wksb.wkebapp.InformationAsyncTaskLoader;
import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.RouteAsyncTaskLoader;
import com.github.wksb.wkebapp.WaypointsAsyncTaskLoader;
import com.github.wksb.wkebapp.activity.InformationActivity;
import com.github.wksb.wkebapp.activity.InstructionsActivity;
import com.github.wksb.wkebapp.activity.ScoreActivity;
import com.github.wksb.wkebapp.utilities.DebugUtils;
import com.github.wksb.wkebapp.QuizzesAsyncTaskLoader;

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

    /**
     * The state of this Activity. The Behaviour of this Activity changes with its current state
     */
    private WelcomePageActivityState activityState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSharedPreferences("TOUR", MODE_PRIVATE).getBoolean("IS_IN_PROGRESS", false)) {
            setActivityState(new TourInProgress(this));
        } else {
            if (getSharedPreferences("MISCELLANEOUS", MODE_PRIVATE).getBoolean("IS_FIRST_APP_LAUNCH", true)) {
                setActivityState(new FirstLaunch(this));
            } else {
                setActivityState(new NoTourInProgress(this));
            }
        }

        activityState.onCreate(savedInstanceState);
    }

    /**
     * Set the state of this Activity
     * @param state The new state of this Activity
     */
    public void setActivityState(WelcomePageActivityState state) {
        activityState = state;
        activityState.initState(); // Initialise state
        DebugUtils.toast(this, "State changed to: " + state.getClass().getSimpleName());
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
    public void onBtnClickedStart(View view) {
        activityState.onBtnClickedStart(view);
    }

    //TODO Documentation
    public void onBtnClickedContinue(View view) {
        activityState.onBtnClickedContinue(view);
    }

    public void onBtnClickedAbout(View view) {
        activityState.onBtnClickedStart(view);
    }
}