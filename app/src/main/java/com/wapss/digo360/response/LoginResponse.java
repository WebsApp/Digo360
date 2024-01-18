package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
//    @SerializedName("result")
//    @Expose
//    private Result result;
//
//    public Result getResult() {
//        return result;
//    }
//
//    public void setResult(Result result) {
//        this.result = result;
//    }
//
//    public class Result {
//
//        @SerializedName("latest")
//        @Expose
//        private Boolean latest;
//        @SerializedName("message")
//        @Expose
//        private String message;
//
//        public Boolean getLatest() {
//            return latest;
//        }
//
//        public void setLatest(Boolean latest) {
//            this.latest = latest;
//        }
//
//        public String getMessage() {
//            return message;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }
//    }
}
