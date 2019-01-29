package com.example.jungjune.whattodo.Custom;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.jungjune.whattodo.Adapter.TaskAdapter;
import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.Item.TaskItemWithView;
import com.example.jungjune.whattodo.R;

public class CustomMenuBarLinearLayout extends LinearLayout{
    private ImageView pin;
    private  ImageView repeat;
    private TaskItem taskItem;
    private TaskItemWithView taskItemWithView;
    private Context context;
    private TaskAdapter adapter;
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
        repeat = (ImageView)v.findViewById(R.id.itemTaskRepeat);


        LinearLayout fixBtn = (LinearLayout)v.findViewById(R.id.itemTaskFixBtn);
        LinearLayout repeatBtn =(LinearLayout)v.findViewById(R.id.itemTaskRepeatBtn);
        LinearLayout deleteBtn = (LinearLayout)v.findViewById(R.id.itemTaskDeleteBtn);


        fixBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                taskItem.setFix(!taskItem.getFix());
                taskItem.setState(0);
                setPin(taskItem);
                adapter.fixSorting(taskItem,taskItemWithView);

            }
        });

        repeatBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                taskItem.setRepeat(!taskItem.getRepeat());
                setRepeat(taskItem);
                adapter.setRepeat(taskItem);
            }
        });

        deleteBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                taskItem.setDeleted(true);
                adapter.deleteItem(taskItem,taskItemWithView);
            }
        });
    }
    private void setPin(TaskItem taskItem){
        if(taskItem.getFix())
            pin.setBackground(context.getDrawable(R.drawable.push_pin_on));
        else
            pin.setBackground(context.getDrawable(R.drawable.push_pin));

    }

    private  void  setRepeat(TaskItem taskItem){
        if(taskItem.getRepeatB())
            repeat.setBackground(context.getDrawable(R.drawable.repeat));
        else
            repeat.setBackground(context.getDrawable(R.drawable.error));

    }

    public void setTaskItem(TaskItem taskItem) {
        this.taskItem = taskItem;
        setPin(taskItem);
        setRepeat(taskItem);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAdapter(TaskAdapter adapter) {
        this.adapter = adapter;
    }

    public void setTaskItemWithView(TaskItemWithView taskItemWithView) {
        this.taskItemWithView = taskItemWithView;
    }
}
