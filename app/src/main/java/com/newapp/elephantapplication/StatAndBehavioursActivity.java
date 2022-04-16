package com.newapp.elephantapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class StatAndBehavioursActivity extends AppCompatActivity {

    LineChart  lineChart;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    LineDataSet lineDataSet  = new LineDataSet(null,null);
    ArrayList<ILineDataSet>  iLineDataSets = new ArrayList<>();
    LineData  lineData ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_and_behaviours);

        lineChart = findViewById(R.id.lineChart);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Behavior_&_Status");
        lineDataSet.setLineWidth(5);
        fetchData();

        XAxis xAxis = lineChart.getXAxis();
        YAxis yAxis  = lineChart.getAxisLeft();
        YAxis YAxixRight = lineChart.getAxisRight();

       // xAxis.setValueFormatter( new MyAxixVlaueFormatter());


    }
    private void  fetchData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            ArrayList<Entry> dataValues =  new ArrayList<Entry>();
                if(snapshot.hasChildren()){
                    for (DataSnapshot myDataSnapshot : snapshot.getChildren()){
                        DataPoint dataPoint = myDataSnapshot.getValue(DataPoint.class);
                        dataValues.add(new Entry(dataPoint.getxValue(),dataPoint.getyValue()));
                        Log.e("dataval","dataval"+dataValues.add(new Entry(dataPoint.getxValue(),dataPoint.getyValue())));
                    }
                    showChart(dataValues);
                }
                else {
                    lineChart.clear();
                    lineChart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showChart(ArrayList<Entry> dataValues) {
        lineDataSet.setValues(dataValues);
        lineDataSet.setLabel("Avoid Invading Elephant");
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);
        lineChart.clear();
        lineChart.setData(lineData);
        lineChart.invalidate();
        lineChart.setContentDescription("Invading Elephant");
        lineChart.setNoDataTextColor(Color.RED);

       lineChart.getDescription().setEnabled(false);

    }
//    private  class MyAxixVlaueFormatter extends ValueFormatter implements IAxisValueFormatter{
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            axis.setLabelCount(3,true);
//            return "Month" + value;
//        }
//    }
}