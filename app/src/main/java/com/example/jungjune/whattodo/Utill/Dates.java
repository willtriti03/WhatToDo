package com.example.jungjune.whattodo.Utill;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {
    Date currentTime = Calendar.getInstance().getTime();
    public int getMounth(){
        return  currentTime.getMonth();
    }
    public int getDay(){
        return currentTime.getDay();
    }

    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM.dd EEE");
        return format.format(currentTime);
    }
}
