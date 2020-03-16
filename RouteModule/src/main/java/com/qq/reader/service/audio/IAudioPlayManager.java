package com.qq.reader.service.audio;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.qq.reader.entity.audio.player.core.SongInfo;
import com.qq.reader.ARouterUtils;

/**
 * Create by jlchang
 *
 * @date 2019/7/17
 * 听书播放控制，为其他模块提供服务，当听书module以插件形式出现时需要使用router方式，因为此时app没有依赖听书moudle
 */
public abstract class IAudioPlayManager implements IProvider {

    private static volatile IAudioPlayManager sInstance;

    @Nullable
    public static IAudioPlayManager getInstance() {
        if (sInstance == null) {
            synchronized (IAudioPlayManager.class) {
                if (sInstance == null) {
                    sInstance = ARouterUtils.getAudioPlayService();
                }
            }
        }
        return sInstance;
    }

    public abstract boolean hasInitialized();
    public abstract int getPlayState();
    public abstract void pausePlay();
    public abstract SongInfo getCurrentSongInfo();
    public abstract void exitPlay();
    public abstract void exit();
    public abstract void becomingNoisy();

    public abstract long getTotalTime();
    public abstract long getCurrentTime();
    public abstract void prevChapter();
    public abstract void nextChapter();
    public abstract void startPlay();
    public abstract int getCurrentPlayStatus();
    public abstract void seek(long position);
}