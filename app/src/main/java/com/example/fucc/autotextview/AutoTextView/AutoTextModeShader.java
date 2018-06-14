package com.example.fucc.autotextview.AutoTextView;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.view.animation.LinearInterpolator;

/**
 * Created by fucc on 2018/5/30.
 *
 * 着色器渲染模式
 */

public class AutoTextModeShader extends AutoTextModeBase {

    private Matrix shaderMatrix;
    private LinearGradient shader;

    @Override
    public void startAnim() {
        int mCurrentTextColor = mPaintText.getColor();
        shaderMatrix = new Matrix();

        shader = new LinearGradient(-mWidth, 0, 0, 0,
                new int[]{
                        mCurrentTextColor,
                        Color.RED,
                        mCurrentTextColor,
                },
                new float[]{
                        0,
                        0.5f,
                        1
                },
                Shader.TileMode.CLAMP
        );

        mAnimator = ValueAnimator.ofFloat(0, mWidth)
                .setDuration(mDuration * 1000);

        mAnimator.setRepeatCount(-1);
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                shaderMatrix.setTranslate(2 * animatedValue, 0);
                shader.setLocalMatrix(shaderMatrix);
                drawView(posx);
            }
        });
        mAnimator.start();

        mPaintText.setShader(shader);
    }

}
