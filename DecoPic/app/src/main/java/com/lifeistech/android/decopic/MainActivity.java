package com.lifeistech.android.decopic;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends ActionBarActivity {

    int picnum = 0;
    FrameLayout frame;

    private ImageView picture;
    private static final int REQUEST_ORIGIN = 0;

    String stampName;
    private ImageView stamp[] = new ImageView[4];

    float x;
    float y;

    boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        picture = (ImageView) findViewById(R.id.picture);

        frame = (FrameLayout) findViewById(R.id.framelayout);

        for (int i = 0; i < 4; i++) {
            stamp[i] = (ImageView) findViewById(getResources().getIdentifier("imageView" + i, "id", getPackageName()));
        }

        stamp[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                stampName = "Star";
                ClipData data = ClipData.newPlainText("Stamp0", "Drag");
                view.startDrag(data, new View.DragShadowBuilder(view), (Object) view, 0);
                return false;
            }
        });

        stamp[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                stampName = "Heart";
                ClipData data = ClipData.newPlainText("Stamp1", "Drag");
                view.startDrag(data, new View.DragShadowBuilder(view), (Object) view, 0);
                return false;
            }
        });

        stamp[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                stampName = "Ribon";
                ClipData data = ClipData.newPlainText("Stamp2", "Drag");
                view.startDrag(data, new View.DragShadowBuilder(view), (Object) view, 0);
                return false;
            }
        });

        stamp[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                stampName = "Note";
                ClipData data = ClipData.newPlainText("Stamp3", "Drag");
                view.startDrag(data, new View.DragShadowBuilder(view), (Object) view, 0);
                return false;
            }
        });

        picture.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_EXITED:
                        flag = false;
                        break;
                    case DragEvent.ACTION_DROP:
                        x = dragEvent.getX();
                        y = dragEvent.getY();
                        break;
                    case DragEvent.ACTION_DRAG_ENTERED:
                        flag = true;
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        for (int i = 0; i < 4; i++) {
            stamp[i].setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent dragEvent) {
                    if (dragEvent.getAction() == DragEvent.ACTION_DRAG_ENDED) {
                        if (flag) {
                            switch (stampName) {
                                case "Star":
                                    addView(0);
                                    break;
                                case "Heart":
                                    addView(1);
                                    break;
                                case "Ribon":
                                    addView(2);
                                    break;
                                case "Note":
                                    addView(3);
                                    break;
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    public void addView(int stampNum) {
        ImageView image = new ImageView(getApplicationContext());
        image.setImageResource(getResources().getIdentifier("stamp" + stampNum, "drawable", getPackageName()));
        frame.addView(image, stamp[stampNum].getWidth(), stamp[stampNum].getHeight());
        image.setTranslationX(x - (stamp[stampNum].getWidth()) / 2);
        image.setTranslationY(y - (stamp[stampNum].getHeight()) / 2);
    }


    public  void select(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,REQUEST_ORIGIN);
    }

    @Override
    protected void  onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            try{
                InputStream in = getContentResolver().openInputStream(data.getData());
                Bitmap img = BitmapFactory.decodeStream(in);
                picture.setImageBitmap(img);

                in.close();
            }catch (Exception e) {

            }
        }
    }

    // 保存するメソッド
    public void save() throws Exception {
        try {
            frame.setDrawingCacheEnabled(true);
            Bitmap save_bmp = Bitmap.createBitmap(frame.getDrawingCache());
            String folderpath = Environment.getExternalStorageDirectory() + "/DecoPic/";
            File folder = new File(folderpath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            File file = new File(folderpath, "sample" + picnum + ".png");
            if (file.exists()) {
                for (; file.exists(); picnum++) {
                    file = new File(folderpath, "sample" + picnum + ".png");
                }
            }
            FileOutputStream outStream = new FileOutputStream(file);
            save_bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            outStream.close();
            Toast.makeText(
                    getApplicationContext(),
                    "Image saved",
                    Toast.LENGTH_SHORT).show();
            frame.setDrawingCacheEnabled(false);
            showFolder(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // イメージファイルが保存されたことを通知するメソッド
    private void showFolder(File path) throws Exception {
        try {
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = getApplicationContext()
                    .getContentResolver();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DATE_MODIFIED,
                    System.currentTimeMillis() / 1000);
            values.put(MediaStore.Images.Media.SIZE, path.length());
            values.put(MediaStore.Images.Media.TITLE, path.getName());
            values.put(MediaStore.Images.Media.DATA, path.getPath());
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            throw e;
        }
    }

    // メニューを作るメソッド
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }

    // メニューのボタンが押された時に呼ばれるメソッド
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            try {
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
