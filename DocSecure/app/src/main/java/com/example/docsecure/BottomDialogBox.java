package com.example.docsecure;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;

public class BottomDialogBox extends BottomSheetDialogFragment {
EditText username;
MaterialButton add,delete,cancel;
String un;
private BottomSheetListener mlistener;

    public BottomDialogBox() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setCancelable(false);
        View view=inflater.inflate(R.layout.add_username,container,false);
        username=view.findViewById(R.id.username);
        add=view.findViewById(R.id.add);
        delete=view.findViewById(R.id.delete);
        cancel=view.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();

            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               un=username.getText().toString().trim();
                String str=new String(un);
                un=str.toLowerCase();
               mlistener.onButtonClicked(un);

               /* String s = "Not Yet Implemented";
                username.setText(s);*/

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {
                un=username.getText().toString().trim();
                String str=new String(un);
                un=str.toLowerCase();
                mlistener.deletebtn(un);
                /*String s = "Not Yet Implemented";
                username.setText(s);*/


            }
        });
        return view;




    }
    public interface BottomSheetListener{
        void onButtonClicked(String name);


        void deletebtn(String un);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            mlistener=(BottomSheetListener)context;

        }
        catch (Exception e)
        {
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }
}
