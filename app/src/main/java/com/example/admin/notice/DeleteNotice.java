package com.example.admin.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.admin.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteNotice extends AppCompatActivity {

    RecyclerView deleteNoticeRecycler;

    ArrayList<NoticeData> list;
    NoticeAdapter adapter;

    DatabaseReference reference;
    ProgressDialog pd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        deleteNoticeRecycler=findViewById(R.id.deleteNoticeRecycler);

        pd=new ProgressDialog(this);
        reference= FirebaseDatabase.getInstance().getReference().child("Notice");

        deleteNoticeRecycler.setLayoutManager(new LinearLayoutManager(this));
        deleteNoticeRecycler.setHasFixedSize(true);

        getNotice();
    }

    private void getNotice() {
        pd.setTitle("Please wait...");
        pd.setMessage("Notice Loading...");
        pd.show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list=new ArrayList<>();
                for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    NoticeData data=dataSnapshot.getValue(NoticeData.class);
                    list.add(data);
                }
                pd.dismiss();
                adapter=new NoticeAdapter(DeleteNotice.this,list);
                adapter.notifyDataSetChanged();



                deleteNoticeRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

pd.dismiss();
                Toast.makeText(DeleteNotice.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}