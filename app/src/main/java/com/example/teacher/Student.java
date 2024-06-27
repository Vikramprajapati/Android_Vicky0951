package com.example.teacher;

public class Student {
    private String rollNo;
    private String name;
    private String branch;
    private String semester;
    private String year;

    public Student(String rollNo, String name, String branch, String semester, String year) {
        this.rollNo = rollNo;
        this.name = name;
        this.branch = branch;
        this.semester = semester;
        this.year = year;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
