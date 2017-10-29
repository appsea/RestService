package com.exuberant.survey.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by rakesh on 22-Sep-2017.
 */
public class Option implements Comparable<Option> {

    public static final Log log = LogFactory.getLog(Option.class);

    private String tag = "";
    private String description;
    private boolean isCorrect;

    public Option(String description) {
        this.description = description;
        if(description.startsWith("A.")){
            this.tag = "A";
        }else if(description.startsWith("B.")){
            this.tag = "B";
        }else if(description.startsWith("C.")){
            this.tag = "C";
        }else if(description.startsWith("D.")){
            this.tag = "D";
        }else {
            log.info("Received Invalid Description: " + description);
        }
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public int compareTo(Option o) {
        return this.tag.compareTo(o.tag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Option option = (Option) o;

        return description != null ? description.equals(option.description) : option.description == null;
    }

    @Override
    public int hashCode() {
        return description != null ? description.hashCode() : 0;
    }
}
