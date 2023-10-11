package com.hfut.edu.emotionalassistant.ui.statistics;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.adapter.DiaryAdapter;
import com.hfut.edu.emotionalassistant.data.bean.DiaryItemBean;
import com.hfut.edu.emotionalassistant.databasehelpers.EmotionDiaryDatabaseHelper;
import com.hfut.edu.emotionalassistant.databinding.FragmentStatisticsBinding;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment implements View.OnClickListener {

    Button btn_emotionDiary,btn_dailyTips, btn_emotionData;
    //数据->适配器->控件
    private ListView diaryListView;//控件
    private List<DiaryItemBean> diaryItemBeanList;//数据
    private DiaryAdapter diaryAdapter;//适配器

    private FragmentStatisticsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        //初始化控件
        btn_emotionDiary = getActivity().findViewById(R.id.statistics_emotionDiary_moreInfoBtn);
        btn_dailyTips = getActivity().findViewById(R.id.statistics_emotionTips_moreInfoBtn);
        btn_emotionData = getActivity().findViewById(R.id.statistics_emotionData_moreInfoBtn);

        //设置监听器
        btn_emotionDiary.setOnClickListener(this);
        btn_dailyTips.setOnClickListener(this);
        btn_emotionData.setOnClickListener(this);

        //初始化日记板块
        initView();
        initData();
        initEvent();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    //点击不同按钮切换Activity
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.statistics_emotionDiary_moreInfoBtn){
            //转换到"情感日记"的详细界面
            Intent intent = new Intent(getActivity(), EmotionDiaryDisplayPage.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.statistics_emotionTips_moreInfoBtn){
            //转换到“每日小贴士”的详细界面
            Intent intent = new Intent(getActivity(),DailyTipsPage.class);
            startActivity(intent);
        }
        else if(view.getId() == R.id.statistics_emotionData_moreInfoBtn){
            //转换到“情感分析数据的详细界面”
            Intent intent = new Intent(getActivity(),EmotionDataPage.class);
            startActivity(intent);
        }
    }

    //初始化Adapter
    public void initEvent(){
        diaryAdapter = new DiaryAdapter(getActivity(),diaryItemBeanList);
        diaryListView.setAdapter(diaryAdapter);
    }

    //数据初始化
    @SuppressLint("Range")
    public void initData(){
        //定义itembean
        diaryItemBeanList = new ArrayList<>();

        //获取数据库
        EmotionDiaryDatabaseHelper emotionDiaryDatabaseHelper = new EmotionDiaryDatabaseHelper(getActivity(),"emotionDiaryDatabase.db",null,1);
        SQLiteDatabase diary_db = emotionDiaryDatabaseHelper.getWritableDatabase();

        //根据输入的字符串在数据库中获取对应的数据
        String sql = "SELECT *from diaryInfo";
        Cursor cursor = diary_db.rawQuery(sql,null);

        cursor.moveToFirst();
        int tmp_count = 0;

        while(!cursor.isAfterLast() && tmp_count < 5){
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
            tmp_count++;
        }
    }

    public void initView(){
        //获取列表
        diaryListView = getActivity().findViewById(R.id.statistics_emotionDiary_DiaryList);
    }
}