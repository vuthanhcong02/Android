package com.btl.quizapp.database;

import android.provider.BaseColumns;

public final class Table {

    private Table() {}
    public static class UsersTable implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PASSWORD = "password";
    }
    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "categories";
        public static final String COLUMN_NAME = "name";
    }
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_QUESTION = "question_title";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_OPTION4 = "option4";
        public static final String COLUMN_ANSWER = "answer_question";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }
    public static class HistoryQuizTable implements BaseColumns {
        public static final String TABLE_NAME = "history_quiz";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_CATEGORY_ID = "question_id";

    }
}
