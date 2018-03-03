package com.example.brankozivanovic.nishackathon;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brankozivanovic.nishackathon.pojo.PostPump;

import java.util.List;

public class PopUpAdapter extends BaseAdapter {

    private Context context;
    private List<PostPump> list;

    public PopUpAdapter(Context context, List<PostPump> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.pop_up_card,null,false);
        }

       PostPump current = list.get(position);


        Button prihvatam = convertView.findViewById(R.id.prihvatam);
        prihvatam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "DOBAR PLASTICAR", Toast.LENGTH_SHORT).show();
            }
        });



        TextView naziv = convertView.findViewById(R.id.naziv);
        TextView popust = convertView.findViewById(R.id.popust);

        naziv.setText(current.getName());
        popust.setText(current.getDiscount());


        Log.e("NAME",current.getName());



        return convertView;
    }
}
