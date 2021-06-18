package com.example.docsecure;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class CreditCard extends AppCompatActivity implements CreditCardDialog.BottomSheetListener {
    String name;
    SQLiteDatabase sql;
    LinearLayout layout;
    AppCompatButton b;
    String id;
    CreditCardDialog creditCardDialogBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);
        layout = findViewById(R.id.linear);
        id=getIntent().getStringExtra("Creditcardid");

        b=findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                creditCardDialogBox = new CreditCardDialog();
                creditCardDialogBox.show(getSupportFragmentManager(),"bottomDialog");

            }
        });


        sql = openOrCreateDatabase(""+id, Context.MODE_PRIVATE, null);
        sql.execSQL("CREATE TABLE IF NOT EXISTS AddCreditcard(UserId INTEGER PRIMARY KEY AUTOINCREMENT,UserName VARCHAR(100));");
        Cursor c = sql.rawQuery("Select * From AddCreditcard", null);
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
                        Intent i = new Intent(view.getContext(),CardspicGlobal.class);
                        i.putExtra("Creditcardnameid", t);
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
        Cursor c=sql.rawQuery("Select * from AddCreditcard",null);
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
            sql.execSQL("Insert Into AddCreditcard(UserName)VALUES('" + name + "');");
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
                    Intent i = new Intent(view.getContext(),CardspicGlobal.class);
                    i.putExtra("Creditcardnameid", t);
                    i.putExtra("username",id);
                    startActivity(i);
                }
            });
            layout.addView(b);
            creditCardDialogBox.dismiss();
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

        Cursor cdel = sql.rawQuery("Select * From AddCreditcard Where UserName ='" + name + "'", null);
        if (cdel.moveToFirst()) {
            sql.execSQL("Delete From AddCreditcard Where UserName ='" + name + "'");
            //shared preferences clearing Certificate  data dynamically
            SharedPreferences sp=getSharedPreferences("Creditcardfront"+id+name,MODE_PRIVATE);
            SharedPreferences.Editor editor=sp.edit();
            editor.clear();
            editor.commit();
            SharedPreferences sp1=getSharedPreferences("Creditcardback"+id+name,MODE_PRIVATE);
            SharedPreferences.Editor editor1=sp1.edit();
            editor1.clear();
            editor1.commit();

            Toast.makeText(this, "" + name + " Deleted", Toast.LENGTH_SHORT).show();
            if ((layout).getChildCount() > 0)
                (layout).removeAllViews();
            Cursor c = sql.rawQuery("Select * From AddCreditcard", null);

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
                            Intent i = new Intent(view.getContext(),CardspicGlobal.class);
                            i.putExtra("Creditcardnameid", t);
                            i.putExtra("username",id);
                            startActivity(i);
                        }
                    });
                    layout.addView(b);

                }

            }
            creditCardDialogBox.dismiss();
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