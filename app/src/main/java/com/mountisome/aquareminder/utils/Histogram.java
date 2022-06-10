package com.mountisome.aquareminder.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.mountisome.aquareminder.R;

public class Histogram extends View {

    int MAX = 1800; // 矩形显示的最大值
    int corner = 0; // 矩形的角度，设置为0则没有角度。
    double data = 0; // 显示的数
    double tempData = 0; // 初始数据
    int textPadding = 0; // 字体与矩形图的距离
    Paint mPaint;
    int mColor;
    Context mContext;

    // 构造函数
    public Histogram(Context context) {
        super(context);
        mContext = context;
    }

    public Histogram(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initPaint();
    }

    public Histogram(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initPaint();
    }

    // 画笔方法
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mColor = mContext.getResources().getColor(R.color.water);
        mPaint.setColor(mColor);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (data == 0.0) {
            mPaint.setTextSize(getWidth() / 2);
            RectF oval3 = new RectF(0, getHeight() - DensityUtils.pxTodip(mContext,
                    20), getWidth(), getHeight()); // 设置个新的长方形
            canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner),
                    DensityUtils.pxTodip(mContext, corner), mPaint);
            canvas.drawText("",
                    getWidth() * 0.5f - mPaint.measureText("0") * 0.5f,
                    getHeight() - DensityUtils.pxTodip(mContext, 20) -
                            2 * DensityUtils.pxTodip(mContext, textPadding),
                    mPaint);
            return;
        }

        // 防止数值很大的的时候，动画时间过长
        int step = (int) (data / 100 + 1.0);
        if (tempData < data - step) {
            tempData = tempData + step;
        } else {
            tempData = data;
        }
        // 画圆角矩形
        String S = tempData + ""; // 如果数字后面需要加% 则在""中添加%
        // 设置显示的字体
        // Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),"digital-7.ttf");
        // mPaint.setTypeface(typeface);
        // 一个字和两,三个字的字号相同
        if (S.length() < 4) {
            mPaint.setTextSize(getWidth() / 2);
        } else {
            mPaint.setTextSize(50); // 可以通过getWidth()/2 改变字体大小 也可以通过设置数字来改变自己想要的字体大小 当超出矩形图宽度时不能显示全部
        }

        float textH = mPaint.ascent() + mPaint.descent();
        float MaxH = getHeight() - textH - 2 * DensityUtils.pxTodip(mContext, textPadding);

        // 圆角矩形的实际高度
        float realH = (float) (MaxH / MAX * tempData);
        RectF oval3 = new RectF(0, getHeight() - realH, getWidth(), getHeight()); // 设置个新的长方形
        canvas.drawRoundRect(oval3, DensityUtils.pxTodip(mContext, corner), DensityUtils.pxTodip(mContext, corner), mPaint);
        // 写数字
        canvas.drawText(S,
                getWidth() * 0.5f - mPaint.measureText(S) * 0.5f,
                getHeight() - realH - 2 * DensityUtils.pxTodip(mContext, textPadding),
                mPaint);

        if (tempData != data) {
            postInvalidate();
        }
    }

    public void setData(double tempData, double data, int MAX) {
        this.tempData = tempData;
        this.data = data;
        this.MAX = MAX;
        postInvalidate();
    }

}
