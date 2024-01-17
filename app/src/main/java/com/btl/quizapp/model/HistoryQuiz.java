package com.btl.quizapp.model;

public class HistoryQuiz {

    private int id;
    private int score;
    private int userID;
    private int categoryID;

    private String username;
    private String category_name;



    public HistoryQuiz() {}
    public HistoryQuiz(int score, int userID, int categoryID, String username, String category_name) {
        this.score = score;
        this.userID = userID;
        this.categoryID = categoryID;
        this.username = username;
        this.category_name = category_name;
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
    public void setUsername(String username) {
        this.username = username;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    public String getUsername() {
        return username;
    }

    public String getCategory_name() {
        return category_name;
    }

    @Override
    public String toString() {
        return "HistoryQuiz{" +
                "id=" + id +
                ", score=" + score +
                ", userID=" + userID +
                ", categoryID=" + categoryID +
                ", username='" + username + '\'' +
                ", category_name='" + category_name + '\'' +
                '}';
    }
}
