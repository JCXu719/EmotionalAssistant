package com.hfut.edu.emotionalassistant.ui.statistics;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfut.edu.emotionalassistant.R;

public class EmotionDataPage extends AppCompatActivity {

    //显示日记列表界面
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_emotiondatapage);
    }
}
