package com.example.jungjune.whattodo.Item;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.jungjune.whattodo.Custom.CustomColorBarLinearLayout;
import com.example.jungjune.whattodo.Custom.CustomMenuBarLinearLayout;

public class TaskItem {
    private int month;
    private int day;
    private int year;
    private int color;
    private int state = 0;
    private String date;
    private String text;
    private boolean repeat;
    private boolean fix;
    private boolean deleted;

    public TaskItem(int month, int day, int year, String date, String text, int color, boolean fix) {
        this.month = month;
        this.day = day;
        this.year = year;
        this.date = year + "." + month + "." + day + " " + date;
        this.text = text;
        this.color = color;
        this.fix = fix;
        this.deleted = false;
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

    public boolean getRepeat() {
        return repeat;
    }

    public boolean getRepeatB() {
        return repeat;
    }

    public boolean getDeleted() {
        return deleted;
    }

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
        this.repeat = repeat;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
