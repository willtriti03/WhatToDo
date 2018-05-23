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
    public static final String KEY_CONNECTIONS = "KEY_CONNECTIONS";
    SharedPreferences.Editor editor;
    SharedPreferences sh;
    ArrayList<TaskItem> data;

    public SaveData(SharedPreferences sh, SharedPreferences.Editor editor ){
        this.editor = editor;
        this.sh = sh;
        data = new ArrayList<TaskItem>();
        data.add(0,new TaskItem(3,4,"sd",4,false));
        Save();
        data.remove(0);
        Save();

    }
    public void clearData(){
        editor.clear();
        editor.commit();
    }

    public void addItem(int month,int day,String text,int color, boolean fix){
        data.add(new TaskItem(month,day,text,color,fix));
    }
    public void additem(TaskItem ts){
        data.add(ts);
    }
    public void Save(){
        clearData();
        String connectionsJSONString = new Gson().toJson(data);
        editor.putString(KEY_CONNECTIONS, connectionsJSONString);
        editor.commit();

    }
    public ArrayList<TaskItem> LoadData(){
        String connectionsJSONString = sh.getString(KEY_CONNECTIONS, null);
        Type type = new TypeToken< ArrayList < TaskItem >>() {}.getType();
        data = new Gson().fromJson(connectionsJSONString, type);

        return data;
    }
    public void deleteYesterday(int month,int day){
        for(int i =0; i<data.size(); ++i){
            if(data.get(i).getDay()<day && data.get(i).getMonth()<=month &&!data.get(i).getFix()){
                data.remove(i);
            }
        }
    }
    public void fixSorting(){
        for(int i =0; i<data.size(); ++i){
            if(data.get(i).getFix()){
                TaskItem ts =data.get(i);
                data.remove(i);
                data.add(0,ts);
            }
        }
    }
    public void test(){
        Log.e("testMass","dsad");
    }
}
