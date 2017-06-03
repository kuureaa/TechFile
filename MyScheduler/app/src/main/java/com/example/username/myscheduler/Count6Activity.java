package com.example.username.myscheduler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Count6Activity extends AppCompatActivity {

    Canvas mCanvas;
    TextView mTimerText;
    MyCountDownTimer mTimer;
    FloatingActionButton mFab;
    Bitmap mBitmap;
    ImageView mImageView;
    AlertDialog.Builder mAlertBuilder;
    AlertDialog.Builder mAlertBuilder1;
    int width, height;
    InputStream in;


    public class MyCountDownTimer extends CountDownTimer {
        public boolean isRunning = false;

        public MyCountDownTimer(long millisInFuture,
                                long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long minute = millisUntilFinished / 1000 / 60;
            long second = millisUntilFinished / 1000 % 60;
            mTimerText.setText(String.format("%2$2d", minute, second));
        }

        @Override
        public void onFinish() {
            mAlertBuilder1.setTitle("すっごーい！");
            mAlertBuilder1.setNegativeButton("閉じる",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertBuilder1.show();
            mTimerText.setText("6");
            mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count6);

        mImageView = (ImageView) findViewById(R.id.imageView);

        mTimerText = (TextView) findViewById(R.id.timer_text);
        mTimerText.setText("6");
        mTimer = new MyCountDownTimer(7 * 1000, 100);

        mAlertBuilder = new AlertDialog.Builder(this);
        mAlertBuilder1 = new AlertDialog.Builder(this);


        mFab = (FloatingActionButton) findViewById(R.id.play_stop);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mAlertBuilder.setTitle("６秒間だけ我慢して！");
                mAlertBuilder.setNegativeButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (mTimer.isRunning) {
                                    mTimer.isRunning = false;
                                    mTimer.cancel();
                                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                                } else {
                                    mTimer.isRunning = true;
                                    mTimer.start();
                                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);
                                }
                                try {
                                    in = openFileInput("filename01.jpg");
                                    mBitmap = BitmapFactory.decodeStream(in);
                                    mImageView.setImageBitmap(mBitmap);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                mAlertBuilder.setPositiveButton("無理",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                mAlertBuilder.show();

//                if (mTimer.isRunning) {
//                    mTimer.isRunning = false;
//                    mTimer.cancel();
//                    mFab.setImageResource(R.drawable.ic_play_arrow_black_24dp);
//                } else {
//                    mTimer.isRunning = true;
//                    mTimer.start();
//                    mFab.setImageResource(R.drawable.ic_stop_black_24dp);
//                }
//                try {
//                    in = openFileInput("filename01.jpg");
//                    mBitmap = BitmapFactory.decodeStream(in);
//                    mImageView.setImageBitmap(mBitmap);
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });


//        try {
//            in = openFileInput("filename01.jpg");
//            mBitmap = BitmapFactory.decodeStream(in);
//            mImageView.setImageBitmap(mBitmap);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


        mImageView.setImageBitmap(mBitmap);
    }

    public void back(View v) {
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (mBitmap == null) {

            width = mImageView.getWidth();
            height = mImageView.getHeight();

            mBitmap = mBitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open:
                Intent intent = new Intent();
                intent.setType("image/");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 0);
                break;

//            case R.id.menu_new:
//                try {
//                in = openFileInput("filename01.jpg");
//                mBitmap = BitmapFactory.decodeStream(in);
//                mImageView.setImageBitmap(mBitmap);
//
//                } catch (IOException e) {
//                e.printStackTrace();
//                }
//                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();


        try {
            mBitmap=loadImage(uri);
            //ローカルファイルへ保存
            final FileOutputStream out = openFileOutput("filename01.jpg",Context.MODE_PRIVATE);
            mBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            in = openFileInput("filename01.jpg");
            mBitmap = BitmapFactory.decodeStream(in);
            mImageView.setImageBitmap(mBitmap);

        } catch (IOException e) {
            e.printStackTrace();
        }


//        try{
//        //ローカルファイルへ保存
//            final FileOutputStream out = openFileOutput("filename01.jpg",Context.MODE_PRIVATE);
//            mBitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
//            out.close();
//        }catch(IOException e){
//            e.printStackTrace();
//        }


        mImageView.setImageBitmap(mBitmap);
    }

    Bitmap loadImage(Uri uri)throws IOException{
        boolean landscape = false;
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        InputStream is = getContentResolver().openInputStream(uri);
        BitmapFactory.decodeStream(is,null,options);
        is.close();
        int oh=options.outHeight;
        int ow=options.outWidth;
        if(ow>oh){
            landscape=true;
            oh=options.outWidth;
            ow=options.outHeight;
        }
        options.inJustDecodeBounds=false;
        options.inSampleSize=Math.max(ow/width,oh/height);
        InputStream is2 = getContentResolver().openInputStream(uri);
        bm = BitmapFactory.decodeStream(is2,null,options);
        is2.close();
        if (landscape) {
            Matrix matrix = new Matrix();
            matrix.setRotate(90.0f);
            bm=Bitmap.createBitmap(bm,0,0,bm.getWidth(),bm.getHeight(),matrix,false);
        }
        bm=Bitmap.createScaledBitmap(bm,width,(int)(width*((double)oh/(double)ow)),false);
        Bitmap offBitmap=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas offCanvas=new Canvas(offBitmap);
        offCanvas.drawBitmap(bm,0,(height - bm.getHeight())/2,null);
        bm=offBitmap;
        return bm;
    }
}
