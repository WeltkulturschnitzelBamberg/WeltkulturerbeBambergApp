package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.databases;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "quizzes" in the Database
 * {@link WeltkulturerbeDatabaseHelper}
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class QuizzesTable {

    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private QuizzesTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** Name of the table in the database **/
    public static final String TABLE_QUIZZES = "quizzes";

    /** Names of the columns inside the table **/
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_SOLUTION = "solution";
    public static final String COLUMN_WRONG_ANSWER_1 = "wrong_answer1";
    public static final String COLUMN_WRONG_ANSWER_2 = "wrong_answer2";
    public static final String COLUMN_WRONG_ANSWER_3 = "wrong_answer3";

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_QUIZZES
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUIZ_ID + " INTEGER NOT NULL,"
            + COLUMN_QUESTION + " TEXT NOT NULL,"
            + COLUMN_SOLUTION + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_1 + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_2 + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_3 + " TEXT NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }

    public static void onUpgrade() {
        //TODO onUpgrade() schreiben
    }
}