package com.example.username.myscheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class ScheduleEditActivity extends AppCompatActivity {

    private Realm mRealm;
    EditText mTitleEdit;
    EditText mDetailEdit;
    Button mDelete;
    AlertDialog.Builder mAlertBuilder;
    AlertDialog.Builder mAlertBuilder1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_edit);
        mRealm = Realm.getDefaultInstance();
        mTitleEdit = (EditText) findViewById(R.id.titleEdit);
        mDetailEdit = (EditText) findViewById(R.id.detailEdit);
        mDelete = (Button) findViewById(R.id.delete);
        mAlertBuilder = new AlertDialog.Builder(this);
        mAlertBuilder1 = new AlertDialog.Builder(this);


        long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {
            RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
            Schedule schedule = results.first();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdf.format(schedule.getDate());
            mTitleEdit.setText(schedule.getTitle());
            mDetailEdit.setText(schedule.getDetail());
            mDelete.setVisibility(View.VISIBLE);
        } else {
            mDelete.setVisibility(View.INVISIBLE);
        }
    }

    public void onSaveTapped(View view) {

        Spinner item = (Spinner) findViewById(R.id.spinner);
        final String selected = (String) item.getSelectedItem();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dateParse = new Date();

        final Date date = dateParse;
        long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {

            final RealmResults<Schedule> results = mRealm.where(Schedule.class)
                    .equalTo("id", scheduleId).findAll();
//            mRealm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    Schedule schedule = results.first();
//                    schedule.setDate(date);
//                    schedule.setTitle(mTitleEdit.getText().toString());
//                    schedule.setDetail(selected);
//                }
//            });

            mAlertBuilder1.setTitle("内容を変更しますか？");
            mAlertBuilder1.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mRealm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Schedule schedule = results.first();
                                    schedule.setDate(date);
                                    schedule.setTitle(mTitleEdit.getText().toString() + "\n\n\n" + "対策：" + "\n\n" + mDetailEdit.getText().toString() + "\n");
                                    schedule.setDetail(selected);
                                }
                            });

                            startActivity(new Intent(ScheduleEditActivity.this,MainActivity.class));

                        }
                    });

            mAlertBuilder1.setPositiveButton("いいえ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertBuilder1.show();
            //startActivity(new Intent(ScheduleEditActivity.this,MainActivity.class));

//            Snackbar.make(findViewById(android.R.id.content),
//                    "アップデートしました", Snackbar.LENGTH_LONG)
//                    .setAction("戻る", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            finish();
//                        }
//                    })
//                    .setActionTextColor(Color.YELLOW)
//                    .show();
        } else {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Schedule.class).max("id");
                    long nextId = 0;
                    if (maxId != null) nextId = maxId.longValue() + 1;
                    Schedule schedule
                            = realm.createObject(Schedule.class, new Long(nextId));
                    schedule.setDate(date);
                    schedule.setTitle(mTitleEdit.getText().toString() + "\n\n\n" + "対策：" + "\n\n" + mDetailEdit.getText().toString() + "\n");
                    schedule.setDetail(selected);

                    //追記
                    startActivity(new Intent(ScheduleEditActivity.this,
                            ChartActivity.class)
                            .putExtra("schedule_id", schedule.getId()));

                }
            });
            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show();

            finish();
        }

    }


    public void onDeleteTapped(View view) {
        final long scheduleId = getIntent().getLongExtra("schedule_id", -1);
        if (scheduleId != -1) {

            mAlertBuilder.setTitle("本当に削除しますか？");
            mAlertBuilder.setNegativeButton("はい",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            mRealm.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    Schedule schedule = realm.where(Schedule.class)
                                            .equalTo("id", scheduleId).findFirst();
                                    schedule.deleteFromRealm();
                                }
                            });
                            startActivity(new Intent(ScheduleEditActivity.this,MainActivity.class));

                        }
                    });
            mAlertBuilder.setPositiveButton("いいえ",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            mAlertBuilder.show();

//            mRealm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm realm) {
//                    Schedule schedule = realm.where(Schedule.class)
//                            .equalTo("id", scheduleId).findFirst();
//                    schedule.deleteFromRealm();
//                }
//            });
//            Toast.makeText(this, "削除しました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
