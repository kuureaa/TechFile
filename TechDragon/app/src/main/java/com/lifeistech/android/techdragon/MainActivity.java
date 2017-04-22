package com.lifeistech.android.techdragon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
    private  int power;
    private TextView powerTextView;
    private ImageView dragonImageView;
    private TextView damageTextView;
    private int hp;
    private ProgressBar hpBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hpBar = (ProgressBar) findViewById(R.id.hpBar);
        powerTextView = (TextView) findViewById(R.id.powerTextView);
        dragonImageView = (ImageView) findViewById(R.id.dragonImageView);
        damageTextView = (TextView) findViewById(R.id.damageTextView);
        hp = 100;
    }


    public void powerUp(View v) {
        power= power + 3;

        switch (power) {
            case 3:
                powerTextView.setTextColor(Color.GREEN);
                break;
            case 30:
                powerTextView.setTextColor(Color.BLUE);
                break;
            case 51:
                powerTextView.setTextColor(Color.RED);
        }


        powerTextView.setText(String.valueOf(power));

        /* 問題2 数字が10以上30未満ならば文字の色が緑に、30以上50未満ならば青、
		 * 50以上ならば赤になるコードを、if文を使って問題1の関数の中に加えてみよう。
		 * 問題3 問題2の条件分岐コードをswitch文で書き換えてみよう。
		 */

    }


    public void attack(View v) {
        hp = hp - power;
        hpBar.setProgress(hp);
        damageAnimation();
        if (hp <= 0) {
            clearAnimation();
        }
    }


    public void retry(View v) {
        hp = 100;
        power = 0;
        dragonImageView.setVisibility(View.VISIBLE);
        powerTextView.setTextColor(Color.BLACK);
    }


    public void info(View v) {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);

    }


    public void damageAnimation() {
        if (power > 0) {
            damageTextView.setText(String.valueOf(power));
        } else {
            damageTextView.setText("miss");
        }
        AttackAnimation attackAnim = new AttackAnimation(
                this.getApplicationContext(),
                dragonImageView, damageTextView);
        dragonImageView.startAnimation(attackAnim);
    }



	public void clearAnimation() {
		AlphaAnimation alphaAnim = new AlphaAnimation(1.0f, 0.0f) ;
		alphaAnim.setDuration(1500) ;
		alphaAnim.setStartOffset(1500) ;
		alphaAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation anim) { }

			@Override
			public void onAnimationRepeat(Animation anim) { }

			@Override
			public void onAnimationEnd(Animation anim) {
				dragonImageView.setVisibility(View.INVISIBLE) ;
			}			
		} ) ;
		
		dragonImageView.startAnimation(alphaAnim) ;
	}


}
