package com.leo.events.abtest;

import android.text.TextUtils;

import com.leo.events.BuildConfig;
import com.leo.events.ProductActivity;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by spf on 2018/11/22.
 */
public class ABTest {
    //注入后代码中读取的数据
    public static Map<String, Map<String, ABTestModel>> data = new HashMap<>();
    public static final String TYPE_UNKNOW = "type_unknow";
    public static final String TYPE_STRING = "type_string";
    public static final String TYPE_INTEGER = "type_integer";
    public static final String TYPE_LONG = "type_long";
    public static final String TYPE_SHORT = "type_short";
    public static final String TYPE_BOOLEAN = "type_boolean";
    public static final String TYPE_FLOAT = "type_float";
    public static final String TYPE_DOUBLE = "type_double";

    public static void inject(Class clz, String method, Object o) {
        if (o == null) {
            return;
        }
        if (TextUtils.isEmpty(method)) {
            return;
        }
        String type = TYPE_UNKNOW;
        if (o instanceof String) {
            type = TYPE_STRING;
        } else if (o instanceof Integer) {
            type = TYPE_INTEGER;
        } else if (o instanceof Long) {
            type = TYPE_LONG;
        } else if (o instanceof Short) {
            type = TYPE_SHORT;
        } else if (o instanceof Boolean) {
            type = TYPE_BOOLEAN;
        } else if (o instanceof Float) {
            type = TYPE_FLOAT;
        } else if (o instanceof Double) {
            type = TYPE_DOUBLE;
        }
        if (type.equals(TYPE_UNKNOW)) {
            return;
        }

        Map<String, ABTestModel> map = data.get(clz.getCanonicalName());
        if (map == null){
            map = new HashMap<>();
            data.put(clz.getCanonicalName(),map);
        }
        ABTestModel model = new ABTestModel(type, String.valueOf(o));
        map.put(method, model);
    }

    public static boolean isDebug() {
        return BuildConfig.DEBUG;
    }

    /**
     * 注入代码中调用
     * @param clz
     * @param method
     * @return
     */
    public static ABTestModel get(String clz, String method) {
        Map<String, ABTestModel> abTestModelMap = data.get(clz);
        if (abTestModelMap != null) {
            ABTestModel model = abTestModelMap.get(method);
            if (model != null) {
                return model;
            }
        }
        return null;
    }

    /**
     * 注入代码中调用
     * @param model
     * @return
     */
    public static Object transform(ABTestModel model) {
        if (model.type.equals(TYPE_STRING)) {
            return model.value;
        }
        return null;
    }


    public static void initConfig() {
        inject(ProductActivity.class, "getName", "你好");
        inject(ProductActivity.class,"getInteger",2345);
    }


    public Integer getInteger() {
        if (ABTest.isDebug()) {
            ABTestModel var1 = ABTest.get("com.leo.events.ProductActivity", "getInteger");
            if (var1 != null) {
                return Integer.valueOf(var1.value);
            }
        }

        return 1;
    }

}
