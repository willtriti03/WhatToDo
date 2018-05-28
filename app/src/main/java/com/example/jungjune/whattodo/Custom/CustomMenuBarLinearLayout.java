package com.example.jungjune.whattodo.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.R;

public class CustomMenuBarLinearLayout extends LinearLayout{
    private ImageView pin;

    private TaskItem taskItem;
    private Context context;
    private View v;

    public CustomMenuBarLinearLayout(Context context) {
        super(context);
        initView();
    }

    public CustomMenuBarLinearLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        initView();

    }

    public CustomMenuBarLinearLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
    }

    private void initView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        v = li.inflate(R.layout.swipe_right_side_layout, this, false);
        addView(v);

        pin = (ImageView)v.findViewById(R.id.itemTaskPin);


        LinearLayout fixBtn = (LinearLayout)v.findViewById(R.id.itemTaskFixBtn);
        LinearLayout repeatBtn =(LinearLayout)v.findViewById(R.id.itemTaskRepeatBtn);
        LinearLayout deleteBtn = (LinearLayout)v.findViewById(R.id.itemTaskDeleteBtn);
        LinearLayout closeBtn = (LinearLayout)v.findViewById(R.id.itemTaskCloseBtn);
    }
    private void setPin(TaskItem taskItem){
        if(taskItem.getFix())
            pin.setBackground(context.getDrawable(R.drawable.push_pin_on));
        else
            pin.setBackground(context.getDrawable(R.drawable.push_pin));

    }


    public void setTaskItem(TaskItem taskItem) {
        this.taskItem = taskItem;
        setPin(taskItem);
        //setRepeat(taskItem);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
