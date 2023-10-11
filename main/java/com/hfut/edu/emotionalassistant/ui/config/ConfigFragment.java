package com.hfut.edu.emotionalassistant.ui.config;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import android.os.IBinder;
import android.widget.Toast;

import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.services.MusicService;

//设置界面，暂时不管
public class ConfigFragment extends PreferenceFragmentCompat {

    SwitchPreference bgm_switch;

    //ServiceConnection用于连接Activity与Service
    private ServiceConnection configServiceConnection = new ServiceConnection() {

        //获取播放曲目信息
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.musicBinder musicBinder = (MusicService.musicBinder) iBinder;
            MusicService musicService = musicBinder.getMusicService();
            Toast.makeText(getActivity(),"当前播放曲目：" + musicService.getMusicName(),Toast.LENGTH_SHORT).show();
        }

        //一般不调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
        setPreferencesFromResource(R.xml.preferences,rootKey);

        //修改背景音乐
        bgm_switch = findPreference("preference_bgm");
        bgm_switch.setChecked(false);

        bgm_switch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                boolean isClicked = (boolean)newValue;
                if(isClicked){
                    //intent初始化
                    Intent intent = new Intent(getActivity(),MusicService.class);
                    intent.putExtra("musicService","afterthestorm_tonyanderson");

                    getActivity().bindService(intent,configServiceConnection, Context.BIND_AUTO_CREATE);
                }
                else {
                    if (configServiceConnection != null) {
                        //断开服务
                        getActivity().unbindService(configServiceConnection);
                        Toast.makeText(getActivity(), "背景音乐关闭", Toast.LENGTH_SHORT).show();
                    }
                }
                return true;
            }
        });
    }
}