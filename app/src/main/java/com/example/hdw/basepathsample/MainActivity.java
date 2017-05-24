package com.example.hdw.basepathsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private WaveView waveView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        waveView =(WaveView)findViewById(R.id.waveView);
    }
    public void wave(View view){
        waveView.doAnimation();
    }
}
