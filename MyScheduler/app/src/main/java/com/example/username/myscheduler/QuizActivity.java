package com.example.username.myscheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends ActionBarActivity {

    AlertDialog.Builder mAlertBuilder;

    // 問題を管理するリスト
    private ArrayList<Question> question_list = new ArrayList<>();
    // 描画更新用Handler
    private Handler handler;
    // 1問あたりの制限時間(sec)
    private int time = 10;
    // 問題数
    private int question_num;
    // 現在の問題
    private Question current_question = null;
    // 残り時間を表示するプログレスバー
    private ProgressBar progress;
    // 残りの制限時間(sec*10)
    private int rest_time;
    // 現在の問題番号
    private int current = 0;
    // 正解を選んだ数
    private int correct_num;

    // TODO [01] ここから
    private  TextView question;

    private  TextView status;

    private  ImageView image;

    private  Button button1;

    private  Button button2;

    private  Button button3;
    // TODO [01] ここまで

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        handler = new Handler();

        // TODO [02]ここから
        question = (TextView) findViewById(R.id.question_text);
        status = (TextView) findViewById(R.id.status);
        image = (ImageView) findViewById(R.id.question_image);
        progress = (ProgressBar) findViewById(R.id.progressBar);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        progress.setMax(time*100);

        makeQuestions();
        startQuestion();

        mAlertBuilder = new AlertDialog.Builder(this);
        // TODO [02] ここまで
    }

    // 問題を作成する
    private void makeQuestions() {
        // TODO [03] ここから
        Question q1 = new Question(R.drawable.n2, "憤懣の意味は？", "怒りが発散できない状態", "すぐにおさまる怒り", "機嫌がわるくなって泣いたり拗ねたりする");
        Question q2 = new Question(R.drawable.n2, "余憤の意味は？", "完全に消えず残っている怒り", "癇癪を起こして怒る", "他人の過ちをしかって非難する");
        Question q3 = new Question(R.drawable.n2, "義憤の意味は？", "人のルールから外れたこと、不公正な事柄に対する怒り", "とにかく腹立たしくてどうしようもない様", "不快で腹が立つ");
        Question q4 = new Question(R.drawable.n2, "息巻くの意味は？", "呼吸を激しくして怒る", "機嫌が悪いこと", "納得できず不満に思うこと");

        question_list.add(q1);
        question_list.add(q2);
        question_list.add(q3);
        question_list.add(q4);

        // TODO [03] ここまで
    }

    // 問題の表示を始める
    private void startQuestion() {
        question_num = question_list.size();
        nextQuestion();
    }

    private void nextQuestion() {
        if (current >= question_num) {
            current = -1;
            // 次の問題がもう無い時
            finish();
            return;
        }
        // TODO [04] ここから
//        status.setText(String.valueOf(current) + "問中"
//                + String.valueOf(correct_num) + "問正解"
//                + "残り" + String.valueOf(question_num - current) + "問");

        current_question = question_list.get(current);

        question.setText(current_question.question_text);
        image.setImageResource(current_question.image_id);

        String[] choices_text = current_question.getChoices();
        button1.setText(choices_text[0]);
        button2.setText(choices_text[1]);
        button3.setText(choices_text[2]);

        current = current + 1;

        startTimeLimitThread();
        // TODO [04] ここまで
    }

    // ボタンがタップされた時に呼ばれるイベントリスナー
    public void click(View v) {
        // TODO [05] ここから
        String buttonText = ((Button) v).getText().toString();

        if (buttonText.equals(current_question.answer)) {
            correct_num = correct_num + 1;
            mAlertBuilder.setTitle("せいかい！");
            mAlertBuilder.setNegativeButton("閉じる",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertBuilder.show();
        } else{
            mAlertBuilder.setTitle("ちがうよ！");
            mAlertBuilder.setNegativeButton("閉じる",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertBuilder.show();
        }

        nextQuestion();
        // TODO [05] ここまで
    }

    /**
     * 1問ごとの制限時間を管理するスレッドを起動する
     */
    private void startTimeLimitThread() {
        rest_time = time * 100;
        progress.setProgress(rest_time);
        // このThreadが担当する問題番号
        final int local_current = current;
        Thread t = new Thread() {
            @Override
            public void run() {
                while (rest_time >= 0) {
                    if (local_current != current) {
                        // すでにボタンをタップして次の問題に進んでいる
                        return;
                    }
                    try {
                        // 10ミリ秒待機
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            rest_time -= 1;
                            progress.setProgress(rest_time);
                        }
                    });
                }
                // まだ問題に解答していない場合
                if (local_current == current) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            nextQuestion();
                        }
                    });
                }
            }
        };
        t.start();
    }

    /**
     * 問題を管理するクラス
     */
    class Question {
        /**
         * 画面に表示する画像のリソースID
         */
        private final int image_id;
        /**
         * 問題文として表示する文字列
         */
        private final String question_text;
        /**
         * 正解の答え
         */
        private final String answer;
        /**
         * 不正解の答え①
         */
        private final String wrong_1;
        /**
         * 不正解の答え②
         */
        private final String wrong_2;

        public Question(int image_id, String question_text, String answer, String wrong_1, String wrong_2) {
            this.image_id = image_id;
            this.question_text = question_text;
            this.answer = answer;
            this.wrong_1 = wrong_1;
            this.wrong_2 = wrong_2;
        }

        /**
         * シャッフルした問題の選択肢を返すメソッド
         */
        public String[] getChoices() {
            // ボタンの位置をランダムにする
            String choices_tmp[] = {answer, wrong_1, wrong_2};
            // 配列からリストへ
            List<String> list = Arrays.asList(choices_tmp);
            // リストをシャッフル
            Collections.shuffle(list);
            // リストをStringの配列にする
            return list.toArray(new String[3]);
        }
    }
}

