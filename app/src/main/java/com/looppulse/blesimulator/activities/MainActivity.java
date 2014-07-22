package com.looppulse.blesimulator.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.common.collect.Lists;
import com.looppulse.blesimulator.R;
import com.looppulse.blesimulator.models.Beacon;
import com.looppulse.blesimulator.models.Event;
import com.looppulse.blesimulator.models.Visitor;

import java.util.List;
import java.util.UUID;


public class MainActivity extends Activity {

    private Button mBtnStartSimulator;

    private void onMBtnStartSimulatorClick() {
        //TODO impl
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnStartSimulator = (Button)findViewById(R.id.mBtnStartSimulator);

        mBtnStartSimulator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMBtnStartSimulatorClick();
            }
        });

        final UUID uuid = UUID.randomUUID();
        final Beacon maleShoe = new Beacon(uuid, (short)0, (short)0, 2, 4, 0);
        final Beacon femaleShoe = new Beacon(uuid, (short)0, (short)1, 2, 4, 4);
        final Beacon maleClothes = new Beacon(uuid, (short)0, (short)2, 2, 0, 2);
        final Beacon femaleClothes = new Beacon(uuid, (short)0, (short)3, 2, 4, 2);
        final Beacon tryingArea = new Beacon(uuid, (short)1, (short)0, 1, 0, 0);
        final Beacon cashier = new Beacon(uuid, (short)1, (short)1, 1, 4, 0);

        final Visitor hannibal = new Visitor(UUID.randomUUID(), "Hannibal", 4, 2);
        final Visitor will = new Visitor(UUID.randomUUID(), "Will", 4, 3);
        final Visitor alana = new Visitor(UUID.randomUUID(), "Alana", 4, 1);



    }
}
