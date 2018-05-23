package com.example.jungjune.whattodo.Utill;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Dates {
    private Date currentTime = Calendar.getInstance().getTime();
    private String date[] ={"Sun","Mon","Tue","Wed","Thu","Fri","Sat"};

    public int getYear(){return currentTime.getYear();}
    public int getMounth(){
        return  currentTime.getMonth();
    }
    public int getDay(){
        return currentTime.getDay();
    }
    public int getSec(){return currentTime.getSeconds();}
    public String getDate(int timeflow){
        if (timeflow<0){
            return "None";
        }
        else{
            return date[(currentTime.getDate()+timeflow)/7];
        }
    }
    public String getDate(int year, int month,int day){
      return"";
    }
    @Override
    public String toString() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("MM.dd");
        return format.format(currentTime)+getDate(0);
    }
}
