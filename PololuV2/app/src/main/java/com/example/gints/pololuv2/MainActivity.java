package com.example.gints.pololuv2;

import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.*;
import android.view.*;
import android.widget.*;

import org.json.*;

public class MainActivity extends AppCompatActivity {

    private SeekBar sBarLeft;
    private SeekBar sBarRight;
    private Switch  onOff;
    private TextView tViewLeft;
    private TextView tViewRight;
    private TextView tView2;
    private JSONObject pololuJson;
    private int pval = 0;
    private String jsonValue;


    //Rest api url
    String C_REST_API_URL = "https://127.0.0.1:58580/api/values";

    //Class elements
    CallService cs = new CallService();


    // Generate json string
    private String generateJson(int speed, String direction,String onOff) {
        try {
            pololuJson.put("direction", direction);
            pololuJson.put("speed", speed);
            pololuJson.put("on_off", onOff);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonValue = pololuJson.toString();
        return jsonValue;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Progress  Bar
        sBarLeft = (SeekBar) findViewById(R.id.seekBarLeft);
        sBarRight = (SeekBar) findViewById(R.id.seekBarRight);

        //Swich

        onOff = (Switch) findViewById(R.id.onOff);

        //text fields
        tViewLeft = (TextView) findViewById(R.id.seekBarLeftValue);
        tViewRight = (TextView) findViewById(R.id.seekBarRightValue);

        tView2 = (TextView) findViewById(R.id.responseText);


        //jsonObject
        pololuJson = new JSONObject();


        sBarLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tViewLeft.setText(pval + "/" + seekBar.getMax());
                jsonValue = generateJson(pval, "","on");
                //  tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
            }
        });

        sBarRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pval = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //write custom code to on start progress
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tViewRight.setText(pval + "/" + seekBar.getMax());
                jsonValue = generateJson(pval, "","on");
                tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
            }
        });

        onOff.setOnClickListener(new View.OnClickListener() {
        public void onClick(View view) {
            String statusSwitch1;
            if (onOff.isChecked()) {
                statusSwitch1 = onOff.getTextOn().toString();
                jsonValue = generateJson(pval, "","on");
                tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
            } else
                statusSwitch1 = onOff.getTextOff().toString();
            jsonValue = generateJson(pval, "","off");
            tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));

            Toast.makeText(getApplicationContext(), "Switch :" + statusSwitch1 + "\n" , Toast.LENGTH_LONG).show(); // display the current state for switch's
        }
    });


    }

}


