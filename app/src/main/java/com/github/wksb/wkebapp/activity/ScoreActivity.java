package com.github.wksb.wkebapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
public class ScoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
    }

    public void onBtnClickBackToStart(View view) {
        startActivity(new Intent(ScoreActivity.this, WelcomePageActivity.class));
    }
}