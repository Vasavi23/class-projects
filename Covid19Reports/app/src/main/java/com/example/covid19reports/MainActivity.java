package com.example.covid19reports;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
TextView country,date,confirmed,active,recover,death;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        country=findViewById(R.id.tv_country);
        date=findViewById(R.id.tv_date);
        confirmed=findViewById(R.id.tv_confirm);
        active=findViewById(R.id.tv_active);
        recover=findViewById(R.id.tv_recovered);
        death=findViewById(R.id.tv_deaths);
        EndpointInterface ei=Covid19Response.getInstance().create(EndpointInterface.class);
        Call<String> c=ei.getData();
        c.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i("ding",response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Something went wrong while fetching the data",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
