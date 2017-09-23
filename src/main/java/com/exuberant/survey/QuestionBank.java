package com.exuberant.survey;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class QuestionBank {
    private String fileName;
    private int totalQuestions;

    public QuestionBank(String fileName, int totalQuestions) {
        this.fileName = fileName;
        this.totalQuestions = totalQuestions;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }
}
