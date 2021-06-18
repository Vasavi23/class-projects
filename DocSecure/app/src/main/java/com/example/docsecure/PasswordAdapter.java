package com.example.docsecure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class PasswordAdapter extends RecyclerView.Adapter<PasswordAdapter.MyViewHolder> {
    Context ct;
    List<Pass> list;
    public PasswordAdapter(Passwords passwords, List<Pass> password) {
        ct=passwords;
        list=password;
    }

    @NonNull
    @Override
    public PasswordAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(ct).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Pass pass=list.get(position);
        holder.t1.setText(pass.getTitle());
        holder.t2.setText(pass.getPassword());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Passwords.viewModel.delete(pass);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView t1,t2;
        ImageView delete,edit;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1=itemView.findViewById(R.id.settitle);
            t2=itemView.findViewById(R.id.setpassword);
            edit=itemView.findViewById(R.id.edit);
            delete=itemView.findViewById(R.id.delete);

            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Access data from TextViews
                    String title=t1.getText().toString();
                    String password=t2.getText().toString();


                    ViewGroup viewGroup=view.findViewById(android.R.id.content);
                    View v=LayoutInflater.from(ct).inflate(R.layout.edit_info,viewGroup,false);
                    final EditText e1,e2;
                    e1=v.findViewById(R.id.uptitle);
                    e2=v.findViewById(R.id.uppass);
                    Button update,cancel;
                    update=v.findViewById(R.id.update);
                    cancel=v.findViewById(R.id.cancel);

                    final BottomSheetDialog dialog=new BottomSheetDialog(ct);
                    dialog.setContentView(v); //display all data in edit info
                    dialog.setCancelable(false);
                    dialog.show();

                    e1.setText(title);
                    e2.setText(password);

                    update.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Pass pass=new Pass();
                            pass.setTitle(e1.getText().toString());
                            pass.setPassword(e2.getText().toString());

                            //Execute the Query
                            Passwords.viewModel.update(pass);
                            Toast.makeText(ct, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });


                }
            });


        }
    }
}