package com.example.jungjune.whattodo.Custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.jungjune.whattodo.R;

public class CustomColorBarLinearLayout extends LinearLayout {
    LinearLayout[] colorBtn = new LinearLayout[7];


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
    }
}