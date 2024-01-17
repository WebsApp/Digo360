package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationResponse {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("pincode")
        @Expose
        private String pincode;
        @SerializedName("image")
        @Expose
        private Object image;
        @SerializedName("imagePath")
        @Expose
        private Object imagePath;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("accountId")
        @Expose
        private String accountId;
        @SerializedName("updatedId")
        @Expose
        private String updatedId;
        @SerializedName("studyYear")
        @Expose
        private String studyYear;
        @SerializedName("areaId")
        @Expose
        private Integer areaId;
        @SerializedName("experienceLevel")
        @Expose
        private String experienceLevel;
        @SerializedName("stateId")
        @Expose
        private Integer stateId;
        @SerializedName("cityId")
        @Expose
        private Integer cityId;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPincode() {
            return pincode;
        }

        public void setPincode(String pincode) {
            this.pincode = pincode;
        }

        public Object getImage() {
            return image;
        }

        public void setImage(Object image) {
            this.image = image;
        }

        public Object getImagePath() {
            return imagePath;
        }

        public void setImagePath(Object imagePath) {
            this.imagePath = imagePath;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getUpdatedId() {
            return updatedId;
        }

        public void setUpdatedId(String updatedId) {
            this.updatedId = updatedId;
        }

        public String getStudyYear() {
            return studyYear;
        }

        public void setStudyYear(String studyYear) {
            this.studyYear = studyYear;
        }

        public Integer getAreaId() {
            return areaId;
        }

        public void setAreaId(Integer areaId) {
            this.areaId = areaId;
        }

        public String getExperienceLevel() {
            return experienceLevel;
        }

        public void setExperienceLevel(String experienceLevel) {
            this.experienceLevel = experienceLevel;
        }

        public Integer getStateId() {
            return stateId;
        }

        public void setStateId(Integer stateId) {
            this.stateId = stateId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
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
