package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.loaders;

import android.content.AsyncTaskLoader;
import android.content.ContentValues;
import android.content.Context;

import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.contentproviders.WeltkulturerbeContentProvider;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.databases.QuizzesTable;
import com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.xml.XmlLoader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

/**
 * This Loader loads the quizzes into the {@link com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.databases.WeltkulturerbeDatabaseHelper}
 *
 * @author Projekt-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public class QuizzesLoader extends AsyncTaskLoader {

    public QuizzesLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Object loadInBackground() {
        try {
            loadQuizzesInDatase();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void loadQuizzesInDatase() throws XmlPullParserException, IOException{
        XmlLoader xmlLoader = new XmlLoader().setParentTag("quiz")
                .setSearchedTags("quiz-id", "question", "solution", "wrong-answer1", "wrong-answer2", "wrong-answer3")
                .load(getContext(), "quizzes.xml");
        for (List<String[]> quizTag : xmlLoader.getResultsByParentTag()) {
            ContentValues values = new ContentValues();
            for (String[] childTag : quizTag) {
                switch (childTag[XmlLoader.INDEX_TAG_NAME]) {
                    case "quiz-id":
                        values.put(QuizzesTable.COLUMN_QUIZ_ID, Integer.parseInt(childTag[XmlLoader.INDEX_TEXT]));
                        break;
                    case "question":
                        values.put(QuizzesTable.COLUMN_QUESTION, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "solution":
                        values.put(QuizzesTable.COLUMN_SOLUTION, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "wrong-answer1":
                        values.put(QuizzesTable.COLUMN_WRONG_ANSWER_1, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "wrong-answer2":
                        values.put(QuizzesTable.COLUMN_WRONG_ANSWER_2, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                    case "wrong-answer3":
                        values.put(QuizzesTable.COLUMN_WRONG_ANSWER_3, childTag[XmlLoader.INDEX_TEXT]);
                        break;
                }
            }
            getContext().getContentResolver().insert(WeltkulturerbeContentProvider.URI_TABLE_QUIZZES, values);
        }
    }
}