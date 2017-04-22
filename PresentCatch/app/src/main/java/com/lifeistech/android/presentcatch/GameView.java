package com.lifeistech.android.presentcatch;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    static final long FPS = 30;
    static final long FRAME_TIME = 1000 / FPS;

    SurfaceHolder surfaceHolder;
    Thread thread;

    int screenWidth, screenHeight;

    int score = 0;
    int life = 10;

    Present present;
    Player player;

    Bitmap playerImage;
    Bitmap presentImage;

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
        Resources resources = context.getResources();

        presentImage = BitmapFactory.decodeResource(resources, R.drawable.img_present0);
        playerImage = BitmapFactory.decodeResource(resources, R.drawable.img_player);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        surfaceHolder = holder;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        screenWidth = width;
        screenHeight = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        thread = null;
    }

    @Override
    public void run() {

        player = new Player();

        present = new Present();

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setFakeBoldText(true);
        textPaint.setTextSize(100);

        while (thread != null) {
            Canvas canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);

            canvas.drawBitmap(playerImage, player.x, player.y, null);
            canvas.drawBitmap(presentImage, present.x, present.y, null);

            canvas.drawText("Score :" + score, 50, 150, textPaint);
            canvas.drawText("LIFE : " + life, 50, 300, textPaint);


            if (player.isEnter(present)) {
                present.reset();
                score += 10;

            } else if (present.y > screenHeight) {
                present.reset();
                life--;
            } else {
                present.update();
            }

            if (life <= 0) {
                canvas.drawText("Game Over", screenWidth / 3, screenHeight / 2, textPaint);
                surfaceHolder.unlockCanvasAndPost(canvas);
                break;
            }

            surfaceHolder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(FRAME_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class Present{
        private static final int WIDTH = 100;
        private static final int HEIGHT = 100;

        float x,y;

        public Present() {
            Random random = new Random();
            x = random.nextInt(screenWidth - WIDTH);
            y = 0;
        }

        public  void  update() {
            y += 15.0f;
        }

        public  void reset() {
            Random random = new Random();
            x = random.nextInt(screenWidth - WIDTH);
            y = 0;
        }
    }

    class Player {
        final int WIDTH = 200;
        final int HEIGHT = 200;

        float x, y;

        public  Player() {
            x = 0;
            y = screenHeight -HEIGHT;
        }

      public void  move(float diffX) {
          this.x += diffX;
          this.x = Math.max(0, x);
          this.x = Math.min(screenWidth - WIDTH, x);

      }

      public boolean isEnter(Present present) {
          if   (present.x + Present.WIDTH > x && present.x < x + WIDTH &&
               present.y + Present.HEIGHT > y && present.y < y + HEIGHT) {
              return  true;
          }
          return false;
      }

    }



}
