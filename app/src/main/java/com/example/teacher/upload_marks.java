package com.example.teacher;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.admin.NoticeData;
import com.example.admin.R;
import com.example.admin.Upload_notice;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class upload_marks extends AppCompatActivity {
    /* variables declared of all xml elements*/

    Spinner spin_year, spin_branch, spin_sem;
    CardView addfile;
    Button uploadfile3;
    Bitmap bitmap1;
    DatabaseReference reference1;
    StorageReference storageReference1;
    String downloadUrl1;
    ProgressDialog pd1;
    String getDownloadUrl1 = "";
    EditText marksTitle;
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList <String>branchList=new ArrayList<>();
    ArrayList<String> semList = new ArrayList<>();
    TextView errorYear,errorBranch,errorSem;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadPermissionGranted=false;
private boolean isLocationPermissionGranted=false;
private boolean isRecordPermissionGranted=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_marks);

        reference1 = FirebaseDatabase.getInstance().getReference();
        storageReference1 = FirebaseStorage.getInstance().getReference();
        pd1 = new ProgressDialog(this);
        addfile = findViewById(R.id.addfile);
        spin_year = findViewById(R.id.year_category1);
        spin_branch = findViewById(R.id.branch_category1);
        spin_sem = findViewById(R.id.sem_category1);
        uploadfile3 = findViewById(R.id.uploadImageBtn);
        marksTitle = findViewById(R.id.marksTitle);

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
                    fillSpinner();
                } else if(adapterView.getItemAtPosition(position).equals("3 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("5 Sem");
                    semList.add("6 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner();
                }
                else if(adapterView.getItemAtPosition(position).equals("2 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("3 Sem");   // adding values of 2spinner in array
                    semList.add("4 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner();
                }
                else if(adapterView.getItemAtPosition(position).equals("4 year")) {
                    branchList.clear();
                    semList.clear();
                    semList.add("7 Sem");
                    semList.add("8 Sem");
                    branchList.add("CSE");
                    branchList.add("ME");     // adding values of 2spinner  in array
                    branchList.add("CE");
                    branchList.add("EE");
                    fillSpinner();
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
mPermissionResultLauncher=registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onActivityResult(Map<String, Boolean> result) {
        if (result.get(Manifest.permission.READ_MEDIA_IMAGES)!=null){
            isReadPermissionGranted= Boolean.TRUE.equals(result.get(Manifest.permission.READ_MEDIA_IMAGES));
        }
        if (result.get(Manifest.permission.ACCESS_FINE_LOCATION)!=null){
            isLocationPermissionGranted= Boolean.TRUE.equals(result.get(Manifest.permission.ACCESS_FINE_LOCATION));
        }
        if (result.get(Manifest.permission.RECORD_AUDIO)!=null){
            isRecordPermissionGranted= Boolean.TRUE.equals(result.get(Manifest.permission.RECORD_AUDIO));
        }
    }
});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions();
        }


        uploadfile3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (marksTitle.getText().toString().isEmpty()) {
                    marksTitle.setError("Empty");
                    marksTitle.requestFocus();
                } else if (bitmap1 == null) {
                    uploadData();

                } else {
                    uploadfile3();
                }
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public void requestPermissions(){
       isReadPermissionGranted= ContextCompat.checkSelfPermission(this,Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;

        isLocationPermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        isRecordPermissionGranted= ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED;
        List  <String> permissionRequest=new ArrayList<String>();
        if(!isReadPermissionGranted){
            permissionRequest.add(Manifest.permission.READ_MEDIA_IMAGES);
        }
        if(!isLocationPermissionGranted){
            permissionRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!isRecordPermissionGranted){
            permissionRequest.add(Manifest.permission.RECORD_AUDIO);
        }

    if (!permissionRequest.isEmpty()) {
        mPermissionResultLauncher.launch(permissionRequest.toArray(new String[0]));
    }
    }


    private void uploadfile3() {
        pd1.setMessage("Uploading...");
        pd1.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalmarks = baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference1.child("Marks").child(finalmarks + "jpg");
        final UploadTask uploadTask = filepath.putBytes(finalmarks);
        uploadTask.addOnCompleteListener(upload_marks.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl1 = String.valueOf(uri);
                                    uploadData();
                                }
                            });
                        }
                    });
                } else {
                    pd1.dismiss();
                    Toast.makeText(upload_marks.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        reference1 = reference1.child("Marks");
        final String uniqueKey = reference1.push().getKey();

        String title = marksTitle.getText().toString();

        Calendar calForData = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MM-yy");
        String date = currentDate.format(calForData.getTime());

        Calendar calForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        String time = currentTime.format(calForData.getTime());

        MarksData marksdata = new MarksData(title, downloadUrl1, date, time, uniqueKey);

        reference1.child(uniqueKey).setValue(marksdata).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                pd1.dismiss();
                Toast.makeText(upload_marks.this, "Marks Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd1.dismiss();
                Toast.makeText(upload_marks.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void fillSpinner(){

    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchList);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_branch.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semList);
        adapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_sem.setAdapter(adapter3);
}
}