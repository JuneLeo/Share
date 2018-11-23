package com.leo.events.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.leo.events.util.DimenUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by spf on 2018/11/4.
 */
public class AppProgressCircleView extends View {

    private long mTime;

    private int mWidth;
    private int mHeight;

    private int mStrokeWidth = DimenUtils.dp2px(12);

    private int mTimeHeightOffset = DimenUtils.dp2px(5);
    private int mTimeTextHeightOffset = DimenUtils.dp2px(15);

    public AppProgressCircleView(Context context) {
        super(context);
    }

    public AppProgressCircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AppProgressCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint mCirclePaint;
    private Paint mTimePaint;

    {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(DimenUtils.dp2px(12));
        mTimePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTimePaint.setColor(Color.GRAY);
        mTimePaint.setTextSize(DimenUtils.dp2px(15));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mCirclePoint != null && !mCirclePoint.isEmpty()) {
            for (CirclePoint circlePoint : mCirclePoint) {
                mCirclePaint.setColor(circlePoint.color);
                RectF rectF = new RectF(DimenUtils.dp2px(mStrokeWidth) / 2, DimenUtils.dp2px(mStrokeWidth) / 2, mWidth - (DimenUtils.dp2px(mStrokeWidth) / 2), mHeight - (DimenUtils.dp2px(mStrokeWidth) / 2));
                canvas.drawArc(rectF, circlePoint.start, circlePoint.precent, false, mCirclePaint);
            }
        }
        if (mDatePoint != null && !mDatePoint.isEmpty()) {
            for (DatePoint datePoint : mDatePoint) {
                canvas.drawText(datePoint.text, datePoint.mPointF.x, datePoint.mPointF.y, datePoint.mPaint);
            }
            float width = getFontWidth(mTimePaint, "总用时");
            canvas.drawText("总用时", (mWidth - width) / 2, mHeight / 2 + getFontHeight(mTimePaint) + mTimeTextHeightOffset, mTimePaint);
        }


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }



    public void setmModels(List<AppProgressModel> mModels) {
        this.mTime = getTotleTime(mModels);
        initModel(mModels);
        invalidate();
    }

    private List<CirclePoint> mCirclePoint;
    private List<DatePoint> mDatePoint;

    private void initModel(List<AppProgressModel> mModels) {
        // 圆圈
        mCirclePoint = new ArrayList<>();
        int start = -90;
        for (int i = 0; i < mModels.size(); i++) {
            AppProgressModel model = mModels.get(i);
            int progress;
            if (i == mModels.size() - 1) {
                progress = 270 - start;

            } else {
                progress = getProgress(model);
            }
            CirclePoint circlePoint = new CirclePoint();
            circlePoint.color = model.color;
            circlePoint.start = start;
            circlePoint.precent = progress;
            mCirclePoint.add(circlePoint);
            start = start + progress;
        }
        //中间文字
        mDatePoint = new ArrayList<>();
        int hours = getHours();
        if (hours > 0) {
            DatePoint hourDate = new DatePoint();
            Paint mPaintHours = getNumberPaint();
            hourDate.mPaint = mPaintHours;
            hourDate.text = String.valueOf(hours);
            float hoursWidth = getFontWidth(mPaintHours, hourDate.text);

            DatePoint hoursLabel = new DatePoint();
            Paint hLabelPaint = getLabelPaint();
            hoursLabel.mPaint = hLabelPaint;
            hoursLabel.text = "h";
            float hoursLabelWidth = getFontWidth(hLabelPaint, hoursLabel.text);

            DatePoint minDate = new DatePoint();
            Paint mPaintMin = getNumberPaint();
            minDate.mPaint = mPaintMin;
            minDate.text = String.valueOf(getMin());
            float minWidth = getFontWidth(mPaintMin, minDate.text);

            DatePoint minLabel = new DatePoint();
            Paint minLabelPaint = getLabelPaint();
            minLabel.mPaint = minLabelPaint;
            minLabel.text = "min";
            float minLabelWidth = getFontWidth(minLabelPaint, minLabel.text);

            float width = hoursWidth + hoursLabelWidth + minWidth + minLabelWidth;

            float hoursX = (mWidth - width) / 2;
            float hoursY = mHeight / 2 + mTimeHeightOffset;
            PointF hoursPointF = new PointF(hoursX, hoursY);
            hourDate.mPointF = hoursPointF;
            Log.d(AppProgressCircleView.class.getSimpleName(), "x:" + hoursX + ",width:" + hoursWidth);
            mDatePoint.add(hourDate);

            float hourLabelX = hoursX + hoursWidth;
            float hourLabelY = mHeight / 2 + mTimeHeightOffset;
            PointF hoursLabelPointF = new PointF(hourLabelX, hourLabelY);
            hoursLabel.mPointF = hoursLabelPointF;
            Log.d(AppProgressCircleView.class.getSimpleName(), "x:" + hourLabelX + ",width:" + hoursLabelWidth);
            mDatePoint.add(hoursLabel);

            float minX = hourLabelX + hoursLabelWidth;
            float minY = mHeight / 2 + mTimeHeightOffset;
            PointF minPointF = new PointF(minX, minY);
            minDate.mPointF = minPointF;
            Log.d(AppProgressCircleView.class.getSimpleName(), "x:" + minX + ",width:" + minWidth);
            mDatePoint.add(minDate);

            float minLabelX = minX + minWidth;
            float minLabelY = mHeight / 2 + mTimeHeightOffset;
            PointF minLabelPointF = new PointF(minLabelX, minLabelY);
            minLabel.mPointF = minLabelPointF;
            Log.d(AppProgressCircleView.class.getSimpleName(), "x:" + minLabelX + ",width:" + minLabelWidth);
            mDatePoint.add(minLabel);
        } else {
            int min = getMin();
            if (min > 0) {
                DatePoint hourDate = new DatePoint();
                Paint mPaintHours = getNumberPaint();
                hourDate.mPaint = mPaintHours;
                hourDate.text = String.valueOf(min);
                float hoursWidth = getFontWidth(mPaintHours, hourDate.text);

                DatePoint hoursLabel = new DatePoint();
                Paint hLabelPaint = getLabelPaint();
                hoursLabel.mPaint = hLabelPaint;
                hoursLabel.text = "min";
                float hoursLabelWidth = getFontWidth(hLabelPaint, hoursLabel.text);


                DatePoint minDate = new DatePoint();
                Paint mPaintMin = getNumberPaint();
                minDate.mPaint = mPaintMin;
                minDate.text = String.valueOf(getSecond());
                float minWidth = getFontWidth(mPaintMin, minDate.text);

                DatePoint minLabel = new DatePoint();
                Paint minLabelPaint = getLabelPaint();
                minLabel.mPaint = minLabelPaint;
                minLabel.text = "s";
                float minLabelWidth = getFontWidth(minLabelPaint, minLabel.text);

                float width = hoursWidth + hoursLabelWidth + minWidth + minLabelWidth;

                float hoursX = (mWidth - width) / 2;
                float hoursY = mHeight / 2 + mTimeHeightOffset;
                PointF hoursPointF = new PointF(hoursX, hoursY);
                hourDate.mPointF = hoursPointF;
                mDatePoint.add(hourDate);

                float hourLabelX = hoursX + hoursWidth;
                float hourLabelY = mHeight / 2 + mTimeHeightOffset;
                PointF hoursLabelPointF = new PointF(hourLabelX, hourLabelY);
                hoursLabel.mPointF = hoursLabelPointF;
                mDatePoint.add(hoursLabel);

                float minX = hourLabelX + hoursLabelWidth;
                float minY = mHeight / 2 + mTimeHeightOffset;
                PointF minPointF = new PointF(minX, minY);
                minDate.mPointF = minPointF;
                mDatePoint.add(minDate);

                float minLabelX = minX + minWidth;
                float minLabelY = mHeight / 2 + mTimeHeightOffset;
                PointF minLabelPointF = new PointF(minLabelX, minLabelY);
                minLabel.mPointF = minLabelPointF;
                mDatePoint.add(minLabel);
            } else {
                int second = getSecond();
                if (second > 0) {
                    DatePoint hourDate = new DatePoint();
                    Paint mPaintHours = getNumberPaint();
                    hourDate.mPaint = mPaintHours;
                    hourDate.text = String.valueOf(second);
                    float hoursWidth = getFontWidth(mPaintHours, hourDate.text);

                    DatePoint hoursLabel = new DatePoint();
                    Paint hLabelPaint = getLabelPaint();
                    hoursLabel.mPaint = hLabelPaint;
                    hoursLabel.text = "s";
                    float hoursLabelWidth = getFontWidth(hLabelPaint, hoursLabel.text);


                    float width = hoursWidth + hoursLabelWidth;

                    float hoursX = (mWidth - width) / 2;
                    float hoursY = mHeight / 2 + mTimeHeightOffset;
                    PointF hoursPointF = new PointF(hoursX, hoursY);
                    hourDate.mPointF = hoursPointF;
                    mDatePoint.add(hourDate);

                    float hourLabelX = hoursX + hoursWidth;
                    float hourLabelY = mHeight / 2 + mTimeHeightOffset;
                    PointF hoursLabelPointF = new PointF(hourLabelX, hourLabelY);
                    hoursLabel.mPointF = hoursLabelPointF;
                    mDatePoint.add(hoursLabel);
                }
            }

        }

    }

    public int getHours() {
        return Math.round(mTime / (60 * 60 * 1000));
    }

    public int getMin() {
        int hours = getHours();
        long min = mTime;
        if (hours > 0) {
            min = min - (hours * 60 * 60 * 1000);
        }
        return Math.round(min / (60 * 1000));
    }

    public int getSecond() {
        int hours = getHours();
        long second = mTime;
        if (hours > 0) {
            second = second - (hours * 60 * 60 * 1000);
        }
        int min = getMin();
        if (min > 0) {
            second = second - (min * 60 * 1000);
        }
        return Math.round(second / 1000);
    }

    private Paint getNumberPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(DimenUtils.dp2px(35));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(Color.BLUE);
        return paint;
    }

    private Paint getLabelPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(DimenUtils.dp2px(15));
        paint.setColor(Color.BLUE);
        return paint;
    }

    public int getProgress(AppProgressModel model) {
        long time = model.time / 1000;
        long mTime = this.mTime / 1000;
        return (int) (360 * time / mTime);
    }

    private long getTotleTime(List<AppProgressModel> mModels) {
        int time = 0;
        if (mModels != null && !mModels.isEmpty()) {
            for (AppProgressModel mModel : mModels) {
                time = time + mModel.time;
            }
        }
        return time;
    }

    public float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }


}
