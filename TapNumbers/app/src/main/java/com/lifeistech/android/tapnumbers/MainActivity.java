package com.lifeistech.android.tapnumbers;

import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int[] hairetu;

    String mondai;

    int seikai;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        start();
    }

    public void start(){
        hairetu = new int[4];
        Random rand = new Random(); //オブジェクトの作成
        hairetu[0] = rand.nextInt(4) + 1;
        hairetu[1] = rand.nextInt(4) + 1;
        hairetu[2] = rand.nextInt(4) + 1;
        hairetu[3] = rand.nextInt(4) + 1;

        mondai = Integer.toString(hairetu[0])
                 +Integer.toString(hairetu[1])
                 +Integer.toString(hairetu[2])
                 +Integer.toString(hairetu[3]);
        textView.setText(mondai);
        seikai = 0;
    }

    public void  number1(View v) {
        if(hairetu[seikai] == 1) {
            mondai = mondai.substring(1);
            textView.setText(mondai);
            seikai = seikai + 1;

            if(seikai == 4) {
                start();
            }
        } else {

            Toast.makeText(this, "数字が違うよ！！", Toast.LENGTH_SHORT).show();
        }
    }

    public void  number2(View v) {
        if (hairetu[seikai] == 2) {
            mondai = mondai.substring(1);
            textView.setText(mondai);
            seikai = seikai + 1;

            if(seikai == 4) {
                start();
            }
        } else {

            Toast.makeText(this, "数字が違うよ！！", Toast.LENGTH_SHORT).show();
        }
    }

    public void  number3(View v) {
        if (hairetu[seikai] == 3) {
            mondai = mondai.substring(1);
            textView.setText(mondai);
            seikai = seikai + 1;

            if(seikai == 4) {
                start();
            }
        } else {

            Toast.makeText(this, "数字が違うよ！！", Toast.LENGTH_SHORT).show();
        }
    }

    public void number4(View v) {
        if (hairetu[seikai] == 4) {
            mondai = mondai.substring(1);
            textView.setText(mondai);
            seikai = seikai + 1;

            if(seikai == 4) {
                start();
            }
        } else {

            Toast.makeText(this, "数字が違うよ！！", Toast.LENGTH_SHORT).show();
        }
    }
}
