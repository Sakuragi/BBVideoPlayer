package com.jim.video;

import android.media.session.MediaController;

import java.util.Map;

/**
 * Created by Jim on 2018/3/6 0006.
 */

public interface IVideoPlayer {

    void start();
    void pause();
    void Resume();
    void stop();
    void destroy();
    void next();
    void previous();
    void setController(MediaController mediaController);
}
