package com.example.admin.faculty;

public class TeacherData {
    String name;
    String post;
    String email;
    String image;

    String number;
    String qualification;
    String password,conPassword;
    String key;

    public TeacherData(){

    }

    public String getQualification() {
        return qualification;
    }


    public TeacherData(String password) {
        this.password = password;
    }

    public TeacherData(String name, String post, String email, String number,  String image, String qualification, String password) {
        this.name = name;
        this.post = post;
        this.email = email;
        this.number = number;


        this.image = image;
        this.qualification=qualification;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public String getKey() {
        return key;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
