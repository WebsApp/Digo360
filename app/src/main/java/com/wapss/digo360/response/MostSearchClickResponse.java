package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MostSearchClickResponse {
    @SerializedName("payload")
    @Expose
    private Boolean payload;

    public Boolean getPayload() {
        return payload;
    }

    public void setPayload(Boolean payload) {
        this.payload = payload;
    }
}
