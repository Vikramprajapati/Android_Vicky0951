package com.example.teacher;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.DBHelper;
import com.example.admin.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;

public class ViewAttendance extends AppCompatActivity {

    private static final int PICK_CSV_FILE = 1;

    Spinner year, branch, sem, subject;
    Button uploadAttendance,viewattendance,takeattendance;
    private Button buttonSelectDate1;
    TextView textViewSelectedDate1;
    ArrayList<String> yearn = new ArrayList<>();
    ArrayList<String> branchn = new ArrayList<>();
    ArrayList<String> semn = new ArrayList<>();
    ArrayList<String> subjectn = new ArrayList<>();
    private String selectedYear,selectedBranch,selectedSemester,selectedSubject,selectedDate1 ;

    private DBHelper dbHelper;
    private RecyclerView recyclerView;
    private AttendanceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        ActionBar actionBar1 = getSupportActionBar();
        if (actionBar1 != null) {
            actionBar1.setDisplayShowCustomEnabled(true);
            actionBar1.setDisplayShowTitleEnabled(false);
            TextView titleTextView = new TextView(this);
            titleTextView.setText("Attendance Section");
            titleTextView.setTextColor(Color.WHITE);
            titleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            titleTextView.setTypeface(null, Typeface.BOLD);
            titleTextView.setGravity(Gravity.CENTER);

            ActionBar.LayoutParams params = new ActionBar.LayoutParams(
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER
            );
            actionBar1.setCustomView(titleTextView, params);
        }

        year = findViewById(R.id.year);
        branch = findViewById(R.id.branch);
        sem = findViewById(R.id.sem);
        subject = findViewById(R.id.subject);
        uploadAttendance = findViewById(R.id.uploadAttendance);
        viewattendance = findViewById(R.id.viewattendance);
        takeattendance=findViewById(R.id.takeattendance);
        buttonSelectDate1 = findViewById(R.id.buttonSelectDate1);
        textViewSelectedDate1=findViewById(R.id.textViewSelectedDate1);

