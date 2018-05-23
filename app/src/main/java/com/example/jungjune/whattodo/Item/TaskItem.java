package com.example.jungjune.whattodo.Item;

import android.graphics.drawable.Drawable;

public class TaskItem {
    private  int month;
    private int day;
    private String date;
    private String text;
    private int color;
    private int state=0;
    private boolean repeat;
    private boolean fix;

    public  TaskItem(int month,int day,String text,int color, boolean fix){
        this.month =month;
        this.day = day;
        this.date= month+"."+day;
        this.text = text;
        this.color =color;
        this.fix =fix;
        repeat = false;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getColor() {
        return color;
    }

    public boolean getFix() {
        return fix;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public int getState() {
        return state;
    }
    public  int getRepeat(){
        if (repeat)
        return  1;
    else
        return  0;}
        public  boolean getRepeatB(){
            return repeat; }
    public void setColor(int color) {
        this.color = color;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFix(boolean fix) {
        this.fix = fix;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRepeat(boolean repeat) {
        this.repeat =repeat;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
