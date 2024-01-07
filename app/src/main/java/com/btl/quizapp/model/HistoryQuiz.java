package com.btl.quizapp.model;

public class HistoryQuiz {

    private int id;
    private int score;
    private int userID;
    private int categoryID;
    public HistoryQuiz() {}
    public HistoryQuiz(int score, int userID, int categoryID) {
        this.score = score;
        this.userID = userID;
        this.categoryID = categoryID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    @Override
    public String toString() {
        return "HistoryQuiz{" +
                "id=" + id +
                ", score=" + score +
                ", userID=" + userID +
                ", categoryID=" + categoryID +
                '}';
    }
}
