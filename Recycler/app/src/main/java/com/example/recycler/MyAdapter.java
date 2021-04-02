package com.example.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context ct;
    String title[];
    String lead[];
    int ptr[];
    public MyAdapter(Context ct,String[] title,String [] lead,int[] ptr){
        this.ct=ct;
        this.title=title;
        this.lead=lead;
        this.ptr=ptr;
    }
    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(ct).inflate(R.layout.design,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv,ld;
        ImageView img;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.title);
            ld=itemView.findViewById(R.id.leader);
            img=itemView.findViewById(R.id.iv);
        }
    }
}
