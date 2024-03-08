package com.example.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Upload_notice extends AppCompatActivity {
     ImageView imageview;
     CardView cardView;
     TextView textView;
     Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);
        imageview=findViewById(R.id.noticeImageView);
        cardView=findViewById(R.id.selectImage);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,1);
            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && null !=data){
            Uri selectedImage=data.getData();
            String[] filepath={MediaStore.Images.Media.DATA};
            Cursor cursor=getContentResolver().query(selectedImage,filepath,null,null,null);
            cursor.moveToFirst();
            int columneIndex=cursor.getColumnIndex(filepath[0]);
            String picturepath=cursor.getString(columneIndex);
            cursor.close();
            imageview.setImageBitmap(BitmapFactory.decodeFile(picturepath));
        }
    }
}