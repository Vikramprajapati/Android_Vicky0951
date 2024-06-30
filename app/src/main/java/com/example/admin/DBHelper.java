package com.example.admin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "COLLEGE.db";
    private static final int DATABASE_VERSION = 1;

    // Table: Students
    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENT_ROLL_NO = "roll_no";
    public static final String COLUMN_STUDENT_NAME = "name";
    public static final String COLUMN_STUDENT_BRANCH = "branch";
    public static final String COLUMN_STUDENT_SEMESTER = "semester";
    public static final String COLUMN_STUDENT_YEAR = "year";

    // Table: Attendance
    public static final String TABLE_ATTENDANCE = "attendance";
    public static final String COLUMN_ATTENDANCE_ROLL_NO = "roll_no";
    public static final String COLUMN_ATTENDANCE_NAME = "name";
    public static final String COLUMN_ATTENDANCE_DATE = "date";
    public static final String COLUMN_ATTENDANCE_SUBJECT = "subject";
    public static final String COLUMN_ATTENDANCE_STATUS = "status";
    public static final String COLUMN_ATTENDANCE_BRANCH ="branch" ;
    public static final String COLUMN_ATTENDANCE_YEAR ="year" ;
    public static final String COLUMN_ATTENDANCE_SEM = "semester";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createStudentsTable = "CREATE TABLE " + TABLE_STUDENTS + " (" +
                COLUMN_STUDENT_ROLL_NO + " TEXT PRIMARY KEY, " +
                COLUMN_STUDENT_NAME + " TEXT, " +
                COLUMN_STUDENT_BRANCH + " TEXT, " +
                COLUMN_STUDENT_SEMESTER + " TEXT, " +
                COLUMN_STUDENT_YEAR + " TEXT)";

        String createAttendanceTable = "CREATE TABLE " + TABLE_ATTENDANCE + " (" +
                COLUMN_ATTENDANCE_ROLL_NO + " integer, " +
                COLUMN_ATTENDANCE_NAME + " TEXT not null, " +
                COLUMN_ATTENDANCE_DATE + " TEXT not null, " +
                COLUMN_ATTENDANCE_YEAR + " TEXT not null, "+
                COLUMN_ATTENDANCE_BRANCH + " TEXT not null, "+
                COLUMN_ATTENDANCE_SEM+ " TEXT not null, "+
                COLUMN_ATTENDANCE_SUBJECT + " TEXT not null, " +
                COLUMN_ATTENDANCE_STATUS + " TEXT not null)" ;


        db.execSQL(createStudentsTable);
        db.execSQL(createAttendanceTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }
    // Methods for Students table
    public boolean insertStudent(String rollNo, String name, String branch, String semester, String year) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_STUDENT_ROLL_NO, rollNo);
        contentValues.put(COLUMN_STUDENT_NAME, name);
        contentValues.put(COLUMN_STUDENT_BRANCH, branch);
        contentValues.put(COLUMN_STUDENT_SEMESTER, semester);
        contentValues.put(COLUMN_STUDENT_YEAR, year);
        long result = db.insert(TABLE_STUDENTS, null, contentValues);
        return result != -1;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_STUDENTS, null);
    }
    public Cursor getFilteredAttendance(String branch, String year, String subject,String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_ATTENDANCE + " WHERE " +
                COLUMN_ATTENDANCE_BRANCH + " = ? AND " +
                COLUMN_ATTENDANCE_YEAR + " = ? AND " +
                COLUMN_ATTENDANCE_SUBJECT + " = ? AND "+
                COLUMN_ATTENDANCE_DATE + " = ?";
        return db.rawQuery(query, new String[]{branch, year, subject, date});
    }


    public Cursor getFilteredStudents(String branch, String year) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_STUDENTS, null,
                COLUMN_STUDENT_BRANCH + "=? AND " + COLUMN_STUDENT_YEAR + "=?",
                new String[]{branch, year}, null, null, null);
    }


    public void insertAttendance(String rollNo, String name, String date,String year,String branch,String semester,String subject, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ATTENDANCE_ROLL_NO, rollNo);
        contentValues.put(COLUMN_ATTENDANCE_NAME, name);
        contentValues.put(COLUMN_ATTENDANCE_DATE, date);
        contentValues.put(COLUMN_ATTENDANCE_YEAR, year);
        contentValues.put(COLUMN_ATTENDANCE_BRANCH, branch);
        contentValues.put(COLUMN_ATTENDANCE_SEM, semester);
        contentValues.put(COLUMN_ATTENDANCE_SUBJECT, subject);
        contentValues.put(COLUMN_ATTENDANCE_STATUS, status);
        db.insert(TABLE_ATTENDANCE, null, contentValues);
    }

    public Cursor getAllAttendance() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_ATTENDANCE, null);
    }
}
