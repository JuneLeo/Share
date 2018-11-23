package com.leo.events.abtest;

/**
 * Created by spf on 2018/11/22.
 */
public class ABTestModel {
    public String method;
    public String value;
    public String source;
    public String des;
    public String type;

    public ABTestModel(String type,String value) {
        this.type = type;
        this.value = value;

    }

    public ABTestModel build() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("if(com.leo.events.abtest.ABTest.isDebug()){ return");
        stringBuilder.append(value);
        stringBuilder.append(";}");
        this.des = stringBuilder.toString();
        return this;
    }
}
