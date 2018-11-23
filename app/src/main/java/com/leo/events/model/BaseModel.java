package com.leo.events.model;

/**
 * Created by spf on 2018/11/7.
 */
public class BaseModel {
    public static int TYPE_DEFAULT = 0;
    public static final int TYPE_ADAPTER_ONE = TYPE_DEFAULT++;
    public static final int TYPE_ADAPTER_TWO = TYPE_DEFAULT++;
    public static final int TYPE_ADAPTER_THREE = TYPE_DEFAULT++;
    public static final int TYPE_ADAPTER_FOR = TYPE_DEFAULT++;


    public int type;
}
