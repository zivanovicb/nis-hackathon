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
        import android.widget.ImageView;
        import android.widget.Toast;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {

            //If the draw over permission is not available open the settings screen
            //to grant the permission.
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
        } else {
            initializeView();
        }
    }


    /**
     * Set and initialize the view elements.
     */
    private void initializeView() {
        findViewById(R.id.notify_me).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Inflate the chat head layout we created
                mChatHeadView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.chat_head, null);

                //Add the view to the window.
                final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT);

                //Specify the chat head position
                params.gravity = Gravity.TOP | Gravity.LEFT;        //Initially view will be added to top-left corner
                params.x = 0;
                params.y = 100;

                //Add the view to the window
                mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                mWindowManager.addView(mChatHeadView, params);

                //Set the close button.
                ImageView closeButton = (ImageView) mChatHeadView.findViewById(R.id.close_btn);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //close the service and remove the chat head from the window

                    }


                });

                //Drag and move chat head using user's touch action.
                final ImageView chatHeadImage = (ImageView) mChatHeadView.findViewById(R.id.chat_head_profile_iv);
                chatHeadImage.setOnTouchListener(new View.OnTouchListener() {
                    private int lastAction;
                    private int initialX;
                    private int initialY;
                    private float initialTouchX;
                    private float initialTouchY;

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:

                                //remember the initial position.
                                initialX = params.x;
                                initialY = params.y;

                                //get the touch location
                                initialTouchX = event.getRawX();
                                initialTouchY = event.getRawY();

                                lastAction = event.getAction();
                                return true;
                            case MotionEvent.ACTION_UP:
                                //As we implemented on touch listener with ACTION_MOVE,
                                //we have to check if the previous action was ACTION_DOWN
                                //to identify if the user clicked the view or not.
                                if (lastAction == MotionEvent.ACTION_DOWN) {
                                    //Open the chat conversation click.
                                    //Intent intent = new Intent(ChatHeadService.this, ChatActivity.class);
                                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //startActivity(intent);

                                    //close the service and remove the chat heads

                                }
                                lastAction = event.getAction();
                                return true;
                            case MotionEvent.ACTION_MOVE:
                                //Calculate the X and Y coordinates of the view.
                                params.x = initialX + (int) (event.getRawX() - initialTouchX);
                                params.y = initialY + (int) (event.getRawY() - initialTouchY);

                                //Update the layout with new X & Y coordinate
                                mWindowManager.updateViewLayout(mChatHeadView, params);
                                lastAction = event.getAction();
                                return true;
                        }
                        return false;
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
