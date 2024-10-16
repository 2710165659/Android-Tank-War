package com.main.tankwar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomImageButton extends androidx.appcompat.widget.AppCompatImageButton {

    private boolean isPressed = false;

    public CustomImageButton(Context context) {
        super(context);
    }

    public CustomImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 按下时
                isPressed = true;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 抬起或取消时
                isPressed = false;
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean isPressed() {
        return isPressed;
    }
}