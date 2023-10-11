package com.hfut.edu.emotionalassistant.data.bean;

public class DiaryItemBean {

    private String date;    //日期
    private String weather;     //天气
    private String emotion;     //情感
    private int emotionResId;   //情感对应的图片的下标
    private String content;     //日记正文
    private int likesCount;     //点赞数
    private int starsCount;     //收藏数
    private int commentsCount;  //评论数

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public int getEmotionResId() {
        return emotionResId;
    }

    public void setEmotionResId(int emotionResId) {
        this.emotionResId = emotionResId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public int getStarsCount() {
        return starsCount;
    }

    public void setStarsCount(int starsCount) {
        this.starsCount = starsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public String toString() {
        return "DiaryItemBean{" +
                "date='" + date + '\'' +
                ", weather='" + weather + '\'' +
                ", emotion='" + emotion + '\'' +
                ", emotionResId=" + emotionResId +
                ", content='" + content + '\'' +
                ", likesCount=" + likesCount +
                ", starsCount=" + starsCount +
                ", commentsCount=" + commentsCount +
                '}';
    }
}
