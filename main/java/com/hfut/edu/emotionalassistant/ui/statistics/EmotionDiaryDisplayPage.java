package com.hfut.edu.emotionalassistant.ui.statistics;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.adapter.DiaryAdapter;
import com.hfut.edu.emotionalassistant.data.bean.DiaryItemBean;
import com.hfut.edu.emotionalassistant.databasehelpers.EmotionDiaryDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class EmotionDiaryDisplayPage extends AppCompatActivity {
    //数据->Adapter->View
    private ListView diaryListView;//View
    private List<DiaryItemBean> diaryItemBeanList;//数据
    private DiaryAdapter diaryAdapter;//Adapter

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_emotiondiarylist);

        initView();
        initData();
        initEvent();
    }

    //初始化Adapter
    public void initEvent(){
        diaryAdapter = new DiaryAdapter(this,diaryItemBeanList);
        diaryListView.setAdapter(diaryAdapter);
    }

    //数据初始化
    @SuppressLint("Range")
    public void initData(){

        //定义itembean
        diaryItemBeanList = new ArrayList<>();

        //获取数据库
        EmotionDiaryDatabaseHelper emotionDiaryDatabaseHelper = new EmotionDiaryDatabaseHelper(this,"emotionDiaryDatabase.db",null,1);
        SQLiteDatabase diary_db = emotionDiaryDatabaseHelper.getWritableDatabase();

        //在数据库中获取对应的数据
        String sql = "SELECT *from diaryInfo";
        Cursor cursor = diary_db.rawQuery(sql,null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            DiaryItemBean diaryItemBean = new DiaryItemBean();
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String weather = cursor.getString(cursor.getColumnIndex("weather"));
            String emotion = cursor.getString(cursor.getColumnIndex("emotion"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            int comment = cursor.getInt(cursor.getColumnIndex("comment_count"));
            int likes = cursor.getInt(cursor.getColumnIndex("likes_count"));
            int stars = cursor.getInt(cursor.getColumnIndex("stars_count"));

            diaryItemBean.setDate(date);
            diaryItemBean.setWeather(weather);
            diaryItemBean.setEmotion(emotion);
            diaryItemBean.setContent(content);
            diaryItemBean.setCommentsCount(comment);
            diaryItemBean.setLikesCount(likes);
            diaryItemBean.setStarsCount(stars);

            diaryItemBeanList.add(diaryItemBean);

            cursor.moveToNext();
        }
    }

    public void initView(){
        //获取需要填充的列表
        diaryListView = findViewById(R.id.statistics_emotionDiary_DiaryList);
    }
}
