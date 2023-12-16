package com.wapss.digo360.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SettingHomeResponse {
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

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("banner1")
        @Expose
        private String banner1;
        @SerializedName("banner2")
        @Expose
        private String banner2;
        @SerializedName("version")
        @Expose
        private String version;
        @SerializedName("maintenance")
        @Expose
        private Boolean maintenance;
        @SerializedName("slider")
        @Expose
        private List<Slider> slider;
        @SerializedName("banner")
        @Expose
        private List<Banner> banner;

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

        public String getBanner1() {
            return banner1;
        }

        public void setBanner1(String banner1) {
            this.banner1 = banner1;
        }

        public String getBanner2() {
            return banner2;
        }

        public void setBanner2(String banner2) {
            this.banner2 = banner2;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public Boolean getMaintenance() {
            return maintenance;
        }

        public void setMaintenance(Boolean maintenance) {
            this.maintenance = maintenance;
        }

        public List<Slider> getSlider() {
            return slider;
        }

        public void setSlider(List<Slider> slider) {
            this.slider = slider;
        }

        public List<Banner> getBanner() {
            return banner;
        }

        public void setBanner(List<Banner> banner) {
            this.banner = banner;
        }
    }
    public class Banner {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
    public class Slider {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("image")
        @Expose
        private String image;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}