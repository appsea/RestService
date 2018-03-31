package com.exuberant.rest.survey.model;

public class QuestionWrapper {

    private Question question;

    public QuestionWrapper(Question question) {
        this.question = question;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public Question getQuestion() {
        return question;
    }

    @Override
    public boolean equals(Object obj) {
        QuestionWrapper other = (QuestionWrapper) obj;
        boolean equals = this.flattenIt(this.question.getDescription()).equals(this.flattenIt(other.question.getDescription())) && this.flattenIt(this.question.getOptions().toString()).equals(this.flattenIt(other.question.getOptions().toString()));
        if (equals) {
            System.err.println("----------Duplicate Questions Start---------------");
            System.err.println(this.question.getDescription());
            System.err.println("-------------------------");
            /*if(!this.question.getId().equals(other.question.getId())){
                System.err.println("Repeat: " + this.question.getId() + " = " + other.question.getId());
                System.err.println("-------------------------");
                System.err.println(this.question.getDescription());
                System.err.println("-------------------------");
                System.err.println(other.question.getDescription());
                System.err.println("-----------END--------------");
            }*/
        }
        return equals;
    }

    public String flattenIt(String string){
        return string.replaceAll("\n", " ").replaceAll("\\s{2,}", " ");
    }
}
