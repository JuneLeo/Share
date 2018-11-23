package com.leo.events.model;

/**
 * Created by spf on 2018/11/7.
 */
public class TaskModel extends BaseModel {
    public String text;
    private Runnable runnable;


    public TaskModel(String text) {
        this.text = text;
        type = TYPE_ADAPTER_TWO;
    }

    public TaskModel setTask(Runnable runnable) {
        this.runnable = runnable;
        return this;
    }


    public void run() {
        if (runnable != null) {
            runnable.run();
        }
    }

}
