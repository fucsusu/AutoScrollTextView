package com.example.fucc.autotextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.fucc.autotextview.AutoTextView.AutoScrollTextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AutoScrollTextView autotext;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autotext = (AutoScrollTextView) findViewById(R.id.auto_text_switch);

        datas=new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            datas.add("switch data "+i);
        }
        autotext.addSwitchData(datas);
    }
}
