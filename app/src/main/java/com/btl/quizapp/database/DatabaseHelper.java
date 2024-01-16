package com.btl.quizapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import androidx.annotation.Nullable;

import com.btl.quizapp.model.Question;
import com.btl.quizapp.model.QuizCategory;
import com.btl.quizapp.model.User;

import java.util.ArrayList;

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

    //Nanh
    public ArrayList<QuizCategory> getAllCategories() {
        ArrayList<QuizCategory> categoriesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                Table.CategoriesTable._ID,
                Table.CategoriesTable.COLUMN_NAME
        };

        Cursor cursor = db.query(
                Table.CategoriesTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String categoryName = cursor.getString(cursor.getColumnIndexOrThrow(Table.CategoriesTable.COLUMN_NAME));
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Table.CategoriesTable._ID));
                categoriesList.add(new QuizCategory(id,categoryName));
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return categoriesList;
    }

    // Thêm phương thức để lấy danh sách câu hỏi ngẫu nhiên theo ID của category
    public ArrayList<Question> getRandomQuestions(int categoryId, int limit) {
        ArrayList<Question> questionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Thực hiện truy vấn để lấy danh sách câu hỏi ngẫu nhiên
        String query = "SELECT * FROM " + Table.QuestionsTable.TABLE_NAME +
                " WHERE " + Table.QuestionsTable.COLUMN_CATEGORY_ID + " = ?" +
                " ORDER BY RANDOM() LIMIT ?";
        String[] selectionArgs = {String.valueOf(categoryId), String.valueOf(limit)};

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Đọc thông tin từ Cursor và thêm vào danh sách
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Table.QuestionsTable._ID));
                String questionTitle = cursor.getString(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_QUESTION));
                String option1 = cursor.getString(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION1));
                String option2 = cursor.getString(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION2));
                String option3 = cursor.getString(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION3));
                String option4 = cursor.getString(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_OPTION4));

                int answer = cursor.getInt(cursor.getColumnIndexOrThrow(Table.QuestionsTable.COLUMN_ANSWER));

                Question question = new Question(id, questionTitle, option1, option2, option3, option4, answer, categoryId);
                questionList.add(question);
            } while (cursor.moveToNext());

            cursor.close();
        }

        db.close();
        return questionList;
    }



    // <>

    public Boolean addUser(User user) {
        db = this.getWritableDatabase();
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
    public Boolean checkUsernamePassword(String username, String password) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " +
                Table.UsersTable.TABLE_NAME + " where " +
                Table.UsersTable.COLUMN_USERNAME +" = ? and " +
                Table.UsersTable.COLUMN_PASSWORD +" = ?",
                new String[] {username, password});
        if(cursor.getCount() > 0) {
            return true;
        }
        return false;
    }
    public User getUserData(String username, String password) {
        db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " +
                        Table.UsersTable.TABLE_NAME + " where " +
                        Table.UsersTable.COLUMN_USERNAME + " = ? and " +
                        Table.UsersTable.COLUMN_PASSWORD + " = ?",
                new String[]{username, password});
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user = new User();
            user.setId(cursor.getInt(0));
            user.setUsername(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));
            return user;
        }
        return null;
    }
    public void updatePassword(int id, String password) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Table.UsersTable.COLUMN_PASSWORD, password);
        db.update(Table.UsersTable.TABLE_NAME, contentValues,
                Table.UsersTable._ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    public long addQuizHistory(int score, int userId, int categoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Table.HistoryQuizTable.COLUMN_SCORE, score);
        values.put(Table.HistoryQuizTable.COLUMN_USER_ID, userId);
        values.put(Table.HistoryQuizTable.COLUMN_CATEGORY_ID, categoryId);

        // Thực hiện chèn dữ liệu và nhận lại ID của dòng được chèn (nếu thành công)
        long result = db.insert(Table.HistoryQuizTable.TABLE_NAME, null, values);
        db.close();
        return result;
    }
}
