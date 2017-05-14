package com.example.username.myscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;


public class ChartActivity extends AppCompatActivity {

    //追記
    private Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        //追記
        mRealm = Realm.getDefaultInstance();

        createBarChart();
        createBarChartData();
    }

    private void createBarChart() {
        BarChart barChart = (BarChart) findViewById(R.id.bar_chart);
        barChart.setDescription("アンガーグラフ");

        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(true);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(false);
        barChart.setEnabled(true);

        barChart.setTouchEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setDoubleTapToZoomEnabled(true);

        barChart.setHighlightEnabled(true);
        barChart.setDrawHighlightArrow(true);
        barChart.setHighlightEnabled(true);

        barChart.setScaleEnabled(true);

        barChart.getLegend().setEnabled(true);

        //X軸周り
        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(0);

        barChart.setData(createBarChartData());

        barChart.invalidate();
        // アニメーション
        barChart.animateY(2000, Easing.EasingOption.EaseInBack);
    }

    // BarChartの設定
    private BarData createBarChartData() {
        ArrayList<BarDataSet> barDataSets = new ArrayList<>();

        // X軸
       final ArrayList<String> xValues = new ArrayList<>();


        //追記

       final RealmResults<Schedule> results = mRealm.where(Schedule.class).findAll();
        final ArrayList<BarEntry> valuesA = new ArrayList<>();


        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                mRealm.where(Schedule.class).findAll();

                int i = 0;

                for(Schedule s1 : results) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
                    String formatDate = sdf.format(s1.getDate());


                    xValues.add(formatDate);
                    valuesA.add(new BarEntry(Integer.parseInt(s1.getDetail()),i));
                    i++;
                }

            }
        });


        BarDataSet valuesADataSet = new BarDataSet(valuesA, "アンガーポイント");
        valuesADataSet.setColor(ColorTemplate.COLORFUL_COLORS[3]);

        barDataSets.add(valuesADataSet);

        BarData barData = new BarData(xValues, barDataSets);
        return barData;
    }

    public void back(View v){
        finish();
    }

}
