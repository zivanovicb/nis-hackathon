package com.example.brankozivanovic.nishackathon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.brankozivanovic.nishackathon.pojo.PostPump;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<PostPump> list;

    public RecyclerAdapter(Context context, List<PostPump> list) {
        this.context = context;
        this.list.clear();
        this.list.addAll(list);
    }

    class Popust extends RecyclerView.ViewHolder{

        public TextView getNaziv() {
            return naziv;
        }

        public void setNaziv(TextView naziv) {
            this.naziv = naziv;
        }

        public TextView getPopust() {
            return popust;
        }

        public void setPopust(TextView popust) {
            this.popust = popust;
        }

        private TextView naziv,popust;

        public Popust(View itemView) {
            super(itemView);
            naziv = itemView.findViewById(R.id.naziv);
            popust = itemView.findViewById(R.id.popust);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Popust(LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_up_card,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.e("GOSPODI","KOMITUJ");

        PostPump current = list.get(position);
        ((Popust)holder).popust.setText(current.getDiscount());
        ((Popust) holder).naziv.setText(current.getName());


    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
