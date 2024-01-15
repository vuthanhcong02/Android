package com.btl.quizapp.model;

public class Question {
    private int id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer;
    private int categoryId;

    public Question(int id, String questionTitle, String option1, String option2, String option3, String option4, int answer, int categoryId) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.categoryId = categoryId;
    }

    public int getId() {
        return id;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswer() {
        return answer;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
