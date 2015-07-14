package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentprovider.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database.QuizzesTable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * This AsyncTaskLoader loads the quizzes into the Weltkulturschnitzel Database
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class QuizzesAsyncTaskLoader extends AsyncTaskLoader {

    public static final int LOADER_ID = 2;

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

    private void writeQuizzesToDatabase() throws IOException, ParserConfigurationException, SAXException {
        Document doc = loadFile();

        NodeList quizzesList = doc.getElementsByTagName(Quizzes.TAG_QUIZ);
        for (int i = 0; i < quizzesList.getLength(); i++) {
            Element quiz = (Element) quizzesList.item(i);
            ContentValues values = new ContentValues();

            String quizID = null;
            String question = null;
            String location = null;
            String solution = null;
            String wrongAnswer1 = null;
            String wrongAnswer2 = null;
            String wrongAnswer3 = null;

            if (quiz.getElementsByTagName(Quizzes.TAG_QUIZ_ID).getLength() > 0) quizID = quiz.getElementsByTagName(Quizzes.TAG_QUIZ_ID).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_LOCATION).getLength() > 0) location = quiz.getElementsByTagName(Quizzes.TAG_LOCATION).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_QUESTION).getLength() > 0) question = quiz.getElementsByTagName(Quizzes.TAG_QUESTION).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_SOLUTION).getLength() > 0) solution = quiz.getElementsByTagName(Quizzes.TAG_SOLUTION).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_1).getLength() > 0) wrongAnswer1 = quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_1).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_2).getLength() > 0) wrongAnswer2 = quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_2).item(0).getTextContent();
            if (quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_3).getLength() > 0) wrongAnswer3 = quiz.getElementsByTagName(Quizzes.TAG_WRONG_ANSWER_3).item(0).getTextContent();

            values.put(QuizzesTable.COLUMN_QUIZ_ID, quizID);
            values.put(QuizzesTable.COLUMN_LOCATION, location);
            values.put(QuizzesTable.COLUMN_QUESTION, question);
            values.put(QuizzesTable.COLUMN_SOLUTION, solution);
            values.put(QuizzesTable.COLUMN_WRONG_ANSWER_1, wrongAnswer1);
            values.put(QuizzesTable.COLUMN_WRONG_ANSWER_2, wrongAnswer2);
            values.put(QuizzesTable.COLUMN_WRONG_ANSWER_3, wrongAnswer3);
            getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_QUIZZES, values);
        }
    }

    private Document loadFile() throws IOException, ParserConfigurationException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        InputSource inputSource = new InputSource(getContext().getAssets().open(Quizzes.FILENAME));
        Document doc = docBuilder.parse(inputSource);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private static class Quizzes {

        private static final String FILENAME = "quizzes.xml";
        private static final String TAG_QUIZ = "quiz";
        private static final String TAG_QUIZ_ID = "id";
        private static final String TAG_LOCATION = "location";
        private static final String TAG_QUESTION = "question";
        private static final String TAG_SOLUTION = "solution";
        private static final String TAG_WRONG_ANSWER_1 = "wrong-answer1";
        private static final String TAG_WRONG_ANSWER_2 = "wrong-answer2";
        private static final String TAG_WRONG_ANSWER_3 = "wrong-answer3";
    }
}