        dbHelper = new DBHelper(this);
//        recyclerView = findViewById(R.id.recyclerViewAttendance);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
          ArrayList<Attendance> attendanceList = new ArrayList<>();
//        adapter = new AttendanceAdapter(this, attendanceList);
//        recyclerView.setAdapter(adapter);
        buttonSelectDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        // Add data to spinners
        yearn.add("Select Year");
        yearn.add("4");
        yearn.add("3");
        yearn.add("2");
        yearn.add("1");
        branchn.add("Select Branch");
        semn.add("Select semester");
        subjectn.add("select subject");
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearn);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        year.setAdapter(adapter1);

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (adapterView.getItemAtPosition(position).equals("1")) {
                    branchn.clear();
                    semn.clear();
                    subjectn.clear();
                    branchn.add("None");
                    semn.add("choose sem");
                    semn.add("1");
                    semn.add("2");
                    subjectn.add("choose subject");
                    subjectn.add("Communication Skills");
                    subjectn.add("Basic Electrical Engineering");
                    subjectn.add("Physics");
                    subjectn.add("Basic Mechanical Engineering");
                    subjectn.add("Basic Civil Engineering");
                    subjectn.add("Computer Fundamentals and Programming");
                    subjectn.add("Engineering Mathematics I");
                    subjectn.add("Engineering Mathematics II");
                    subjectn.add("Human Values");
                    subjectn.add("Physics Lab");
                    subjectn.add("Basic Electrical Engineering Lab");
                    subjectn.add("Manufacturing Practices Workshop");
                    subjectn.add(" Basic Civil Engineering Lab");
                    subjectn.add(" Computer Fundamentals and Programming Lab");
                    subjectn.add("Communication Skills Lab");
                    subjectn.add("Human Values Activities");
                    subjectn.add("Computer Aided Engineering Graphics");
                    subjectn.add("Computer Aided Machine Drawing");
                    subjectn.add("");
                    fillSpinner();
                } else if (adapterView.getItemAtPosition(position).equals("3")) {
                    branchn.clear();
                    semn.clear();
                    subjectn.clear();
                    semn.add("choose sem");
                    semn.add("5");
                    semn.add("6");
                    branchn.add("choose branch");
                    branchn.add("CSE");
                    branchn.add("ME");
                    branchn.add("CE");
                    branchn.add("EE");
                    subjectn.add("choose subject");
                    fillSpinner();
                } else if (adapterView.getItemAtPosition(position).equals("2")) {
                    branchn.clear();
                    semn.clear();
                    subjectn.clear();
                    semn.add("choose sem");
                    semn.add("3");
                    semn.add("4");
                    branchn.add("choose branch");
                    branchn.add("CSE");
                    branchn.add("ME");
                    branchn.add("CE");
                    branchn.add("EE");
                    subjectn.add("choose subject");
//                    subjectn.add("DSA");
//                    subjectn.add("OS");
                    fillSpinner();
                } else if (adapterView.getItemAtPosition(position).equals("4")) {
                    branchn.clear();
                    semn.clear();
                    subjectn.clear();
                    semn.add("choose sem");
                    semn.add("7");
                    semn.add("8");
                    branchn.add("choose branch");
                    branchn.add("CSE");
                    branchn.add("ME");
                    branchn.add("CE");
                    branchn.add("EE");
                    subjectn.add("choose subject");
//                    subjectn.add("BDA");
//                    subjectn.add("IOT");
                    fillSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView1, View view1, int position1, long id1) {
                String brn = adapterView1.getItemAtPosition(position1).toString();
                if (adapterView1.getItemAtPosition(position1).equals("CSE")){
                    if(year.getSelectedItem().toString().equals("2")){
                        subjectn.clear();
                        subjectn.add("Advanced Engineering Mathematics");
                        subjectn.add("Digital Electronics");
                        subjectn.add("Data Structures and Algorithms");
                        subjectn.add("Object Oriented Programming Using C++");
                        subjectn.add("Software Engineering");
                        subjectn.add("Introduction to Cyber Security");
                        subjectn.add("Data Structures and Algorithms Lab");
                        subjectn.add("Object Oriented Programming Using C++ Lab");
                        subjectn.add("Linux and Shell Programming Lab");
                        subjectn.add("Digital Electronics Lab ");
                        subjectn.add("Discrete Mathematics ");
                        subjectn.add("Microprocessor and Interfaces");
                        subjectn.add("Theory of Computation");
                        subjectn.add("Database Management Systems ");
                        subjectn.add("Introduction to Python Programming");
                        subjectn.add("Introduction to Java Programming");
                        subjectn.add("Database Management Systems Lab");
                        subjectn.add("Microprocessor and InterfacesLab");
                        subjectn.add("Python Programming Lab ");
                        subjectn.add("Java Programming Lab");

                    }
                    else if (year.getSelectedItem().toString().equals("3")){
                        subjectn.clear();
                        subjectn.add("Machine Learning");
                        subjectn.add("Artificial Intelligence");
                        subjectn.add("Operating System");
                        subjectn.add("Computer Networks");
                        subjectn.add("Compiler Design");
                        subjectn.add("Distributed Systems");
                        subjectn.add("Cloud Computing");
                        subjectn.add("Computer Architecture and Organisation");
                        subjectn.add("ML Lab");
                        subjectn.add("Mobile Application Development Lab");
                        subjectn.add("Information Security and System");
                        subjectn.add("Information Theory And Coding");
                        subjectn.add("Digital Image Processing");
                        subjectn.add("Digital Image Processing Lab");
                        subjectn.add("Analysis of Algorithms");
                        subjectn.add("Analysis of Algorithms Lab");

                    }
                    else if (year.getSelectedItem().toString().equals("4")){
                    // 4yr
                        subjectn.clear();
                    subjectn.add("BDA");
                    subjectn.add("IOT");
                    subjectn.add("Disaster Management");
                    subjectn.add("Software testing and validation Lab");
                    subjectn.add("BDA lab");
                    subjectn.add("IOT Lab");
                    subjectn.add("cyber Security Lab");

                }
                }
                else if(adapterView1.getItemAtPosition(position1).equals("ME")){

                    if (year.getSelectedItem().toString().equals("4") ){
                        subjectn.clear();
                        subjectn.add("I. C. Engines");
                        subjectn.add("Operations Research");
                        subjectn.add("Turbo machines");
                        subjectn.add("FEA Lab");
                        subjectn.add("Thermal Engineering Lab II ");
                        subjectn.add("Quality Control Lab");
                        subjectn.add("Hybrid and Electric Vehicles");
                        subjectn.add("Supply and Operations Management");
                        subjectn.add("Additive Manufacturing");
                        subjectn.add("Industrial Engineering Lab");
                        subjectn.add("Metrology Lab");
                    }
                    else if (year.getSelectedItem().toString().equals("2")){
                        subjectn.clear();
                        subjectn.add("Engineering Thermodynamics");
                        subjectn.add("Materials Engineering and Technology");
                        subjectn.add("Manufacturing Processes");
                        subjectn.add("Mechanics of Solids");
                        subjectn.add("Renewable  Energy  Systems");
                        subjectn.add("Engineering Mechanics");
                        subjectn.add("Materials Testing Lab");
                        subjectn.add("Production Practice Lab ");
                        subjectn.add("Machine Drawing Practice");
                        subjectn.add("Theory of Machines-I");
                        subjectn.add("Fluid Mechanics ");
                        subjectn.add("3 Internal Combustion Engines and Gas Turbines");
                        subjectn.add("Industrial Engineering");
                        subjectn.add("Manufacturing Technology-I");
                        subjectn.add("Advanced Engineering Mathematics");
                        subjectn.add("Theory of Machines Lab-I ");
                        subjectn.add("Fluid Mechanics Lab ");
                        subjectn.add("MATLAB Programming");
                        subjectn.add("Production Engineering Lab");
                    }
                    else if (year.getSelectedItem().toString().equals("3")){
                        subjectn.clear();
                        subjectn.add("Theory of Machines-II");
                        subjectn.add("Heat Transfer ");
                        subjectn.add("Machine Design-I");
                        subjectn.add("Manufacturing TechnologyII ");
                        subjectn.add("Automobile Engineering");
                        subjectn.add("Fuels and Combustions");
                        subjectn.add("Manufacturing Technology Lab");
                        subjectn.add("Theory of Machines Lab- II ");
                        subjectn.add("Heat Transfer Lab");
                        subjectn.add("Machine Design Practice-I ");
                        subjectn.add("Turbo Machines");
                        subjectn.add("Control System & Advanced\n" +
                                "Measurement Theory");
                        subjectn.add("Machine Design-II ");
                        subjectn.add("Refrigeration and Air Conditioning");
                        subjectn.add("Power Generation");
                        subjectn.add("Automation Lab");
                        subjectn.add("Thermal Engineering Lab ");
                        subjectn.add("Turbo Machine Lab ");
                        subjectn.add("Machine Design Practice-II ");
                    }
                    // 3 yr


                }
                else if(adapterView1.getItemAtPosition(position1).equals("EE")){
                    // 4y
                    if (year.getSelectedItem().toString().equals("4")){
                        subjectn.clear();
                        subjectn.add("Wind and Solar Energy Systems");
                        subjectn.add("Power quality and facts");
                        subjectn.add("Control System Design");
                        subjectn.add("Embedded Systems Lab");
                        subjectn.add("Advance control system lab");
                        subjectn.add("HVDC Transmission System.");
                        subjectn.add("Line Commutative rectifiers");
                        subjectn.add("Advanced Electric Drives");
                        subjectn.add("Energy Systems Lab");
                    }
                    else if (year.getSelectedItem().toString().equals("2")){
                        // 2 yr
                        subjectn.clear();
                        subjectn.add("Electrical Circuit Analysis-I");
                        subjectn.add("Electrical Machines-I ");
                        subjectn.add("3 Electrical Measurements ");
                        subjectn.add("Analog Electronics");
                        subjectn.add("Power System Instrumentation ");
                        subjectn.add("Advanced Engineering Mathematics-I");
                        subjectn.add("Electrical Machines Lab-I");
                        subjectn.add("Electrical Measurement and Instrumentation Lab");
                        subjectn.add("Analog Electronics Lab ");
                        subjectn.add("Electrical Circuit Lab ");
                        subjectn.add("Electrical Machines-II");
                        subjectn.add("Electrical Machines-II Lab");
                        subjectn.add("Generation of Electrical Power");
                        subjectn.add("Electrical Circuit Analysis-II ");
                        subjectn.add("Electrical Machine Design ");
                        subjectn.add("Computer Programming");
                        subjectn.add("Advanced Engineering Mathematics-II");
                        subjectn.add("Power System Design Lab ");
                        subjectn.add("Computer Programming Lab ");
                        subjectn.add("Electrical Machine Design Lab");
                    }

                    else if (year.getSelectedItem().toString().equals("3")){
                        subjectn.clear();
                        subjectn.add("Control System Engineering");
                        subjectn.add("Power System-I");
                        subjectn.add("Computer Architecture and Microprocessors");
                        subjectn.add("High Voltage Engineering");
                        subjectn.add("Digital Electronics");
                        subjectn.add("Engineering Materials");
                        subjectn.add("Control System Engineering Lab");
                        subjectn.add("High Voltage Engineering Lab");
                        subjectn.add("Microprocessors Lab");
                        subjectn.add("MATLAB Programming Lab");
                        subjectn.add("Control System Engineering Lab");
                        subjectn.add("Power Electronics");
                        subjectn.add("Modern Control Systems");
                        subjectn.add("Power System-II");
                        subjectn.add("Switchgear and Protection of Power System");
                        subjectn.add("Signals and Systems");
                        subjectn.add("Digital Control System");
                        subjectn.add("Power Electronics Lab");
                        subjectn.add("Power System Simulation Lab");

                    }
                    // 3yr

                }
                else if(adapterView1.getItemAtPosition(position1).equals("CE")){
                    if (year.getSelectedItem().toString().equals("3")){
                        subjectn.clear();
                        subjectn.add("Construction Technology & Equipments");
                        subjectn.add("Disaster Management");
                        subjectn.add("Energy Science & Engineering");
                        subjectn.add("Structural Analysis-I");
                        subjectn.add("Geotechnical Engineering");
                        subjectn.add("Design of Concrete Structures");
                        subjectn.add("Concrete Structures Design");
                        subjectn.add("Geotechnical Engineering Lab");
                        subjectn.add("Water Resource Engineering Design");
                        subjectn.add("Wind & Seismic Analysis");
                        subjectn.add("Structural Analysis-II");
                        subjectn.add("Environmental Engineering");
                        subjectn.add("Design of Steel Structures");
                        subjectn.add("Estimating & Costing");
                        subjectn.add("Traffic Engineering and Management");
                        subjectn.add("Rock Engineering");
                        subjectn.add("Environmental Engineering Design and Lab");
                        subjectn.add("Quantity Surveying and Valuation");
                        subjectn.add("Foundation Design");
                    }
                    else if (year.getSelectedItem().toString().equals("4")){
                        subjectn.clear();
                        subjectn.add("Transportation Engineering");
                        subjectn.add("Environmental Monitoring and Design Lab");
                        subjectn.add("Project Planning and Construction Management");
                        subjectn.add("Project Planning & Construction Management Lab");
                        subjectn.add("Pavement Design");

                    }
                    else if (year.getSelectedItem().toString().equals("2")) {
                        subjectn.clear();
                        subjectn.add("Fluid Mechanics");
                        subjectn.add("Surveying");
                        subjectn.add("Building Materials");
                        subjectn.add("Architecture Drawing and Building Construction");
                        subjectn.add("Engineering Geology");
                        subjectn.add("Engineering Mechanics");
                        subjectn.add("Fluid Mechanics Lab");
                        subjectn.add("Surveying Lab");
                        subjectn.add("Computer Aided Civil Engineering Drawing");
                        subjectn.add("Geology Lab");
                        subjectn.add("Civil Engineering Lab-I");
                        subjectn.add("01 Geotechnical Engineering-I");
                        subjectn.add("Mechanics of Solids");
                        subjectn.add("Environmental Engineering");
                        subjectn.add("Hydraulics Engineering");
                        subjectn.add("Construction Management");
                        subjectn.add("Advanced Engineering Mathematics");
                        subjectn.add("Environmental Engineering Lab");
                        subjectn.add("Hydraulics Engineering Lab");
                        subjectn.add("Civil Engineering Lab-II");
                        subjectn.add("Geotechnical Engineering Lab -I");

                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView1) {

            }
        });

        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView3, View view3, int position3, long id3) {
                String semester = adapterView3.getItemAtPosition(position3).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView4, View view4, int position4, long id4) {
                String subj = adapterView4.getItemAtPosition(position4).toString();
        }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView4) {

            }
        });
        viewattendance.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              // Fetch attendance for the selected criteria
              selectedYear = year.getSelectedItem() != null ? year.getSelectedItem().toString() : "";
              selectedBranch = branch.getSelectedItem() != null ? branch.getSelectedItem().toString() : "";
              selectedSemester = sem.getSelectedItem() != null ? sem.getSelectedItem().toString() : "";
              selectedSubject = subject.getSelectedItem() != null ? subject.getSelectedItem().toString() : "";
              Log.d("ViewAttendance", "Selected Criteria: Year=" + selectedYear + ", Branch=" + selectedBranch +
                      ", Semester=" + selectedSemester + ", Subject=" + selectedSubject);
              if (!selectedYear.equals("Select Year") && !selectedBranch.equals("choose Branch") && !selectedSemester.equals("choose sem") && !selectedSubject.equals("choose subject")&&selectedDate1 != null) {
                  Cursor cursor = dbHelper.getFilteredAttendance(selectedBranch, selectedYear, selectedSubject,selectedDate1);
                  attendanceList.clear();
                  // Clear the attendance list before adding new items
                  Log.d("ViewAttendance", "Cursor count: " + cursor.getCount());

                  if (cursor.moveToFirst()) {
                      do {
                          String rollNo = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_ROLL_NO));
                          String name = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_NAME));
                          String status = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_STATUS));
