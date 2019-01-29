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
import com.example.jungjune.whattodo.Custom.CustomColorBarLinearLayout;
import com.example.jungjune.whattodo.Custom.CustomMenuBarLinearLayout;
import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.Item.TaskItemWithView;
import com.example.jungjune.whattodo.R;
import com.example.jungjune.whattodo.Utill.SwipeDetector;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class TaskAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<TaskItem> data;
    private ArrayList<TaskItemWithView> bigData;
    private Context context;
    private int layout;

    private Animation animTransRightOut;
    private Animation animTransLeftOut;
    private Animation animTransRightIn;
    private Animation animTransLeftIn;

    private ImageView repeat;
    private MainActivity mainActivity;
    private CustomMenuBarLinearLayout customMenuBarLinearLayout;
    private CustomColorBarLinearLayout customColorBarLinearLayout;

    public TaskAdapter(Context context, int layout, ArrayList<TaskItem> data, MainActivity mainActivity) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
        this.context = context;
        this.mainActivity = mainActivity;

        this.bigData = new ArrayList<TaskItemWithView>();
        for (int i = 0; i < data.size(); ++i) {
//            if (!data.get(i).getDeleted())
            this.bigData.add(new TaskItemWithView(data.get(i)));
        }

        animTransRightOut = AnimationUtils.loadAnimation(context, R.anim.animation_right_out);
        animTransLeftOut = AnimationUtils.loadAnimation(context, R.anim.animation_left_out);
        animTransRightIn = AnimationUtils.loadAnimation(context, R.anim.animation_right_in);
        animTransLeftIn = AnimationUtils.loadAnimation(context, R.anim.animation_left_in);


    }

    public void additem(TaskItem ts) {
        data.add(ts);
        bigData.add(new TaskItemWithView(ts));
        mainActivity.refresh();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public TaskItemWithView getItem(int position) {
        return bigData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }
        final TaskItem taskItem = data.get(position);
        bigData.get(position).setV(convertView);
        TextView date = (TextView) convertView.findViewById(R.id.itemTaskDate);
        final TextView text = (TextView) convertView.findViewById(R.id.itemTaskText);
        final LinearLayout tag = (LinearLayout) convertView.findViewById(R.id.itemTaskTag);
        final LinearLayout textBox = (LinearLayout) convertView.findViewById(R.id.itemTaskTextBox);
        repeat = (ImageView) convertView.findViewById(R.id.itemTaskRepeat);

        customMenuBarLinearLayout = (CustomMenuBarLinearLayout) convertView.findViewById(R.id.itemTaskSettingBox);
        customColorBarLinearLayout = (CustomColorBarLinearLayout) convertView.findViewById(R.id.itemTaskColorBox);

        customMenuBarLinearLayout.setContext(context);
        customMenuBarLinearLayout.setTaskItem(taskItem);
        customMenuBarLinearLayout.setAdapter(this);

        customColorBarLinearLayout.setContext(context);
        customColorBarLinearLayout.setTaskItem(taskItem);
        customColorBarLinearLayout.setAdapter(this);

        setState(taskItem);

        tag.setBackgroundColor(getTagColor(taskItem.getColor()));

        setRepeat(taskItem);
        date.setText(taskItem.getDate());
        text.setText(taskItem.getText());

        return convertView;
    }

    private int getTagColor(int color) {
        int colorCode = Color.parseColor("#00000000");
        switch (color) {
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
        return colorCode;
    }

    public void setRepeat(TaskItem taskItem) {
        if (taskItem.getRepeat())
            repeat.setBackground(context.getDrawable(R.drawable.repeat));
        else
            repeat.setBackground(context.getDrawable(R.drawable.trans));

    }

    public ArrayList<TaskItem> getData() {
        return data;
    }

    public void deleteItem(TaskItem item, TaskItemWithView bigItem) {
        bigData.remove(bigItem);
        data.remove(item);
        notifyDataSetChanged();
    }

    public void setState(TaskItem taskItem) {
        if (taskItem.getState() == 0) {
            customColorBarLinearLayout.setVisibility(View.INVISIBLE);
            customMenuBarLinearLayout.setVisibility(View.INVISIBLE);
        } else if (taskItem.getState() == 1) {
            customColorBarLinearLayout.setVisibility(View.VISIBLE);
            customMenuBarLinearLayout.setVisibility(View.INVISIBLE);
        } else if (taskItem.getState() == 2) {
            customColorBarLinearLayout.setVisibility(View.INVISIBLE);
            customMenuBarLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    public void fixSorting(TaskItem taskItem, TaskItemWithView taskItemWithView) {
        data.remove(taskItem);
      //  bigData.remove(taskItemWithView);

        data.add(0,taskItem);
     //   bigData.add(0,taskItemWithView);

        notifyDataSetChanged();
    }
}
