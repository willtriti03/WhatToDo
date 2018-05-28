package com.example.jungjune.whattodo.Utill;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {
    private Date currentTime = Calendar.getInstance().getTime();
    private String date[] ={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};
    private SimpleDateFormat format;
    public Dates(){
       format = new SimpleDateFormat();
    }
    public int getYear(){
        format.applyPattern("YYYY");
        return Integer.parseInt(format.format(currentTime));}
    public int getMounth(){
        format.applyPattern("MM");
        return Integer.parseInt(format.format(currentTime));
    }
    public int getDay(){
        format.applyPattern("dd");
        return Integer.parseInt(format.format(currentTime));
    }
    public int getDate(){
        Log.e("date",currentTime.getDay()+"");
        return currentTime.getDay();
    }
    public int getSec(){return currentTime.getSeconds();}
    public String getDate(int timeflow){
        if (timeflow<0){
            return "None";
        }
        else{
            return date[(getDate()+timeflow)%7];
        }
    }
    public String getDate(int year, int month,int day){
        int days = 0;
        days = (year - 1900) * 365;
        days += (year - 1900) / 4;
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
            if (month < 3)
                days--;
        }

        for (int m = 1; m < month; m++) {
            switch (m) {
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    days += 31;
                    break;
                case 4: case 6: case 9: case 11:
                    days += 30;
                    break;
                case 2:
                    days += 28;
                    break;
            }

        }
        days += day;
        return date[days/7];
    }
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM.dd");
        return format.format(currentTime)+" "+getDate(0);
    }
}
