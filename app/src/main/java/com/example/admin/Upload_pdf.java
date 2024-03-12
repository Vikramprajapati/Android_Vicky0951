package com.example.admin;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Upload_pdf extends AppCompatActivity {


    EditText pdftitle;
    ImageView pdfView;
    CardView addPdf;
    TextView textView;
    Button uploadpdfbtn;
    Uri pdfData;
    DatabaseReference reference;
    StorageReference storageReference;
    String downloadUrl = "";
    String getDownloadUrl = "";
    ProgressDialog pd;
    Bitmap bitmap;
    TextView pdfText;
    String pdfname,title;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pdf);

        reference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();
        pd = new ProgressDialog(this);


        addPdf = findViewById(R.id.addPdf);
        pdftitle = findViewById(R.id.pdfTitle);
        uploadpdfbtn = findViewById(R.id.uploadPdfbtn);
        pdfText=findViewById(R.id.pdfTextView);



        uploadpdfbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=pdftitle.getText().toString();
                if(title.isEmpty()){
                    pdftitle.setError("Empty");
                    pdftitle.requestFocus();
                } else if (pdfData==null) {
                    Toast.makeText(Upload_pdf.this,"Please Select PDF",Toast.LENGTH_SHORT).show();

                }
                else {
                    uploadPdf();
                }
            }
        });


        addPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent,"Select PDF File"),1);

            }
        });




    }

    private void uploadPdf() {
        pd.setTitle("Please Wait...");
        pd.setMessage("Uploading pdf");
        pd.show();
        StorageReference reference1=storageReference.child("pdf/"+pdfname+"-"+System.currentTimeMillis()+".pdf");
        reference1.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri=uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Upload_pdf.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void uploadData(String downloadUrl) {
        String uniquekey=reference.child("pdf").push().getKey();

        HashMap data=new HashMap();
        data.put("pdfTitle",title);
        data.put("pdfUrl",downloadUrl);


        reference.child("pdf").child(uniquekey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(Upload_pdf.this,"PDF Uploaded successfully",Toast.LENGTH_SHORT).show();
                pdftitle.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(Upload_pdf.this,"Failed to Upload pdf ",Toast.LENGTH_SHORT).show();
            }
        });

    }


    @SuppressLint("Range")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode==RESULT_OK ) {
            pdfData=data.getData();

            if(pdfData.toString().startsWith("content://")){

                Cursor cursor=null;
                try {
                    cursor=Upload_pdf.this.getContentResolver().query(pdfData,null,null,null,null);
                    if(cursor !=null && cursor.moveToFirst()){
                        pdfname=cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            } else if (pdfData.toString().startsWith("file://")) {
                pdfname=new File(pdfData.toString()).getName();
            }
            pdfText.setText(pdfname);


        }

    }
}