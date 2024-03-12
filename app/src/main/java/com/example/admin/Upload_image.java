package com.example.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.IOException;
import java.util.ArrayList;

public class Upload_image extends AppCompatActivity {
    CardView SelectImage;
    ImageSwitcher switcher;
    Button next,previous;
    Spinner imageCategory;
    Button uploadImage;
    ImageView imageView;
    Bitmap bitmap;
    String category;
    int position=0;

    Uri ImageUri;
    ArrayList<Uri> imageUris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        SelectImage = findViewById(R.id.addGalleryImage);
        imageCategory = findViewById(R.id.image_category);
        uploadImage = findViewById(R.id.uploadImageBtn);
        switcher=findViewById(R.id.imageIs);
        next=findViewById(R.id.next);
        previous=findViewById(R.id.previous);


        imageUris=new ArrayList<>();

        switcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView1=new ImageView(getApplicationContext());
                return imageView1;
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        SelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImagesIntent();
            }
        });

       imageCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
               String item=adapterView.getItemAtPosition(position).toString();

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
        ArrayList<String> arrayList=new ArrayList<>();
        arrayList.add("Select Category");
        arrayList.add("Tarunya");
        arrayList.add("Lakshya");
        arrayList.add("Convocation");
        arrayList.add("Republic Day");
        arrayList.add("Independence Day");
        arrayList.add("Other Events");


        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        imageCategory.setAdapter(adapter);



    }
    private void pickImagesIntent()
    {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Images"),0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){
            if(resultCode== Activity.RESULT_OK){
                if(data.getClipData() !=null){

                    //select multiple images
                    int count=data.getClipData().getItemCount();
                    for(int i=0;i<count;i++){
                        Uri imageUri=data.getClipData().getItemAt(i).getUri();
                        imageUris.add(imageUri);

                    }
                    imageView.setImageURI(imageUris.get(0));


                }
                else {

                    //select single image
                    Uri imageUri=data.getData();
                    imageUris.add(imageUri);
                    imageView.setImageURI(imageUris.get(0));
                }
            }
        }
    }
}