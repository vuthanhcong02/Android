package com.btl.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.btl.quizapp.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String USERS_TABLE = "CREATE TABLE " +
                Table.UsersTable.TABLE_NAME + " ( " +
                Table.UsersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.UsersTable.COLUMN_USERNAME + " TEXT, " +
                Table.UsersTable.COLUMN_EMAIL + " TEXT, " +
                Table.UsersTable.COLUMN_PASSWORD + " TEXT" +
                " );";
        final String CATEGORIES_TABLE = "CREATE TABLE " +
                Table.CategoriesTable.TABLE_NAME + " ( " +
                Table.CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.CategoriesTable.COLUMN_NAME + " TEXT" +
                " );";

        final String QUESTIONS_TABLE = "CREATE TABLE " +
                Table.QuestionsTable.TABLE_NAME + " ( " +
                Table.QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                Table.QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                Table.QuestionsTable.COLUMN_ANSWER + " INTEGER, " +
                Table.QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                " FOREIGN KEY (" + Table.QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                Table.CategoriesTable.TABLE_NAME + "(" + Table.CategoriesTable._ID + ")" + " ON DELETE CASCADE" + " ON UPDATE CASCADE" + ");";

        final String HISTORY_QUIZ_TABLE = "CREATE TABLE " +
                Table.HistoryQuizTable.TABLE_NAME + " ( " +
                Table.HistoryQuizTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Table.HistoryQuizTable.COLUMN_SCORE + " INTEGER, " +
                Table.HistoryQuizTable.COLUMN_USER_ID + " INTEGER, " +
                Table.HistoryQuizTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                " FOREIGN KEY (" + Table.HistoryQuizTable.COLUMN_USER_ID + ") REFERENCES " +
                Table.UsersTable.TABLE_NAME + "(" + Table.UsersTable._ID + ")" + " ON DELETE CASCADE" + " ON UPDATE CASCADE" + ");";

        db.execSQL(USERS_TABLE);
        db.execSQL(CATEGORIES_TABLE);
        db.execSQL(QUESTIONS_TABLE);
        db.execSQL(HISTORY_QUIZ_TABLE);
        }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table.UsersTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Table.HistoryQuizTable.TABLE_NAME);
        onCreate(db);
    }

    public Boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.UsersTable.COLUMN_USERNAME, user.getUsername());
        contentValues.put(Table.UsersTable.COLUMN_EMAIL, user.getEmail());
        contentValues.put(Table.UsersTable.COLUMN_PASSWORD, user.getPassword());
        long result = db.insert(Table.UsersTable.TABLE_NAME, null, contentValues);
        if(result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
