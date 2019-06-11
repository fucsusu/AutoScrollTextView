package com.example.fucc.autotextview.AutoTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceHolder;


/**
 * Created by fucc on 2018/5/29.
 *
 * 实现类的接口
 */

public interface AutoTextModempl {
    //配置数据
    void startConfigure(AutoScrollTextBean resourceBean);
    //开始动画
    void startAnim();
    //结束动画
    void stopAnim();
    //计算控件大小
    void onMeasure(int with, int height);
    //获取截取屏幕的bitmap
    Bitmap captureScreen();
    //获取SurfaceHolder
    void setSurfaceHodler(SurfaceHolder mHolder);
    //判断是否开始动画
    boolean isStartAnim();
}
