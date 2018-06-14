package com.example.fucc.autotextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fucc.autotextview.AutoTextView.AutoScrollTextView;

public class MainActivity extends AppCompatActivity {

    private AutoScrollTextView autotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
