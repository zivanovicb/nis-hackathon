package com.example.brankozivanovic.nishackathon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class PopUpAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;

    public PopUpAdapter(Context context, List<String> list) {
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


        Button prihvatam = convertView.findViewById(R.id.prihvatam);
        prihvatam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "DOBAR PLASTICAR", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
