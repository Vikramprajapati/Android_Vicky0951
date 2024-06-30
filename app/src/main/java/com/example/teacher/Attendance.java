package com.example.teacher;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Attendance implements Parcelable {

    private String rollNo;
    private String name;
    private String date;
    private String subject;
    private String status;
    private String branch;
    private String semester;
    private String year;

    public Attendance(String rollNo, String name, String date,String year, String branch, String semester, String subject, String status) {
        this.rollNo = rollNo;
        this.name = name;
        this.date = date;
        this.year=year;
        this.branch=branch;
        this.semester=semester;
        this.subject = subject;
        this.status = status;
    }
    public Attendance(String rollNo, String name, String status) {
        this.rollNo = rollNo;
        this.name = name;
        this.status = status;
    }
    protected Attendance(Parcel in) {
        rollNo = in.readString();
        name = in.readString();
        date = in.readString();
        year=in.readString();
        branch=in.readString();
        semester=in.readString();
        subject = in.readString();
        status = in.readString();
    }
    public static final Creator<Attendance> CREATOR = new Creator<Attendance>() {
        @Override
        public Attendance createFromParcel(Parcel in) {
            return new Attendance(in);
        }

        @Override
        public Attendance[] newArray(int size) {
            return new Attendance[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(rollNo);
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(year);
        dest.writeString(branch);
        dest.writeString(semester);
        dest.writeString(subject);
        dest.writeString(status);
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
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
}
