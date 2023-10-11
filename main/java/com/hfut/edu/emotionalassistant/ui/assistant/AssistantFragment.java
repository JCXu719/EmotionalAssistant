package com.hfut.edu.emotionalassistant.ui.assistant;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.hfut.edu.emotionalassistant.R;
import com.hfut.edu.emotionalassistant.databinding.FragmentAssistantBinding;
import com.hfut.edu.emotionalassistant.ui.statistics.EditEmotionDiaryPage;

public class AssistantFragment extends Fragment implements View.OnClickListener {

    private FragmentAssistantBinding binding;
    private Button btn_captureTalk,btn_writeEmotionDiary,btn_switchScenes,btn_moreFunctions;
    private ImageView iv_assistant;
    int[] background_img = new int[]{R.drawable.twt1,R.drawable.twt2,R.drawable.twt3,R.drawable.twt4};   //存放背景图片
    int currentBackgroundId = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAssistantBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //获取助手界面上的按钮控件
        btn_captureTalk = getActivity().findViewById(R.id.assistant_captureTalk_btn);
        btn_writeEmotionDiary = getActivity().findViewById(R.id.assistant_writingDiary_btn);
        btn_switchScenes = getActivity().findViewById(R.id.assistant_switchScenes_btn);
        btn_moreFunctions = getActivity().findViewById(R.id.assistant_moreFunctions_btn);
        iv_assistant = getActivity().findViewById(R.id.assistant_imgView);

        //对按钮设置监听器
        btn_captureTalk.setOnClickListener(this);
        btn_writeEmotionDiary.setOnClickListener(this);
        btn_switchScenes.setOnClickListener(this);
        btn_moreFunctions.setOnClickListener(this);
        iv_assistant.setOnClickListener(this);
    }

    //点击不同按钮，跳转到不同的Activity中
    @Override
    public void onClick(View view) {
        //情感分析
        if(view.getId() == R.id.assistant_captureTalk_btn){
            Intent intent = new Intent(getActivity(),AssistantSentimentAnalysis.class);
            startActivity(intent);
        }

        //撰写日记
        if(view.getId() == R.id.assistant_writingDiary_btn){
            Intent intent = new Intent(getActivity(), EditEmotionDiaryPage.class);
            startActivity(intent);
        }

        //切换背景
        if(view.getId() == R.id.assistant_switchScenes_btn){
            View currentScreenView = getActivity().findViewById(R.id.container);
            currentScreenView.setBackground(ContextCompat.getDrawable(getContext(),background_img[currentBackgroundId]));
            currentBackgroundId = (currentBackgroundId + 1) % background_img.length;
        }

        //更多功能
        if(view.getId() == R.id.assistant_moreFunctions_btn){
            Intent intent = new Intent(getActivity(),AssistantMoreFunctions.class);
            startActivity(intent);
        }

        //点击助手，弹出信息
        if(view.getId() == R.id.assistant_imgView){
            String tmp_message = "你好";
            showChatToast(tmp_message);
        }
    }

    //显示聊天框
    public void showChatToast(String message){
        //自定义Toast
        Toast toast = new Toast(getActivity());

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.assistant_chattoast,null);

        TextView chatTv = view.findViewById(R.id.assistant_chatToast_tv);
        chatTv.setText(message);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,-550);
        toast.setView(view);
        toast.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}