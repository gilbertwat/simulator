package com.looppulse.blesimulator.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.looppulse.blesimulator.R;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator, mBtnUploadSampleWorld,mBtnChangeEndPoint;
    

    private void onBtnStartSimulatorClick() {
        //TODO impl
    }

    private void onBtnUploadSampleWorld() {
        //TODO impl
    }

    private void onBtnChangeEndPoint() {
        //TODO impl
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartSimulator = (Button)findViewById(R.id.mBtnStartSimulator);
        mBtnUploadSampleWorld = (Button)findViewById(R.id.mBtnUploadSampleWorld);
        mBtnChangeEndPoint = (Button)findViewById(R.id.mBtnChangeEndPoint);

        mBtnStartSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnStartSimulatorClick();
            }
        });

        mBtnUploadSampleWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnUploadSampleWorld();
            }
        });

        mBtnUploadSampleWorld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnChangeEndPoint();
            }
        });

    }
}
