package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.Utilities.DebugUtils;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.loader.QuizzesLoader;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.loader.RouteLoader;

/**
 * This activity is the launch activity of the World-Heritage-Application
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WelcomePageActivity extends Activity implements LoaderManager.LoaderCallbacks{

    private static final int ROUTE_LOADER_ID = 0;
    private static final int QUIZZES_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_app);

        /** On first launch waypoints and routes are getting loaded into the Database via the {@link RouteLoader} **/
        if (getSharedPreferences("PREFERENCES", MODE_PRIVATE).getBoolean("IS_FIRST_LAUNCH", true)) {
            getLoaderManager().initLoader(ROUTE_LOADER_ID, null, this);
            getLoaderManager().initLoader(QUIZZES_LOADER_ID, null, this);
            getSharedPreferences("PREFERENCES", MODE_PRIVATE).edit().putBoolean("IS_FIRST_LAUNCH", false).commit();
        }
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        switch (id) {
            case ROUTE_LOADER_ID:
                DebugUtils.toast(this, "Loading Routes for first Launch ...");
                return new RouteLoader(this);
            case QUIZZES_LOADER_ID:
                DebugUtils.toast(this, "Loading Quizzes for first Launch ...");
                return new QuizzesLoader(this);
            default:
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
            case ROUTE_LOADER_ID:
                DebugUtils.toast(this, "Routes loaded");
                getLoaderManager().destroyLoader(ROUTE_LOADER_ID);
                break;
            case QUIZZES_LOADER_ID:
                DebugUtils.toast(this, "Quizzes Loaded");
                getLoaderManager().destroyLoader(QUIZZES_LOADER_ID);
                break;
        }
    }
}