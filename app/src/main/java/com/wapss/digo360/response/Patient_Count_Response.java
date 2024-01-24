package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Patient_Count_Response {
    @SerializedName("result")
    @Expose
    private List<Result> result;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public class PatientDetail {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("pid")
        @Expose
        private String pid;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("age")
        @Expose
        private String age;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("dob")
        @Expose
        private String dob;
        @SerializedName("accountId")
        @Expose
        private String accountId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

    }

    public class Result {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("patientDetail")
        @Expose
        private PatientDetail patientDetail;
        @SerializedName("disease")
        @Expose
        private Disease disease;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public PatientDetail getPatientDetail() {
            return patientDetail;
        }

        public void setPatientDetail(PatientDetail patientDetail) {
            this.patientDetail = patientDetail;
        }

        public Disease getDisease() {
            return disease;
        }

        public void setDisease(Disease disease) {
            this.disease = disease;
        }

    }
    public class Disease {

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