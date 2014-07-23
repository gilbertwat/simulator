package com.looppulse.blesimulator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.looppulse.blesimulator.R;
import com.looppulse.blesimulator.models.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator, mBtnUploadSampleWorld,mBtnChangeEndPoint;

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private void onBtnStartSimulatorClick() {
        //TODO read the JSON from firebase
        //TODO Use a looper to run Action for every 1 sec
        //TODO each action will check condition of visitior is in any beacon or not
        //TODO fire event accordingly
    }

    private void onBtnUploadSampleWorld() {
        World world = World.getSampleWorld();
        //TODO Can't handle circular dependency for Gson, need to switch to Jackson
        //Gson gson = new Gson();
        //logger.debug(gson.toJson(gson));
    }

    private void onBtnChangeEndPoint() {
        Intent intent = new Intent(this, ChangeEndpointActivity.class);
        startActivity(intent);
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

        mBtnChangeEndPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnChangeEndPoint();
            }
        });

    }
}
