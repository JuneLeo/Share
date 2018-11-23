package com.leo.events.accessibility.event;

import android.os.Handler;
import android.os.Looper;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created by spf on 2018/10/23.
 */

public class AccessibilityEventManaer {
    LinkedList<AccessibilityEvent> linkedList = new LinkedList<>();
    LinkedBlockingDeque<AccessibilityEvent> linkedBlockingDeque = new LinkedBlockingDeque<>();
    Handler mHandler;

    public void addEvent(AccessibilityEvent event) {
        linkedList.add(event);
    }

    public void addFistEvent(AccessibilityEvent event) {
        linkedList.addFirst(event);
    }

    public synchronized boolean excute() {
        if (linkedList.isEmpty()) {
            return false;
        }
        AccessibilityEvent first = linkedList.getFirst();
        boolean operator = first.operator();
        if (operator) {
            linkedList.remove(first);
            first.dispose();
        }
        return operator;
    }

    private static AccessibilityEventManaer accessibilityEventManaer = new AccessibilityEventManaer();

    public static AccessibilityEventManaer get() {
        return accessibilityEventManaer;
    }

    public void dispose() {
        linkedList.clear();
    }

    public void init() {
        mHandler = new Handler(Looper.getMainLooper());
        initBlockingDeque();
    }

    public void initBlockingDeque() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AccessibilityEvent event;
                    while (true) {
                        event = linkedBlockingDeque.take();
                        if (event.isUIThread()) {
                            final AccessibilityEvent uiEvent = event;
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    uiEvent.operator();
                                }
                            });
                        } else {
                            event.operator();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void putEvent(AccessibilityEvent event) {
        linkedBlockingDeque.push(event);
    }

}
