package com.example.admin;

public class StudentData {

    String fatherName,email,mobile,password,conPassword,parentMobile,DOB,address,image,gender,key,sem,branch;


    public StudentData() {
    }

    public StudentData( String fatherName, String email, String mobile, String password, String parentMobile, String DOB,String sem,String branch, String address, String image, String gender){

        this.fatherName = fatherName;
        this.email = email;
        this.mobile = mobile;
        this.password = password;

        this.parentMobile = parentMobile;
        this.DOB = DOB;
        this.address = address;
        this.image = image;
        this.gender = gender;
        this.key = key;
this.sem=sem;
this.branch=branch;

}




    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }





    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getKey() {
        return key;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
