package com.example.jungjune.whattodo.Utill;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class SwipeDetector extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {
    Context context;
    GestureDetector gDetector;
    ListView v;
    static final int SWIPE_MIN_DISTANCE = 50;
    static final int SWIPE_MAX_OFF_PATH = 250;
    static final int SWIPE_THRESHOLD_VELOCITY = 200;

    public SwipeDetector() {
        super();
    }

    public SwipeDetector(ListView v, Context context) {
        this(v, context, null);
    }

    public SwipeDetector(ListView v, Context context, GestureDetector gDetector) {

        if (gDetector == null)
            gDetector = new GestureDetector(context, this);
        this.v = v;
        this.context = context;
        this.gDetector = gDetector;
    }

    public void swipeUp(int positon) {
    }

    public void swipeDown(int positon) {
    }

    public void swipeRight(int positon) {
    }

    public void swipeLeft(int positon) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int position = v.pointToPosition(
                Math.round(e1.getX()), Math.round(e1.getY()));

        if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE) {
            swipeUp(position);
            return true;
        } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
            swipeDown(position);
            return true;
        }
        if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE) {
            swipeLeft(position);
            return true;
        } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE) {
            swipeRight(position);
            return true;
        }


        return super.onFling(e1, e2, velocityX, velocityY);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return gDetector.onTouchEvent(event);
    }

    public GestureDetector getDetector() {
        return gDetector;
    }

}
