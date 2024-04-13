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
    RecyclerView adminDepartment,accountDepartment,csDepartment,ceDepartment,eeDepartment,meDepartment;

    LinearLayout adminNoData,accountNoData,csNoData,meNoData,ceNoData,eeNoData;

    List<TeacherData> list1,list2,list3,list4,list5,list6;

    DatabaseReference reference,dbref;

    TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        fab=findViewById(R.id.fab);


        adminDepartment=findViewById(R.id.adminDepartment);
        accountDepartment=findViewById(R.id.accountDepartment);
        csDepartment=findViewById(R.id.csDepartment);
        meDepartment=findViewById(R.id.meDepartment);
        ceDepartment=findViewById(R.id.ceDepartment);
        eeDepartment=findViewById(R.id.eeDepartment);

        adminNoData=findViewById(R.id.adminNoData);
        accountNoData=findViewById(R.id.accountNoData);
        ceNoData=findViewById(R.id.ceNoData);
        csNoData=findViewById(R.id.csNoData);
        meNoData=findViewById(R.id.meNoData);
        eeNoData=findViewById(R.id.eeNoData);


        reference=FirebaseDatabase.getInstance().getReference().child("Teachers");


        adminDepartment();
        accountDepartment();
        csDepartment();
        ceDepartment();
        meDepartment();
        eeDepartment();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Update_faculty.this,addFaculty.class);
                startActivity(intent);
            }
        });


    }

    private void adminDepartment() {
        dbref=reference.child("Admin");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1=new ArrayList<>();
                if(!snapshot.exists()){
                    adminDepartment.setVisibility(View.GONE);
                    adminNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    adminDepartment.setHasFixedSize(true);
                    adminDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list1,Update_faculty.this,"Admin");
                    adminDepartment.setAdapter(adapter);
                    adminDepartment.setVisibility(View.VISIBLE);
                    adminNoData.setVisibility(View.GONE);

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
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2=new ArrayList<>();
                if(!snapshot.exists()){
                   accountDepartment.setVisibility(View.GONE);
                    accountNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    accountDepartment.setHasFixedSize(true);
                    accountDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list2,Update_faculty.this,"Account");
                    accountDepartment.setAdapter(adapter);
                    accountDepartment.setVisibility(View.VISIBLE);
                    accountNoData.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void csDepartment() {
        dbref=reference.child("CSE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list3=new ArrayList<>();
                if(!snapshot.exists()){
                    csDepartment.setVisibility(View.GONE);
                    csNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list3,Update_faculty.this,"CSE");
                    csDepartment.setAdapter(adapter);
                    csDepartment.setVisibility(View.VISIBLE);
                    csNoData.setVisibility(View.GONE);

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
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list4=new ArrayList<>();
                if(!snapshot.exists()){
                    meDepartment.setVisibility(View.GONE);
                    meNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    meDepartment.setHasFixedSize(true);
                    meDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list4,Update_faculty.this,"ME");
                    meDepartment.setAdapter(adapter);
                    meDepartment.setVisibility(View.VISIBLE);
                    meNoData.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void ceDepartment() {
        dbref=reference.child("EE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list5=new ArrayList<>();
                if(!snapshot.exists()){
                    eeDepartment.setVisibility(View.GONE);
                    eeNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    eeDepartment.setHasFixedSize(true);
                    eeDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list5,Update_faculty.this,"EE");
                    eeDepartment.setAdapter(adapter);
                    eeDepartment.setVisibility(View.VISIBLE);
                    eeNoData.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void eeDepartment() {
        dbref=reference.child("CE");
        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list6=new ArrayList<>();
                if(!snapshot.exists()){
                    ceDepartment.setVisibility(View.GONE);
                    ceNoData.setVisibility(View.VISIBLE);
                }else {
                    for(DataSnapshot snapshot1: snapshot.getChildren()){
                        TeacherData data=snapshot1.getValue(TeacherData.class);
                        list6.add(data);
                    }
                    ceDepartment.setHasFixedSize(true);
                    ceDepartment.setLayoutManager(new LinearLayoutManager(Update_faculty.this));
                    adapter=new TeacherAdapter(list6,Update_faculty.this,"CE");
                    ceDepartment.setAdapter(adapter);
                    ceDepartment.setVisibility(View.VISIBLE);
                    ceNoData.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_faculty.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }




}