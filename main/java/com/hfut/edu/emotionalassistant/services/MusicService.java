package com.hfut.edu.emotionalassistant.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.hfut.edu.emotionalassistant.R;

//音乐服务，这个文件暂时不管
public class MusicService extends Service {

    //媒体播放器
    MediaPlayer mp_background = null;

    //用于信息交换的IBinder
    IBinder iBinder = null;

    //自定义Binder类
    public class musicBinder extends Binder {
        //用于返回Service
        public MusicService getMusicService(){return MusicService.this;}
    }

    //无参构造
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //连接服务后，判断
        if(intent.getExtras().get("musicService").equals("afterthestorm_tonyanderson")){
            //播放背景音乐
            mp_background = MediaPlayer.create(this, R.raw.afterthestorm_tonyanderson);
            mp_background.start();
        }
        iBinder = new musicBinder();
        return iBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //服务销毁时释放媒体播放器
        mp_background.release();
    }

    //返回
    public String getMusicName(){
        return "After the Storm --Tony Anderson";
    }
}