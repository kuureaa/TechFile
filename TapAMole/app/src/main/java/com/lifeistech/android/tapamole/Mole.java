package com.lifeistech.android.tapamole;

import android.widget.ImageView;

/**
 * Created by kouhe on 2017/04/22.
 */

public class Mole {
    int state;
    ImageView moleImage;

    android.os.Handler h;

    Runnable hide;

    public  Mole(ImageView imageView) {
        state = 0;
        moleImage = imageView;
        moleImage.setImageResource(R.drawable.mole1);

        h = new  android.os.Handler();
        hide = new Runnable() {
            @Override
            public void run() {
                state = 0;

                moleImage.setImageResource(R.drawable.mole1);
            }
        };
    }

    public void start() {
        if(state == 0) {
            state = 1;
            moleImage.setImageResource(R.drawable.mole2);

            h.postDelayed(hide,1000);
        }
    }

    public int tapMole() {
        if(state == 1) {
            state = 2;
            moleImage.setImageResource(R.drawable.mole3);

            h.removeCallbacks(hide);
            h.postDelayed(hide,1000);
            return 1;
        }
        return 0;
    }
}
