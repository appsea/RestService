package com.exuberant.rest.survey;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class QuestionBank {
    private String inputFile;
    private int totalQuestions;
    private int questionVersion;
    private final int playStoreVersion;
    private final boolean showAd;

    public QuestionBank(String inputFile, int totalQuestions, int questionVersion, int playStoreVersion, boolean showAd) {
        this.inputFile = inputFile;
        this.totalQuestions = totalQuestions;
        this.questionVersion = questionVersion;
        this.playStoreVersion = playStoreVersion;
        this.showAd = showAd;
    }

    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getQuestionVersion() {
        return questionVersion;
    }

    public void setQuestionVersion(int questionVersion) {
        this.questionVersion = questionVersion;
    }

    public int getPlayStoreVersion() {
        return playStoreVersion;
    }

    public boolean isShowAd() {
        return showAd;
    }
}
