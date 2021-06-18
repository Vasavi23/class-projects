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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class PanCard extends AppCompatActivity implements View.OnClickListener, PanBottomSheetDialog.BottomSheetListener{
    TextView panno,name,dob,gender;
    MaterialButton panadd,addimage,shareimage;
    ImageView panview;
    static Bitmap imagezoom;
    private Uri mCropImageUri;
    MaterialCardView front,detailsview;
    String id,image;
    SharedPreferences sp,spd;
    static String vpannum,vpanname,vpandob,vpangen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan_card);
        if (ActivityCompat.checkSelfPermission(PanCard.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED || ActivityCompat.checkSelfPermission(PanCard.this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(PanCard.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},0);


        }
        //Sharing Mode
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        //Intent extra text
        id=getIntent().getStringExtra("Panid");
        //Text Views
        panno=findViewById(R.id.panno);
        name=findViewById(R.id.panname);
        dob=findViewById(R.id.pandob);
        gender=findViewById(R.id.pangen);
        //Buttons
        panadd=findViewById(R.id.panadd);
        panadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PanBottomSheetDialog bottomSheet = new PanBottomSheetDialog(PanCard.this);
                bottomSheet.show(getSupportFragmentManager(), "PanBottomSheet");
            }
        });
        addimage=findViewById(R.id.panpic);
        addimage.setOnClickListener(this);
        shareimage=findViewById(R.id.picshare);

        //ImageView
        panview=findViewById(R.id.panimageview);
        //Material Card
        front=findViewById(R.id.panfront);
        detailsview=findViewById(R.id.pancard1);
        //Shared Preference Database
        sp=getSharedPreferences("panview"+id,MODE_PRIVATE);
        if(sp!=null) {
            String image2 = sp.getString("panimage", "");
            Bitmap b=convertbit(image2);
            if(!image2.equals("")) {
                panview.setImageBitmap(b);
                findViewById(R.id.picshare).setVisibility(View.VISIBLE);
                front.setVisibility(View.VISIBLE);
            }
            else {
                setMargins(addimage,100,20,30,0);
            }
        }
        spd=getSharedPreferences("pandata"+id,MODE_PRIVATE);
        if(spd!=null) {
            vpannum = spd.getString("panno", "");
            vpanname = spd.getString("name", "");
            vpandob = spd.getString("dob", "");
            vpangen = spd.getString("gen", "");
            if (!vpannum.equals("")) {
                panno.setText(vpannum);
                name.setText(vpanname);
                dob.setText(vpandob);
                gender.setText(vpangen);
                detailsview.setVisibility(View.VISIBLE);
                findViewById(R.id.share_det).setVisibility(View.VISIBLE);

            }
            else {
                setMargins(panadd,100,20,30,0);
            }

        }

        shareimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable mDrawable = panview.getDrawable();
                Bitmap mBitmap = ((BitmapDrawable) mDrawable).getBitmap();
                try {
                    File f=new File(PanCard.this.getExternalCacheDir(),"PanImage"+ " - " + (Calendar.getInstance().getTime())+".jpeg");
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
                    Toast.makeText(PanCard.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });

        panview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String pic=sp.getString("panimage","");
                    Bitmap bitmap = convertbit(pic);
                    Intent full = new Intent(PanCard.this, FullScreenZoom.class);
                    imagezoom=bitmap;
                    full.putExtra("zoomid","panzoom");
                    startActivity(full);
                }
                catch (Exception e){
                    Toast.makeText(PanCard.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    public static String pannum () {
        return vpannum;
    }
    public static String panname () { return vpanname;}
    public static String pandob () {
        return vpandob;
    }

    public void sharepandetails(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "PAN NO : "+vpannum+"\nNAME : "+vpanname+"\nDOB : "+vpandob+"\nGENDER : "+vpangen);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.panpic:
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
                    editor.putString("panimage", ims);
                    editor.commit();
                    panview.setImageBitmap(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.picshare).setVisibility(View.VISIBLE);
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


    @Override
    public void onButtonClicked(String gpanno, String gname, String gdob, String ggender) {
        panno.setText(gpanno);
        name.setText(gname);
        dob.setText(gdob);
        gender.setText(ggender);

        SharedPreferences.Editor editor = spd.edit();
        editor.putString("panno", gpanno);
        editor.putString("name", gname);
        editor.putString("dob", gdob);
        editor.putString("gen", ggender);

        vpannum=gpanno;
        vpanname=gname;
        vpandob=gdob;
        vpangen=ggender;
        editor.commit();
        detailsview.setVisibility(View.VISIBLE);
        findViewById(R.id.share_det).setVisibility(View.VISIBLE);

            setMargins(panadd,0,0,0,0);

    }
    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }
}