package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.florent37.shapeofview.shapes.CircleView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_pisa, btn_tori, btn_monas, btn_pagoda, btn_candi, btn_sphinx;

    CircleView btn_to_profile;
    ImageView photo_home_user;
    TextView user_balance, nama_lengkap, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_pisa = findViewById(R.id.btn_pisa);
        btn_tori = findViewById(R.id.btn_tori);
        btn_pagoda = findViewById(R.id.btn_pagoda);
        btn_candi = findViewById(R.id.btn_candi);
        btn_monas = findViewById(R.id.btn_monas);
        btn_sphinx = findViewById(R.id.btn_sphinx);

        btn_to_profile = findViewById(R.id.btn_to_profile);

        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                user_balance.setText(dataSnapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this).load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_pisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "pisa");
                startActivity(gotopisaticket);
            }
        });


        btn_candi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Candi");
                startActivity(gotopisaticket);
            }
        });

        btn_monas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Monas");
                startActivity(gotopisaticket);
            }
        });

        btn_sphinx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "Sphinx");
                startActivity(gotopisaticket);
            }
        });

        btn_tori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "tori");
                startActivity(gotopisaticket);
            }
        });

        btn_pagoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotopisaticket = new Intent(HomeAct.this, TicketDetailsAct.class);
                gotopisaticket.putExtra("jenis_tiket", "pagoda");
                startActivity(gotopisaticket);
            }
        });
    }
    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
