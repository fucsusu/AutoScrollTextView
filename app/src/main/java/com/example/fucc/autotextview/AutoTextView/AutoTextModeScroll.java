package com.example.fucc.autotextview.AutoTextView;

import android.graphics.Paint;

import static com.example.fucc.autotextview.AutoTextView.AutoScrollTextView.ANIM_RIGHT;


/**
 * Created by fucc on 2018/5/30.
 * <p>
 * 滚动模式实现类
 */

public class AutoTextModeScroll extends AutoTextModeBase implements Runnable {

    //绘制刷新帧数
    public static final int TIME_IN_FRAME = 15;
    //判断是否开始绘制
    private boolean isDrawView;
    //移动结束位置
    private int moveEnd;
    //移动起始位置
    private int moveStart;
    //移动速度
    private int speed;

    public void setPaintTextSize() {
        this.posy = mHeight;
        double textHeight;
        Paint.FontMetrics fm;
        do {
            mPaintText.setTextSize(posy--);
            fm = mPaintText.getFontMetrics();
            textHeight = Math.ceil(fm.descent - fm.ascent);

        } while (textHeight > mHeight);
        posy -= 10;
        mPaintText.setTextSize(posy);
        this.posy = (int) (mHeight - (fm.descent - fm.leading));
    }

    @Override
    public void startAnim() {
        super.startAnim();
        initRunData();
        new Thread(this).start();
    }

    @Override
    public void stopAnim() {
        super.stopAnim();
        isDrawView = false;
    }

    //定时绘制画布
    @Override
    public void run() {
        isDrawView = true;

        while (isDrawView) {
            posx = posx + speed;
            long startTime = System.currentTimeMillis();

            synchronized (surfaceHolder) {
                drawView(posx,posy);
            }

            long endTime = System.currentTimeMillis();

            int diffTime = (int) (endTime - startTime);

            if (diffTime>TIME_IN_FRAME){
               // L.e("绘制超时"+diffTime);
            }

            while (diffTime <= TIME_IN_FRAME) {
                diffTime = (int) (System.currentTimeMillis() - startTime);
                Thread.yield();
            }


            if ((posx < moveEnd && speed <0)||(posx > moveEnd && speed >0)) {
                posx = moveStart;
            }
        }
    }

    //获取文字长度
    protected int getTextWidth() {
        return (int) mPaintText.measureText(mText);
    }

    //初始化滚动数据
    protected void initRunData(){
        //判断文字滚动方向
        if (ANIM_RIGHT.equals(autoScrollTextBean.getTextScrollDirection())) {
            moveStart = mWidth;
            moveEnd = -getTextWidth();
            speed = -autoScrollTextBean.getTextSpeed();
        } else {
            moveStart = -getTextWidth();
            moveEnd = mWidth;
            speed = autoScrollTextBean.getTextSpeed();
            mText = new StringBuffer(mText).reverse().toString();
        }
        posx = moveStart;
    }
}
