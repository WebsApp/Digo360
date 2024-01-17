package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateResponse {
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
    public static class Result {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("status")
        @Expose
        private Boolean status;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }
}
