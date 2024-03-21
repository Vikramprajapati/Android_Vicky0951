package com.example.teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class upload_time_table extends AppCompatActivity {

    Spinner spin_year, spin_branch, spin_sem;
    CardView addtable;
    Button uploadTable;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadUrl1;
    ProgressDialog pd3;
    EditText tableTitle;
    private Calendar calForData;
    private SimpleDateFormat currentDate, currentTime;
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList<String> branchList = new ArrayList<>();
    ArrayList<String> semList = new ArrayList<>();
    TextView tablelist;
    ArrayList<Uri> fileuris = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_time_table);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd3 = new ProgressDialog(this);
        addtable = findViewById(R.id.addtable);
        spin_year = findViewById(R.id.year_category3);
        spin_branch = findViewById(R.id.branch_category3);
        spin_sem = findViewById(R.id.sem_category3);
        uploadTable = findViewById(R.id.uploadtable);
        tableTitle = findViewById(R.id.tableTitle);
        tablelist = findViewById(R.id.tablelist);

        calForData = Calendar.getInstance();
        currentDate = new SimpleDateFormat("dd-MM-yy");
        currentTime = new SimpleDateFormat("hh:mm a");
        // adding values of 1spinner in array
        yearList.add("Select Year");
        yearList.add("4 year");
        yearList.add("3 year");
        yearList.add("2 year");
        yearList.add("1 year");
        branchList.add("Select Branch");
        semList.add("Select semester");
        // setting the values in spinner 1
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearList);
        adapter1.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_year.setAdapter(adapter1);
// creating action when spinner values will be chosen
        spin_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (adapterView.getItemAtPosition(position).equals("1 year")) {
                    branchList.clear();
                    semList.clear();
                    branchList.add("None");   // adding values of 2spinner in array
                    semList.add("1 Sem");
                    semList.add("2 Sem");
                    fillSpinner3();
                }
                else if (adapterView.getItemAtPosition(position).equals("3 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("5 Sem");
                    semList.add("6 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner3();
                }
                else if (adapterView.getItemAtPosition(position).equals("2 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("3 Sem");   // adding values of 2spinner in array
                    semList.add("4 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner3();
                }
                else if (adapterView.getItemAtPosition(position).equals("4 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("7 Sem");
                    semList.add("8 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner3();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        spin_branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView1, View view1, int position1, long id1) {
                String item = adapterView1.getItemAtPosition(position1).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView1) {

            }
        });
        spin_sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView2, View view2, int position2, long id2) {
                String item2 = adapterView2.getItemAtPosition(position2).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        uploadTable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tableTitle.getText().toString().isEmpty()) {
                    tableTitle.setError("Empty");
                    tableTitle.requestFocus();
                } else if (fileuris == null || fileuris.isEmpty()) {
                    Toast.makeText(upload_time_table.this, "Please Select PDF", Toast.LENGTH_SHORT).show();
                } else if (spin_year.getSelectedItem() == null || spin_branch.getSelectedItem() == null || spin_sem.getSelectedItem() == null) {
                    Toast.makeText(upload_time_table.this, "all three fields are required for selection ", Toast.LENGTH_SHORT).show();

                } else {
                    uploadtable();
                }
            }
        });
    }

    public void opentablechooser(View view5) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, 1);
    }

    public void uploadtable() {
        pd3.setMessage("Uploading pdf");
        pd3.show();
        for (int j = 0; j < fileuris.size(); j++) {
            Uri perfile = fileuris.get(j);
            StorageReference folder = FirebaseStorage.getInstance().getReference().child("Time Table");
            StorageReference filename = folder.child("file" + perfile.getLastPathSegment());

            filename.putFile(perfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override

                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Time Table");
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("Time Table Url", String.valueOf((uri)));
                            hashMap.put("Date", currentDate.format(calForData.getTime()));
                            hashMap.put("Time", currentTime.format(calForData.getTime()));
                            hashMap.put("Unique Key", databaseReference.child("TIME TABLE").push().getKey());
                            hashMap.put("Time Table Title", tableTitle.getText().toString());
                            hashMap.put("Year", spin_year.getSelectedItem().toString());
                            hashMap.put("Branch:", spin_branch.getSelectedItem().toString());
                            hashMap.put("Semester:", spin_sem.getSelectedItem().toString());
                            databaseReference.push().setValue(hashMap);
                            pd3.dismiss();
                            Toast.makeText(upload_time_table.this, "Time Table uploaded successfully ", Toast.LENGTH_SHORT).show();
                            fileuris.clear();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd3.dismiss();
                    Toast.makeText(upload_time_table.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }

            });


        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestCode && resultCode == Activity.RESULT_OK) {
            if (data == null) return;
            if (null != data.getClipData()) {
                String tempstring = "";
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri file = data.getClipData().getItemAt(i).getUri();
                    String fileName = getFileNameFromUri3(file);
                    fileuris.add(file);
                    tempstring += fileName + "\n";
                }
                tablelist.setText(tempstring);
            } else {
                Uri file = data.getData();
                if (file != null) {
                    fileuris.add(file);
                    String fileName = getFileNameFromUri3(file);
                    tablelist.setText(fileName);
                }
            }

        }
    }
        public void fillSpinner3() {
            ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchList);
            adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spin_branch.setAdapter(adapter2);
            ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semList);
            adapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
            spin_sem.setAdapter(adapter3);
        }
        public String getFileNameFromUri3(Uri uri){
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
                            // Handle the case when DISPLAY_NAME column is not found
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
            }
            else if (uri.toString().startsWith("file://")) {
                fileName = new File(uri.getPath()).getName();
            }
            return fileName;
        }

}