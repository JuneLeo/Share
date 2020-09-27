package com.leo.events;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by spf on 2018/11/29.
 */
public class PayWindowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.window_share);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            window.setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        }else {
            window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
        }
    }
}
