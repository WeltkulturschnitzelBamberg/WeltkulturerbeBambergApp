package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;

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

        //TODO Remvove onClickListener
        Button btn_score_back_to_start = (Button) findViewById(R.id.btn_score_back_to_start);
        btn_score_back_to_start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(ScoreActivity.this, WelcomePageActivity.class));
            }
        });
    }
}