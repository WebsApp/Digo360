package com.wapss.digo360.model;

public class Report_Model {
    private String P_ID;
    private String P_name;
    private String P_gender;
    private String P_age;
    private String P_desease;
    private String Account_Id;

    public Report_Model(String p_ID, String p_name, String p_gender, String p_age, String p_desease, String account_Id) {
        P_ID = p_ID;
        P_name = p_name;
        P_gender = p_gender;
        P_age = p_age;
        P_desease = p_desease;
        Account_Id = account_Id;
    }

    public String getP_ID() {
        return P_ID;
    }

    public void setP_ID(String p_ID) {
        P_ID = p_ID;
    }

    public String getP_name() {
        return P_name;
    }

    public void setP_name(String p_name) {
        P_name = p_name;
    }

    public String getP_gender() {
        return P_gender;
    }

    public void setP_gender(String p_gender) {
        P_gender = p_gender;
    }

    public String getP_age() {
        return P_age;
    }

    public void setP_age(String p_age) {
        P_age = p_age;
    }

    public String getP_desease() {
        return P_desease;
    }

    public void setP_desease(String p_desease) {
        P_desease = p_desease;
    }

    public String getAccount_Id() {
        return Account_Id;
    }

    public void setAccount_Id(String account_Id) {
        Account_Id = account_Id;
    }
}
