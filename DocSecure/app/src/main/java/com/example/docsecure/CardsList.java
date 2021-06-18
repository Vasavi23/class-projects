package com.example.docsecure;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class CardsList extends AppCompatActivity {
    String id;
    MaterialButton AdhaarButton,PanButton,CertificatesButton,OthersButton,CreditButton,IDButton,PassportButton,RationButton,LicenceButton,RCButton,PasswordsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_list);

        id=getIntent().getStringExtra("id");
        AdhaarButton=findViewById(R.id.adhaar);
        PanButton=findViewById(R.id.pan);
        CertificatesButton=findViewById(R.id.certificate);
        OthersButton=findViewById(R.id.other);
        CreditButton=findViewById(R.id.atm);
        IDButton=findViewById(R.id.id);
        PassportButton=findViewById(R.id.passport);
        RationButton=findViewById(R.id.ration);
        LicenceButton=findViewById(R.id.driving);
        RCButton=findViewById(R.id.rc);
        PasswordsButton=findViewById(R.id.Passwords);
        AdhaarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this, Adhaar.class);
                i.putExtra("Aadhaarid",id);
                startActivity(i);
            }
        });
        PanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this,PanCard.class);
                i.putExtra("Panid",id);
                startActivity(i);
            }
        });
        CertificatesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this,Certificates.class);
                i.putExtra("Certificateid",id);
                startActivity(i);

            }
        });
        OthersButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this,Others.class);
                i.putExtra("Othersid",id);
                startActivity(i);


            }
        }));
        CreditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this,CreditCard.class);
                i.putExtra("Creditcardid",id);
                startActivity(i);
            }
        });
        IDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(CardsList.this,IDCards.class);
                i.putExtra("Idcardsid",id);
                startActivity(i);
            }
        });
        PassportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(CardsList.this,Passport.class);
                i.putExtra("Passportid",id);
                startActivity(i);
            }
        });
        RationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(CardsList.this,Rationcard.class);
                i.putExtra("rationcardid",id);
                startActivity(i);
            }
        });

        LicenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(CardsList.this,Licence.class);
                i.putExtra("Licenceid",id);
                startActivity(i);
            }
        });
        RCButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(CardsList.this,RCbook.class);
                i.putExtra("RCbookid",id);
                startActivity(i);
            }
        });

        PasswordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CardsList.this,Passwords.class);

                startActivity(i);
            }
        });

    }
}