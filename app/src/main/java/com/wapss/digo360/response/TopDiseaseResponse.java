package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopDiseaseResponse {
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

        @SerializedName("disease_name")
        @Expose
        private String diseaseName;
        @SerializedName("disease_image")
        @Expose
        private String diseaseImage;
        @SerializedName("disease_imagePath")
        @Expose
        private String diseaseImagePath;
        @SerializedName("diseaseId")
        @Expose
        private String diseaseId;
        @SerializedName("searchCount")
        @Expose
        private String searchCount;

        public String getDiseaseName() {
            return diseaseName;
        }

        public void setDiseaseName(String diseaseName) {
            this.diseaseName = diseaseName;
        }

        public String getDiseaseImage() {
            return diseaseImage;
        }

        public void setDiseaseImage(String diseaseImage) {
            this.diseaseImage = diseaseImage;
        }

        public String getDiseaseImagePath() {
            return diseaseImagePath;
        }

        public void setDiseaseImagePath(String diseaseImagePath) {
            this.diseaseImagePath = diseaseImagePath;
        }

        public String getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(String diseaseId) {
            this.diseaseId = diseaseId;
        }

        public String getSearchCount() {
            return searchCount;
        }

        public void setSearchCount(String searchCount) {
            this.searchCount = searchCount;
        }
    }

}
