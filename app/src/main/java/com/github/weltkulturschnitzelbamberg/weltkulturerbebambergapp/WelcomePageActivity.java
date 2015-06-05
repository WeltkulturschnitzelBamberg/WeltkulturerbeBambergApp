package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.os.Bundle;
/**
 * This activity is the launch activity of the World-Heritage-Application
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class WelcomePageActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page_app);
    }
}