package com.jim.video;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Jim on 2018/3/6 0006.
 */

public class VideoPlayer extends FrameLayout implements IVideoPlayer,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener {

    private static final int STATE_ERROR = -1; //播放器错误
    private static final int STATE_IDLE = 0;    //空闲
    private static final int STATE_PREPARING = 1; //初始化中
    private static final int STATE_PREPARED = 2; //初始化完成
    private static final int STATE_PLAYING = 3; //播放
    private static final int STATE_PAUSED = 4;//暂停
    private static final int STATE_RESUME = 5;//暂停->播放
    public static final int STATE_COMPLETED = 6; //完成
    private static final int STATE_STOP = 7;//停止
    private static final int STATE_SUSPEND_UNSUPPORTED = 8;

    private final String TAG=this.getClass().getSimpleName();

    private ViewGroup container;
    private SurfaceView mSurfaceView;
    private MediaPlayer mMediaPlayer;
    private String url;
    private int currentPlayState;

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        container = new FrameLayout(context);
        container.setBackgroundColor(Color.BLACK);
        this.addView(container);
    }

    @Override
    public void start() {
        initMediaPlayer();
    }

    public void setDataSource(String url) {
        this.url = url;
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        try {
            if (TextUtils.isEmpty(url)){
                throw new IllegalArgumentException("leak of url");
            }
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(TAG,e.toString());
            return;
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void Resume() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void destroy() {

    }

    //流媒体播放完成时回调
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    //    当播放中发生错误的时候回调
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    //    当装载流媒体完毕的时候回调
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mMediaPlayer != null &&currentPlayState==0){
            mMediaPlayer.start();
            currentPlayState=STATE_PLAYING;
        }
    }

    //    当使用seekTo()设置播放位置的时候回调
    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
