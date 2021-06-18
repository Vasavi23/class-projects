package com.example.docsecure;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

public class Others extends AppCompatActivity implements OthersDialog.BottomSheetListener {
    String name;
    SQLiteDatabase sql;
    LinearLayout layout;
    AppCompatButton b;
    String id;
    OthersDialog othersDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        layout = findViewById(R.id.linear);
        id=getIntent().getStringExtra("Othersid");
        if (ActivityCompat.checkSelfPermission(Others.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Others.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE},0);
            ActivityCompat.requestPermissions(Others.this,new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
        }
        if (ActivityCompat.checkSelfPermission(Others.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Others.this,new String[]{
                    Manifest.permission.CAMERA},1);
        }
        b=findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                othersDialogBox=new OthersDialog();
                othersDialogBox.show(getSupportFragmentManager(),"bottomDialog");

            }
        });


        sql = openOrCreateDatabase(""+id, Context.MODE_PRIVATE, null);
        sql.execSQL("CREATE TABLE IF NOT EXISTS AddOthers(UserId INTEGER PRIMARY KEY AUTOINCREMENT,UserName VARCHAR(100));");
        Cursor c = sql.rawQuery("Select * From AddOthers", null);
        if (c.getCount() != 0) {
            while (c.moveToNext()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

                final Button b = new Button(this);
                b.setLayoutParams(params);
                b.setTag(c.getString(1));
                b.setText(c.getString(1));
                b.setBackgroundResource(R.drawable.background);
                b.setTextColor(Color.parseColor("#FFFFFF"));
                b.setTypeface(b.getTypeface(), Typeface.BOLD);
                setMargins(b,0,15,0,15);
                final String t = c.getString(1);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(),Othercards.class);
                        i.putExtra("Othersnameid", t);
                        i.putExtra("username",id);
                        startActivity(i);
                    }
                });
                layout.addView(b);
            }
        }
    }

    @Override
    public void onButtonClicked(String name) {
        this.name = name;
        Cursor c=sql.rawQuery("Select * from AddOthers",null);
        int check=0;
        if (name.equals("")) {
            Toast.makeText(this, "Cardname Required", Toast.LENGTH_SHORT).show();
        }
        else if(c.getCount()!=0){
            while(c.moveToNext())
            {
                if((c.getString(1)).equals(name))
                {

                    check=1;
                    break;
                }
            }

        }





        if(check==0 && !(name.equals(""))){
            sql.execSQL("Insert Into AddOthers(UserName)VALUES('" + name + "');");
            Toast.makeText(this, "Record Saved", Toast.LENGTH_SHORT).show();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            final Button b = new Button(this);
            b.setLayoutParams(params);
            b.setTag(name);
            b.setText(name);
            b.setBackgroundResource(R.drawable.background);
            b.setTextColor(Color.parseColor("#FFFFFF"));
            b.setTypeface(b.getTypeface(), Typeface.BOLD);
            setMargins(b,0,15,0,15);
            final String t = name;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(),Othercards.class);
                    i.putExtra("Othersnameid", t);
                    i.putExtra("username",id);
                    startActivity(i);
                }
            });
            layout.addView(b);
            othersDialogBox.dismiss();
        }
        else if(check==1)
        {
            Toast.makeText(this, "Cardname Already Exists", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void deletebtn(String un) {
        int count=0;
        name = un;

        Cursor cdel = sql.rawQuery("Select * From AddOthers Where UserName ='" + name + "'", null);
        if (cdel.moveToFirst()) {
            sql.execSQL("Delete From AddOthers Where UserName ='" + name + "'");
            //shared preferences clearing Certificate  data dynamically
            SharedPreferences sp=getSharedPreferences("otherfront"+id+name,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            SharedPreferences sp1=getSharedPreferences("otherback"+id+name,MODE_PRIVATE);
            SharedPreferences.Editor editor1=sp1.edit();
            editor1.clear();
            editor1.commit();

            Toast.makeText(this, "" + name + " Deleted", Toast.LENGTH_SHORT).show();
            if ((layout).getChildCount() > 0)
                (layout).removeAllViews();
            Cursor c = sql.rawQuery("Select * From AddOthers", null);

            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    //Toast.makeText(this, "" + c.getString(1), Toast.LENGTH_SHORT).show();
                    count++;
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);

                    final Button b = new Button(this);
                    b.setLayoutParams(params);
                    b.setTag(c.getString(1));
                    b.setText(c.getString(1));
                    b.setBackgroundResource(R.drawable.background);
                    b.setTextColor(Color.parseColor("#FFFFFF"));
                    b.setTypeface(b.getTypeface(), Typeface.BOLD);
                    setMargins(b,0,15,0,15);
                    final String t = c.getString(1);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(view.getContext(),Othercards.class);
                            i.putExtra("Othersnameid", t);
                            i.putExtra("username",id);
                            startActivity(i);
                        }
                    });
                    layout.addView(b);

                }

            }
            othersDialogBox.dismiss();
        }
        else if(name.equals(""))
        {
            Toast.makeText(this, "Please Enter CardName to delete", Toast.LENGTH_SHORT).show();
        }
        else if(count==0)
        {
            Toast.makeText(this, "Card not found", Toast.LENGTH_SHORT).show();
        }
        if(count == cdel.getCount() && count!=0)
        {
            Toast.makeText(this, "Card not found", Toast.LENGTH_SHORT).show();
        }

    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}