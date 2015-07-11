package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity;

import android.app.Activity;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.R;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.QuizzesTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.utilities.DebugUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class QuizActivity extends Activity {

    public static final String TAG_QUIZ_ID = "quiz_id";
    public static final int TAG_QUIZ_ID_DEFAULT = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setUpQuiz();
    }

    /**
     * This Method sets up the Quiz Activity. The Quiz depends on the committed QuizID in the starting Intent
     */
    private void setUpQuiz(){
        Quiz quiz = getQuizByID(getQuizIDFromIntent());
        DebugUtils.log(quiz.getQuizId() + "-" + quiz.getQuestion());
    }

    /**
     *
     * @return returns IntExtra with the Quiz ID from the starting Intent
     */
    private int getQuizIDFromIntent(){
        return getIntent().getIntExtra(TAG_QUIZ_ID, TAG_QUIZ_ID_DEFAULT);
    }

    /**
     * This Method queries the SQLite Database for quizzes with the committed ID and returns it as a {@link com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity.QuizActivity.Quiz}
     * @param quizID The QuizID of the quiz to load
     * @return {@link com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.activity.QuizActivity.Quiz} with the first found query result
     */
    private Quiz getQuizByID(int quizID){
        String[] projection = {QuizzesTable.COLUMN_QUIZ_ID, QuizzesTable.COLUMN_QUESTION, QuizzesTable.COLUMN_SOLUTION, QuizzesTable.COLUMN_WRONG_ANSWER_1,
                                QuizzesTable.COLUMN_WRONG_ANSWER_2, QuizzesTable.COLUMN_WRONG_ANSWER_3};
        String selection = QuizzesTable.COLUMN_QUIZ_ID + "=?";
        String[] selectionArgs = {Integer.toString(quizID)};
        String sortOrder = null;
        Cursor cursor = getContentResolver().query(WeltkulturerbeContentProvider.URI_TABLE_QUIZZES, projection, selection, selectionArgs, sortOrder);

        if (cursor.isBeforeFirst()) cursor.moveToNext();
        String question = cursor.getString(cursor.getColumnIndex(QuizzesTable.COLUMN_QUESTION));
        String solution = cursor.getString(cursor.getColumnIndex(QuizzesTable.COLUMN_SOLUTION));
        String wrongAnswer1 = cursor.getString(cursor.getColumnIndex(QuizzesTable.COLUMN_WRONG_ANSWER_1));
        String wrongAnswer2 = cursor.getString(cursor.getColumnIndex(QuizzesTable.COLUMN_WRONG_ANSWER_2));
        String wrongAnswer3 = cursor.getString(cursor.getColumnIndex(QuizzesTable.COLUMN_WRONG_ANSWER_3));

        return new Quiz(quizID, question, solution, wrongAnswer1, wrongAnswer2, wrongAnswer3);
    }

    /**
     * This class represents a Quiz with one question and four different answers. Only one answer is the solution
     */
    private class Quiz {

        private int quizID;
        private final String question;
        private final String solution;
        private final List<String> wrongAnswers;

        public Quiz(int quizID, String question, String solution, String... wrong_answers){
            this.quizID = quizID;
            this.question = question;
            this.solution = solution;
            this.wrongAnswers = Arrays.asList(wrong_answers);
        }

        public int getQuizId(){
            return this.quizID;
        }

        public String getQuestion(){
            return this.question;
        }

        public String getSolution(){
            return this.solution;
        }

        public List<String> getWrongAnswers(){
            return this.wrongAnswers;
        }
    }
}