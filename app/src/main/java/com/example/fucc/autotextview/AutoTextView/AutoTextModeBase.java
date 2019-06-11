package com.example.fucc.autotextview.AutoTextView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by fucc on 2018/5/29.
 * <p>
 * 实现类的基类
 * 实现了根据控件高度设置字体的大小以及获取当前控件的画面
 */

public class AutoTextModeBase implements AutoTextModempl {
    private String TAG = "AutoTextModeBase";
    //控件的宽高
    protected int mWidth = 1;
    protected int mHeight = 1;

    //文字绘制XY轴
    protected float posy;
    protected float posx = 0;

    protected TextPaint mPaintText;
    //文本内容
    protected String mText = "";
    protected int mBackGroundColor;

    protected ValueAnimator mAnimator;
    //动画的执行时间
    protected int mDuration = 10;
    protected SurfaceHolder surfaceHolder;

    protected AutoScrollTextBean autoScrollTextBean;

    protected boolean isStartAnim = false;

    public AutoTextModeBase() {
        this.mPaintText = new TextPaint();
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
    }

    //配置参数
    @Override
    public void startConfigure(AutoScrollTextBean autoScrollTextBean) {
        this.autoScrollTextBean = autoScrollTextBean;
        mText = autoScrollTextBean.getTextDetail().trim();
        mPaintText.setColor(Color.parseColor(autoScrollTextBean.getTextColor().trim()));
        mBackGroundColor = Color.parseColor(autoScrollTextBean.getTextBackbroundcolor());
    }

    //开启动画
    @Override
    public void startAnim() {
        setPaintTextSize();
        drawView(posx, posy);
        isStartAnim = true;
    }

    //结束动画回收资源
    @Override
    public void stopAnim() {
        if (mAnimator != null) {
            Log.e(TAG, "stopAnim: ");
            mAnimator.cancel();
            mAnimator = null;
        }
        isStartAnim = false;
    }

    //根据控件大小设置字体大小
    public void setPaintTextSize() {
        this.posy = mHeight;
        double textHeight;
        Paint.FontMetrics fm;
        do {
            mPaintText.setTextSize(posy--);
            fm = mPaintText.getFontMetrics();
            textHeight = Math.ceil(fm.descent - fm.ascent);

        } while (textHeight > mHeight);

        do {
            mPaintText.setTextSize(posy--);
        } while (mPaintText.measureText(mText) > mWidth);

        posx -= 10;
        mPaintText.setTextSize(posy);
        fm = mPaintText.getFontMetrics();
        this.posy = (mHeight / 2 + (fm.descent - fm.ascent) / 2 - (fm.descent - fm.leading) / 2);
        this.posx = ((mWidth - mPaintText.measureText(mText)) / 2);
    }

    //获取控件宽高
    @Override
    public void onMeasure(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    //绘制视图
    protected void drawView(float x, float y) {
        if (surfaceHolder == null) {
            return;
        }
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawText(mText, x, y, mPaintText);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    //获取截屏
    @Override
    public Bitmap captureScreen() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mBackGroundColor);
        canvas.drawText(mText, posx, posy, mPaintText);
        return bitmap;
    }

    @Override
    public void setSurfaceHodler(SurfaceHolder mHolder) {
        surfaceHolder = mHolder;
    }

    @Override
    public boolean isStartAnim() {
        return isStartAnim;
    }
}
