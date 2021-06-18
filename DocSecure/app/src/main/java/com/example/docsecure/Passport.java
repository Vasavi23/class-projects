package com.example.docsecure;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.docsecure.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class Passport extends AppCompatActivity implements View.OnClickListener{

    MaterialButton addimage,shareimage;
    ImageView passportview;
    static Bitmap imagezoom;
    private Uri mCropImageUri;
    MaterialCardView front;
    String image,username;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passport);
        if (ActivityCompat.checkSelfPermission(Passport.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(Passport.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Passport.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},0);


        }
        //Sharing Mode
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //Intent extra text

        username=getIntent().getStringExtra("Passportid");
        //Text Views

        //Buttons

        addimage=findViewById(R.id.passportpic);
        addimage.setOnClickListener(this);
        shareimage=findViewById(R.id.passportpicshare);
        //text view


        //ImageView
        passportview=findViewById(R.id.passportimageview);
        //Material Card
        front=findViewById(R.id.passportfront);

        //Shared Preference Database
        sp=getSharedPreferences("passportview"+username,MODE_PRIVATE);
        if(sp!=null) {
            String image2 = sp.getString("Passportimage", "");
            Bitmap b=convertbit(image2);
            if(!image2.equals("")) {
                passportview.setImageBitmap(b);
                findViewById(R.id.passportpicshare).setVisibility(View.VISIBLE);
                front.setVisibility(View.VISIBLE);


            }
            else {
                setMargins(addimage,100,20,30,0);
            }
        }

        shareimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable mDrawable = passportview.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                try {
                    File f=new File(Passport.this.getExternalCacheDir(),"Passportimage"+ " - " + (Calendar.getInstance().getTime())+".jpeg");
                    FileOutputStream fout=new FileOutputStream(f);
                    mBitmap.compress(Bitmap.CompressFormat.JPEG,80,fout);
                    fout.flush();
                    fout.close();
                    f.setReadable(true,false);


                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(f));
                    intent.setType("image/*");
                    startActivity(Intent.createChooser(intent, "Share Image"));


                }
                catch (Exception e){
                    Toast.makeText(Passport.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        passportview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String pic=sp.getString("Passportimage","");
                    Bitmap bitmap = convertbit(pic);
                    Intent full = new Intent(Passport.this, FullScreenZoom.class);
                    imagezoom=bitmap;
                    full.putExtra("zoomid","passportzoom");
                    startActivity(full);
                }
                catch (Exception e){
                    Toast.makeText(Passport.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.passportpic:
                onSelectImageClick(view);
                break;


        }
    }

    public void onSelectImageClick(View view) {

        CropImage.startPickImageActivity(this);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                image = (result.getUri()).toString();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , result.getUri());
                    String ims=convert(bitmap);
                    // Toast.makeText(this, ""+ims, Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("Passportimage", ims);
                    editor.commit();
                    passportview.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.passportpicshare).setVisibility(View.VISIBLE);
                front.setVisibility(View.VISIBLE);

                    setMargins(addimage,0,0,0,0);


                //Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();


            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        } else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    public String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }
    public Bitmap convertbit(String base64Str) throws IllegalArgumentException {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",") + 1),
                Base64.DEFAULT        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }



}