//                          String date=cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_ATTENDANCE_DATE));
                          Log.d("ViewAttendance", "Attendance Record: RollNo=" + rollNo + ", Name=" + name + " Status=" + status);
//                                if (subject.equals(subj)) {
                          attendanceList.add(new Attendance(rollNo, name, status));
//
                      } while (cursor.moveToNext());
                  }
                  else {
                      Log.d("ViewAttendance", "No data found for the selected criteria");
                      Toast.makeText(ViewAttendance.this, "No data available for the selected criteria", Toast.LENGTH_SHORT).show();
                  }
                  cursor.close();

                  if (attendanceList.isEmpty()) {
                      // Display a toast message if there is no data
                      Toast.makeText(ViewAttendance.this, "No data available for the selected criteria", Toast.LENGTH_SHORT).show();
                  } else {
                      Intent intent = new Intent(ViewAttendance.this, ShowAttendanceActivity.class);
                      intent.putExtra("selectedYear", selectedYear);
                      intent.putExtra("selectedBranch", selectedBranch);
                      intent.putExtra("selectedSemester", selectedSemester);
                      intent.putExtra("selectedSubject", selectedSubject);
                      intent.putExtra(("selectedDate1"),selectedDate1);
                      intent.putParcelableArrayListExtra("attendanceList", attendanceList);
                      startActivity(intent);
                      // Update the adapter with the new data

                  }


              } else {
                  Toast.makeText(ViewAttendance.this, "Please select all criteria and date", Toast.LENGTH_SHORT).show();
              }
          }
        });


        uploadAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("text/*"); // Primary MIME type
                String[] mimeTypes = {
                        "text/csv",
                        "text/comma-separated-values",
                        "application/csv",
                        "application/excel",
                        "application/vnd.ms-excel",
                        "application/vnd.msexcel",
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                };
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select CSV File"), PICK_CSV_FILE);
            }
        });
        takeattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (year.getSelectedItem()== ("Select Year") ||branch.getSelectedItem()== ("choose Branch") ||sem.getSelectedItem()== ("choose sem")||subject.getSelectedItem()== ("choose subject")){
                    Toast.makeText(ViewAttendance.this, "Please select all criteria of year,branch,sem,subject", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(ViewAttendance.this, TakeAttendanceActivity.class);
                    intent.putExtra("selectedYear",year.getSelectedItem().toString());
                    intent.putExtra("selectedBranch", branch.getSelectedItem().toString());
                    intent.putExtra("selectedSemester", sem.getSelectedItem().toString() );
                    intent.putExtra("selectedSubject", subject.getSelectedItem().toString() );
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CSV_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            readCSV(uri);
        }
    }

    private void readCSV(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            // Skip the first line if it contains headers
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length == 8) {
                    String rollNo = tokens[0];
                    String name = tokens[1];
                    String date = tokens[2];
                    String year = tokens[3];
                    String branch = tokens[4];
                    String sem = tokens[5];
                    String subject = tokens[6];
                    String status = tokens[7];
                    dbHelper.insertAttendance(rollNo, name, date, year, branch, sem, subject, status);
                }
                else {
                    // Handle the case where the line does not have the expected number of tokens
                    Log.e("CSVReader", "Invalid line format: " + line);
                    Toast.makeText(this, "Invalid line format in CSV", Toast.LENGTH_SHORT).show();
                }
            }
            reader.close();
            Toast.makeText(this, "Attendance uploaded successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error uploading attendance", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillSpinner() {
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchn);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        branch.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semn);
        adapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        sem.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectn);
        adapter4.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        subject.setAdapter(adapter4);
    }
    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate1 = dayOfMonth + "/" + (month + 1) + "/" + year;
                        textViewSelectedDate1.setText("Selected Date: " + selectedDate1);
                    }
                }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}
