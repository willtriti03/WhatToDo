package com.example.jungjune.whattodo.Item;

import android.view.View;

public class TaskItemWithView {
    private View v;
    private TaskItem taskItem;
    public  TaskItemWithView(TaskItem tk){
        taskItem= tk;
    }

    public void setTaskItem(TaskItem taskItem) {
        this.taskItem = taskItem;
    }

    public void setV(View v) {
        this.v = v;
    }

    public TaskItem getTaskItem() {
        return taskItem;
    }

    public View getV() {
        return v;
    }
}
