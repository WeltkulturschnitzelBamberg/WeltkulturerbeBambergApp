package com.github.weltkulturschnitzelbamberg.weltkulturerbebambergapp.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * This class describes the table "quizzes" in the Database {@link WeltkulturerbeDatabaseHelper} which contains
 * different quizzes. Each quiz has a question, a solution, three wrong answers and the ID of a Information.
 * The corresponding Information is saved inside the {@link InformationTable} with the same ID in its
 * Column {@link InformationTable#COLUMN_INFORMATION_ID}
 *
 * @author Project-Seminar "Schnitzeljagd World-heritage" 2015/2016 des Clavius Gymnasiums Bamberg
 * @version 1.0
 * @since 2015-06-04
 */
public final class QuizzesTable {

    /** Name of the table in the database **/
    public static final String TABLE_QUIZZES = "quizzes";

    /** Name of the Column containing the ID (index) of each Row **/
    public static final String COLUMN_ID = "_id";
    /** Name of the Column containing the ID of each Quiz **/
    public static final String COLUMN_QUIZ_ID = "quiz_id";
    /** Name of the Column containing the location of the each Quiz **/
    public static final String COLUMN_LOCATION = "location";
    /** Name of the Column containing the Question of each Quiz **/
    public static final String COLUMN_QUESTION = "question";
    /** Name of the Column containing the Solution to each Quiz **/
    public static final String COLUMN_SOLUTION = "solution";
    /** Name of the Column containing the first wrong answer to each Quiz **/
    public static final String COLUMN_WRONG_ANSWER_1 = "wrong_answer1";
    /** Name of the Column containing the second wrong answer to each Quiz **/
    public static final String COLUMN_WRONG_ANSWER_2 = "wrong_answer2";
    /** Name of the Column containing the third wrong answer to each Quiz **/
    public static final String COLUMN_WRONG_ANSWER_3 = "wrong_answer3";
    /** Name of the Column containing the ID of the Information belonging to each Quiz **/
    public static final String COLUMN_INFO_ID = "info_id";


    /** Private constructor to prevent instantiation. If this class is instantiated,
     * it throws a {@link IllegalAccessException}
     * @throws IllegalAccessException
     */
    private QuizzesTable() throws IllegalAccessException {
        throw new IllegalAccessException("'" + this.getClass().getName() + "' should not be instantiated");
    }

    /** SQL command to create the table **/
    private static final String SQL_CREATE = "CREATE TABLE " + TABLE_QUIZZES
            + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_QUIZ_ID + " INTEGER NOT NULL,"
            + COLUMN_LOCATION + " TEXT NOT NULL,"
            + COLUMN_QUESTION + " TEXT NOT NULL,"
            + COLUMN_SOLUTION + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_1 + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_2 + " TEXT NOT NULL,"
            + COLUMN_WRONG_ANSWER_3 + " TEXT NOT NULL,"
            + COLUMN_INFO_ID + " INTEGER NOT NULL"
            + ");";

    /**
     * Add this Table to a SQLite Database
     * @param database {@link SQLiteDatabase} The Database you want to add this Table to
     */
    public static void addToDatabase(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE);
    }
}