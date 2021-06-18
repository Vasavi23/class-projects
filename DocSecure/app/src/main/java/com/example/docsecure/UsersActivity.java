package com.example.docsecure;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;

public class UsersActivity extends AppCompatActivity implements BottomDialogBox.BottomSheetListener {
    String name, check = "";
    SQLiteDatabase sql;
    LinearLayout layout;
    AppCompatButton b;
    BottomDialogBox bottomDialogBox;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        layout = findViewById(R.id.linear);

        if (ActivityCompat.checkSelfPermission(UsersActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(UsersActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(UsersActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 0);

        }

        b = findViewById(R.id.b1);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialogBox = new BottomDialogBox();
                bottomDialogBox.show(getSupportFragmentManager(), "bottomDialog");

            }
        });


        sql = openOrCreateDatabase("Users", Context.MODE_PRIVATE, null);
        sql.execSQL("CREATE TABLE IF NOT EXISTS AddUser(UserId INTEGER PRIMARY KEY AUTOINCREMENT,UserName VARCHAR(100));");
        Cursor c = sql.rawQuery("Select * From AddUser", null);
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
                setMargins(b, 0, 15, 0, 15);
                final String t = c.getString(1);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(view.getContext(), CardsList.class);
                        i.putExtra("id", t);
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
        Cursor c = sql.rawQuery("Select * from AddUser", null);
        int check = 0;
        if (name.equals("")) {
            Toast.makeText(this, "Username Required", Toast.LENGTH_SHORT).show();
        } else if (c.getCount() != 0) {
            while (c.moveToNext()) {
                if ((c.getString(1)).equals(name)) {

                    check = 1;
                    break;
                }
            }

        }


        if (check == 0 && !(name.equals(""))) {
            sql.execSQL("Insert Into AddUser(UserName)VALUES('" + name + "');");

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);

            final Button b = new Button(this);
            b.setLayoutParams(params);
            b.setTag(name);
            b.setText(name);
            b.setBackgroundResource(R.drawable.users);
            b.setTextColor(Color.parseColor("#FFFFFF"));
            b.setTypeface(b.getTypeface(), Typeface.BOLD);
            setMargins(b, 0, 15, 0, 15);
            final String t = name;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(view.getContext(), CardsList.class);
                    i.putExtra("id", t);
                    startActivity(i);
                }
            });
            layout.addView(b);
            bottomDialogBox.dismiss();
        } else if (check == 1) {
            Toast.makeText(this, "Username Already Exists", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void deletebtn(String un) {
        count = 0;
        name = un;
        TextView textView = new TextView(UsersActivity.this);
        textView.setText("ALERT !!!");
        textView.setPadding(50, 30, 20, 30);
        textView.setTextSize(20F);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setBackgroundColor(Color.RED);
        textView.setTextColor(Color.WHITE);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setCustomTitle(textView);
        builder1.setMessage("Do you want to remove User " + name);

        builder1.setCancelable(true);

        Cursor cdel = sql.rawQuery("Select * From AddUser Where UserName ='" + name + "'", null);
        if (cdel.moveToFirst()) {
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            sql.execSQL("Delete From AddUser Where UserName ='" + name + "'");

                            Toast.makeText(UsersActivity.this, "" + name + " Deleted", Toast.LENGTH_SHORT).show();
                            if ((layout).getChildCount() > 0)
                                (layout).removeAllViews();
                            Cursor c = sql.rawQuery("Select * From AddUser", null);

                            if (c.getCount() != 0) {
                                while (c.moveToNext()) {
                                    //Toast.makeText(this, "" + c.getString(1), Toast.LENGTH_SHORT).show();
                                    count++;
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                            LinearLayout.LayoutParams.MATCH_PARENT,
                                            LinearLayout.LayoutParams.WRAP_CONTENT);

                                    final Button b = new Button(UsersActivity.this);
                                    b.setLayoutParams(params);
                                    b.setTag(c.getString(1));
                                    b.setText(c.getString(1));
                                    b.setBackgroundResource(R.drawable.users);
                                    b.setTextColor(Color.parseColor("#FFFFFF"));
                                    b.setTypeface(b.getTypeface(), Typeface.BOLD);
                                    setMargins(b, 0, 15, 0, 15);
                                    final String t = c.getString(1);
                                    b.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent i = new Intent(view.getContext(), CardsList.class);
                                            i.putExtra("id", t);
                                            startActivity(i);
                                        }
                                    });
                                    layout.addView(b);
                                }
                            }
                            bottomDialogBox.dismiss();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();
            TextView textV = (TextView) alert11.getWindow().findViewById(android.R.id.message);
            textV.setTextSize(20);
            textV.setTypeface(Typeface.DEFAULT_BOLD, Typeface.BOLD);

        } else if (name.equals("")) {
            Toast.makeText(UsersActivity.this, "Please Enter UserName to delete", Toast.LENGTH_SHORT).show();
        } else if (count == 0) {
            Toast.makeText(UsersActivity.this, "User not found", Toast.LENGTH_SHORT).show();
        }
        if (count == cdel.getCount() && count != 0) {
            Toast.makeText(UsersActivity.this, "User not found", Toast.LENGTH_SHORT).show();
        }
    }
    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}