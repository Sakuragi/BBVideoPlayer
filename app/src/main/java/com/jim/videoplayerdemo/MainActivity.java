package com.jim.videoplayerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jim.video.VideoPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        VideoPlayer player=findViewById(R.id.video_player);
        player.setDataSource("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4",null);
//        player.start();
    }
}
