package com.exuberant.rest.survey;

/**
 * Created by rakesh on 23-Sep-2017.
 */
public class QuestionBank {
    private String inputFile;
    private int totalQuestions;
    private int questionVersion;
    private int freeQuestions = 200;
    private final int playStoreVersion;
    private final boolean showAd;
    private final boolean enablePrashna;

    public QuestionBank(String inputFile, int freeQuestions, int totalQuestions, int questionVersion, int playStoreVersion, boolean showAd) {
        this(inputFile, freeQuestions, totalQuestions, questionVersion, playStoreVersion, showAd, false);
    }

    public QuestionBank(String inputFile, int freeQuestions, int totalQuestions, int questionVersion, int playStoreVersion, boolean showAd, boolean enablePrashna) {
        this.inputFile = inputFile;
        this.totalQuestions = totalQuestions;
        this.freeQuestions = freeQuestions;
        this.questionVersion = questionVersion;
        this.playStoreVersion = playStoreVersion;
        this.showAd = showAd;
        this.enablePrashna = enablePrashna;
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

    public boolean isEnablePrashna() {
        return enablePrashna;
    }

    public int getFreeQuestions() {
        return freeQuestions;
    }
}
