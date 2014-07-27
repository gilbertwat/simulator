package com.looppulse.blesimulator.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.ValueEventListener;
import com.google.common.collect.Maps;
import com.looppulse.blesimulator.R;
import com.looppulse.blesimulator.models.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator, mBtnUploadSampleWorld,mBtnChangeEndPoint;

    private String s;

    private Integer time;

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private void onBtnStartSimulatorClick() {
        final Firebase firebase = new Firebase(s + "test");
        firebase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String s = (String) dataSnapshot.getValue();
                final World w;
                ObjectMapper om = new ObjectMapper();
                try {
                    w = om.readValue(s, World.class);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }

                //TODO Use a looper to run Action for every 1 sec
                //TODO each action will check condition of visitior is in any beacon or not
                //TODO fire event accordingly
            }

            @Override
            public void onCancelled() {
                logger.error("The read failed.");
            }
        });

    }

    private void onBtnUploadSampleWorld() {
        final Firebase firebase = new Firebase(s + "test");
        World world = World.getSampleWorld();

        firebase.setValue(world);
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

        SharedPreferences sp = getSharedPreferences(ChangeEndpointActivity.SP_NAME_ENDPOINT, Activity.MODE_PRIVATE);
        s = sp.getString(ChangeEndpointActivity.SP_KEY_ENDPOINT, ChangeEndpointActivity.DEFAULT_ENDPOINT);


    }
}
