package com.example.docsecure;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Passwords extends AppCompatActivity {

    public static Password_database database;
    TextView tv;
    EditText et;
    Button b;
    RecyclerView rv;
    static MyViewModel viewModel;
    List<Pass> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwords);


        rv=findViewById(R.id.recycler);
        tv=findViewById(R.id.textView);
        Password_database.databasename="MYDBPASS";

        //Initializing the view Model
        viewModel=new ViewModelProvider(Passwords.this).get(MyViewModel.class);

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Passwords.this,InsertActivity.class));
            }
        });


        //For retrieving data
        viewModel.readData().observe(Passwords.this, new Observer<List<Pass>>() {
            @Override
            public void onChanged(List<Pass> password) {
                //Toast.makeText(MainActivity.this, ""+students.get(0).d(), Toast.LENGTH_SHORT).show();
                list=password;

                if(password.size()==0){
                    tv.setVisibility(View.VISIBLE);
                    rv.setVisibility(View.GONE);
                }
                else {
                    tv.setVisibility(View.GONE);
                    rv.setVisibility(View.VISIBLE);
                    rv.setLayoutManager(new LinearLayoutManager(Passwords.this));


                    rv.setAdapter(new PasswordAdapter(Passwords.this, password));
                }
            }
        });


    }


}