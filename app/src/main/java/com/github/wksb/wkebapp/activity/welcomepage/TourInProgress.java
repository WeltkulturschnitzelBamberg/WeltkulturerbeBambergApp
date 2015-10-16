package com.github.wksb.wkebapp.activity.welcomepage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.activity.NavigationActivity;
import com.github.wksb.wkebapp.utilities.DebugUtils;

/**
 * State for the {@link WelcomePageActivity} when the guided tour is in progress (started)
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-10-16
 */
public class TourInProgress implements WelcomePageActivityState{

    /**
     * The overlying {@link WelcomePageActivity}
     */
    private WelcomePageActivity welcomePageActivity;

    /**
     * @param welcomePageActivity The overlying {@link WelcomePageActivity}
     */
    public TourInProgress (WelcomePageActivity welcomePageActivity) {
        this.welcomePageActivity = welcomePageActivity;
    }

    @Override
    public void onActivityStart() {
        welcomePageActivity.setContentView(R.layout.activity_welcome_page_app_tourinprogress);
    }

    @Override
    public void initState() {

    }

    @Override
    public void onBtnClickedStart(View view) {
    }

    @Override
    public void onBtnClickedAbout(View view) {
        DebugUtils.toast(welcomePageActivity, "About");
    }

    @Override
    public void onBtnClickedContinue(View view) {
        Intent startShortRoute = new Intent(welcomePageActivity, NavigationActivity.class);
        startShortRoute.putExtra(NavigationActivity.TAG_ROUTE_CODE, NavigationActivity.FLAG_ROUTE_CODE_SHORT);
        welcomePageActivity.startActivity(startShortRoute);
    }

    @Override
    public void onBtnClickedRestartTour(View view) {
        DebugUtils.toast(welcomePageActivity, "Restart Tour");
    }
}
