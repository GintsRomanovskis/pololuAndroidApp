package com.example.gints.pololuv2;
import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.*;
import android.view.*;
import android.widget.*;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    private SeekBar sBar;
    private Button btnLeft;
    private Button btnRights;
    private TextView tView;
    private TextView tView2;
    private JSONObject pololuJson;
    private int pval = 0;
    private String jsonValue;


    //Rest api url
    String C_REST_API_URL = "https://127.0.0.1:58580/api/values";

    //Class elements
    CallService cs = new CallService();


    // Generate json string
    private String generateJson(int speed, String direction) {
        try {
            pololuJson.put("direction", direction);
            pololuJson.put("speed", speed);
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
        sBar = (SeekBar) findViewById(R.id.seekBar);

        //Buttons
        btnLeft = (Button) findViewById(R.id.buttonLeft);
        btnRights = (Button) findViewById(R.id.buttonRight);

        //text fields
        tView = (TextView) findViewById(R.id.seekBarValue);
        tView2 = (TextView) findViewById(R.id.test);


        //jsonObject
        pololuJson = new JSONObject();


        sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

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
                tView.setText(pval + "/" + seekBar.getMax());
                jsonValue = generateJson(pval, "");
                tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
            }
        });

        btnLeft.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                generateJson(pval, "left");
                tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
            }
        });


        btnRights.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                jsonValue = generateJson(pval, "right");
                tView2.setText(cs.doInBackground(jsonValue, C_REST_API_URL));
                return false;
            }
        });
    }

}


