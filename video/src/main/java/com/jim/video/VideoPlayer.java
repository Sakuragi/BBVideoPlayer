package com.jim.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.Map;

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

    private final String TAG = this.getClass().getSimpleName();

    private ViewGroup container;
    private MediaPlayer mMediaPlayer;
    private String uri;
    private int currentPlayState;
    private SurfaceView mSurfaceView;
    private SurfaceHolder mSurfaceHolder;
    private Context mContext;
    private MediaController mMediaController;

    private SurfaceHolder.Callback mShCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            Log.d(TAG,"surfaceCreated");
            mSurfaceHolder = holder;
            start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        mContext = context;
    }

    private void init(Context context) {
        mSurfaceView = new SurfaceView(context);
//        mSurfaceHolder=mSurfaceView.getHolder();
        mSurfaceView.getHolder().addCallback(mShCallBack);
//        container = new FrameLayout(context);
        LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
//        container.setBackgroundColor(Color.BLACK);
        this.addView(mSurfaceView, params);
        currentPlayState = STATE_IDLE;
    }

    @Override
    public void start() {
        if (isCanStart() && mSurfaceHolder != null) {
            mMediaPlayer.start();
            Log.d(TAG, "start()");
        }
    }

    private boolean isCanStart(){
        return  currentPlayState != STATE_IDLE&&
                currentPlayState != STATE_PREPARING &&
                currentPlayState != STATE_ERROR;
    }

    public void setDataSource(String uri, Map<String, String> headers) {
        this.uri = uri;
        initMediaPlayer();
    }

    private void initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnSeekCompleteListener(this);
        mMediaPlayer.setDisplay(mSurfaceHolder);
        try {
            if (TextUtils.isEmpty(uri)) {
                throw new IllegalArgumentException("leak of uri");
            }
            mMediaPlayer.setDataSource(mContext, Uri.parse(uri), null);
            mMediaPlayer.prepareAsync();
            currentPlayState=STATE_PREPARING;
        } catch (Exception e) {
            Log.e(TAG, e.toString());
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

    @Override
    public void next() {

    }

    @Override
    public void previous() {

    }

    @Override
    public void setController(MediaController mediaController) {
        this.mMediaController = mediaController;
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
        currentPlayState=STATE_PREPARED;
        if (mMediaPlayer != null && isCanStart()) {
            Log.d(TAG, "onPrepared");
            start();
            currentPlayState = STATE_PLAYING;
        }
    }

    //    当使用seekTo()设置播放位置的时候回调
    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
