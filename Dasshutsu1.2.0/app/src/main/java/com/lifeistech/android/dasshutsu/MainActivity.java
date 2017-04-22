package com.lifeistech.android.dasshutsu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private  boolean hasKey;
    private boolean isOpened;
    private Button buttonKey;
    private Button buttonDoor;


    //アプリが起動した時に呼ばれるメソッド
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hasKey = false;
        isOpened = false;

        buttonDoor = (Button) findViewById(R.id.buttonDoor);
        buttonKey = (Button) findViewById(R.id.buttonKey);
    }

    public void  clickKey(View v) {
        Toast.makeText(this,"鍵を手に入れたよ！",Toast.LENGTH_SHORT).show();
        hasKey = true;
        buttonKey.setVisibility(View.INVISIBLE);
    }

    public  void  clickDoor(View v) {
        if(isOpened) {
            Intent intent = new Intent(this, Stage2Activity.class);
            startActivity(intent);

        }else {
            if (hasKey) {
                buttonDoor.setBackgroundResource(R.drawable.door2);
                isOpened = true;
                Toast.makeText(this,"ドアが開いたよ",Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(this,"鍵がないよ！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}