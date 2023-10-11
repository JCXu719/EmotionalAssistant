package com.hfut.edu.emotionalassistant.ui.assistant;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.common.util.concurrent.ListenableFuture;
import com.hfut.edu.emotionalassistant.R;

import org.pytorch.IValue;
import org.pytorch.LiteModuleLoader;
import org.pytorch.Module;
import org.pytorch.Tensor;
import org.pytorch.torchvision.TensorImageUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssistantSentimentAnalysis extends AppCompatActivity implements View.OnClickListener{

    Button btn_text, btn_image, btn_audio, btn_video;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assistant_sentimentanalysis);
    }

    @Override
    protected void onStart() {
        super.onStart();
        btn_text = findViewById(R.id.assistant_sentimentanalysis_textClassificationBtn);
        btn_image = findViewById(R.id.assistant_sentimentanalysis_imageClassificationBtn);
        btn_audio = findViewById(R.id.assistant_sentimentanalysis_audioClassificationBtn);
        btn_video = findViewById(R.id.assistant_sentimentanalysis_videoClassificationBtn);

        btn_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.assistant_sentimentanalysis_textClassificationBtn)
        {
            Intent intent = new Intent(this,AssistantTextClassification.class);
            startActivity(intent);
        }
    }
}
