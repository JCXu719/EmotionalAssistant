package com.hfut.edu.emotionalassistant.databasehelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class EmotionDiaryDatabaseHelper extends SQLiteOpenHelper {
    String sql = null; //sql语句

    //构造函数
    public EmotionDiaryDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //建表语句
        sql = "CREATE TABLE diaryInfo(diary_id integer primary key autoincrement," +
                "date text not null," +
                "weather text not null," +
                "emotion text not null," +
                "emotion_img integer not null," +
                "content text not null," +
                "comment_count integer not null," +
                "likes_count integer not null," +
                "stars_count integer not null)";
        //执行语句
        sqLiteDatabase.execSQL(sql);
    }

    //暂时不用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
