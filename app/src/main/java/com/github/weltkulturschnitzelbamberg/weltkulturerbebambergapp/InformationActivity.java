package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Diese Activity zeigt Informationen (informativer Text, Bild, ...) zu einem Weltkulturerbe-Standpunkt an.
 * Sie wird nach dem Beenden von verschiedener Quizee von der {@link QuizActivity} aufgerufen. Abhängig von dem
 * beendeten Quiz und dem Weltkulturerbe-Standpunkt werden verschiedene Informationen angezeigt
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InformationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
    }
}