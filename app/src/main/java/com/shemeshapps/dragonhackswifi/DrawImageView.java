package com.shemeshapps.dragonhackswifi;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.test.suitebuilder.annotation.Suppress;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by tomer on 4/1/17.
 */

public class DrawImageView extends ImageView {
    public DrawImageView(Context context) {
        super(context);
        init();
    }
    Paint mTextPaint;
    public Point drawPoint;

    public DrawImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }


    private void init()
    {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(android.R.color.black));
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(drawPoint != null)
        {
            canvas.drawCircle(drawPoint.x, drawPoint.y, 20, mTextPaint);
        }

    }
}
