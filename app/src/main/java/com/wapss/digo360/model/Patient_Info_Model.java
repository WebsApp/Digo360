package com.wapss.digo360.model;

public class Patient_Info_Model {
    private String name;
    private String gender;
    private String id;

    public Patient_Info_Model(String name, String gender, String id) {
        this.name = name;
        this.gender = gender;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
