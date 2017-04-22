package com.lifeistech.android.clap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.widget.CursorTreeAdapter;

/**
 * Created by kouhe on 2017/04/21.
 */

public class Clap {
    private SoundPool soundPool;
    private  int soundID;

    public Clap(Context context){
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);

        soundID = soundPool.load(context,R.raw.clap,1);
    }

    public void  play() {
        soundPool.play(soundID,1.0f,1.0f,0,0,1.0f);
    }


    public void repeatPlay(int repeat) {
        int count = 0;

        while(count < repeat) {
            play();

            count++;

            try {
                Thread.sleep(500);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}
