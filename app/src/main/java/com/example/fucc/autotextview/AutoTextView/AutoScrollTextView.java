package com.example.fucc.autotextview.AutoTextView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.example.fucc.autotextview.R;

import java.util.ArrayList;


/**
 * 自适应文本view
 * Created by yao on 2018/3/12.
 */

@SuppressLint("AppCompatCustomView")
public class AutoScrollTextView extends SurfaceView implements SurfaceHolder.Callback {

    private final String TAG = "autotext";
    //动画类型
    protected static final String ANIM_NONE = "0";
    protected static final String ANIM_SCROLL = "1";
    protected static final String ANIM_SHADER = "2";
    protected static final String ANIM_SWITCH = "3";

    //滚动方向
    protected static final String ANIM_LEFT = "0";
    protected static final String ANIM_RIGHT = "1";

    //显示模式的抽象控制类
    private AutoTextModempl textModempl;

    private SurfaceHolder mSurfaceHolder;

    private static AutoScrollTextView acquire;
    private AutoScrollTextBean autoScrollTextBean;

    public AutoScrollTextView(Context context) {
        super(context);
        initView(context);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e(TAG, "AutoScrollTextView: ");
        initView(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollTextView);
        String text = ta.getString(R.styleable.AutoScrollTextView_android_text);
        String textColor = ta.getString(R.styleable.AutoScrollTextView_textColor);
        String textEffect = ta.getString(R.styleable.AutoScrollTextView_textEffect);
        String textbg = ta.getString(R.styleable.AutoScrollTextView_android_background);
        String textDirection = ta.getString(R.styleable.AutoScrollTextView_textScrollDirection);
        int textSpeed = ta.getInt(R.styleable.AutoScrollTextView_textSpeed, 5);

        autoScrollTextBean = new AutoScrollTextBean(text, textbg, textColor, textEffect, textDirection, textSpeed);
        startConfigure(autoScrollTextBean);
        ta.recycle();
    }

    public static AutoScrollTextView obtain(Context context) {
        if (acquire == null) {
            acquire = new AutoScrollTextView(context);
        }
        return acquire;
    }

    //初始化
    @SuppressLint("ResourceAsColor")
    private void initView(Context context) {
        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);

        setZOrderOnTop(true);
        setFocusable(true);
    }

    //传入配置 根据resource是否为null 进行判断
    public void startConfigure(AutoScrollTextBean autoScrollTextBean) {
        this.setBackgroundColor(Color.parseColor(autoScrollTextBean.getTextBackbroundcolor()));
        switch (autoScrollTextBean.getTextEffect()) {
            case ANIM_SCROLL:
                textModempl = new AutoTextModeScroll();
                break;
            case ANIM_SHADER:
                textModempl = new AutoTextModeShader();
                break;
            case ANIM_SWITCH:
                textModempl=new AutoTextModeSwitch();
                break;
            default:
                textModempl = new AutoTextModeBase();
                break;
        }
        textModempl.startConfigure(autoScrollTextBean);

        //开启绘制
        startAnim();
    }

    public void addSwitchData(ArrayList<String> data) {
        ArrayList<String> switchDatas = autoScrollTextBean.getSwitchDatas();
        if (switchDatas != null) {
            switchDatas.addAll(data);
        } else {
            autoScrollTextBean.setSwitchDatas(data);
        }
        textModempl.startConfigure(autoScrollTextBean);
    }


    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility != VISIBLE) {
            textModempl.stopAnim();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        textModempl.onMeasure(getMeasuredWidth(), getMeasuredHeight());
    }


    //获取当前画面的bitmap
    public Bitmap getFrameBitmap() {
        return textModempl.captureScreen();
    }

    private void startTextModeAnim() {
        textModempl.startAnim();
    }

    //开启动画
    private void startAnim() {
        post(new Runnable() {
            @Override
            public void run() {
                startTextModeAnim();
            }
        });
    }


    public void recycle() {
        textModempl.stopAnim();
        setVisibility(View.GONE);
        setTag("empty");
        //回收隐藏后的图片资源
        destroyDrawingCache();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mSurfaceHolder = holder;
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        textModempl.setSurfaceHodler(mSurfaceHolder);
        //textModempl.startAnim();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mSurfaceHolder = holder;
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        textModempl.setSurfaceHodler(mSurfaceHolder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        textModempl.stopAnim();
    }
}
