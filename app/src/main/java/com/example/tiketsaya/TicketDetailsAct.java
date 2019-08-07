package com.example.tiketsaya;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
//import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class TicketDetailsAct extends AppCompatActivity {

    Button btn_buyticket, btn_back;

    TextView title_ticket, location_ticket, photo_spot, wifi, festival, short_desc;

    ImageView header;

    DatabaseReference reference;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        btn_buyticket = findViewById(R.id.btn_buyticket);
        btn_back = findViewById(R.id.btn_back);
        header = findViewById(R.id.header);

        location_ticket = findViewById(R.id.location_ticket);
        title_ticket = findViewById(R.id.title_ticket);
        photo_spot = findViewById(R.id.photo_spot);
        wifi = findViewById(R.id.wifi);
        festival = findViewById(R.id.festival);
        short_desc = findViewById(R.id.short_desc);

        //Mengambil Data Dari Intent
        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        // Mengambil Database
        reference = FirebaseDatabase.getInstance().getReference()
                .child("Wisata").child(jenis_tiket_baru);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yang ada dengan data yang baru yang disimpan di firebase

                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_spot.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc.setText(dataSnapshot.child("short_desc").getValue().toString());

                Picasso.with(TicketDetailsAct.this).load(dataSnapshot.child("url_thumbnail").getValue().toString()).centerCrop().fit().into(header);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_buyticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(TicketDetailsAct.this,TicketCheckoutAct.class);
                gotocheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
