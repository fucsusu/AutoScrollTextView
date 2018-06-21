package com.example.fucc.autotextview.AutoTextView;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.animation.DecelerateInterpolator;

import java.util.logging.Handler;

/**
 * Created by fucc on 2018/6/21.
 * <p>
 * 上下切换模式
 */

public class AutoTextModeSwitch extends AutoTextModeBase {

    private int index = 0;
    private String nextText;

    @Override
    public void startConfigure(AutoScrollTextBean autoScrollTextBean) {
        super.startConfigure(autoScrollTextBean);
        if (autoScrollTextBean.getSwitchDatas() != null && autoScrollTextBean.getSwitchDatas().size() > 0) {
            index = 0;
            mText = autoScrollTextBean.getSwitchDatas().get(index);
            nextText = autoScrollTextBean.getSwitchDatas().get(index + 1);
            index = ++index % autoScrollTextBean.getSwitchDatas().size();
        }
    }

    @Override
    public void startAnim() {
        super.startAnim();
        if (index >= 0) {
            mAnimator = ValueAnimator.ofFloat(mHeight + posy, posy)
                    .setDuration(2000);
            mAnimator.setRepeatCount(-1);
            mAnimator.setRepeatMode(ValueAnimator.RESTART);
            mAnimator.setInterpolator(new DecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    float drawy = (float) animation.getAnimatedValue();
                    drawView(posx, drawy);
                }
            });

            mAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {

                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                    mText = autoScrollTextBean.getSwitchDatas().get(index);
                    index = ++index % autoScrollTextBean.getSwitchDatas().size();
                    nextText = autoScrollTextBean.getSwitchDatas().get(index);
                }
            });
            mAnimator.start();
        } else {
            isStartAnim = false;
        }
    }

    @Override
    protected void drawView(float x, float y) {
        if (surfaceHolder == null) {
            return;
        }
        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if (index < 0) {
            canvas.drawText(mText, x, y, mPaintText);
        } else {
            canvas.drawText(mText, x, y - mHeight, mPaintText);
            canvas.drawText(nextText, x, y, mPaintText);
        }
        surfaceHolder.unlockCanvasAndPost(canvas);
    }
}
