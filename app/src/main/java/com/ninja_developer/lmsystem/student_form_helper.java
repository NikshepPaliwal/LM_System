package com.ninja_developer.lmsystem;

public class student_form_helper {
    String student_name;
    String roll;
    String className;
    String branch;

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    String imageUri;

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    String father_name;

    public student_form_helper(String student_name, String roll, String className, String branch, String father_name, String mobile_number, String address, String imgUri) {
        this.student_name = student_name;
        this.roll = roll;
        this.className = className;
        this.branch = branch;
        this.father_name = father_name;
        this.mobile_number = mobile_number;
        this.address = address;
        this.imageUri=imgUri;
    }

    String mobile_number;
    String address;

}
