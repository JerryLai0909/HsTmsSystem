/*
 * Copyright (c) 2018. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.infinity.jerry.yyd_tms.utils;

import android.content.Context;
import android.media.MediaPlayer;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * Created by jerry on 2017/8/24.
 */

public class ZMediaSoundHelper {

    private MediaPlayer mediaPlayer;
    private Context context;
    private IZMediaVoiceState currentIZ;
    private IZMediaVoiceState lastIZ;

    private static ZMediaSoundHelper helper = null;

    public void setCurrentIZ(IZMediaVoiceState currentIZ) {
        if (currentIZ == null) {
            this.currentIZ = currentIZ;
        } else {
            this.lastIZ = this.currentIZ;
            this.currentIZ = currentIZ;
        }
    }

    public static ZMediaSoundHelper getInstance(Context context) {
        if (helper == null) {
            helper = new ZMediaSoundHelper(context);
        }
        return helper;
    }

    private ZMediaSoundHelper(Context context) {
        mediaPlayer = new MediaPlayer();
        this.context = context;
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (currentIZ != null) {
                    currentIZ.onFinish();
                }
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                if (currentIZ != null) {
                    currentIZ.onPlaying();//开始播放
                }
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                mediaPlayer.reset();
                return false;
            }
        });
    }

    public void loadingVoice(FileDescriptor voiceUrl) {
        if (currentIZ != null) {
            currentIZ.onLoading();
        }
        if (lastIZ != null) {
            lastIZ.onStop();
            lastIZ = null;
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
        }
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(voiceUrl);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            mediaPlayer.reset();
        }
    }

    public void stopVoice() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            currentIZ.onStop();
        }
    }


    public interface IZMediaVoiceState {
        void onLoading();

        void onPlaying();//正在播放

        void onFinish();//播放完

        void onStop();//停止
    }
}
