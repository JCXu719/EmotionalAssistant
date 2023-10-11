package com.hfut.edu.emotionalassistant.ui.assistant;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.hfut.edu.emotionalassistant.R;

public class AssistantTextClassification extends AppCompatActivity implements View.OnClickListener{

    EditText et;
    Button btn;
    TextView tv;
    Bundle resultBundle = new Bundle();
    textViewHandler tv_handler = new textViewHandler();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistant_textclassification);

    }

    @Override
    protected void onStart() {
        super.onStart();

        et = findViewById(R.id.assistant_textClassification_et);
        btn = findViewById(R.id.assistant_textClassification_btn);
        tv = findViewById(R.id.assistant_textClassification_tv);

        btn.setOnClickListener(this);
    }

    //点击按钮，对文本进行分析
    @Override
    public void onClick(View view) {
        tv.setText("分析中...");
        String input_text = et.getText().toString();

        Message sendResultMsg = new Message();
        new processText2Sentiment(input_text,sendResultMsg).start();    //调用情感分析函数
    }

    //调用对应py文件，获取情感处理结果
    public String getSentimentResult(String input_text){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        Python py = Python.getInstance();
        final PyObject pyob1 = py.getModule("textClassificationModelEnglish");

        PyObject obj = null;
        obj = pyob1.callAttr("main", input_text);

        return obj.toString();
    }

    //handler用于更新ui界面
    class textViewHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            Bundle resultBundle = msg.getData();

            String result = resultBundle.getString("result");

            tv.setText(result);
        }
    }

    //processText2Sentiment线程类用于计算情感
    class processText2Sentiment extends Thread{
        private String input_text = null;
        private Message sendResultMsg = null;

        @Override
        public void run() {
            super.run();

            String result = getSentimentResult(input_text); //调用情感分析函数

            //将信息传递给handler进行ui更新
            resultBundle.putString("result",result);
            sendResultMsg.setData(resultBundle);
            tv_handler.sendMessage(sendResultMsg);

        }

        //构造函数，用于传入部分参数
        public processText2Sentiment(String input_text, Message sendResultMsg){
            this.input_text = input_text;
            this.sendResultMsg = sendResultMsg;
        }
    }
}
