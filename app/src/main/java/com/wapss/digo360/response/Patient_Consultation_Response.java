package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Patient_Consultation_Response {
        @SerializedName("bp")
        @Expose
        private String bp;
        @SerializedName("pulseRate")
        @Expose
        private String pulseRate;
        @SerializedName("weight")
        @Expose
        private String weight;
        @SerializedName("height")
        @Expose
        private String height;
        @SerializedName("temperature")
        @Expose
        private String temperature;
        @SerializedName("sugar")
        @Expose
        private String sugar;
        @SerializedName("allergy")
        @Expose
        private String allergy;
        @SerializedName("surgery")
        @Expose
        private String surgery;
        @SerializedName("infection")
        @Expose
        private String infection;
        @SerializedName("operated")
        @Expose
        private String operated;
        @SerializedName("patientDetailId")
        @Expose
        private String patientDetailId;
        @SerializedName("diseaseId")
        @Expose
        private String diseaseId;
        @SerializedName("doctorDetailId")
        @Expose
        private String doctorDetailId;
        @SerializedName("otherProblem")
        @Expose
        private Object otherProblem;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getBp() {
            return bp;
        }

        public void setBp(String bp) {
            this.bp = bp;
        }

        public String getPulseRate() {
            return pulseRate;
        }

        public void setPulseRate(String pulseRate) {
            this.pulseRate = pulseRate;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getTemperature() {
            return temperature;
        }

        public void setTemperature(String temperature) {
            this.temperature = temperature;
        }

        public String getSugar() {
            return sugar;
        }

        public void setSugar(String sugar) {
            this.sugar = sugar;
        }

        public String getAllergy() {
            return allergy;
        }

        public void setAllergy(String allergy) {
            this.allergy = allergy;
        }

        public String getSurgery() {
            return surgery;
        }

        public void setSurgery(String surgery) {
            this.surgery = surgery;
        }

        public String getInfection() {
            return infection;
        }

        public void setInfection(String infection) {
            this.infection = infection;
        }

        public String getOperated() {
            return operated;
        }

        public void setOperated(String operated) {
            this.operated = operated;
        }

        public String getPatientDetailId() {
            return patientDetailId;
        }

        public void setPatientDetailId(String patientDetailId) {
            this.patientDetailId = patientDetailId;
        }

        public String getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(String diseaseId) {
            this.diseaseId = diseaseId;
        }

        public String getDoctorDetailId() {
            return doctorDetailId;
        }

        public void setDoctorDetailId(String doctorDetailId) {
            this.doctorDetailId = doctorDetailId;
        }

        public Object getOtherProblem() {
            return otherProblem;
        }

        public void setOtherProblem(Object otherProblem) {
            this.otherProblem = otherProblem;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
}
