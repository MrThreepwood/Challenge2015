package com.myriadmobile.Challenge2015.Easter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by joshuaswoyer on 10/29/15.
 */
public class BallView extends View {

    public float x;
    public float y;
    private int r;
    Handler mHandler = new Handler(); //so redraw occurs in main thread
    Timer mTmr = null;
    public int mScrWidth, mScrHeight;
    float dragCoeff = .001f;
    PointF mBallSpd = new PointF(0,0);
    PointF mBallAcl = new PointF(0,0);
    PointF coin1Pos = new PointF(0,0);
    PointF coin2Pos = new PointF(0,0);
    float coinSize;
    int coinsColected = 0;
    long time;
    boolean coin1 = true;
    boolean coin2 = true;


    private final Paint ballColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint coinColor = new Paint(Paint.ANTI_ALIAS_FLAG);

    //construct new ball object
    public BallView(Context context) {
        super(context);
        //color hex is [transparency][red][green][blue]
        ballColor.setColor(0xFF00FF00);  //not transparent. color is green
        coinColor.setColor(0xffffff00);
        //create timer to move ball to new position
    }
    public void startTimer() {
        mTmr = new Timer();
        mTmr.schedule(new MyTimerTask(), 10, 10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        DisplayMetrics display = getResources().getDisplayMetrics();
        mScrWidth = w;
        mScrHeight = h;
        x = mScrWidth / 2;
        y = mScrHeight / 2;
        int scalar = h*w/200000;
        r =  Math.round(scalar*1.8f);//radius
        coinSize = Math.round(scalar*1.4f);
        dragCoeff = scalar*dragCoeff;
        placeCoin(1);
        placeCoin(2);
        time = System.currentTimeMillis()/1000;
        ((TiltBallActivity)getContext()).scalar = scalar;
        startTimer();
    }

    //called by invalidate()
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xff000000);
        if (r == 0) {
            return;
        }
        if (coin1) {
            double xDiff1 = x - coin1Pos.x;
            double yDiff1 = y - coin1Pos.y;
            if (xDiff1 * xDiff1 + yDiff1 * yDiff1 < coinSize*coinSize + r*r) {
                placeCoin(1);
            }
            if(coin1)
                canvas.drawCircle(coin1Pos.x, coin1Pos.y, coinSize, coinColor);
        }
        if (coin2) {
            double xDiff2 = x-coin2Pos.x;
            double yDiff2 = y-coin2Pos.y;
            if (xDiff2 * xDiff2 + yDiff2 * yDiff2 < coinSize*coinSize + r*r) {
                placeCoin(2);
            }
            if (coin2)
                canvas.drawCircle(coin2Pos.x, coin2Pos.y, coinSize, coinColor);
        }
        canvas.drawCircle(x, y, r, ballColor);
    }
    public void placeCoin (int coin) {
        if (coinsColected >= 10) {
            if (coin == 1)
                coin1 = false;
            if (coin == 2)
                coin2 = false;
        }
        if (coin == 1) {
            coin1Pos.x = (float) Math.random()*mScrWidth;
            coin1Pos.y = (float) Math.random()*mScrHeight;
        }
        if (coin == 2) {
            coin2Pos.x = (float) Math.random()*mScrWidth;
            coin2Pos.y = (float) Math.random()*mScrHeight;
        }
        coinsColected ++;
        if (coinsColected >= 12) {
            long completedIn = System.currentTimeMillis()/1000- time;
            CharSequence text = "You collected 10 coins in " + completedIn + " seconds!";
            Toast congrats = Toast.makeText(getContext(), text, Toast.LENGTH_LONG);
            congrats.show();
        }
    }
    class MyTimerTask extends TimerTask {
        public void run() {
            if ((mBallAcl.x < .02f && mBallAcl.x > 0) || (mBallAcl.x > -.02f && mBallAcl.x < 0))
                mBallAcl.x =0;
            if ((mBallAcl.y < .02f && mBallAcl.y > 0) || (mBallAcl.y > -.02f && mBallAcl.y < 0))
                mBallAcl.y =0;
            mBallAcl.x -= dragCoeff*mBallSpd.x;
            mBallAcl.y -= dragCoeff*mBallSpd.y;
            mBallSpd.x += mBallAcl.x;
            mBallSpd.y += mBallAcl.y;
            x += mBallSpd.x;
            y += mBallSpd.y;
            //if ball goes off screen, reposition to opposite side of screen
            if (x > mScrWidth){
                x = mScrWidth;
                mBallSpd.x = -mBallSpd.x;
            }
            if (y > mScrHeight){
                mBallSpd.y = -mBallSpd.y;
                y = mScrHeight;
            }
            if (x < 0) {
                mBallSpd.x = -mBallSpd.x;
                x = 0;
            }
            if (y < 0){
                y = 0;
                mBallSpd.y = -mBallSpd.y;
            }
            mHandler.post(new Runnable() {
                public void run() {
                    invalidate();
                }
            });
        }
    }
    public void debugWindow (Context context) {

    }
    public void reset() {
        if (coinsColected >= 12) {
            time = System.currentTimeMillis()/1000;
            coinsColected = 0;
            placeCoin(1);
            placeCoin(2);
            coin1 = true;
            coin2 = true;
        }
    }
}
