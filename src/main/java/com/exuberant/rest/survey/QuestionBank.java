package com.exuberant.rest.survey;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class QuestionBank {
    private String fileName;
    private int totalQuestions;
    private int version;

    public QuestionBank(String fileName, int totalQuestions, int version) {
        this.fileName = fileName;
        this.totalQuestions = totalQuestions;
        this.version = version;
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
