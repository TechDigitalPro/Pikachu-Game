package com.onet.onet.utils;

import android.content.Context;
import android.media.MediaPlayer;
import com.onet.onet.R;

public class MusicBackground
{
    public MediaPlayer mediaPlayer;
    
    public void loadMusic(final Context context) {

        (this.mediaPlayer = MediaPlayer.create(context, R.raw.musicbackground)).setVolume(0.5f, 0.5f);
        this.mediaPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener)new MediaPlayer.OnCompletionListener() {
            public void onCompletion(final MediaPlayer mediaPlayer) {
                MusicBackground.this.play();
            }
        });
    }
    
    public void pause() {
        if (this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.pause();
        }
    }
    
    public void play() {
        this.mediaPlayer.seekTo(0);
        this.mediaPlayer.start();
    }
    
    public void release() {
        this.mediaPlayer.release();
    }
    
    public void resume() {
        if (!this.mediaPlayer.isPlaying()) {
            this.mediaPlayer.start();
        }
    }
}