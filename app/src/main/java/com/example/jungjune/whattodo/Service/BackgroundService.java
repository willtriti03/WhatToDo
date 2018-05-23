package com.example.jungjune.whattodo.Service;

import android.app.Service;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.jungjune.whattodo.Activity.MainActivity;
import com.example.jungjune.whattodo.Utill.Dates;
import com.example.jungjune.whattodo.Utill.SaveData;

public class BackgroundService extends Service implements SensorListener{
    private SensorManager sensorMgr;
    private static  BackgroundService instance;
    final private int SHAKE_THRESHOLD=1600;
    private float x,y,z,last_x,last_y,last_z;
    private long lastUpdate=0;
    private int shakeTime=0;
    private boolean canSave=false;
    private SaveData saveData;
    private  int month,day;
    private  String texts;
    private Dates dates;
    IBinder mBinder = new BackgroundService.LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return mBinder;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        dates = new Dates();
        sensorMgr= (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorMgr.registerListener(this,
                SensorManager.SENSOR_ACCELEROMETER,
                SensorManager.SENSOR_DELAY_GAME);
        final android.content.ClipboardManager clipboard = (android.content.ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if(clipboard.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)==true){
                    ClipData clip = clipboard.getPrimaryClip();
                    ClipData.Item item = clip.getItemAt(0);
                    String input = item.getText().toString();
                    String[] array = input.replaceAll(" ","/").replace("월","/").replace("일","/").split("/");
                    try {
                        month = Integer.parseInt(array[0]);
                        day = Integer.parseInt(array[1]);
                        String text = "";
                        for (int i = 2; i < array.length; ++i) {
                            text += array[i] + " ";
                        }
                        texts =text;
                        canSave = true;
                        Toast.makeText(getApplicationContext(), month + "월" + day + "일" + text+"\n일정에 추가하려면 흔들어주세요", Toast.LENGTH_LONG).show();
                    }catch (NumberFormatException e){

                    }
                }
            }
        });

    }

    @Override
    public void onSensorChanged(int sensor, float[] values) {
        if (sensor == SensorManager.SENSOR_ACCELEROMETER) {
            long curTime = System.currentTimeMillis();
            // only allow one update every 100ms.
            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                x = values[SensorManager.DATA_X];
                y = values[SensorManager.DATA_Y];
                z = values[SensorManager.DATA_Z];

                float speed = Math.abs(x+y+z - last_x - last_y - last_z) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {
                    shakeTime++;
                    if(shakeTime>4){
                        if(canSave){
                            Log.e("canSave",canSave+"");
                            saveData.LoadData();
                            saveData.addItem(month,day,dates.getYear(),texts,6,false);
                            saveData.Save();
                            Toast.makeText(getApplicationContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
                            shakeTime=0;
                            canSave = false;
                        }
                        else {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse("whattodo://whattodo"));
                            startActivity(intent);
                            shakeTime = 0;
                        }
                    }
                }
                last_x = x;
                last_y = y;
                last_z = z;
            }
        }
    }


    @Override
    public void onAccuracyChanged(int i, int i1) {

    }

    public void setSaveData(SaveData saveData) {
        this.saveData = saveData;
    }
    public static BackgroundService getInstance() {
        if (instance == null)
            return instance = new BackgroundService();
        else
            return instance;
    }
    public class LocalBinder extends Binder {
        public BackgroundService getServerInstance() {
            return BackgroundService.this;

        }
    }
}
