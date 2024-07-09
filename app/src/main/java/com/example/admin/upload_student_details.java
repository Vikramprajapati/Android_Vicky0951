package com.example.admin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.common.DBHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class upload_student_details extends AppCompatActivity {
    CardView addexcelfile;
    Button uploadexcelfile;
    Button check;
    ProgressDialog pdg;
    ArrayList<Uri> fileuris = new ArrayList<>();
    DBHelper dbHelper;
    TextView studentDetailslist;
    private Calendar calForData;
    private SimpleDateFormat currentDate;

    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_student_details);

        pdg = new ProgressDialog(this);
        addexcelfile = findViewById(R.id.addStudentfile);
        studentDetailslist = findViewById(R.id.nameoffile);
        uploadexcelfile = findViewById(R.id.upload_student_detail1);
        dbHelper = new DBHelper(this);
        check = findViewById(R.id.check);

        addexcelfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openStudentFileChooser(v);
            }
        });

        uploadexcelfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fileuris == null || fileuris.isEmpty()) {
                    Toast.makeText(upload_student_details.this, "Please Select .csv file", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        readCSVAndInsertData(dbHelper);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public void readCSVAndInsertData(DBHelper dbHelper) throws IOException {
        for (Uri fileUri : fileuris) {
            InputStream inputStream = getContentResolver().openInputStream(fileUri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 5) { // Student data
                        String rollNo = values[0];
                        String name = values[1];
                        String branch = values[2];
                        String semester = values[3];
                        String year = values[4];
                        dbHelper.insertStudent(rollNo, name, branch, semester, year);
                    } else if (values.length == 8) { // Attendance data
                        String rollNo = values[0];
                        String name = values[1];
                        String date=values[2];
                        String year = values[3];
                        String branch = values[4];
                        String sem = values[5];
                        String subject = values[6];
                        String status = values[7];
                        dbHelper.insertAttendance(rollNo, name, date, year, branch, sem, subject, status);
                    }
                    else {
                        // Handle invalid line format
                        Log.e("CSVReader", "Invalid line format: " + line);
                        Toast.makeText(upload_student_details.this, "Invalid line format in CSV", Toast.LENGTH_SHORT).show();
                    }
                }
                Toast.makeText(upload_student_details.this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(upload_student_details.this, "Error uploading data", Toast.LENGTH_SHORT).show();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data == null) return;

            if (data.getClipData() != null) {
                StringBuilder tempstring = new StringBuilder();
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri file = data.getClipData().getItemAt(i).getUri();
                    fileuris.add(file);
                    tempstring.append(getFileNameFromUri(file)).append("\n");
                }
                studentDetailslist.setText(tempstring.toString());
            } else {
                Uri file = data.getData();
                if (file != null) {
                    fileuris.add(file);
                    studentDetailslist.setText(getFileNameFromUri(file));
                }
            }
        }
    }

    public void openStudentFileChooser(View fileChooser) {
        Intent fileIntent = new Intent(Intent.ACTION_GET_CONTENT);
        fileIntent.setType("text/*"); // Primary MIME type
        String[] mimeTypes = {
                "text/csv",
                "text/comma-separated-values",
                "application/csv",
                "application/excel",
                "application/vnd.ms-excel",
                "application/vnd.msexcel",
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        };
        fileIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        fileIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        try {
            startActivityForResult(fileIntent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            studentDetailslist.setText("No activity can handle picking a file. Showing alternatives.");
        }
    }

    public String getFileNameFromUri(Uri uri) {
        String fileName = "";
        if (uri.toString().startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int columnIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (columnIndex != -1) {
                        fileName = cursor.getString(columnIndex);
                    } else {
                        fileName = uri.getLastPathSegment();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (uri.toString().startsWith("file://")) {
            fileName = new File(uri.getPath()).getName();
        }
        return fileName;
    }

    private String getCurrentDate() {
        calForData = Calendar.getInstance();
        currentDate = new SimpleDateFormat("yyyy-MM-dd");
        return currentDate.format(calForData.getTime());
    }
}
