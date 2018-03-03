package com.example.brankozivanovic.nishackathon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ChatActivity extends AppCompatActivity {

    public ChatActivity(){
        Log.e("WOO","ROO");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
    }
}
