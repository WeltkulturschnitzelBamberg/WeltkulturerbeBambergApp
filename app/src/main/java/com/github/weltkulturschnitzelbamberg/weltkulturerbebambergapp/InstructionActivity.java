package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.app.Activity;
import android.os.Bundle;

/**
 * Diese Activity zeigt einen Gruﬂ und eine Erkl‰rung an, wie die Weltkulturerbe-Application verwendet
 * wird. Sie wird von der Activity {@link WelcomePageActivity} nach dem Start der App aufgerufen
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class InstructionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
    }

}
