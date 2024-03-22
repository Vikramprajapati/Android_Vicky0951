package com.example.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

public class Upload_image extends AppCompatActivity {
    CardView cardView;
    ImageSwitcher imageSwitcher;
    TextView imagetitle;
    Button next,previous,upload;
    ArrayList<Uri> imageUris;
    int position;
    ProgressDialog pd;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        pd=new ProgressDialog(this);

        cardView=findViewById(R.id.addGalleryImage);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);
        imageSwitcher=findViewById(R.id.imageIs);
        upload=findViewById(R.id.uploadImageBtn);
        imagetitle=findViewById(R.id.imagetitle);

        imageUris=new ArrayList<>();


        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView=new ImageView(getApplicationContext());

                return imageView;
            }
        });

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), 1);
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position>0){
                    position--;
                    imageSwitcher.setImageURI(imageUris.get(position));
                }
                else {
                    Toast.makeText(Upload_image.this,"No Preious Images...",Toast.LENGTH_SHORT).show();
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position<imageUris.size()-1){
                    position++;
                    imageSwitcher.setImageURI(imageUris.get(position));
                }
                else {
                    Toast.makeText(Upload_image.this,"No Next Images...",Toast.LENGTH_SHORT).show();
                }


            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imagetitle.getText().toString().isEmpty()){
                    imagetitle.setError("Empty");
                    imagetitle.requestFocus();
                }
                else {
                    uploadImages(new ArrayList<>());
                }
            }
        });


    }

    private void uploadImages(ArrayList<String> imageUrlList) {
        pd.setTitle("Please wait...");
        pd.setMessage("Uploading Images...");
        pd.show();
        StorageReference refrence2=FirebaseStorage.getInstance().getReference().child("images").child(UUID.randomUUID().toString());
        Uri uri=imageUris.get(imageUrlList.size());
        refrence2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                refrence2.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        String url=task.getResult().toString();
                        imageUrlList.add(url);

                        if(imageUris.size()==imageUrlList.size()){
                            pd.dismiss();
                            Toast.makeText(Upload_image.this,"Images Uploaded Successfully",Toast.LENGTH_SHORT).show();
                            upload.setText("Upload Images");
                            upload.setEnabled(true);
                        }
                        else {
                            uploadImages(imageUrlList);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pd.dismiss();
                        Toast.makeText(Upload_image.this,"Failed to upload images",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(resultCode==Activity.RESULT_OK){
                if(data.getClipData() !=null){
                    int count=data.getClipData().getItemCount();
                    for(int i=0;i<count;i++){
                        Uri imageUri=data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);

                    }

                    imageSwitcher.setImageURI(imageUris.get(0));
                    int position=0;
                }
                else {
                    Uri imageUri=data.getData();
                    imageUris.add(imageUri);
                    imageSwitcher.setImageURI(imageUris.get(0));
                    int position=0;
                }
            }
        }
    }
}