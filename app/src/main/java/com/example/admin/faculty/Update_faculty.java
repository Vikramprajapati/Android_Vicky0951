package com.example.admin.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Update_faculty extends AppCompatActivity {


    FloatingActionButton fab;
    RecyclerView csDepartment,eeDepartment,meDepartment,ceDepartment,adminDepartment,accountDepartment;
    LinearLayout csNoData,eeNoData,ceNoData,meNoData,adminNoData,accountNoData;
    List<TeacherData> list1,list2,list3,list4,list5,list6;
    TeacherAdapter adapter;

    DatabaseReference reference,dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);


        csDepartment=findViewById(R.id.csDepartment);
        eeDepartment=findViewById(R.id.eeDepartment);
        ceDepartment=findViewById(R.id.ceDepartment);
        meDepartment=findViewById(R.id.meDepartment);
        adminDepartment=findViewById(R.id.adminDepartment);


        csNoData=findViewById(R.id.csNoData);
        eeNoData=findViewById(R.id.eeNoData);
        ceNoData=findViewById(R.id.ceNoData);
        meNoData=findViewById(R.id.meNoData);
        adminNoData=findViewById(R.id.adminNoData);
        accountNoData=findViewById(R.id.accountNoData);

        reference= FirebaseDatabase.getInstance().getReference().child("Teachers");

     //   csDepartment();
       // ceDepartment();
        //meDepartment();
        //eeDepartment();
        //adminDepartment();
        //accountDepartment();


        accountDepartment=findViewById(R.id.accountDepartment);

        fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(Update_faculty.this,addFaculty.class);
               startActivity(intent);
            }
        });

    }

 /***   private void csDepartment() {
        dbref=reference.child("CSE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list1=new ArrayList<>();
                if(!datasnapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else{
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list1,Update_faculty.this);
                    csDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ceDepartment() {
        dbref=reference.child("CE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2=new ArrayList<>();
                if(!datasnapshot.exists()){
                    ceNoData.setVisibility(View.VISIBLE);
                    ceDepartment.setVisibility(View.GONE);
                }else{
                    ceNoData.setVisibility(View.GONE);
                    ceDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    ceDepartment.setHasFixedSize(true);
                    ceDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list2,Update_faculty.this);
                    ceDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void eeDepartment() {
        dbref=reference.child("EE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list3=new ArrayList<>();
                if(!datasnapshot.exists()){
                    eeNoData.setVisibility(View.VISIBLE);
                    eeDepartment.setVisibility(View.GONE);
                }else{
                    eeNoData.setVisibility(View.GONE);
                    eeDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    eeDepartment.setHasFixedSize(true);
                    eeDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list3,Update_faculty.this);
                    eeDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void meDepartment() {
        dbref=reference.child("ME");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list4=new ArrayList<>();
                if(!datasnapshot.exists()){
                    meNoData.setVisibility(View.VISIBLE);
                    meDepartment.setVisibility(View.GONE);
                }else{
                    meNoData.setVisibility(View.GONE);
                    meDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    meDepartment.setHasFixedSize(true);
                    meDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list4,Update_faculty.this);
                    meDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adminDepartment() {
        dbref=reference.child("Admin");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list5=new ArrayList<>();
                if(!datasnapshot.exists()){
                    adminNoData.setVisibility(View.VISIBLE);
                    adminDepartment.setVisibility(View.GONE);
                }else{
                    adminNoData.setVisibility(View.GONE);
                    adminDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    adminDepartment.setHasFixedSize(true);
                    adminDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list5,Update_faculty.this);
                    adminDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void accountDepartment() {
        dbref=reference.child("Account");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list6=new ArrayList<>();
                if(!datasnapshot.exists()){
                    accountNoData.setVisibility(View.VISIBLE);
                    accountDepartment.setVisibility(View.GONE);
                }else{
                    accountNoData.setVisibility(View.GONE);
                    accountDepartment.setVisibility(View.VISIBLE);

                    for(DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data
                                =snapshot.getValue(TeacherData.class);
                        list6.add(data);
                    }
                    accountDepartment.setHasFixedSize(true);
                    accountDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list6,Update_faculty.this);
                    accountDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }***/

}