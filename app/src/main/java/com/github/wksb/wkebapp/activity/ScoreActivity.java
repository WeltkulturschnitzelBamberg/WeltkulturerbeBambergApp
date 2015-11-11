package com.github.wksb.wkebapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.wksb.wkebapp.R;
import com.github.wksb.wkebapp.activity.welcomepage.WelcomePageActivity;

/**
 * This activity shows the score of the user.
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class ScoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    private void setUpActionBar() {
        if (getSupportActionBar() == null)return;

        // Use Custom ActionBar Layout and Display BackButton
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP);

        // Set Custom ActionBar Layout
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
    }

    public void onBtnClickBackToStart(View view) {
        startActivity(new Intent(ScoreActivity.this, WelcomePageActivity.class));
    }
}