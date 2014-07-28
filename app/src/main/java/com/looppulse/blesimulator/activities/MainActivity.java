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
import com.google.common.collect.Sets;
import com.looppulse.blesimulator.R;
import com.looppulse.blesimulator.models.Action;
import com.looppulse.blesimulator.models.Beacon;
import com.looppulse.blesimulator.models.FloorPlan;
import com.looppulse.blesimulator.models.Visitor;
import com.looppulse.blesimulator.models.World;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.base.Preconditions.checkNotNull;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator, mBtnUploadSampleWorld,mBtnChangeEndPoint;

    private String s;

    private Integer time;

    private Integer maxTime;

    private static final Logger logger = LoggerFactory.getLogger(MainActivity.class);

    private AtomicReference<World> mWorld = new AtomicReference<World>();
    private Set<UUID> mIVisitors = Sets.newHashSet();

    private void onReadApiCompleted() {
        if (mWorld.get().actions != null && mWorld.get().beacons != null && mWorld.get().visitors != null) {
            logger.info("Load Success");
            Timer t = new Timer();
            TimerTask tt = new TimerTask() {
                @Override
                public void run() {
                    if (time < maxTime) {
                        logger.info("Time: " + time + "s");
                        t
                        time++;
                    }
                }
            };


            t.scheduleAtFixedRate(tt, 0, 1000);

            //TODO each action will check condition of visitior is in any beacon or not
            //TODO fire event accordingly
        }
    }

    private void onBtnStartSimulatorClick() {
        mWorld.getAndSet(new World());
        final Firebase firebaseActions = new Firebase(s + "test/actions");
        firebaseActions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Map<String, Object>> actions = (List<Map<String, Object>>) dataSnapshot.getValue();
                List<Action> resultant = Lists.newArrayList();

                for (Map<String, Object> map : actions) {
                    Action action = null;
                    if (mWorld.get().visitors == null) { //if actions is faster than visitors
                        //TODO observer pattern to observe visitor call is done
                        UUID iVisitors = UUID.fromString((String)map.get(Action.KEY_I_VISITOR));
                        action = new Action(iVisitors, (Integer)map.get(Action.KEY_TIME), Action.Type.valueOf((String)map.get(Action.KEY_ACTION)));
                    } else { //if visitors
                       //TODO observer pattern
                       UUID iVisitors = UUID.fromString((String) map.get(Action.KEY_I_VISITOR));
                        for (Visitor v : mWorld.get().visitors) {
                            if (v.uuid.equals(iVisitors)) {
                                action = new Action(v, ((Long)map.get(Action.KEY_TIME)).intValue(), Action.Type.valueOf((String)map.get(Action.KEY_ACTION)));
                                break;
                            }
                        }
                        checkNotNull(action);
                    }
                    resultant.add(action);
                    if (maxTime < action.time) {
                        maxTime = action.time;
                    }
                }

                mWorld.getAndSet(new World(resultant, mWorld.get().beacons, mWorld.get().visitors));

                logger.info("Action read:" + actions.toString());
                onReadApiCompleted();
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
                    List<List<Boolean>> fp = (List<List<Boolean>>)((Map)v.get(Beacon.KEY_AREA_COVERED)).get(FloorPlan.KEY_MAP);

                    Beacon rv = new Beacon(UUID.fromString((String)v.get(Beacon.KEY_UUID)),
                            ((Long)v.get(Beacon.KEY_MAJOR)).shortValue(),
                            ((Long)v.get(Beacon.KEY_MINOR)).shortValue(),
                            new FloorPlan(fp));
                    resultant.add(rv);
                }
                mWorld.getAndSet(new World(mWorld.get().actions, resultant, mWorld.get().visitors));
                logger.info("Beacons read:" + beacons.toString());

                onReadApiCompleted();
            }

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
                            ((Long)v.get(Visitor.KEY_POS_X)).intValue(),
                            ((Long)v.get(Visitor.KEY_POS_Y)).intValue());
                    resultant.add(rv);
                }

                List<Action> resultantActions = mWorld.get().actions;
                if (resultantActions != null) { //TODO should use observable
                    for (Visitor v : resultant) {
                        for (int i = 0; i < resultantActions.size(); i++) {
                            if (resultantActions.get(i).iVisitor.equals(v.uuid)) {
                                Action oldAction = resultantActions.get(i);
                                resultantActions.set(i, new Action(v, oldAction.time, oldAction.action));
                            }
                        }
                    }
                }

                mWorld.getAndSet(new World(resultantActions, mWorld.get().beacons, resultant));
                logger.info("Visitors read:" + resultant.toString());

                onReadApiCompleted();

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
