package com.example.tiketsaya;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyVieHolder> {

    Context context;
    ArrayList <MyTicket> myTicket;

    public TicketAdapter (Context c, ArrayList<MyTicket> p) {
        context = c;
        myTicket = p;
    }

    @NonNull
    @Override
    public MyVieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyVieHolder(LayoutInflater.
                from(context).inflate(R.layout.item_myticket,
                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyVieHolder myVieHolder, int i) {
        myVieHolder.xnama_wisata.setText(myTicket.get(i).getNama_wisata());
        myVieHolder.xlokasi.setText(myTicket.get(i).getLokasi());
        myVieHolder.xjumlah_tiket.setText(myTicket.get(i).getJumlah_tiket()+ " Tickets");

        final String getNamaWisata = myTicket.get(i).getNama_wisata();

        myVieHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent GotoMyTicketDetail = new Intent(context, MyTicketAct.class);
                GotoMyTicketDetail.putExtra("nama_wisata", getNamaWisata);
                context.startActivity(GotoMyTicketDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myTicket.size();
    }

    class MyVieHolder extends RecyclerView.ViewHolder {

        TextView xnama_wisata, xlokasi, xjumlah_tiket;

        public MyVieHolder(@NonNull View itemView) {
            super(itemView);

            xnama_wisata = itemView.findViewById(R.id.xnama_wisata);
            xlokasi = itemView.findViewById(R.id.xlokasi);
            xjumlah_tiket = itemView.findViewById(R.id.xjumlah_tiket);
        }
    }
}
