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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.looppulse.blesimulator.R;
import com.looppulse.blesimulator.models.Beacon;
import com.looppulse.blesimulator.models.FloorPlan;
import com.looppulse.blesimulator.models.Visitor;
import com.looppulse.blesimulator.models.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator, mBtnUploadSampleWorld,mBtnChangeEndPoint;

    private String s;

    private Integer time;

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private AtomicReference<World> mWorld;

    private void onReadApiCompleted() {
        if (mWorld.get().actions != null && mWorld.get().beacons != null && mWorld.get().visitors != null) {
            logger.info("Load Success");
            //TODO Use a looper to run Action for every 1 sec
            //TODO each action will check condition of visitior is in any beacon or not
            //TODO fire event accordingly
        }
    }

    private void onBtnStartSimulatorClick() {
        final Firebase firebaseActions = new Firebase(s + "test/actions");
        firebaseActions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO depends on Visitors dont want callback hell
            }

            @Override
            public void onCancelled() {
                logger.error("The read failed.");
            }
        });

        //TODO generic maybe?
        final Firebase firebaseBeacons = new Firebase(s + "test/beacons");
        firebaseBeacons.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Map<String, Object>> beacons =
                        (List<Map<String, Object>>)dataSnapshot.getValue();
                final List<Beacon> resultant = Lists.newArrayList();
                for (Map<String, Object> v : beacons) {
                    List<List<Boolean>> fp = (List<List<Boolean>>)v.get(v.get(Beacon.KEY_AREA_COVERED));

                    Beacon rv = new Beacon(UUID.fromString((String)v.get(Beacon.KEY_UUID)),
                            (Short)v.get(Beacon.KEY_MAJOR),
                            (Short)v.get(Beacon.KEY_MINOR),
                            new FloorPlan(fp));
                    resultant.add(rv);
                }
                mWorld.getAndSet(new World(mWorld.get().actions, resultant, mWorld.get().visitors));
                logger.info("Beacons read:" + beacons.toString());            }

            @Override
            public void onCancelled() {
                logger.error("The read failed.");
            }
        });

        final Firebase firebaseVisitors = new Firebase(s + "test/visitors");
        firebaseVisitors.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Map<String, Object>> visitors =
                        (List<Map<String, Object>>)dataSnapshot.getValue();
                final List<Visitor> resultant = Lists.newArrayList();
                for (Map<String, Object> v : visitors) {
                    Visitor rv = new Visitor(UUID.fromString((String)v.get(Visitor.KEY_UUID)),
                            (String)v.get(Visitor.KEY_NAME),
                            (Integer)v.get(Visitor.KEY_POS_X),
                             (Integer)v.get(Visitor.KEY_POS_Y));
                    resultant.add(rv);
                }
                mWorld.getAndSet(new World(mWorld.get().actions, mWorld.get().beacons, resultant));
                logger.info("Visitors read:" + resultant.toString());

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
