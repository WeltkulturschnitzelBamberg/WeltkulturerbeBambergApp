package com.github.wksb.wkebapp.activity.welcomepage;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.github.wksb.wkebapp.InformationAsyncTaskLoader;
import com.github.wksb.wkebapp.RouteSegmentsAsyncTaskLoader;
import com.github.wksb.wkebapp.RoutesAsyncTaskLoader;
import com.github.wksb.wkebapp.WaypointsAsyncTaskLoader;
import com.github.wksb.wkebapp.utilities.DebugUtils;
import com.github.wksb.wkebapp.QuizzesAsyncTaskLoader;

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

    /** LOADER-ID of {@link QuizzesAsyncTaskLoader} **/
    private static final int QUIZZES_LOADER_ID = 0;
    /** LOADER-ID of {@link InformationAsyncTaskLoader} **/
    private static final int INFORMATION_LOADER_ID = 1;
    /** LOADER-ID of {@link RoutesAsyncTaskLoader} **/
    private static final int ROUTES_LOADER_ID = 2;
    /** LOADER-ID of {@link WaypointsAsyncTaskLoader} **/
    private static final int WAYPOINTS_LOADER_ID = 3;
    /** LOADER-ID of {@link RouteSegmentsAsyncTaskLoader} **/
    private static final int ROUTE_SEGMENTS_LOADER_ID = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DebugUtils.toast(this, getPackageName() + ", " + getFilesDir().getAbsolutePath());
        if (getSharedPreferences("MISCELLANEOUS", MODE_PRIVATE).getBoolean("IS_FIRST_APP_LAUNCH", true)) {
            onFirstLaunch();
            getSharedPreferences("MISCELLANEOUS", MODE_PRIVATE).edit().putBoolean("IS_FIRST_APP_LAUNCH", false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getSharedPreferences("TOUR", MODE_PRIVATE).getBoolean("IS_IN_PROGRESS", false)) {
            setActivityState(new TourInProgress(this));
        } else {
            setActivityState(new NoTourInProgress(this));
        }

        activityState.onActivityStart();
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

    /**
     * Logic executed on first Launch of this Application
     */
    private void onFirstLaunch() {
        // Initialise Loaders
        getLoaderManager().initLoader(WAYPOINTS_LOADER_ID, null, this);
        getLoaderManager().initLoader(ROUTES_LOADER_ID, null, this);
        getLoaderManager().initLoader(QUIZZES_LOADER_ID, null, this);
        getLoaderManager().initLoader(INFORMATION_LOADER_ID, null, this);
        getLoaderManager().initLoader(ROUTE_SEGMENTS_LOADER_ID, null, this);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ROUTES_LOADER_ID:
                // Return new RoutesAsyncTaskLoader to load routes in the database
                DebugUtils.toast(this, "Loading Routes in Database ...");
                return new RoutesAsyncTaskLoader(this);
            case QUIZZES_LOADER_ID:
                // Return new QuizzesAsyncTaskLoader to load quizzes in the database
                DebugUtils.toast(this, "Loading Quizzes in Database ...");
                return new QuizzesAsyncTaskLoader(this);
            case WAYPOINTS_LOADER_ID:
                // Return new WaypointsAsyncTaskLoader to load waypoints in the database
                DebugUtils.toast(this, "Loading Waypoints in Database ...");
                return new WaypointsAsyncTaskLoader(this);
            case INFORMATION_LOADER_ID:
                // Return new InformationAsyncTaskLoader to load the information about the waypoints in the database
                DebugUtils.toast(this, "Loading Information about the Waypoints in the Database ...");
                return new InformationAsyncTaskLoader(this);
            case ROUTE_SEGMENTS_LOADER_ID:
                // Return new RouteSegmentsAsyncTaskLoader to load the rout-segments
                DebugUtils.toast(this, "Loading Route Segments in Database ...");
                return new RouteSegmentsAsyncTaskLoader(this);
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
            case ROUTES_LOADER_ID:
                DebugUtils.toast(this, "Routes loaded");
                getLoaderManager().destroyLoader(ROUTES_LOADER_ID);
                break;
            case QUIZZES_LOADER_ID:
                DebugUtils.toast(this, "Quizzes loaded");
                getLoaderManager().destroyLoader(QUIZZES_LOADER_ID);
                break;
            case WAYPOINTS_LOADER_ID:
                DebugUtils.toast(this, "Waypoints loaded");
                getLoaderManager().destroyLoader(WAYPOINTS_LOADER_ID);
                break;
            case INFORMATION_LOADER_ID:
                DebugUtils.toast(this, "Information about Waypoints loaded");
                getLoaderManager().destroyLoader(INFORMATION_LOADER_ID);
                break;
            case ROUTE_SEGMENTS_LOADER_ID:
                DebugUtils.toast(this, "Route Segments loaded");
                getLoaderManager().destroyLoader(ROUTE_SEGMENTS_LOADER_ID);
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

    public void onBtnClickedRestartTour(View view) {
        activityState.onBtnClickedRestartTour(view);
    }
}