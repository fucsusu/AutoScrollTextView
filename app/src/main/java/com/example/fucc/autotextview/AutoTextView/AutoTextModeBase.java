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
import android.view.SurfaceHolder;

/**
 * Created by fucc on 2018/5/29.
 * <p>
 * 自适应文本的基类
 */

public class AutoTextModeBase implements AutoTextModempl {
    private String TAG = "AutoTextModeBase";
    //控件的宽高
    protected int mWidth = 1;
    protected int mHeight = 1;

    //文字绘制XY轴
    protected float posy;
    protected int posx = 0;

    protected TextPaint mPaintText;
    //文本内容
    protected String mText = "";
    protected int mBackGroundColor;

    protected ValueAnimator mAnimator;
    //动画的执行时间
    protected int mDuration = 10;
    protected SurfaceHolder surfaceHolder;

    //文字起始位置
    protected int mDrawX = 0;

    protected Context mContext;

    protected AutoScrollTextBean autoScrollTextBean;

    public AutoTextModeBase() {
        this.mPaintText = new TextPaint();
        mPaintText.setTypeface(Typeface.DEFAULT_BOLD);
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(Color.WHITE);
    }

    //配置参数
    @Override
    public void startConfigure(Context mContext, AutoScrollTextBean autoScrollTextBean) {
        this.autoScrollTextBean=autoScrollTextBean;
        this.mContext = mContext;
        mText = autoScrollTextBean.getTextDetail().trim();
        mPaintText.setColor(Color.parseColor(autoScrollTextBean.getTextColor().trim()));
        mBackGroundColor = Color.parseColor(autoScrollTextBean.getTextBackbroundcolor());
    }

    @Override
    public void startAnim() {
        drawView(posx);
    }

    @Override
    public void stopAnim() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    //设置字体大小
    @Override
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
        this.posy = mHeight / 2 + (fm.descent - fm.ascent) / 2 - (fm.descent - fm.leading) / 2;
        this.posx = (int) ((mWidth - mPaintText.measureText(mText)) / 2);
        this.mDrawX = posx;
    }

    //获取控件宽高
    @Override
    public void onMeasure(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    //绘制视图
    protected void drawView(int x) {
        if (surfaceHolder == null) {
            return;
        }
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        canvas.drawText(mText, x, posy, mPaintText);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }


    //获取截屏
    @Override
    public Bitmap captureScreen() {
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(mBackGroundColor);
        canvas.drawText(mText, mDrawX, posy, mPaintText);
        return bitmap;
    }

    @Override
    public void setSurfaceHodler(SurfaceHolder mHolder) {
        surfaceHolder = mHolder;
    }
}
