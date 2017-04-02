package com.shemeshapps.dragonhackswifi;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tomer on 4/1/17.
 */

public class DrawImageView extends ImageView {
    public DrawImageView(Context context) {
        super(context);
        init();
    }
    Paint mTextPaint;
    Paint mTextPaintGreen;
    Paint mTextPaintRouter;

    public dPoint routerLoc;
    public dPoint drawPoint;

    public List<dPoint> pastPoints = new ArrayList<>();

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

    public void setcolor(int color)
    {
        mTextPaintGreen.setColor(color);
    }

    private void init()
    {
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(getResources().getColor(android.R.color.black));

        mTextPaintGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintGreen.setColor(getResources().getColor(android.R.color.holo_green_light));

        mTextPaintRouter = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintRouter.setColor(getResources().getColor(android.R.color.holo_purple));
    }
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(dPoint p:pastPoints)
        {
            canvas.drawCircle(p.x, p.y, 20, mTextPaintGreen);

        }
        if(drawPoint != null)
        {
            canvas.drawCircle(drawPoint.x, drawPoint.y, 20, mTextPaint);
        }

        if(routerLoc != null)
        {
            canvas.drawCircle(routerLoc.x, routerLoc.y, 20, mTextPaintRouter);
        }

    }
}
