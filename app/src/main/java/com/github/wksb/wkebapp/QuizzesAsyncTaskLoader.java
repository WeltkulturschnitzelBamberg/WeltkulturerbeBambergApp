package com.github.wksb.wkebapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.wksb.wkebapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.wksb.wkebapp.database.QuizzesTable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This AsyncTaskLoader loads the quizzes into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class QuizzesAsyncTaskLoader extends AsyncTaskLoader {

    public QuizzesAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            writeQuizzesToDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void writeQuizzesToDatabase() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new InputStreamReader(getContext().getAssets().open("quizzes.json"), "UTF-8"));

        if (obj instanceof JSONObject) {
            JSONObject jsonObject = (JSONObject) obj;

            JSONArray quizzes = (JSONArray) jsonObject.get("quizzes");
            for (int i = 0; i < quizzes.size(); i++) {
                JSONObject quizz = (JSONObject) quizzes.get(i);

                Long id = 0L;
                String location = null;
                String question = null;
                String solution = null;
                String wrongAnswer1 = null;
                String wrongAnswer2 = null;
                String wrongAnswer3 = null;
                Long infoID = 0L;

                if (quizz.get("id") instanceof Long) id = (long) quizz.get("id");
                if (quizz.get("location") instanceof String) location = (String) quizz.get("location");
                if (quizz.get("question") instanceof String) question = (String) quizz.get("question");
                if (quizz.get("solution") instanceof String) solution = (String) quizz.get("solution");
                if (quizz.get("wrong-answer1") instanceof String) wrongAnswer1 = (String) quizz.get("wrong-answer1");
                if (quizz.get("wrong-answer2") instanceof String) wrongAnswer2 = (String) quizz.get("wrong-answer2");
                if (quizz.get("wrong-answer3") instanceof String) wrongAnswer3 = (String) quizz.get("wrong-answer3");
                if (quizz.get("info-id") instanceof Long) infoID = (long) quizz.get("info-id");

                ContentValues values = new ContentValues();
                values.put(QuizzesTable.COLUMN_QUIZ_ID, id);
                values.put(QuizzesTable.COLUMN_LOCATION, location);
                values.put(QuizzesTable.COLUMN_QUESTION, question);
                values.put(QuizzesTable.COLUMN_SOLUTION, solution);
                values.put(QuizzesTable.COLUMN_WRONG_ANSWER_1, wrongAnswer1);
                values.put(QuizzesTable.COLUMN_WRONG_ANSWER_2, wrongAnswer2);
                values.put(QuizzesTable.COLUMN_WRONG_ANSWER_3, wrongAnswer3);
                values.put(QuizzesTable.COLUMN_INFO_ID, infoID);

                getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_QUIZZES, values);
            }
        }
    }
}