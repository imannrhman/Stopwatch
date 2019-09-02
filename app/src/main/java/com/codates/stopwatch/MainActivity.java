package com.codates.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.os.Handler;

import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button start,pause,reset,lap;
    long MillisecondTime,StartTime,TimeBuff,UpdateTime = 0L;
    Handler handler;
    int Seconds,Minutes,MilliSeconds;
    ListView listView;
    String[] listElements = new String[]{ };
    List<String> ListELementsArrayList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        start = findViewById(R.id.button);
        pause = findViewById(R.id.button2);
        reset = findViewById(R.id.button3);
        lap = findViewById(R.id.button4);
        listView = findViewById(R.id.listview1);

        handler = new Handler();

        ListELementsArrayList = new ArrayList<String>(Arrays.asList(listElements));

        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,ListELementsArrayList);

        listView.setAdapter(adapter);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable,0);
                Toast.makeText(getApplicationContext(),"Ini Diclick",Toast.LENGTH_LONG).show();
                reset.setEnabled(false);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff +=MillisecondTime;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MillisecondTime = 0;
                StartTime = 0L;
                TimeBuff = 0L;
                UpdateTime = 0L;
                Seconds = 0;
                Minutes = 0;
                MilliSeconds = 0;
                textView.setText("00:00:00");
                ListELementsArrayList.clear();
                adapter.notifyDataSetChanged();
            }
        });

        lap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListELementsArrayList.add(textView.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

    }

    public  Runnable runnable = new Runnable() {

        public void run() {
            MillisecondTime = SystemClock.uptimeMillis() - StartTime ;
            UpdateTime = TimeBuff + MillisecondTime;
            Seconds = (int) (UpdateTime / 1000);
            Minutes = Seconds / 60;
            Seconds = Seconds % 60;
            MilliSeconds = (int) (UpdateTime % 1000);
            Log.d("sds",String.valueOf(UpdateTime));
            textView.setText("" + Minutes + ":" + String.format("%02d",Seconds)+ ":" + String.format("%03d",MilliSeconds));
            handler.postDelayed(this,0);
        }
    };
}
