package com.jim.videoplayerdemo.player;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.jim.videoplayerdemo.R;

/**
 * Created by Jim on 2018/4/13 0013.
 */

public class PlayerTestAcitivity extends AppCompatActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton=findViewById(R.id.btn);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AudioPlayer player=new AudioPlayer();
                player.setDataSource("http://mp3-cdn.luoo.net/low/luoo/radio889/01.mp3");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        player.play();
                    }
                },5000);
            }
        });
    }

}
