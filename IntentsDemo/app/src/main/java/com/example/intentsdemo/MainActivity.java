package com.example.intentsdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
Button explicit;
EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e=findViewById(R.id.VS);
        explicit=findViewById(R.id.People);
        explicit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent V1=new Intent(MainActivity.this,Second.class);
                startActivity(V1);
            }
        });
    }

    public void Rock(View view) {
        Uri uri=Uri.parse("https://www.google.com/");
        Intent V2=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(V2);
    }

    public void msgdata(View view) {
      String m=e.getText().toString();
      Intent i=new Intent(MainActivity.this,Second.class);
      i.putExtra("Vasavi",m);
      startActivity(i);
    }
}