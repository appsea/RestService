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
        boolean equals = this.flattenIt(this.question.getQuestion()).equals(this.flattenIt(other.question.getQuestion())) && this.flattenIt(this.question.getOptions().toString()).equals(this.flattenIt(other.question.getOptions().toString()));
        if (equals) {
            /*if(!this.question.getId().equals(other.question.getId())){
                System.err.println("Repeat: " + this.question.getId() + " = " + other.question.getId());
                System.err.println("-------------------------");
                System.err.println(this.question.getQuestion());
                System.err.println("-------------------------");
                System.err.println(other.question.getQuestion());
                System.err.println("-----------END--------------");
            }*/
        }
        return equals;
    }

    public String flattenIt(String string){
        return string.replaceAll("\n", " ").replaceAll("\\s{2,}", " ");
    }
}
