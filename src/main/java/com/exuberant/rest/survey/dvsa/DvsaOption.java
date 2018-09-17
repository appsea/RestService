package com.exuberant.rest.survey.dvsa;

import lombok.Data;

public class DvsaOption {
    private String text;
    private boolean isCorrect;
    private String picture;

    public boolean getIsCorrect(){
        return isCorrect;
    }

    public String getText() {
        return text;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
