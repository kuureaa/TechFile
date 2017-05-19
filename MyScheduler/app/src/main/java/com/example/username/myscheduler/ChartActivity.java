package com.example.username.myscheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

        createLineChart();
        createLineChartData();
    }

    private void createLineChart() {
        LineChart lineChart = (LineChart) findViewById(R.id.line_chart);
        lineChart.setDescription("アンガーグラフ");


        lineChart.getAxisRight().setEnabled(false);
        lineChart.getAxisLeft().setEnabled(true);
        lineChart.setDrawGridBackground(true);
        //lineChart.setDrawBarShadow(false);
        lineChart.setEnabled(true);

        lineChart.setTouchEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDoubleTapToZoomEnabled(true);

        lineChart.setHighlightEnabled(true);
        //lineChart.setDrawHighlightArrow(true);
        lineChart.setHighlightEnabled(true);

        lineChart.setScaleEnabled(true);

        lineChart.getLegend().setEnabled(true);

        //X軸周り
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setSpaceBetweenLabels(0);

        lineChart.setData(createLineChartData());

        lineChart.invalidate();
        // アニメーション
        lineChart.animateY(2000, Easing.EasingOption.EaseInBack);
    }

    // BarChartの設定
    private LineData createLineChartData() {
        ArrayList<LineDataSet> lineDataSets = new ArrayList<>();

        // X軸
       final ArrayList<String> xValues = new ArrayList<>();


        //追記

       final RealmResults<Schedule> results = mRealm.where(Schedule.class).findAll();
        final ArrayList<Entry> valuesA = new ArrayList<>();


        mRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                mRealm.where(Schedule.class).findAll();

                int i = 0;

                for(Schedule s1 : results) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
                    String formatDate = sdf.format(s1.getDate());


                    xValues.add(formatDate);
                    valuesA.add(new Entry(Integer.parseInt(s1.getDetail()),i));
                    i++;
                }

            }
        });



        LineDataSet valuesADataSet = new LineDataSet(valuesA, "アンガーポイント");
        valuesADataSet.setColor(ColorTemplate.COLORFUL_COLORS[0]);
        valuesADataSet.setLineWidth(10.0f);

        lineDataSets.add(valuesADataSet);

        LineData lineData = new LineData(xValues, lineDataSets);
        return lineData;
    }

    public void back(View v){
        finish();
    }

}
