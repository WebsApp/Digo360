package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OTP_Response {
    @SerializedName("result")
    @Expose
    private Result result;

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("latest")
        @Expose
        private Boolean latest;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Boolean getLatest() {
            return latest;
        }

        public void setLatest(Boolean latest) {
            this.latest = latest;
        }
    }

    public class User {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("deviceId")
        @Expose
        private String deviceId;
        @SerializedName("createdBy")
        @Expose
        private String createdBy;
        @SerializedName("roles")
        @Expose
        private String roles;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("doctorDetail")
        @Expose
        private List<Object> doctorDetail;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDeviceId() {
            return deviceId;
        }

        public void setDeviceId(String deviceId) {
            this.deviceId = deviceId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getRoles() {
            return roles;
        }

        public void setRoles(String roles) {
            this.roles = roles;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<Object> getDoctorDetail() {
            return doctorDetail;
        }

        public void setDoctorDetail(List<Object> doctorDetail) {
            this.doctorDetail = doctorDetail;
        }
    }
}
