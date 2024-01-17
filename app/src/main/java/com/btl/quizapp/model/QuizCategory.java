package com.btl.quizapp.model;

public class QuizCategory {
    private int categoryId;
    private String categoryName;

    public QuizCategory(int id, String categoryName) {
        this.categoryId = id;
        this.categoryName = categoryName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
