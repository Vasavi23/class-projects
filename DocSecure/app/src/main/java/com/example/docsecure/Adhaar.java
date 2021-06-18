package com.example.docsecure;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Adhaar extends AppCompatActivity implements View.OnClickListener, ExampleBottomSheetDialog.BottomSheetListener{

    String id,image;
    MaterialButton Button,Button2;
    MaterialButton bt1,share,share1;
    ImageView img,img2;
    MaterialCardView card1,card2,front,back;
    static Bitmap imagezoom;
    TextView t1,t2,t3,t4;
    private Uri mCropImageUri;
    SharedPreferences sp,sp2,spd;
    int a=0;
    static String ac,b,c,gen;

    Uri b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhaar);
        if (ActivityCompat.checkSelfPermission(Adhaar.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(Adhaar.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Adhaar.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},0);


        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        id=getIntent().getStringExtra("Aadhaarid");
        share=findViewById(R.id.share);
        share1=findViewById(R.id.share2);
        img=findViewById(R.id.um);
        img2=findViewById(R.id.um2);
        t1=findViewById(R.id.applyaad);
        t2=findViewById(R.id.applyname);
        t3=findViewById(R.id.applydob);
        t4=findViewById(R.id.applygen);
        Button = findViewById(R.id.but1);
        Button.setOnClickListener(this);
        Button2 = findViewById(R.id.but2);
        Button2.setOnClickListener(this);
        card1=findViewById(R.id.card1);
        card2=findViewById(R.id.card2);
        front=findViewById(R.id.aadhaarfrontu1);
        back=findViewById(R.id.aadhaarbacku1);
        bt1=findViewById(R.id.add);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ExampleBottomSheetDialog bottomSheet = new ExampleBottomSheetDialog(Adhaar.this);
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");

            }
        });

        sp=getSharedPreferences("aadhaarfront"+id,MODE_PRIVATE);
        if(sp!=null) {
            String image2 = sp.getString("i", "");
            Bitmap b=convertbit(image2);
            if(!image2.equals("")) {
                img.setImageBitmap(b);
                findViewById(R.id.share).setVisibility(View.VISIBLE);
                front.setVisibility(View.VISIBLE);
            }

            else {
                setMargins(Button,100,20,30,0);
            }

        }
        sp2=getSharedPreferences("aadhaarback"+id,MODE_PRIVATE);
        if(sp2!=null) {
            String image3 = sp2.getString("j", "");
            Bitmap b1=convertbit(image3);
            if(!image3.equals("")) {
                img2.setImageBitmap(b1);
                findViewById(R.id.share2).setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }
            else {
                setMargins(Button2,100,20,30,0);
            }
        }
        spd=getSharedPreferences("aadhaarappdata"+id,MODE_PRIVATE);
        if(spd!=null) {
            ac = spd.getString("aadhaar", "");
            b = spd.getString("name", "");
            c = spd.getString("dob", "");
            gen=spd.getString("gen","");

            if(!ac.equals("")) {
                t1.setText(ac);
                t2.setText(b);
                t3.setText(c);
                t4.setText(gen);
                card1.setVisibility(View.VISIBLE);
                findViewById(R.id.share_det).setVisibility(View.VISIBLE);

            }
            else {
                setMargins(bt1,100,20,0,0);
            }



        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String pic=sp.getString("i","");
                    Bitmap bitmap = convertbit(pic);
                    Intent full = new Intent(Adhaar.this, FullScreenZoom.class);
                    imagezoom=bitmap;
                    full.putExtra("zoomid","adhaarzoom");
                    startActivity(full);
                }
                catch (Exception e){
                    Toast.makeText(Adhaar.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String pic=sp2.getString("j","");
                    Bitmap bitmap = convertbit(pic);
                    Intent full = new Intent(Adhaar.this, FullScreenZoom.class);
                    imagezoom=bitmap;
                    full.putExtra("zoomid","adhaarzoom");
                    startActivity(full);
                }
                catch (Exception e){
                    Toast.makeText(Adhaar.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable mDrawable = img.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                try {
                    File f=new File(Adhaar.this.getExternalCacheDir(),"ThisIsImageTitleString"+ " - " + (Calendar.getInstance().getTime())+".jpeg");
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
                    Toast.makeText(Adhaar.this, ""+e, Toast.LENGTH_SHORT).show();
                }


            }
        });
        share1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable mDrawable = img2.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                try {
                    File f=new File(Adhaar.this.getExternalCacheDir(),"ThisIsImageTitle"+ " - " + (Calendar.getInstance().getTime())+".jpeg");
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
                    Toast.makeText(Adhaar.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static String aadhaar () {
        return ac;
    }
    public static String name () {
        return b;
    }
    public static String dob () {
        return c;
    }

    @Override

    public void onClick(View view) {
        switch(view.getId()){
            case R.id.but1:
                a=1;
                onSelectImageClick(view);
                break;
            case R.id.but2:
                a=2;
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
                if(a==1) {
                    image = (result.getUri()).toString();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver() , result.getUri());
                        String ims=convert(bitmap);
                        // Toast.makeText(this, ""+ims, Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("i", ims);
                        editor.commit();
                        img.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    findViewById(R.id.share).setVisibility(View.VISIBLE);
                    front.setVisibility(View.VISIBLE);

                    setMargins(Button,0,0,0,0);


                    //Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
                }
                else if(a==2) {
                    image = (result.getUri()).toString();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                        String ima = convert(bitmap);
                        SharedPreferences.Editor editor = sp2.edit();
                        editor.putString("j", ima);
                        editor.commit();
                        img2.setImageBitmap(bitmap);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //img.setImageURI(result.getUri());

                    //b2=result.getUri();
                    findViewById(R.id.share2).setVisibility(View.VISIBLE);
                    back.setVisibility(View.VISIBLE);
                    setMargins(Button2,0,0,0,0);
                    // Toast.makeText(this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG).show();
                }
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


//Sharing Image





    @Override
    public void onButtonClicked(String aadhaar, String name, String dob,String gender) {
        t1.setText(aadhaar);
        t2.setText(name);
        t3.setText(dob);
        t4.setText(gender);

        SharedPreferences.Editor editor = spd.edit();
        editor.putString("aadhaar", aadhaar);
        editor.putString("name", name);
        editor.putString("dob", dob);
        editor.putString("gen", gender);

        ac=aadhaar;
        b=name;
        c=dob;
        gen=gender;
        editor.commit();
        card1.setVisibility(View.VISIBLE);
        findViewById(R.id.share_det).setVisibility(View.VISIBLE);
        setMargins(bt1,0,0,0,0);

    }

    public void shareinfo(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "AADHAAR NO : "+ac+"\nNAME : "+b+"\nDOB : "+c+"\nGENDER : "+gen);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
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