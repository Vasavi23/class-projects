package com.example.docsecure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

import java.util.Calendar;

public class Register extends AppCompatActivity {

    final Calendar calendar = Calendar.getInstance();
    TextView tv;
    LinearLayout s;
    String p1 = "*", p2;
    EditText dob;
    int year, month, day;
    String pin, dateofbirth;
    SharedPreferences sharedPreferences;
    AppCompatButton materialButton;
    private OtpView otp1, otp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv = findViewById(R.id.header_title);
        LinearGradient linearGradient = new LinearGradient(0f, 0f, 0f, tv.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        tv.getPaint().setShader(linearGradient);

        s = findViewById(R.id.activity_register);
        AnimationDrawable ad = (AnimationDrawable) s.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();
        otp1 = findViewById(R.id.r_p1);
        otp2 = findViewById(R.id.r_p2);
        dob = findViewById(R.id.r_dob);
        sharedPreferences = getSharedPreferences("Login", MODE_PRIVATE);
        materialButton = findViewById(R.id.style);
       /* AnimationDrawable ad1 = (AnimationDrawable) materialButton.getBackground();
        ad1.setEnterFadeDuration(2000);
        ad1.setExitFadeDuration(4000);
        ad1.start();*/

        if (sharedPreferences != null) {
            pin = sharedPreferences.getString("pin", "");
            if (!(pin.equals(""))) {
                materialButton.setText("Reset");

            }
            dateofbirth = sharedPreferences.getString("dob", "");
        }

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                //creation of Date Picker
                DatePickerDialog pickerDialog = new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },year,month,day);
                pickerDialog.show();
               /* DatePickerDialog Dialog = new DatePickerDialog(Register.this, AlertDialog.THEME_TRADITIONAL, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        dob.setText(d + "/" + (m + 1) + "/" + y);
                    }
                }, year, month, day);
                //Visibility to user
                Dialog.show();*/
            }
        });


        otp1.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                p1 = otp;
                otp2.requestFocus();
            }
        });
        otp2.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                p2 = otp;
                if (!p1.equals(p2)) {

                    Canceldialog alert = new Canceldialog();
                    alert.showdialog(Register.this, "Password Mismatch");
                }
            }
        });
        otp2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (p1.equals("*")) {
                    otp1.requestFocus();

                }
            }
        });
    }

    public void r_register(View view) {
        if ("".equals(dob.getText().toString()) || "".equals(p1) || "".equals(p2)) {

            Canceldialog alert = new Canceldialog();
            alert.showdialog(Register.this, "Please fill all the Details");

        } else if (p1.equals(p2) && (dateofbirth.equals(""))) {
            pin = p1;
            dateofbirth = dob.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", pin);
            editor.putString("dob", dateofbirth);
            editor.commit();

            Success alert = new Success();
            alert.showdialog(Register.this, "Thank you! \nRegistered Successfully");

        } else if (dateofbirth.equals(dob.getText().toString()) && p1.equals(p2)) {
            pin = p1;
            dateofbirth = dob.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("pin", pin);
            editor.putString("dob", dateofbirth);
            editor.commit();

            Success alert = new Success();
            alert.showdialog(Register.this, " Password Reset Successful");


        } else {

            Canceldialog alert = new Canceldialog();
            alert.showdialog(Register.this, " InCorrect Date of Birth Entered.\nPlease Enter Registered Date of Birth to Reset your pin");
        }

    }
}