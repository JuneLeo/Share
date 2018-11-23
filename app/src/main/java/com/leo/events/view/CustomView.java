package com.leo.events.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by spf on 2018/11/8.
 */
public class CustomView extends View {
    private Paint mPaint;
    private Paint mPaintBg;
    private int mWidth;
    private int mPadding = 80;

    private int mYellowPointHeight = 70;
    private int mYellowPointWidth = 20;

    private int mRedBoldPointHeight = 60;
    private int mRedBoldPointwidth = 10;
    private int mRedPointHeight = 20;
    private int mRedPointWidth = 4;

    private int hoursPointHeight = 260 - mPadding;
    private int minPintHeight = 330 - mPadding;
    private int secondPintHeight = 420 - mPadding;

    private Canvas mBufferCanvas;
    private Bitmap mBufferBitmap;


    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBg.setStyle(Paint.Style.STROKE);
        mPaintBg.setStrokeWidth(mPadding);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();

        setMeasuredDimension(MeasureSpec.getSize(mWidth), MeasureSpec.getSize(mWidth));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int middleX = mWidth / 2;
        if (mBufferBitmap == null) {
            mBufferBitmap = Bitmap.createBitmap(mWidth, mWidth, Bitmap.Config.ARGB_8888);
            mBufferCanvas = new Canvas(mBufferBitmap);
//            Canvas mBufferCanvas = canvas;
            setLayerType(LAYER_TYPE_SOFTWARE, null);

            // 背景
            Rect rect = new Rect(0, 0, mWidth, mWidth);
            mPaint.setColor(Color.parseColor("#333233"));
//        mPaint.setColor(Color.WHITE);
            mBufferCanvas.drawRect(rect, mPaint);
            // 边
            LinearGradient linearGradient = new LinearGradient(0, 0, mWidth, mWidth,
                    new int[]{0xff000000, 0xffffffff, 0xff000000}, new float[]{0, 0.5f, 1}, Shader.TileMode.CLAMP);
            mPaintBg.setShader(linearGradient);
            mPaintBg.setShadowLayer(20, 0, 0, 0x88ffffff);
            RectF rectBg = new RectF((mPadding / 2), (mPadding / 2), (mWidth - mPadding / 2), (mWidth - mPadding / 2));
//        canvas.drawArc(rectBg, -135, 360, false, mPaintBg);
            mBufferCanvas.drawCircle(middleX, middleX, middleX - mPadding / 2, mPaintBg);

            //内圈
            mPaint.setColor(Color.parseColor("#333233"));
//        mPaint.setColor(Color.WHITE);
            mBufferCanvas.drawCircle(middleX, middleX, middleX - mPadding, mPaint);
            //鹰派
            mPaint.setColor(Color.parseColor("#DEAD5A"));
            mPaint.setTextSize(30);
            float textWidth = getFontWidth(mPaint, "鹰牌");
            mBufferCanvas.drawText("鹰牌", middleX - (textWidth / 2), middleX - 250, mPaint);

            mBufferCanvas.save();
            for (int i = 0; i < 60; i++) {
                Rect rect1;
                if (i % 15 == 0) {
                    mPaint.setColor(Color.parseColor("#DEAD5A"));
                    rect1 = new Rect(middleX - (mYellowPointWidth / 2), mPadding, middleX + (mYellowPointWidth / 2), mPadding + mYellowPointHeight);
                    mBufferCanvas.drawRect(rect1, mPaint);

                    int mNumHeight = 160 + mPadding;

                    if (i == 0) {

                        mBufferCanvas.save();
                        mPaint.setColor(Color.parseColor("#DEAD5A"));
                        mPaint.setTextSize(90);
                        float widthText = getFontWidth(mPaint, "12");
                        mBufferCanvas.drawText("12", (float) middleX - (widthText / 2), mNumHeight, mPaint);
                        mBufferCanvas.restore();
                    } else if (i == 15) {
                        mBufferCanvas.save();

                        mPaint.setTextSize(90);
                        mPaint.getTextBounds("3", 0, 1, rect1);
                        mPaint.setColor(Color.BLACK);
                        float centerY = mNumHeight - ((rect1.bottom - rect1.top) / 2);
//                    canvas.drawRect(new RectF(middleX - 30, 0, middleX + 30, centerY), mPaint);
                        mBufferCanvas.rotate(-90, middleX, centerY);
                        float widthText = getFontWidth(mPaint, "3");
                        mPaint.setColor(Color.parseColor("#DEAD5A"));
                        mBufferCanvas.drawText("3", (float) middleX - (widthText / 2), mNumHeight, mPaint);
                        mBufferCanvas.restore();
                    } else if (i == 30) {
                        mBufferCanvas.save();

                        mPaint.setTextSize(90);
                        mPaint.getTextBounds("6", 0, 1, rect1);
                        mPaint.setColor(Color.BLACK);
                        float centerY = mNumHeight - ((rect1.bottom - rect1.top) / 2);
//                    canvas.drawRect(new RectF(middleX - 30, 0, middleX + 30, centerY), mPaint);
                        mBufferCanvas.rotate(-180, middleX, centerY);
                        float widthText = getFontWidth(mPaint, "6");
                        mPaint.setColor(Color.parseColor("#DEAD5A"));
                        mBufferCanvas.drawText("6", (float) middleX - (widthText / 2), mNumHeight, mPaint);
                        mBufferCanvas.restore();
                    } else if (i == 45) {
                        mBufferCanvas.save();

                        mPaint.setTextSize(90);
                        mPaint.getTextBounds("6", 0, 1, rect1);
                        mPaint.setColor(Color.BLACK);
                        float centerY = mNumHeight - ((rect1.bottom - rect1.top) / 2);
//                    canvas.drawRect(new RectF(middleX - 30, 0, middleX + 30, centerY), mPaint);
                        mBufferCanvas.rotate(90, middleX, centerY);
                        float widthText = getFontWidth(mPaint, "9");
                        mPaint.setColor(Color.parseColor("#DEAD5A"));
                        mBufferCanvas.drawText("9", (float) middleX - (widthText / 2), mNumHeight, mPaint);
                        mBufferCanvas.restore();
                    }
                }
                if (i % 5 == 0) {
                    mPaint.setColor(Color.RED);
                    rect1 = new Rect(middleX - (mRedBoldPointwidth / 2), mPadding, middleX + (mRedBoldPointwidth / 2), mPadding + mRedBoldPointHeight);
                } else {
                    mPaint.setColor(Color.RED);
                    rect1 = new Rect(middleX - (mRedPointWidth / 2), mPadding, middleX + (mRedPointWidth / 2), mPadding + mRedPointHeight);
                }


                mBufferCanvas.drawRect(rect1, mPaint);
                mBufferCanvas.rotate(6, middleX, middleX);
            }

            mBufferCanvas.restore();
        }

