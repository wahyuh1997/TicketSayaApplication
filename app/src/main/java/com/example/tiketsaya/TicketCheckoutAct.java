package com.example.tiketsaya;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class TicketCheckoutAct extends AppCompatActivity {

    Button btn_buyticket, btnmines, btnplus, btn_back;
    TextView jumlah, texttotalharga, textmyballance, nama_wisata, lokasi, ketentuan;
    ImageView notice_uang;

    Integer valuejumlahticket = 1;
    Integer myballance = 0;
    Integer valuetotalharga = 0;
    Integer valuehargatiket = 0;
    Integer sisa_balance = 0;

    DatabaseReference reference, reference2, reference3, reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    // Generate Nomor Integer Secara Random
    Integer nomor_trx = new Random().nextInt();
    // -------------#----------

    String date_wisata = "";
    String time_wisata = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_checkout);

        getUsernameLocal();

        Bundle bundle = getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        btnmines = findViewById(R.id.btnmines);
        btnplus = findViewById(R.id.btnplus);
        jumlah = findViewById(R.id.jumlah);
        texttotalharga = findViewById(R.id.texttotalharga);
        textmyballance = findViewById(R.id.textmyballance);

        btn_buyticket = findViewById(R.id.btn_buyticket);
        btn_back = findViewById(R.id.btn_back);
        notice_uang = findViewById(R.id.notice_uang);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        //Setting Value baru untuk beberapa komponen
        jumlah.setText(valuejumlahticket.toString());


        // Secara Default, Hide Button Minus
        btnmines.animate().alpha(0).setDuration(300).start();
        btnmines.setEnabled(false);
        notice_uang.setVisibility(View.GONE);


        // Mengambil Data dari Firebase berdasarkan Intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());

                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();

                valuehargatiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                valuetotalharga = valuehargatiket * valuejumlahticket;
                texttotalharga.setText("US$" +valuetotalharga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Mengambil Data User dari Firebase berdasarkan Intent
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                myballance = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                textmyballance.setText("US$ " + myballance + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        // JIKA SALDO TIDAK CUKUP
        btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahticket+=1;
                jumlah.setText(valuejumlahticket.toString());
                if (valuejumlahticket > 1) {
                    btnmines.animate().alpha(1).setDuration(300).start();
                    btnmines.setEnabled(true);
                }

                valuetotalharga = valuehargatiket * valuejumlahticket;
                texttotalharga.setText("US$" +valuetotalharga+"");
                if (valuetotalharga > myballance) {
                    btn_buyticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buyticket.setEnabled(false);
                    textmyballance.setTextColor(Color.parseColor("#D1206B"));
                    notice_uang.setVisibility(View.VISIBLE);
                }
            }
        });
        // JIKA SALDO CUKUP
        btnmines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                valuejumlahticket-=1;
                jumlah.setText(valuejumlahticket.toString());
                if (valuejumlahticket < 2 ) {
                    btnmines.animate().alpha(0).setDuration(300).start();
                    btnmines.setEnabled(false);
                }
                valuetotalharga = valuehargatiket * valuejumlahticket;
                texttotalharga.setText("US$" +valuetotalharga+"");
                if (valuetotalharga < myballance) {
                    btn_buyticket.animate().translationY(0).alpha(1).setDuration(350).start();
                    btn_buyticket.setEnabled(true);
                    textmyballance.setTextColor(Color.parseColor("#203DD1"));
                    notice_uang.setVisibility(View.GONE);
                }
            }
        });

        btn_buyticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Menyimpan data User kepada Firebase dan Membuat Tabel baru "MyTicket"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTickets").child(username_key_new).child(nama_wisata.getText().toString() + nomor_trx);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_tiket").setValue(nama_wisata.getText().toString() + nomor_trx);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("jumlah_tiket").setValue(valuejumlahticket.toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);

                        Intent gotosuccess = new Intent(TicketCheckoutAct.this,SuccessBuyTicketAct.class);
                        startActivity(gotosuccess);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                // Updata Data Balance kepada Users (yang saat ini Login)
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = myballance - valuetotalharga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getUsernameLocal() {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
