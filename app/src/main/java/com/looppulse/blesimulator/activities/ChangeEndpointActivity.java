package com.looppulse.blesimulator.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.looppulse.blesimulator.R;

public class ChangeEndpointActivity extends Activity {

    public static final String SP_NAME_ENDPOINT = "SP_NAME_ENDPOINT";
    public static final String SP_KEY_ENDPOINT = "SP_KEY_ENDPOINT";
    public static final String DEFAULT_ENDPOINT = "https://crackling-fire-7607.firebaseio.com/";

    private SharedPreferences sp;

    private EditText mEtServer;
    private Button mBtnChangeServer;

    private void onBtnChangeServerClick() {
        sp.edit().putString(SP_KEY_ENDPOINT, mEtServer.getText().toString()).commit();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_endpoint);

        mEtServer = (EditText)findViewById(R.id.mEtServer);
        mBtnChangeServer = (Button)findViewById(R.id.mBtnChangeServer);
        mBtnChangeServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnChangeServerClick();
            }
        });

        sp = getSharedPreferences(SP_NAME_ENDPOINT, Activity.MODE_PRIVATE);
        final String s = sp.getString(SP_KEY_ENDPOINT, DEFAULT_ENDPOINT);

        mEtServer.setText(s);
    }
}
