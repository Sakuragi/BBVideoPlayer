package com.jim.videoplayerdemo.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jim.videoplayerdemo.R;
import com.jim.videoplayerdemo.player1.MediaProxyServer;

import java.io.File;
import java.io.IOException;

/**
 * Created by Jim on 2018/4/13 0013.
 */

public class PlayerTestAcitivity extends AppCompatActivity {

    private Button startBtn;
    private Button pauseBtn;
    private Button stopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_test);
        startBtn=findViewById(R.id.btn_start);
        pauseBtn=findViewById(R.id.btn_pause);
        stopBtn=findViewById(R.id.btn_stop);
        final AudioPlayer player=new AudioPlayer();
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                player.setDataSource("http://mp3-cdn.luoo.net/low/luoo/radio889/01.mp3");
//                MediaProxyServer proxy = new MediaProxyServer();
//                final String proxyUrl=proxy.getProxyHostUrl("http://mp3-cdn.luoo.net/low/luoo/radio889/01.mp3");
//                Log.d("TAG","proxy url: "+proxyUrl);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        player.setDataSource("http://mp3-cdn.luoo.net/low/luoo/radio889/01.mp3");
                    }
                },5000);
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.pause();
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
            }
        });
    }


    public static File getVideoCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "video-cache");
    }

}
