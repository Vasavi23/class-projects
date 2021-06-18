package com.example.docsecure;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class PanBottomSheetDialog extends BottomSheetDialogFragment {
    private BottomSheetListener mlistener;
    TextInputEditText e1,e2;
    EditText e3;
    MaterialButton add,cancel;
    Context c;
    String gender;
    RadioButton r1,r2;
    int d,m,y;
    final Calendar myCalendar = Calendar.getInstance();
    Context ct;
    public PanBottomSheetDialog(PanCard pan) {
        ct=pan;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setCancelable(false);
        View v=inflater.inflate(R.layout.pan_details,container,false);
        e1=v.findViewById(R.id.setpannum);
        e2=v.findViewById(R.id.setpanname);
        e3=v.findViewById(R.id.pandatepick);
        r1=v.findViewById(R.id.male);
        r2=v.findViewById(R.id.female);

        assign();
        add=v.findViewById(R.id.addbtn);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Radio Buttons
                if(r1.isChecked()){
                    gender="Male";
                }
                else if(r2.isChecked()){
                    gender="Female";
                }
                mlistener.onButtonClicked(e1.getText().toString(),e2.getText().toString()
                        ,e3.getText().toString(),gender);
                e1.setText(e1.getText().toString());
                dismiss();
            }
        });
        cancel=v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        //DATE PICKER



        e3= v.findViewById(R.id.pandatepick);


        e3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Calendar c=Calendar.getInstance();
                y=c.get(Calendar.YEAR);
                m=c.get(Calendar.MONTH);
                d=c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dp=new DatePickerDialog(ct,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        //Toast.makeText(ct, dayOfMonth+"/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();
                        e3.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },y,m,d);
                dp.show();

            }
        });


        return v;
    }
    // to retrieve
    public void assign(){
        e1.setText(PanCard.pannum());
        e2.setText(PanCard.panname());
        e3.setText(PanCard.pandob());

    }
    public interface BottomSheetListener{
        void onButtonClicked(String panno, String name, String dob,String gender);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mlistener = (BottomSheetListener) context;
        }
        catch (Exception e){
            Toast.makeText(context, "Must Implement Pan BottomSheet Listener", Toast.LENGTH_SHORT).show();
        }
    }
}