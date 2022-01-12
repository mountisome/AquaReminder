package com.mountisome.aquareminder.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.mountisome.aquareminder.R;

public class ClockActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clock);
        mediaPlayer = MediaPlayer.create(this, R.raw.bongo);
        mediaPlayer.start();
        // 创建一个闹钟提醒的对话框，点击确定关闭铃声与页面
        new AlertDialog.Builder(ClockActivity.this).setTitle("闹钟")
                .setMessage("喝水时间到啦，记得喝水哦~").setPositiveButton("关闭闹铃",
                new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaPlayer.stop();
                ClockActivity.this.finish();
            }
        }).show();
    }

}