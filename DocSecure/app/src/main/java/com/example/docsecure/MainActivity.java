package com.example.docsecure;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.mukesh.OnOtpCompletionListener;
import com.mukesh.OtpView;

public class MainActivity extends AppCompatActivity {
    LinearLayout s;
    SharedPreferences sp;
    String pin, dateofbirth, pin1;
    TextView tv;
    AppCompatButton b, b1;
    private OtpView otpView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.header_title);
        LinearGradient linearGradient = new LinearGradient(0f, 0f, 0f, tv.getTextSize(), Color.RED, Color.BLUE, Shader.TileMode.CLAMP);
        tv.getPaint().setShader(linearGradient);
        s = findViewById(R.id.activity_main);
        b = findViewById(R.id.gb1);
        b1 = findViewById(R.id.gb2);
        otpView = findViewById(R.id.otp_view);

        sp = getSharedPreferences("Login", MODE_PRIVATE);


        if (sp != null) {
            pin = sp.getString("pin", "");

            dateofbirth = sp.getString("dob", "");
            if (dateofbirth.equals("")) {


                Instructions alert = new Instructions();
                alert.showdialog(MainActivity.this, "Hello User! \n" +
                        "Just Click on Register Button to Register");

            }
        }
        AnimationDrawable ad = (AnimationDrawable) s.getBackground();
        ad.setEnterFadeDuration(2000);
        ad.setExitFadeDuration(4000);
        ad.start();
        /*AnimationDrawable ad1 = (AnimationDrawable) b.getBackground();
        ad1.setEnterFadeDuration(2000);
        ad1.setExitFadeDuration(4000);
        ad1.start();
        AnimationDrawable ad2 = (AnimationDrawable) b1.getBackground();
        ad2.setEnterFadeDuration(2000);
        ad2.setExitFadeDuration(4000);
        ad2.start();*/

        pin1 = "";
        otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                // do Stuff
                pin1 = otp;
            }
        });

    }

    public void forgot(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }

    public void login(View view) {
        if (pin1.equals("")) {
            Canceldialog alert = new Canceldialog();
            alert.showdialog(MainActivity.this, "Please Enter your pin");
        } else if (pin1.equals(pin)) {
            otpView.setText("");
            pin1 = "";
            Intent i = new Intent(this, UsersActivity.class);
            startActivity(i);
        } else {
            Canceldialog alert = new Canceldialog();
            alert.showdialog(MainActivity.this, "Incorrect Pin");
        }
    }

    public void register(View view) {
        Intent i = new Intent(this, Register.class);
        startActivity(i);
    }
}