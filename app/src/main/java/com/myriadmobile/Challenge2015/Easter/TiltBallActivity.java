package com.myriadmobile.Challenge2015.Easter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.myriadmobile.Challenge2015.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TiltBallActivity extends AppCompatActivity implements SensorEventListener, View.OnTouchListener {

    BallView mBallView = null;
    float accelCoeff = .004f;
    public int scalar = 0;
    @Bind(R.id.main_view) FrameLayout mainView;
    //Nav Drawer stuff
    String[] strings = {"Option 1", "Option 2", "Option 3", "Option 4"};
    @Bind(R.id.drawer_layout) DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer) RecyclerView mRecyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tilt_ball);
        //Nav Drawer Stuff
        ButterKnife.bind(this);
        mRecyclerView.setAdapter(new NavDrawerAdapter(strings));
        LinearLayoutManager lmm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(lmm);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //Super duper old tiltball stuff
        //set app to full screen and keep screen on

        //create initial ball
        mBallView = new BallView(this);
        mainView.addView(mBallView); //add ball to main screen
        mBallView.invalidate(); //call onDraw in BallView
        //listener for accelerometer, use anonymous class for simplicity
        ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).registerListener(this,
                ((SensorManager) getSystemService(Context.SENSOR_SERVICE)).getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_GAME);
        //listener for touch event
        mainView.setOnTouchListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mBallView.mTmr == null) {
            return;
        }
        mBallView.mTmr.cancel(); //kill\release timer (our only background thread)
        mBallView.mTmr = null;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        //Only start the timer if one doesn't exist.
        if (mBallView.mTmr == null) {
            mBallView.startTimer();
        }
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        //set ball speed based on phone tilt (ignore Z axis)
        if (scalar != 0) {
            mBallView.mBallAcl.x = -event.values[0] * accelCoeff * scalar;
            mBallView.mBallAcl.y = event.values[1] * accelCoeff * scalar;
        }
        //timer event will redraw ball
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //set ball position based on screen touch
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (x > 10 && x < mBallView.mScrWidth - 10 && y > 10 && y < mBallView.mScrHeight - 10)
            mBallView.reset();
        //timer event will redraw ball
        return true;
    }
}

