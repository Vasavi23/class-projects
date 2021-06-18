package com.example.docsecure;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InsertActivity extends AppCompatActivity {
    EditText e1,e2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        e1=findViewById(R.id.title);
        e2=findViewById(R.id.pass);


    }

    public void Submit(View view) {
        String title = e1.getText().toString();
        String password = e2.getText().toString();
        if (title.equals("") || password.equals((""))) {
            final AlertDialog alertDialog = new AlertDialog.Builder(InsertActivity.this).create();
            alertDialog.setCancelable(false);
            alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Alert!</font>"));
            alertDialog.setMessage("Please fill all the fields ");

            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
        } else {


            Pass pass = new Pass();
            pass.setTitle(title);
            pass.setPassword(password);


            //MainActivity.database.myDao().insert(student);
            Passwords.viewModel.insert(pass);
            Toast.makeText(this, "Data Saved Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

}