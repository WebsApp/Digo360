package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile_Response {

    public class Account {

        @SerializedName("phoneNumber")
        @Expose
        private String phoneNumber;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

    }

    public class Area {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

    public class City {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }

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
        @SerializedName("collegeName")
        @Expose
        private Object collegeName;
        @SerializedName("startDate")
        @Expose
        private Object startDate;
        @SerializedName("endDate")
        @Expose
        private Object endDate;
        @SerializedName("experience")
        @Expose
        private Object experience;
        @SerializedName("studyYear")
        @Expose
        private String studyYear;
        @SerializedName("experienceLevel")
        @Expose
        private String experienceLevel;
        @SerializedName("doctorDetailDegree")
        @Expose
        private List<Object> doctorDetailDegree;
        @SerializedName("city")
        @Expose
        private City city;
        @SerializedName("state")
        @Expose
        private State state;
        @SerializedName("area")
        @Expose
        private Area area;
        @SerializedName("doctorSpecialization")
        @Expose
        private List<Object> doctorSpecialization;
        @SerializedName("account")
        @Expose
        private Account account;

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

        public Object getCollegeName() {
            return collegeName;
        }

        public void setCollegeName(Object collegeName) {
            this.collegeName = collegeName;
        }

        public Object getStartDate() {
            return startDate;
        }

        public void setStartDate(Object startDate) {
            this.startDate = startDate;
        }

        public Object getEndDate() {
            return endDate;
        }

        public void setEndDate(Object endDate) {
            this.endDate = endDate;
        }

        public Object getExperience() {
            return experience;
        }

        public void setExperience(Object experience) {
            this.experience = experience;
        }

        public String getStudyYear() {
            return studyYear;
        }

        public void setStudyYear(String studyYear) {
            this.studyYear = studyYear;
        }

        public String getExperienceLevel() {
            return experienceLevel;
        }

        public void setExperienceLevel(String experienceLevel) {
            this.experienceLevel = experienceLevel;
        }

        public List<Object> getDoctorDetailDegree() {
            return doctorDetailDegree;
        }

        public void setDoctorDetailDegree(List<Object> doctorDetailDegree) {
            this.doctorDetailDegree = doctorDetailDegree;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public Area getArea() {
            return area;
        }

        public void setArea(Area area) {
            this.area = area;
        }

        public List<Object> getDoctorSpecialization() {
            return doctorSpecialization;
        }

        public void setDoctorSpecialization(List<Object> doctorSpecialization) {
            this.doctorSpecialization = doctorSpecialization;
        }

        public Account getAccount() {
            return account;
        }

        public void setAccount(Account account) {
            this.account = account;
        }
    public class State {

        @SerializedName("name")
        @Expose
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    }
}
