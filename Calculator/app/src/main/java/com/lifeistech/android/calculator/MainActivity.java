package com.lifeistech.android.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int number1;
    int number2;
    int answer;
    int ope;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        number1 = 0;
        number2 = 0;
        answer = 0;
        ope = 0;

        textView = (TextView) findViewById(R.id.textView);

        textView.setText("0");
    }

    public void  click(View v) {

        String tag = (String) v.getTag();

        switch (tag) {
            case "0":
            number1 = 0;
            break;
            case "1":
                number1 = number1 * 10 + 1;
                break;
            case "2":
                number1 = number1 * 10 +  2;
                break;
            case "3":
                number1 = number1 * 10 +  3;
                break;
            case "4":
                number1 = number1 * 10 +  4;
                break;
            case "5":
                number1 = number1 * 10 +  5;
                break;
            case "6":
                number1 = number1 * 10 + 6;
                break;
            case "7":
                number1 = number1 * 10 + 7;
                break;
            case "8":
                number1 = number1 * 10 + 8;
                break;
            case "9":
                number1 = number1 * 10 + 9;
                break;

            default:
            break;

        }

        textView.setText(String.valueOf(number1));
    }

    public void  plus(View v) {
        number2 = number1;
        number1 = 0;
        ope = 1;
    }

    public void  minus(View v) {
        number2 = number1;
        number1 = 0;
        ope = 2;
    }

    public void  multiply(View v) {
        number2 = number1;
        number1 = 0;
        ope = 3;
    }

    public void  divide(View v) {
        number2 = number1;
        number1 = 0;
        ope = 4;
    }

    public  void  equal(View v) {
        if(ope == 1) {
            answer = number2 + number1;
        }
        if (ope == 2) {
            answer = number2 - number1;
        }
        if (ope == 3) {
            answer = number2 * number1;
        }
        if(ope == 4) {
            answer = number2 / number1;
        }
        textView.setText(String.valueOf(answer));
        number1 = 0;
    }

    public void clear(View v) {
        number1 = 0;
        number2 = 0;
        answer = 0;
        ope = 0;
        textView.setText("0");
    }
}
