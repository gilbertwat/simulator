package com.looppulse.blesimulator.api;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.firebase.client.Firebase;
import com.looppulse.blesimulator.activities.ChangeEndpointActivity;
import com.looppulse.blesimulator.activities.MainActivity;
import com.looppulse.blesimulator.models.Beacon;
import com.looppulse.blesimulator.models.Visitor;

import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Created by Gilbert on 7/22/2014.
 */
public class Event {

    public static final String EVENT = "event/";

    public enum Type {
        didRangeBeacons,
        didExitRegion;
    }

    public static void didRangeBeacons(final Context context, final Visitor.VisitorEnterSignal v) {
        checkArgument(context instanceof UiUpdater);
        //TODO not very efficient
        SharedPreferences sp = context.getSharedPreferences(ChangeEndpointActivity.SP_NAME_ENDPOINT, Activity.MODE_PRIVATE);
        String endPoint = sp.getString(ChangeEndpointActivity.SP_KEY_ENDPOINT, ChangeEndpointActivity.DEFAULT_ENDPOINT);
        String testName = sp.getString(MainActivity.SP_KEY_TEST_NAME, "testResult") + "/";
        //firebaseAPI
        if (v.count == 0) {
            return;
        } else {
            Firebase firebase = new Firebase(endPoint + EVENT + testName);
            Firebase entry = firebase.push();
            Date currTime = Calendar.getInstance().getTime();

            entry.setValue(new LogEntry(
                    currTime.toString(),
                    v.visitor.uuid.toString(),
                    v.beacon.uuid.toString(),
                    v.beacon.major.toString(),
                    v.beacon.minor.toString(),
                    Type.didExitRegion.toString()));

            if (v.count == Visitor.enterRegionSignalCount) {
                ((UiUpdater) context).doEnterRegion(v.visitor, v.beacon, currTime);
            }
            v.count--;

        }
    }

    public static void didExitRegion(Context context, Visitor.VisitorEnterSignal v) {
        checkArgument(context instanceof UiUpdater);
        //TODO not very efficient
        SharedPreferences sp = context.getSharedPreferences(ChangeEndpointActivity.SP_NAME_ENDPOINT, Activity.MODE_PRIVATE);
        String endPoint = sp.getString(ChangeEndpointActivity.SP_KEY_ENDPOINT, ChangeEndpointActivity.DEFAULT_ENDPOINT);
        String testName = sp.getString(MainActivity.SP_KEY_TEST_NAME, "testResult") + "/";

        Firebase firebase = new Firebase(endPoint + EVENT + testName);
        Firebase entry = firebase.push();
        Date currTime = Calendar.getInstance().getTime();
        entry.setValue(new LogEntry(
                            currTime.toString(),
                            v.visitor.uuid.toString(),
                            v.beacon.uuid.toString(),
                            v.beacon.major.toString(),
                            v.beacon.minor.toString(),
                            Type.didExitRegion.toString()));

        ((UiUpdater)context).doExitRegion(v.visitor, v.beacon, currTime);
    }

    public static class LogEntry {
        public final String time;
        public final String visitorUuid;
        public final String uuid;
        public final String major;
        public final String minor;
        public final String event;

        public LogEntry(String time, String uuid, String visitorUuid, String major, String minor, String event) {
            this.time = time;
            this.uuid = uuid;
            this.visitorUuid = visitorUuid;
            this.major = major;
            this.minor = minor;
            this.event = event;
        }
    }

    public static interface UiUpdater {
        public void doEnterRegion(Visitor v, Beacon b, Date d);
        public void doExitRegion(Visitor v, Beacon b, Date d);
    }

}

