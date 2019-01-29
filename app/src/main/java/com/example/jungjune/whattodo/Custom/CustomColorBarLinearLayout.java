package com.example.jungjune.whattodo.Custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jungjune.whattodo.Adapter.TaskAdapter;
import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.Item.TaskItemWithView;
import com.example.jungjune.whattodo.R;

public class CustomColorBarLinearLayout extends LinearLayout {
    LinearLayout[] colorBtn = new LinearLayout[7];
    TaskItem taskItem;
    TaskItemWithView taskItemWithView;
    Context context;
    TaskAdapter adapter;

    public CustomColorBarLinearLayout(Context context) {

        super(context);
        initView();

    }

    public CustomColorBarLinearLayout(Context context, AttributeSet attrs) {

        super(context, attrs);

        initView();

    }

    public CustomColorBarLinearLayout(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs);
        initView();
    }


    private void initView() {

        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.swipe_left_side_layout, this, false);
        addView(v);

        colorBtn[0] = (LinearLayout)v.findViewById(R.id.itemTaskRedBtn);
        colorBtn[1] = (LinearLayout)v.findViewById(R.id.itemTaskOrangeBtn);
        colorBtn[2] = (LinearLayout)v.findViewById(R.id.itemTaskGreenBtn);
        colorBtn[3] = (LinearLayout)v.findViewById(R.id.itemTaskMintBtn);
        colorBtn[4] = (LinearLayout)v.findViewById(R.id.itemTaskBlueBtn);
        colorBtn[5] = (LinearLayout)v.findViewById(R.id.itemTaskNavyBtn);
        colorBtn[6] = (LinearLayout)v.findViewById(R.id.itemTaskTransBtn);

        for(int i = 0; i<7; ++i){
            final int finalI = i;
            colorBtn[i].setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskItem.setColor(finalI);
                    taskItem.setState(0);
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    public void setTaskItem(TaskItem taskItem) {
        this.taskItem = taskItem;
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