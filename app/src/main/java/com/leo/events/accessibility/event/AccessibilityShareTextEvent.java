package com.leo.events.accessibility.event;

import com.leo.events.accessibility.AccessibilityOperator;

/**
 * Created by spf on 2018/10/23.
 */

public class AccessibilityShareTextEvent extends AccessibilityEvent {

    public AccessibilityShareTextEvent(int type) {
        super(type);
    }

    @Override
    public boolean operator() {
        return AccessibilityOperator.getInstance().clickByText("发表");
    }
}
