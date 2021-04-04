package com.example.asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {
EditText et;
ProgressBar progressBar;
Button bt;
ImageView iv;
TextView tv;
String myurl="https://pixabay.com/api/?key=20988201-920d2a8155382694db2e9368b&q=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.imagename);
        bt=findViewById(R.id.submit);
        iv=findViewById(R.id.imageview);
        tv=findViewById(R.id.textview);
        progressBar=findViewById(R.id.progress);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        new ImageTask().execute();
            }
        });
    }
    class ImageTask extends AsyncTask<String,Void,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            @SuppressLint("WrongThread") String data= et.getText().toString();
            try {
                URL url=new URL(myurl+data);
                Log.i("mydata",url.toString());
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                InputStream inputStream=urlConnection.getInputStream();
                Scanner scanner=new Scanner(inputStream);
                scanner.useDelimiter("aaa");
                if(scanner.hasNext()){
                    return scanner.next();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Toast.makeText(MainActivity.this,s,Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            try {
                JSONObject imagedata=new JSONObject(s);
                JSONArray hitArray=imagedata.getJSONArray("hits");
                JSONObject hitObject=hitArray.getJSONObject(19);
                String image=hitObject.getString("largeImageURL");
                String user=hitObject.getString("user");
                Picasso.get().load(image).into(iv);
                tv.setText(user);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