        canvas.drawBitmap(mBufferBitmap, 0, 0, null);

        // h
        canvas.save();
        canvas.rotate(mHoursPercent, middleX, middleX);
        Path pathHours = new Path();
//        pathHours.setFillType(Path.FillType.WINDING);
        RectF h = new RectF(middleX - 11, middleX - hoursPointHeight + 30, middleX + 11, middleX);
        pathHours.addRect(h, Path.Direction.CCW);
        pathHours.moveTo(middleX - 11, middleX - hoursPointHeight + 30);
        pathHours.lineTo(middleX, middleX - hoursPointHeight);
        pathHours.lineTo(middleX + 11, middleX - hoursPointHeight + 30);
        canvas.drawPath(pathHours, mPaint);
        canvas.restore();
        //min
        canvas.save();
        canvas.rotate(mMinPercent, middleX, middleX);
        Path pathMin = new Path();
        RectF mins = new RectF(middleX - 7, middleX - minPintHeight + 40, middleX + 7, middleX);
        pathMin.addRect(mins, Path.Direction.CCW);
        pathMin.moveTo(middleX - 7, middleX - minPintHeight + 40);
        pathMin.lineTo(middleX, middleX - minPintHeight);
        pathMin.lineTo(middleX + 7, middleX - minPintHeight + 40);
        canvas.drawPath(pathMin, mPaint);
        canvas.restore();
        // s
        canvas.save();
        canvas.rotate(mSecondPercent, middleX, middleX);
        Rect min = new Rect(middleX - 3, middleX - secondPintHeight, middleX + 3, middleX + 80);
        canvas.drawRect(min, mPaint);
        canvas.restore();

        //
//        mPaint.setColor(Color.WHITE);
//        canvas.drawCircle(middleX, middleX, 25, mPaint);
        mPaint.setColor(0xffaaaaaa);
        canvas.drawCircle(middleX, middleX, 24, mPaint);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(middleX, middleX, 3, mPaint);

    }

    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }


    public float getFontHeight(int size) {
        Paint paint = new Paint();
        paint.setTextSize(size);
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    public int getFontHeights(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }

    int mSecondPercent;
    int mMinPercent;
    int mHoursPercent;

    int mHours;
    int mSecond;
    int mMin;
    Timer timer;

    public void start(int hours, int min, int second) {
        this.mHours = hours;
        this.mSecond = second;
        this.mMin = min;

        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mSecond = mSecond + 1;
                mSecondPercent = mSecond * 6;
                //min 6秒转1度

                if (mSecond == 60) {
                    mSecond = 0;
                    mMin = mMin + 1;
                }
                mMinPercent = mMin * 6 + mSecond / 10;
                if (mMin == 60) {
                    mMin = 0;
                    mHours = mHours + 1;
                }
                //h 2分转1度
                mHoursPercent = mHours * 30 + mMin / 2;
                if (mHours == 12) {
                    mHours = 0;
                }

                postInvalidate();
                Log.d(CustomView.class.getName(), "hours:" + mHours + ",min:" + mMin + ",second:" + mSecond);
            }
        }, 0, 1000);
    }


}
