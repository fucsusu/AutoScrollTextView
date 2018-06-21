package com.example.fucc.autotextview.AutoTextView;

import java.util.ArrayList;

import static com.example.fucc.autotextview.AutoTextView.AutoScrollTextView.ANIM_NONE;
import static com.example.fucc.autotextview.AutoTextView.AutoScrollTextView.ANIM_RIGHT;

/**
 * 数据实体类
 * Created by fucc on 2018/6/1.
 */

public class AutoScrollTextBean {

    /**
     * 配置信息的拆分
     * 1.字幕内容
     * 2.字体颜色
     * 3.滚动方向
     * 4.动画类型
     * 5.滚动速度
     * 6.背景颜色
     * 7.上下切换数据
     */

    private String textDetail = "hello word!!!";
    private String textBackbroundcolor = "#122223";
    private String textColor = "#984512";
    private String textEffect = ANIM_NONE;
    private String textScrollDirection = ANIM_RIGHT;
    private int textSpeed = 5;
    private ArrayList<String> switchDatas=null;

    public AutoScrollTextBean(String textDetail, String textBackbroundcolor, String textColor, String textEffect, String textScrollDirection, int textSpeed) {
        if (textDetail != null) {
            setTextDetail(textDetail);
        }
        if (textBackbroundcolor != null) {
            setTextBackbroundcolor(textBackbroundcolor);
        }
        if (textColor != null) {
            setTextColor(textColor);
        }
        if (textEffect != null) {
            setTextEffect(textEffect);
        }
        if (textScrollDirection != null) {
            setTextScrollDirection(textScrollDirection);
        }
        this.textSpeed = textSpeed;
    }

    public String getTextDetail() {
        return textDetail;
    }

    public void setTextDetail(String textDetail) {
        this.textDetail = textDetail;
    }

    public String getTextBackbroundcolor() {
        return textBackbroundcolor;
    }

    public void setTextBackbroundcolor(String textBackbroundcolor) {
        this.textBackbroundcolor = textBackbroundcolor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getTextEffect() {
        return textEffect;
    }

    public void setTextEffect(String textEffect) {
        this.textEffect = textEffect;
    }

    public String getTextScrollDirection() {
        return textScrollDirection;
    }

    public void setTextScrollDirection(String textScrollDirection) {
        this.textScrollDirection = textScrollDirection;
    }

    public int getTextSpeed() {
        if (textSpeed > 15) {
            return 15;
        }
        return textSpeed;
    }

    public void setTextSpeed(int textSpeed) {
        this.textSpeed = textSpeed;
    }

    public ArrayList<String> getSwitchDatas() {
        return switchDatas;
    }

    public void setSwitchDatas(ArrayList<String> switchDatas) {
        this.switchDatas = switchDatas;
    }
}
