package com.example.docsecure;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class FullScreenZoom extends AppCompatActivity {
    ImageView full;
    //Uri imageuri;
    //Resizing
    String dynamicimg;
    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_zoom);
        dynamicimg=getIntent().getStringExtra("zoomid");
        full=findViewById(R.id.fullscreenimageview);

        Intent calling=getIntent();
        if(calling!=null){

            Bitmap bmp=null;
            if(dynamicimg.equals("adhaarzoom"))
            {
                bmp = Adhaar.imagezoom;
            }
            else if ((dynamicimg.equals("panzoom")))
            {
                bmp= PanCard.imagezoom;
            }
            else if(dynamicimg.equals("certificatezoom"))
            {
                bmp= CertificatepicGlobal.imagezoom;
            }
            else if(dynamicimg.equals("othercardzoom"))
            {
                bmp= Othercards.imagezoom;
            }
            else if(dynamicimg.equals(("creditcardzoom")))
            {
                bmp= CardspicGlobal.imagezoom;
            }
            else if(dynamicimg.equals(("idcardzoom")))
            {
                bmp= IDpicGlobal.imagezoom;
            }
            else if(dynamicimg.equals(("passportzoom")))
            {
                bmp= Passport.imagezoom;
            }
            else if(dynamicimg.equals(("rationcardzoom")))
            {
                bmp= Rationcard.imagezoom;
            }
            else if(dynamicimg.equals(("licencezoom")))
            {
                bmp= Licence.imagezoom;
            }
            else if(dynamicimg.equals(("rczoom")))
            {
                bmp= RCbook.imagezoom;
            }
            if(bmp!=null && full!=null){
                Glide.with(this).load(bmp).into(full);
            }
        }
        full.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ImageView view1 = (ImageView) view;
                view1.bringToFront();
                viewTransformation(view1, motionEvent);
                return true;
            }
        });
    }
    private void viewTransformation(View view, MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                xCoOrdinate = view.getX() - event.getRawX();
                yCoOrdinate = view.getY() - event.getRawY();

                start.set(event.getX(), event.getY());
                isOutSide = false;
                mode = DRAG;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist > 10f) {
                    midPoint(mid, event);
                    mode = ZOOM;
                }

                lastEvent = new float[4];
                lastEvent[0] = event.getX(0);
                lastEvent[1] = event.getX(1);
                lastEvent[2] = event.getY(0);
                lastEvent[3] = event.getY(1);
                d = rotation(event);
                break;
            case MotionEvent.ACTION_UP:
                isZoomAndRotate = false;
                if (mode == DRAG) {
                    float x = event.getX();
                    float y = event.getY();
                }
            case MotionEvent.ACTION_OUTSIDE:
                isOutSide = true;
                mode = NONE;
                lastEvent = null;
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                lastEvent = null;
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isOutSide) {
                    if (mode == DRAG) {
                        isZoomAndRotate = false;
                        view.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                    }
                    if (mode == ZOOM && event.getPointerCount() == 2) {
                        float newDist1 = spacing(event);
                        if (newDist1 > 10f) {
                            float scale = newDist1 / oldDist * view.getScaleX();
                            view.setScaleX(scale);
                            view.setScaleY(scale);
                        }
                        if (lastEvent != null) {
                            newRot = rotation(event);
                            view.setRotation((float) (view.getRotation() + (newRot - d)));
                        }
                    }
                }
                break;
        }
    }

    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (int) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}