package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionResponse {
    @SerializedName("question")
    @Expose
    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public class Question {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("question")
        @Expose
        private String question;
        @SerializedName("diseaseAnswer")
        @Expose
        private List<DiseaseAnswer> diseaseAnswer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public List<DiseaseAnswer> getDiseaseAnswer() {
            return diseaseAnswer;
        }

        public void setDiseaseAnswer(List<DiseaseAnswer> diseaseAnswer) {
            this.diseaseAnswer = diseaseAnswer;
        }
    }
    public class DiseaseAnswer {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("answer")
        @Expose
        private String answer;
        @SerializedName("next")
        @Expose
        private String next;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getNext() {
            return next;
        }

        public void setNext(String next) {
            this.next = next;
        }
    }
}
