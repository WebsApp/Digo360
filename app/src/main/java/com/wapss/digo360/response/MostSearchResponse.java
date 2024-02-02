package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MostSearchResponse {
    @SerializedName("result")
    @Expose
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("disease_mostSearchImage")
        @Expose
        private String diseaseMostSearchImage;
        @SerializedName("diseaseId")
        @Expose
        private String diseaseId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("searchCount")
        @Expose
        private String searchCount;

        public String getDiseaseMostSearchImage() {
            return diseaseMostSearchImage;
        }

        public void setDiseaseMostSearchImage(String diseaseMostSearchImage) {
            this.diseaseMostSearchImage = diseaseMostSearchImage;
        }

        public String getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(String diseaseId) {
            this.diseaseId = diseaseId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSearchCount() {
            return searchCount;
        }

        public void setSearchCount(String searchCount) {
            this.searchCount = searchCount;
        }
    }
}
