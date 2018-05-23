package com.example.jungjune.whattodo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.jungjune.whattodo.Activity.MainActivity;
import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.R;
import com.example.jungjune.whattodo.Utill.SwipeDetector;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class TaskAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<TaskItem> data;
    private Context context;
    private int layout;

    final Animation animTransRightOut;
    final Animation animTransLeftOut;
    final Animation animTransRightIn;
    final Animation animTransLeftIn;

    private ImageView pin;
    private ImageView repeat;
    private MainActivity mainActivity;

    public TaskAdapter(Context context, int layout, ArrayList<TaskItem> data,MainActivity mainActivity){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
        this.context = context;
        this.mainActivity =mainActivity;

        animTransRightOut = AnimationUtils.loadAnimation(context,R.anim.animation_right_out);
        animTransLeftOut = AnimationUtils.loadAnimation(context,R.anim.animation_left_out);
        animTransRightIn = AnimationUtils.loadAnimation(context,R.anim.animation_right_in);
        animTransLeftIn = AnimationUtils.loadAnimation(context,R.anim.animation_left_in);
    }
    public void addItem(int month,int day,int year,String date,String text,int color, boolean fix){
        data.add(new TaskItem(month,day,year,date,text,color,fix));
        mainActivity.refresh();
    }
    public void additem(TaskItem ts){
        data.add(ts);
        mainActivity.refresh();
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).getText();}

    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        final TaskItem taskItem=data.get(position);
        TextView date=(TextView) convertView.findViewById(R.id.itemTaskDate);
        final TextView text= (TextView)convertView.findViewById(R.id.itemTaskText);
        final LinearLayout tag = (LinearLayout)convertView.findViewById(R.id.itemTaskTag);
        final LinearLayout textBox = (LinearLayout)convertView.findViewById(R.id.itemTaskTextBox);
        final LinearLayout settingBox= (LinearLayout)convertView.findViewById(R.id.itemTaskSettingBox);
        LinearLayout itemBox = (LinearLayout)convertView.findViewById(R.id.itemTaskItemBox);
        final LinearLayout colorBox= (LinearLayout)convertView.findViewById(R.id.itemTaskColorBox);

        LinearLayout[] colorBtn = new LinearLayout[7];
        colorBtn[0] = (LinearLayout)convertView.findViewById(R.id.itemTaskRedBtn);
        colorBtn[1] = (LinearLayout)convertView.findViewById(R.id.itemTaskOrangeBtn);
        colorBtn[2] = (LinearLayout)convertView.findViewById(R.id.itemTaskGreenBtn);
        colorBtn[3] = (LinearLayout)convertView.findViewById(R.id.itemTaskMintBtn);
        colorBtn[4] = (LinearLayout)convertView.findViewById(R.id.itemTaskBlueBtn);
        colorBtn[5] = (LinearLayout)convertView.findViewById(R.id.itemTaskNavyBtn);
        colorBtn[6] = (LinearLayout)convertView.findViewById(R.id.itemTaskTransBtn);

        LinearLayout fixBtn = (LinearLayout)convertView.findViewById(R.id.itemTaskFixBtn);
        LinearLayout repeatBtn =(LinearLayout)convertView.findViewById(R.id.itemTaskRepeatBtn);
        LinearLayout deleteBtn = (LinearLayout)convertView.findViewById(R.id.itemTaskDeleteBtn);
        LinearLayout closeBtn = (LinearLayout)convertView.findViewById(R.id.itemTaskCloseBtn);

        pin = (ImageView)convertView.findViewById(R.id.itemTaskPin);
        repeat = (ImageView)convertView.findViewById(R.id.itemTaskRepeat);

        setPin(convertView,taskItem);
        setRepeat(convertView,taskItem);

        tag.setBackgroundColor(getTagColor(taskItem.getColor()));
        date.setText(taskItem.getDate());
        text.setText(taskItem.getText());

        for(int i =0; i< 7; ++i){
            final int finalI = i;
            colorBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    taskItem.setColor(finalI);
                    tag.setBackgroundColor(getTagColor(taskItem.getColor()));
                    colorBox.startAnimation(animTransLeftIn);
                    tag.startAnimation(animTransLeftIn);
                    text.startAnimation(animTransLeftIn);
                    animTransLeftIn.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            LinearLayout.LayoutParams colorRate2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f);
                            colorBox.setLayoutParams(colorRate2);
                            taskItem.setState(0);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });


                }
            });
        }
        final View finalConvertView = convertView;
        repeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskItem.setRepeat(!taskItem.getRepeatB());
                switch (taskItem.getState()){
                    case 2:
                        settingBox.startAnimation(animTransRightIn);
                        textBox.startAnimation(animTransRightIn);
                        animTransRightIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams textRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 9.5f);
                                LinearLayout.LayoutParams settingRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0);
                                settingBox.setLayoutParams(settingRate2);
                                textBox.setLayoutParams(textRate2);
                                taskItem.setState(0);
                                setRepeat(finalConvertView,taskItem);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        taskItem.setState(0);
                        break;
                }
            }
        });

        fixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskItem.setFix(!taskItem.getFix());
                Log.e("fix",taskItem.getFix()+"");
                switch (taskItem.getState()){
                    case 2:
                        settingBox.startAnimation(animTransRightIn);
                        textBox.startAnimation(animTransRightIn);
                        animTransRightIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams textRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 9.5f);
                                LinearLayout.LayoutParams settingRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0);
                                settingBox.setLayoutParams(settingRate2);
                                textBox.setLayoutParams(textRate2);
                                taskItem.setState(0);
                                setPin(finalConvertView,taskItem);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        taskItem.setState(0);
                        mainActivity.refresh();
                        break;
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (taskItem.getState()){
                    case 2:
                        settingBox.startAnimation(animTransRightIn);
                        textBox.startAnimation(animTransRightIn);
                        animTransRightIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams textRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 9.5f);
                                LinearLayout.LayoutParams settingRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0);
                                settingBox.setLayoutParams(settingRate2);
                                textBox.setLayoutParams(textRate2);
                                taskItem.setState(0);
                                data.remove(position);
                                mainActivity.refresh();
                                Log.e("delete",position+"");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        taskItem.setState(0);

                        break;
                }
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (taskItem.getState()){
                    case 2:
                        settingBox.startAnimation(animTransRightIn);
                        textBox.startAnimation(animTransRightIn);
                        animTransRightIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams textRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 9.5f);
                                LinearLayout.LayoutParams settingRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0);
                                settingBox.setLayoutParams(settingRate2);
                                textBox.setLayoutParams(textRate2);
                                taskItem.setState(0);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        taskItem.setState(0);

                        break;
                }
            }
        });


        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                LinearLayout.LayoutParams textRate;
                LinearLayout.LayoutParams settingRate;
                switch (taskItem.getState()){
                    case 0:
                        textRate = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 5.5f);
                        settingRate = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,4);
                        settingBox.setLayoutParams(settingRate);
                        textBox.setLayoutParams(textRate);
                        settingBox.startAnimation(animTransLeftOut);
                        textBox.startAnimation(animTransLeftOut);
                        taskItem.setState(2);
                        break;
                    case 2:
                        settingBox.startAnimation(animTransRightOut);
                        textBox.startAnimation(animTransRightOut);
                        animTransRightOut.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams textRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 9.5f);
                                LinearLayout.LayoutParams settingRate2 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT,0);
                                settingBox.setLayoutParams(settingRate2);
                                textBox.setLayoutParams(textRate2);
                                taskItem.setState(0);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        taskItem.setState(0);

                        break;
                }
                return false;
            }
        });
        tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearLayout.LayoutParams colorRate;

                switch (taskItem.getState()){
                    case 0:
                        colorRate = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 9.5f);
                        colorBox.setLayoutParams(colorRate);
                        colorBox.startAnimation(animTransRightOut);
                        tag.startAnimation(animTransRightOut);
                        text.startAnimation(animTransRightOut);
                        taskItem.setState(1);
                        break;
                    case 1:
                        colorBox.startAnimation(animTransLeftIn);
                        tag.startAnimation(animTransLeftIn);
                        text.startAnimation(animTransLeftIn);
                        animTransLeftIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                LinearLayout.LayoutParams colorRate2 = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 0f);
                                colorBox.setLayoutParams(colorRate2);
                                taskItem.setState(0);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                        break;
                }
            }
        });



        return convertView;
    }

    private int getTagColor(int color){
        int colorCode = Color.parseColor("#00000000");
        switch (color){
            case 0:
               colorCode = Color.parseColor("#FFA9B0");
                break;
            case 1:
                colorCode = Color.parseColor("#FFDDA6");
                break;
            case 2:
                colorCode = Color.parseColor("#B8F3B8");
                break;
            case 3:
                colorCode = Color.parseColor("#2ED7D2");
                break;
            case 4:
                colorCode = Color.parseColor("#CCD1FF");
                break;
            case 5:
                colorCode = Color.parseColor("#9197B5");
                break;
        }
        mainActivity.refresh();
        return  colorCode;
    }
    private void setPin(View v, TaskItem taskItem){
        pin = v.findViewById(R.id.itemTaskPin);
        if(taskItem.getFix())
            pin.setBackground(context.getDrawable(R.drawable.push_pin_on));
        else
            pin.setBackground(context.getDrawable(R.drawable.push_pin));

    }
    private void setRepeat(View v, TaskItem taskItem){
        repeat = v.findViewById(R.id.itemTaskRepeat);
        switch (taskItem.getRepeat()){
            case 0:
                repeat.setBackground(context.getDrawable(R.drawable.trans));
                break;
            case 1:
                repeat.setBackground(context.getDrawable(R.drawable.repeat));
        }

    }


}
