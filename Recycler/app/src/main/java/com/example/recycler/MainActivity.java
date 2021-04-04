package com.example.recycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
RecyclerView rv;
String names[];
String lead[];
int poster[];
MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv=findViewById(R.id.r);
        names=getResources().getStringArray(R.array.Title);
        lead=getResources().getStringArray(R.array.Leader);
        poster=new int[]{R.drawable.bts,R.drawable.bp,R.drawable.exo,R.drawable.got7,R.drawable.itzy,R.drawable.mamamoo,R.drawable.twice,R.drawable.nct};
    adapter=new MyAdapter(this,names,lead,poster);
    rv.setLayoutManager(new LinearLayoutManager(this));
    rv.setAdapter(adapter);
    }
}