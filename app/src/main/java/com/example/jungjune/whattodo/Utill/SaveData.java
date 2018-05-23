package com.example.jungjune.whattodo.Utill;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.jungjune.whattodo.Item.TaskItem;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SaveData {
    public static final String KEY_CONNECTIONS = "ToDo";
    private SharedPreferences.Editor editor;
    private SharedPreferences sh;
    private ArrayList<TaskItem> data;
    private Dates dates;

    public SaveData(SharedPreferences sh, SharedPreferences.Editor editor) {
        dates = new Dates();
        this.editor = editor;
        this.sh = sh;
        data = LoadData();
        if(data.size()==0)
            data.add(0, new TaskItem(0, 0, 0,"", "테스트용 일정입니다", 4, false));
    }
    public void clearData() {
        editor.clear();
        editor.commit();
    }

    public void addItem(int month, int day,int year, String date, String text, int color, boolean fix) {
        data.add(new TaskItem(month, day, year,date, text, color, fix));
    }
    public void addItem(int month, int day,int year, String text, int color, boolean fix) {
        data.add(new TaskItem(month, day,year, dates.getDate(year,month,day),text, color, fix));
    }

    public void additem(TaskItem ts) {
        data.add(ts);
    }

    public void Save() {
        clearData();
        String connectionsJSONString = new Gson().toJson(data);
        editor.putString(KEY_CONNECTIONS, connectionsJSONString);
        editor.commit();

    }

    public ArrayList<TaskItem> LoadData() {
        String connectionsJSONString = sh.getString(KEY_CONNECTIONS, null);
        Type type = new TypeToken<ArrayList<TaskItem>>() {}.getType();
        data = new Gson().fromJson(connectionsJSONString, type);

        return data;
    }

    public void deleteYesterday(int month, int day) {
        for (int i = 0; i < data.size(); ++i) {
            if (data.get(i).getDay() < day && data.get(i).getMonth() <= month && !data.get(i).getFix()) {
                data.remove(i);
            }
        }
    }

    public void fixSorting() {
        for (int i = 0; i < data.size(); ++i) {
            if (data.get(i).getFix()) {
                TaskItem ts = data.get(i);
                data.remove(i);
                data.add(0, ts);
            }
        }
    }

    public void test() {
        Log.e("testMass", "dsad");
    }

    public void setBackService(boolean bool) {
        if (bool)
            editor.putInt("back", 1);
        else
            editor.putInt("back", 0);
        editor.commit();
    }
    public boolean getBackservice(){
        if(sh.getInt("back",1)==1)
            return true;
        else
            return false;
    }
}
