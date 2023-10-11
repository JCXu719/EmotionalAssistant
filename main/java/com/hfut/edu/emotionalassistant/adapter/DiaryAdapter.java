package com.hfut.edu.emotionalassistant.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hfut.edu.emotionalassistant.MainActivity;
import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.data.bean.DiaryItemBean;
import com.hfut.edu.emotionalassistant.databasehelpers.EmotionDiaryDatabaseHelper;
import com.hfut.edu.emotionalassistant.ui.statistics.EmotionDiaryDisplayPage;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DiaryAdapter extends BaseAdapter {

    private List<DiaryItemBean> diaryItemBeanList;  //列表数据
    private LayoutInflater layoutInflater;  //样式填充
    private Context context;    //上下文

    //DiaryAdapter构造方法：获取上下文context，列表数据diaryItemBeanList和填充器layoutInflater
    public DiaryAdapter(Context context, List<DiaryItemBean> diaryItemBeanList){
        this.context = context;
        this.diaryItemBeanList = diaryItemBeanList;
        layoutInflater = LayoutInflater.from(context);
    }

    //返回列表元素个数
    @Override
    public int getCount() {
        return diaryItemBeanList.size();
    }

    //获取第i个元素
    @Override
    public Object getItem(int i) {
        return diaryItemBeanList.get(i);
    }

    //获取元素下标
    @Override
    public long getItemId(int i) {
        return i;
    }

    //利用列表数据对情感日记列表进行填充，返回值为已经填充好的列表控件View
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        //创建控件
        view = layoutInflater.inflate(R.layout.statistics_emotiondiary,viewGroup,false);

        //获取控件
        TextView date = view.findViewById(R.id.statistics_emotionDiary_tvDate);
        TextView weather = view.findViewById(R.id.statistics_emotionDiary_tvWeather);
        TextView emotion = view.findViewById(R.id.statistics_emotionDiary_tvEmotion);
        ImageView emoji = view.findViewById(R.id.statistics_emotionDiary_ivEmoji);
        TextView content = view.findViewById(R.id.statistics_emotionDiary_tvContent);
        TextView comments = view.findViewById(R.id.statistics_emotionDiary_tvComments);
        TextView likes = view.findViewById(R.id.statistics_emotionDiary_tvLikes);
        TextView stars = view.findViewById(R.id.statistics_emotionDiary_tvStars);

        //填充数据
        DiaryItemBean diaryItemBean = diaryItemBeanList.get(i); //获取下标为i的当前列表项
        date.setText(diaryItemBean.getDate());
        weather.setText(diaryItemBean.getWeather());
        emotion.setText(diaryItemBean.getEmotion());
        content.setText(diaryItemBean.getContent());
        comments.setText(String.valueOf(diaryItemBean.getCommentsCount()));
        likes.setText(String.valueOf(diaryItemBean.getLikesCount()));
        stars.setText(String.valueOf(diaryItemBean.getStarsCount()));

        //判断情感，修改图片
        int emotionResId = R.drawable.neutral;
        switch(diaryItemBean.getEmotion()){
            case "Angry": emotionResId = R.drawable.angry;break;
            case "生气": emotionResId = R.drawable.angry;break;
            case "Fear": emotionResId = R.drawable.fear;break;
            case "恐惧": emotionResId = R.drawable.fear;break;
            case "Sad": emotionResId = R.drawable.sad;break;
            case "难过": emotionResId = R.drawable.sad;break;
            case "Happy":emotionResId = R.drawable.happy;break;
            case "开心": emotionResId = R.drawable.happy;break;
            case "Gross": emotionResId = R.drawable.gross;break;
            case "恶心": emotionResId = R.drawable.gross;break;
            case "Surprised":emotionResId = R.drawable.surprised;break;
            case "惊喜": emotionResId = R.drawable.surprised;break;
            default: emotionResId = R.drawable.neutral;
        }

        //设置图片
        emoji.setImageResource(emotionResId);

        //返回已经填充完成的列表控件
        return view;
    }
}
