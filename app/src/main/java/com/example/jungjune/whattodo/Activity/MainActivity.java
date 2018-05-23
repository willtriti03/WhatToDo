package com.example.jungjune.whattodo.Activity;

import android.app.Activity;

import com.example.jungjune.whattodo.Service.BackgroundService;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jungjune.whattodo.Adapter.TaskAdapter;
import com.example.jungjune.whattodo.Item.TaskItem;
import com.example.jungjune.whattodo.R;
import com.example.jungjune.whattodo.Utill.Dates;
import com.example.jungjune.whattodo.Utill.SaveData;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity {
    private Button dateBtn[] = new Button[4];
    private LinearLayout calenderBtn;
    private ImageView calender;
    private int alphaNum=0;
    private static MainActivity instance;
    TaskAdapter taskAdapter;
    ListView listView;
    SharedPreferences.Editor editor;
    SaveData saveData;
    LinearLayout addBtn;
    int month;
    int day;
    int year;
    public static MainActivity getInstance() {
        if (instance == null)
            return instance = new MainActivity();
        else
            return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Dates d = new Dates();
        final InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        editor= getPreferences(MODE_PRIVATE).edit();

        saveData = new SaveData(getPreferences(MODE_PRIVATE),editor);
        BackgroundService.getInstance().setSaveData(saveData);
        saveData.deleteYesterday(d.getMounth(),d.getDay());
        bindService(new Intent(MainActivity.this,BackgroundService.class),mUploadConnection,BIND_AUTO_CREATE);

        dateBtn[0] = (Button)findViewById(R.id.mainTodayBtn);
        dateBtn[1] = (Button)findViewById(R.id.mainTomorrowBtn);
        dateBtn[2] = (Button)findViewById(R.id.mainNextWeekBtn);
        dateBtn[3] = (Button)findViewById(R.id.mainNoneBtn);

        calenderBtn = (LinearLayout) findViewById(R.id.mainCalenderBtn);
        calender = (ImageView)findViewById(R.id.mainCalender);
        addBtn = (LinearLayout)findViewById(R.id.mainAddBTN);
       // startActivity(new Intent(MainActivity.this,SplashActivity.class));

        ArrayList<TaskItem> data =  saveData.LoadData();
        listView = (ListView) findViewById(R.id.mainTaskList);
        taskAdapter = new TaskAdapter(this,R.layout.item_task,data,this);
        listView.setAdapter(taskAdapter);

        year =2018;
        month = d.getMounth();
        day=d.getDay();

        TextView dateText = (TextView)findViewById(R.id.mainDateText);

        dateText.setText(d.toString());

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender.setBackground(getDrawable(R.drawable.calendar_on));
                for(int j =0; j<4; ++j){
                        dateBtn[j].setTextColor(getColor(R.color.turnOff));
                }
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener,year,month,day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(Color.parseColor("#FCF1CA"));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        final EditText ed = (EditText)findViewById(R.id.mainAddItem);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed.getText().toString().length()!=0) {
                    imm.hideSoftInputFromWindow(ed.getWindowToken(),0);
                    TaskItem ts = new TaskItem(month, day + alphaNum, ed.getText().toString(), 6, false);
                    taskAdapter.additem(ts);
                    ed.setText("");
                    ed.clearFocus();
                    refresh();
                }else
                    Toast.makeText(getApplicationContext(),"내용을 입력해주세요",Toast.LENGTH_SHORT).show();
            }
        });

        for(int i =0; i< 4; ++i){
            final int finalI = i;
            dateBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    month = d.getMounth();
                    day=d.getDay();
                    calender.setBackground(getDrawable(R.drawable.calendar_off));
                    for(int j =0; j<4; ++j){
                        if(finalI !=j)
                            dateBtn[j].setTextColor(getColor(R.color.turnOff));
                        else
                            dateBtn[j].setTextColor(getColor(R.color.turnOn));
                    }
                    switch (finalI){
                        case 0:
                            alphaNum=0;
                            break;
                        case 1:
                            alphaNum=1;
                            break;
                        case 2:
                            alphaNum=7;
                            break;
                        case 3:
                            alphaNum = -1;
                            break;
                    }
                }
            });
        }

    }
        private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub

                String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
                month = monthOfYear+1;
                day =dayOfMonth;
                alphaNum =0;
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

            }

        };

    public void refresh(){
        saveData.fixSorting();
        taskAdapter.notifyDataSetChanged();
        saveData.Save();
    }
    ServiceConnection mUploadConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("connected", "upload success");
            BackgroundService.LocalBinder mLocalBinder = (BackgroundService.LocalBinder) service;
            mLocalBinder.getServerInstance().setSaveData(saveData);

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("connected", "failed");
        }

    };
}
