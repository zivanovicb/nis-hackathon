package com.example.brankozivanovic.nishackathon;

        import android.content.Intent;
        import android.graphics.PixelFormat;
        import android.net.Uri;
        import android.os.Build;
        import android.provider.Settings;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
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

        import com.example.brankozivanovic.nishackathon.service.OverlayService;

        import java.util.Arrays;
        import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private WindowManager mWindowManager;
    private View mChatHeadView;

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
       */     Intent svc = new Intent(this, OverlayService.class);
            startService(svc);
        initializeView();
            //finish();
        //}
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

                ListView list = mChatHeadView.findViewById(R.id.list);
                List<String> strings = Arrays.asList("aa","aa");
                PopUpAdapter adapter = new PopUpAdapter(getApplicationContext(),strings);
                list.setAdapter(adapter);

                ImageButton close = mChatHeadView.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //mWindowManager.removeViewImmediate(mChatHeadView);
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


}
