package com.example.brankozivanovic.nishackathon;

        import android.content.Intent;
        import android.graphics.PixelFormat;
        import android.net.Uri;
        import android.os.Build;
        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.LayoutInflater;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ImageButton;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.Toast;

        import com.example.brankozivanovic.nishackathon.networking.Api;
        import com.example.brankozivanovic.nishackathon.networking.ApiUtill;
        import com.example.brankozivanovic.nishackathon.pojo.PostPump;
        import com.example.brankozivanovic.nishackathon.service.ChatHeadService;
        import com.example.brankozivanovic.nishackathon.service.OverlayService;
        import com.google.gson.Gson;

        import java.util.Arrays;
        import java.util.List;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private WindowManager mWindowManager;
    private View mChatHeadView;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Check if the application has draw over other apps permission or not?
        //This permission is by default available for API<23. But for API > 23
        //you have to ask for the permission in runtime.
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
       */     Intent svc = new Intent(this, ChatHeadService.class);
            startService(svc);
       // initializeView();
            //finish();
        //}

        goGetPump();
    }


    /**
     * Set and initialize the view elements.
     */
    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Inflate the chat head layout we created
                mChatHeadView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.pop_up, null);

                //Add the view to the window.
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);

                //Specify the chat head position
                params.gravity = Gravity.CENTER | Gravity.LEFT;        //Initially view will be added to top-left corner
                /*params.x = 0;
                params.y = 100;*/

                //Add the view to the window
                mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                mWindowManager.addView(mChatHeadView, params);



                ImageButton close = mChatHeadView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mWindowManager.removeViewImmediate(mChatHeadView);
                        //mWindowManager.removeView(mChatHeadView);
                    }
                });

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            // Settings activity never returns proper value so instead check with following method
            if (Settings.canDrawOverlays(this)) {
                initializeView();
            } else { //Permission is not available
                Toast.makeText(this,
                        "Draw over other app permission not available. Closing the application",
                        Toast.LENGTH_SHORT).show();

                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void goGetPump(){
        ApiUtill.getApi().postPump("123","312").enqueue(new Callback<List<PostPump>>() {
            @Override
            public void onResponse(Call<List<PostPump>> call, Response<List<PostPump>> response) {
                if(response.isSuccessful()){
                    System.out.println("aaa");
                    setupList(response.body());


                }else {
                    Log.e("RESPONSE FAILED","RESPONSE FAILED");
                    Log.e("RAW",response.raw().toString());
                    System.out.println("Od golgote do kalkute");
                }
            }

            @Override
            public void onFailure(Call<List<PostPump>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void setupList(List<PostPump> popusti){
        RecyclerView recycler = mChatHeadView.findViewById(R.id.recycler);
        RecyclerView.LayoutManager manager = new  LinearLayoutManager(this);
        RecyclerAdapter adapter = new RecyclerAdapter(this,popusti);
        recycler.setLayoutManager(manager);
        recycler.setAdapter(adapter);
    }


}
