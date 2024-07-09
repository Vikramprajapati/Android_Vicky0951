package com.example.teacher;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;


import com.example.admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class upload_marks extends AppCompatActivity {
    /* variables declared of all xml elements*/

    Spinner spin_year, spin_branch, spin_sem;
    CardView addfile;
    Button uploadfile3;
    Bitmap bitmap1;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadUrl1;
    ProgressDialog pd1;
    EditText marksTitle;
    private Calendar calForData;
    private SimpleDateFormat currentDate,currentTime;
    ArrayList<String> yearList = new ArrayList<>();
    ArrayList <String>branchList=new ArrayList<>();
    ArrayList<String> semList = new ArrayList<>();
    TextView filelist;
    ActivityResultLauncher<String[]> mPermissionResultLauncher;
    private boolean isReadPermissionGranted=false;
private boolean isLocationPermissionGranted=false;
private boolean isRecordPermissionGranted=false;
ArrayList<Uri> fileuris =new ArrayList<>();


int requestcode=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_marks);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd1 = new ProgressDialog(this);
        addfile = findViewById(R.id.addfile);
        spin_year = findViewById(R.id.year_category1);
        spin_branch = findViewById(R.id.branch_category1);
        spin_sem = findViewById(R.id.sem_category1);
        uploadfile3 = findViewById(R.id.uploadfileBtn);
        marksTitle = findViewById(R.id.marksTitle);
        filelist=findViewById(R.id.filelist);

        calForData=Calendar.getInstance();
        currentDate=new SimpleDateFormat("dd-MM-yy");
        currentTime=new SimpleDateFormat("hh:mm a");
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
                } else if (fileuris == null|| fileuris.isEmpty()) {
                    Toast.makeText(upload_marks.this, "Please Select PDF", Toast.LENGTH_SHORT).show();
                }
                else if (spin_year.getSelectedItem()== null || spin_branch.getSelectedItem()== null ||spin_sem.getSelectedItem()== null) {
                    Toast.makeText(upload_marks.this,"all three fields are required for selection ",Toast.LENGTH_SHORT).show();

                }
               else {
                    uploadfiles();
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
    public void openfilechooser(View view5){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        startActivityForResult(intent,1);
    }

public void uploadfiles(){
    pd1.setMessage("Uploading pdf");
    pd1.show();
        for(int j=0;j<fileuris.size();j++){
            Uri perfile=fileuris.get(j);
            StorageReference folder=FirebaseStorage.getInstance().getReference().child("Marks");
            StorageReference filename=folder.child("file"+perfile.getLastPathSegment());

            filename.putFile(perfile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                   filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("Marks");
                           HashMap<String,String> hashMap=new HashMap<>();
                           hashMap.put("url",String.valueOf((uri)));
                           hashMap.put("date",currentDate.format(calForData.getTime()));
                           hashMap.put("time",currentTime.format(calForData.getTime()));
                           hashMap.put("key",databaseReference.child("Marks").push().getKey());
                           hashMap.put("title",marksTitle.getText().toString());
                           hashMap.put("year",spin_year.getSelectedItem().toString());
                           hashMap.put("branch:",spin_branch.getSelectedItem().toString());
                           hashMap.put("semester:",spin_sem.getSelectedItem().toString());
                           databaseReference.push().setValue(hashMap);
                           pd1.dismiss();
                           Toast.makeText(upload_marks.this,"marks uploaded successfully ",Toast.LENGTH_SHORT).show();
                           fileuris.clear();
                       }
                   });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd1.dismiss();
                    Toast.makeText(upload_marks.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                }

            });


        }

}

    @Override
    protected void onActivityResult(int requestCode,int resultCode,@Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == requestcode && resultCode == Activity.RESULT_OK) {
            if (data == null) return;
            if (null != data.getClipData()) {
                String tempstring = "";
                for (int i = 0; i < data.getClipData().getItemCount(); i++) {
                    Uri file = data.getClipData().getItemAt(i).getUri();
                    String fileName = getFileNameFromUri(file);
                    fileuris.add(file);
                    tempstring += fileName + "\n";
                }
                filelist.setText(tempstring);
            } else {
                Uri file = data.getData();
                if (file != null) {
                    fileuris.add(file);
                    String fileName = getFileNameFromUri(file);
                    filelist.setText(fileName);
                }
            }

        }
    }













    public void fillSpinner(){

    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, branchList);
        adapter2.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_branch.setAdapter(adapter2);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, semList);
        adapter3.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spin_sem.setAdapter(adapter3);
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
        } else if (uri.toString().startsWith("file://")) {
            fileName = new File(uri.getPath()).getName();
        }
        return fileName;
    }
}