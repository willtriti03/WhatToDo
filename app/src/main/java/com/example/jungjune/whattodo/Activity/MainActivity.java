package com.example.jungjune.whattodo.Activity;

import android.app.Activity;

import com.example.jungjune.whattodo.Custom.CustomColorBarLinearLayout;
import com.example.jungjune.whattodo.Custom.CustomMenuBarLinearLayout;
import com.example.jungjune.whattodo.Item.TaskItemWithView;
import com.example.jungjune.whattodo.Service.BackgroundService;

import com.example.jungjune.whattodo.Utill.SwipeDetector;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
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

    private int day;
    private int year;
    private int month;
    private int alphaNum = 0;
    private boolean backService = true;
    private boolean isActive = false;

    private ArrayList<TaskItem> data;
    private SaveData saveData;
    private final Dates d = new Dates();

    private Animation animTransRightOut;
    private Animation animTransLeftOut;
    private Animation animTransRightIn;
    private Animation animTransLeftIn;

    private Button dateBtn[] = new Button[4];
    private EditText ed;
    private ListView listView;
    private TextView dateText;
    private ImageView calender;
    private TaskAdapter taskAdapter;
    private LinearLayout calenderBtn;
    private LinearLayout addBtn;
    private SharedPreferences.Editor editor;
    private InputMethodManager imm;
    private FrameLayout conLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        year = 2018;
        month = d.getMounth();
        day = d.getDay();

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        editor = getPreferences(MODE_PRIVATE).edit();


        saveData = new SaveData(getPreferences(MODE_PRIVATE), editor);
        saveData.deleteYesterday(d.getMounth(), d.getDay());
        data = saveData.LoadData();
        taskAdapter = new TaskAdapter(this, R.layout.item_task, data, this);


        BackgroundService.getInstance().setSaveData(saveData);
        if (bindService(new Intent(MainActivity.this, BackgroundService.class), mUploadConnection, BIND_AUTO_CREATE));


        ed = (EditText) findViewById(R.id.mainAddItem);

        dateText = (TextView) findViewById(R.id.mainDateText);

        dateBtn[0] = (Button) findViewById(R.id.mainTodayBtn);
        dateBtn[1] = (Button) findViewById(R.id.mainTomorrowBtn);
        dateBtn[2] = (Button) findViewById(R.id.mainNextWeekBtn);
        dateBtn[3] = (Button) findViewById(R.id.mainNoneBtn);

        addBtn = (LinearLayout) findViewById(R.id.mainAddBTN);
        calender = (ImageView) findViewById(R.id.mainCalender);
        calenderBtn = (LinearLayout) findViewById(R.id.mainCalenderBtn);

        conLayout = (FrameLayout) findViewById(R.id.mainContentPanel);

        listView = (ListView) findViewById(R.id.mainTaskList);
        listView.setAdapter(taskAdapter);

        dateText.setText(d.toString());
        ed.clearFocus();
        imm.hideSoftInputFromWindow(ed.getWindowToken(), 0);

        listView.setOnTouchListener(new SwipeDetector(listView, this) {
            @Override
            public void swipeRight(int position) {
                if (position!=-1) {
                    TaskItemWithView itemWithView = ((TaskItemWithView) listView.getAdapter().getItem(position));
                    View v = itemWithView.getV();
                    CustomColorBarLinearLayout customColorBarLinearLayout = (CustomColorBarLinearLayout) v.findViewById(R.id.itemTaskColorBox);
                    final CustomMenuBarLinearLayout customMenuBarLinearLayout = (CustomMenuBarLinearLayout) v.findViewById(R.id.itemTaskSettingBox);
                    setAni(v.getContext());
                    if (itemWithView.getTaskItem().getState() == 0) {
                        itemWithView.getTaskItem().setState(1);
                        customColorBarLinearLayout.setVisibility(View.VISIBLE);
                        customColorBarLinearLayout.startAnimation(animTransRightOut);
                    } else if (itemWithView.getTaskItem().getState() == 2) {
                        itemWithView.getTaskItem().setState(0);
                        customMenuBarLinearLayout.startAnimation(animTransRightIn);
                        animTransRightIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                customMenuBarLinearLayout.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }
            }

            @Override
            public void swipeLeft(int position) {
                if (position != -1) {
                    TaskItemWithView itemWithView = ((TaskItemWithView) listView.getAdapter().getItem(position));
                    View v = itemWithView.getV();
                    final CustomColorBarLinearLayout customColorBarLinearLayout = (CustomColorBarLinearLayout) v.findViewById(R.id.itemTaskColorBox);
                    CustomMenuBarLinearLayout customMenuBarLinearLayout = (CustomMenuBarLinearLayout) v.findViewById(R.id.itemTaskSettingBox);
                    setAni(v.getContext());
                    if (itemWithView.getTaskItem().getState() == 0) {
                        itemWithView.getTaskItem().setState(2);
                        customMenuBarLinearLayout.setVisibility(View.VISIBLE);
                        customMenuBarLinearLayout.startAnimation(animTransLeftOut);
                    } else if (itemWithView.getTaskItem().getState() == 1) {
                        itemWithView.getTaskItem().setState(0);
                        customColorBarLinearLayout.startAnimation(animTransLeftIn);
                        animTransLeftIn.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                customColorBarLinearLayout.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                }

            }
        });

        calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calender.setBackground(getDrawable(R.drawable.calendar_on));
                for (int j = 0; j < 4; ++j) {
                    dateBtn[j].setTextColor(getColor(R.color.turnOff));
                }
                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(dateSetListener, year, month, day);

                datePickerDialog.setThemeDark(false);

                datePickerDialog.showYearPickerFirst(false);

                datePickerDialog.setAccentColor(getColor(R.color.background));

                datePickerDialog.setTitle("Select Date From DatePickerDialog");

                datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ed.getText().toString().length() != 0) {
                    TaskItem ts = new TaskItem(month, day + alphaNum, year, d.getDate(alphaNum), ed.getText().toString(), 6, false);
                    taskAdapter.additem(ts);
                    ed.setText("");
                    ed.requestFocus();
                    refresh();
                } else
                    Toast.makeText(getApplicationContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        });

        for (int i = 0; i < 4; ++i) {
            final int finalI = i;
            dateBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    calender.setBackground(getDrawable(R.drawable.calendar_off));
                    for (int j = 0; j < 4; ++j) {
                        if (finalI != j)
                            dateBtn[j].setTextColor(getColor(R.color.turnOff));
                        else
                            dateBtn[j].setTextColor(getColor(R.color.turnOn));
                    }
                    switch (finalI) {
                        case 0:
                            alphaNum = 0;
                            break;
                        case 1:
                            alphaNum = 1;
                            break;
                        case 2:
                            alphaNum = 7;
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
            String msg = String.format("%d / %d / %d", year, monthOfYear + 1, dayOfMonth);
            month = monthOfYear + 1;
            day = dayOfMonth;
            alphaNum = 0;
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();

        }

    };

    private boolean isMyServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (BackgroundService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void refresh() {
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
            isActive = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isActive = false;
            Log.e("connected", "failed");
        }

    };

    public boolean getBackService() {
        return backService;
    }

    public void setBackService(boolean backService) {
        this.backService = backService;
    }

    private void setAni(Context context) {
        animTransRightOut = AnimationUtils.loadAnimation(context, R.anim.animation_right_out);
        animTransLeftOut = AnimationUtils.loadAnimation(context, R.anim.animation_left_out);
        animTransRightIn = AnimationUtils.loadAnimation(context, R.anim.animation_right_in);
        animTransLeftIn = AnimationUtils.loadAnimation(context, R.anim.animation_left_in);
    }

}
