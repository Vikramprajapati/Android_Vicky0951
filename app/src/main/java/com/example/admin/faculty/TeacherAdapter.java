package com.example.admin.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {
     List<TeacherData> list;
     Context context;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    // Bind data from teacherdata.java
    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData item=list.get(position);
        holder.name.setText(item.getName());
        holder.post.setText(item.getPost());
        holder.email.setText(item.getEmail());
        holder.number.setText(item.getNumber());
        try {
            Picasso.get().load(item.getImage()).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,UpdateTeacherActivity.class);
                intent.putExtra("name",item.getName());
                intent.putExtra("post",item.getName());
                intent.putExtra("name",item.getName());
                intent.putExtra("name",item.getName());


            }
        });

    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {
        TextView name,post,email,number;
        Button update;
        ImageView imageView;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.teachername);
            post=itemView.findViewById(R.id.teacherpost);
            email=itemView.findViewById(R.id.teacheremail);
            number=itemView.findViewById(R.id.teachernumber);
            update=itemView.findViewById(R.id.teacherupdate);
            imageView=itemView.findViewById(R.id.teacherimage);
        }
    }
}
