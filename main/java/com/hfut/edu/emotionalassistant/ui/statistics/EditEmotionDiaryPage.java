package com.hfut.edu.emotionalassistant.ui.statistics;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.hfut.edu.emotionalassistant.MainActivity;
import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.databasehelpers.EmotionDiaryDatabaseHelper;

public class EditEmotionDiaryPage extends AppCompatActivity implements View.OnClickListener {
    Button btn_clear, btn_submit;   //获取页面上的“清除”和“提交按钮”
    EditText etDate,etWeather,etEmotion,etContent;      //文本信息
    int emotion_img = -1, comment_count = 0, likes_count = 0, stars_count = 0;  //其他信息
    String sql = null;  //sql语句
    SQLiteDatabase emotionDiary_db = null;  //数据库

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_emotiondiary_edit);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取控件和添加监听器
        btn_clear = findViewById(R.id.statistics_emotionDiary_editDiary_clearBtn);
        btn_submit = findViewById(R.id.statistics_emotionDiary_editDiary_submitBtn);

        etDate = findViewById(R.id.statistics_emotionDiary_editDiary_etDate);
        etWeather = findViewById(R.id.statistics_emotionDiary_editDiary_etWeather);
        etEmotion = findViewById(R.id.statistics_emotionDiary_editDiary_etEmotion);
        etContent = findViewById(R.id.statistics_emotionDiary_editDiary_etContent);

        btn_clear.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        //初始化数据库
        init_diary_database();

        //获取输入框中的内容
        String date,weather,emotion,content;
        date = etDate.getText().toString();
        weather = etWeather.getText().toString();
        emotion = etEmotion.getText().toString();
        content = etContent.getText().toString();

        if(view.getId() == R.id.statistics_emotionDiary_editDiary_clearBtn){    //清空
            etDate.setText("");
            etWeather.setText("");
            etEmotion.setText("");
            etContent.setText("");
        }else if(view.getId() == R.id.statistics_emotionDiary_editDiary_submitBtn){     //提交

            //提交过程中，将数据增加到数据库中
            sql = "INSERT INTO diaryInfo(date,weather,emotion,emotion_img,content,comment_count,likes_count,stars_count)"
            + " VALUES(" + "'"+date+"'," + "'"+weather+"'," + "'"+emotion+"'," + "-1" + "," + "'"+content+"'," + "0" + "," + "0" + "," + "0" + ");";

            //执行sql语句
            emotionDiary_db.execSQL(sql);

            //对数据进行文本情感分析


            //提示提交成功
            Toast.makeText(this,"提交成功！",Toast.LENGTH_SHORT).show();

            //清空内容
            etDate.setText("");
            etWeather.setText("");
            etEmotion.setText("");
            etContent.setText("");

            //转换到情感日记展示页
            Intent intent = new Intent(this, EmotionDiaryDisplayPage.class);
            startActivity(intent);
        }
    }

    //文本情感分析
    public String getText2SentimentResult(String input_text){

        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();

        final PyObject text2Sentiment = py.getModule("textClassificationModelEnglish");

        PyObject result = null;
        result = text2Sentiment.callAttr("main", input_text);

        return result.toString();
    }

    //更新ui的Handler
    private class textViewHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle resultBundle = msg.getData();
        }
    }
    //执行文本情感分析的线程
    private class processText2Sentiment extends Thread{
        private String input_text = null;

        @Override
        public void run() {
            super.run();
            String result = getText2SentimentResult(input_text);

            Bundle resultBundle = new Bundle();
            resultBundle.putString("result",result);

            Message resultMsg = new Message();
            resultMsg.setData(resultBundle);

            textViewHandler tv_Handler = new textViewHandler();
            tv_Handler.sendMessage(resultMsg);

        }

        public processText2Sentiment(String input_text){
            this.input_text = input_text;
        }
    }

    //初始化情感日记数据库
    public void init_diary_database(){
        EmotionDiaryDatabaseHelper emotionDiaryDatabaseHelper = new EmotionDiaryDatabaseHelper(this,"emotionDiaryDatabase.db",null,1);
        emotionDiary_db = emotionDiaryDatabaseHelper.getWritableDatabase();
    }
}
