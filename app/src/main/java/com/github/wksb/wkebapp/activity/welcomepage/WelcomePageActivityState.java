package com.github.wksb.wkebapp.activity.welcomepage;

import android.os.Bundle;
import android.view.View;

/**
 * Interface for different states of the {@link WelcomePageActivity}. Each state can have different layouts or logic for the overlying Activity.
 * This is part of a "State Design Pattern".
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-10-15
 */
public interface WelcomePageActivityState {

    /**
     * Logic that is to be executed when the {@linkplain WelcomePageActivity#onResume()} Method in the {@link WelcomePageActivity} is called
     */
    void onResume();

    /**
     * Called if the state of the overlying {@link WelcomePageActivity} was changed. Initialises the new state
     */
    void initState();

    /**
     * Logic executed if the Button {@link com.github.wksb.wkebapp.R.id#btn_welcome_start} gets clicked
     * @param view The view calling this method
     */
    void onBtnClickedStart(View view);

    /**
     * Logic executed if the Button {@link com.github.wksb.wkebapp.R.id#btn_welcome_about} gets clicked
     * @param view The view calling this method
     */
    void onBtnClickedAbout(View view);

    /**
     * Logic executed if the Button {@link com.github.wksb.wkebapp.R.id#btn_welcome_continue} gets clicked
     * @param view The view calling this method
     */
    void onBtnClickedContinue(View view);

    /**
     * Logic executed if the Button {@link com.github.wksb.wkebapp.R.id#btn_welcome_start} gets clicked
     * @param view The view calling this method
     */
    void onBtnClickedRestartTour(View view);
}