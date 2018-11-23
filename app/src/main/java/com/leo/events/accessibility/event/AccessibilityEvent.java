package com.leo.events.accessibility.event;

/**
 * Created by spf on 2018/10/23.
 */

public abstract class AccessibilityEvent {
    protected static final String TAG = "accessibility-";
    public static final int WECHAT_SHARE_IMAGE = 1;
    public static final int WECHAT_SHARE_TEXT = 2;
    public int type ;

    public AccessibilityEvent(int type) {
        this.type = type;
    }

    public AccessibilityEvent() {
    }

    abstract public boolean operator();

    protected void dispose(){

    }

    public boolean isUIThread() {
        return true;
    }
}
