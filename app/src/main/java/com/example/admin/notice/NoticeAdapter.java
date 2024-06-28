package com.example.admin.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<NoticeData> list;


    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout,parent,false);
        return new NoticeViewAdapter(view);
    }


    //get Notice from firebase
    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        NoticeData currentItem=list.get(position);


        holder.title.setText(currentItem.getTitle());


        try {
            if(currentItem.getImage()!=null)
                 Picasso.get().load(currentItem.getImage()).into(holder.noticeImage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }




        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //set alert dialog

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this notice ?");
                builder.setCancelable(true);
                builder.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                          //delete Notice


                                DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Notice");
                                reference.child(currentItem.getKey()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText(context,"Notice Deleted",Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context,"Something Went Wrong",Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                notifyItemRemoved(position);

                            }
                        }


                );


                builder.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        }
                );


                AlertDialog alertDialog=null;
                try {
                     alertDialog=builder.create();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if(alertDialog!=null){
                    alertDialog.show();
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {

        Button delete;
        TextView title;
        ImageView noticeImage;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.delete);
            title=itemView.findViewById(R.id.title);
            noticeImage=itemView.findViewById(R.id.NoticeImage);

        }
    }

}
