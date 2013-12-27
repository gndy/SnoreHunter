package com.bigwiv.snorehunter;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Record extends Activity {
	public static final String TAG = "test";
	public RecordManager rm = null;

	private ImageView startbutton;
	private TextView tv_flight_mode;
	boolean button_start_pressed;
	boolean on_flight_mode;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record);
		tv_flight_mode = (TextView)findViewById(R.id.tv_flight_mode);
		on_flight_mode = Settings.System.getInt(getContentResolver(),Settings.System.AIRPLANE_MODE_ON, 0) == 1;
		if (on_flight_mode) {
			tv_flight_mode.setText("飞行模式已开启");
		}else {
			tv_flight_mode.setText("飞行模式未开启，建议开启");

		}
		startbutton = (ImageView) findViewById(R.id.start_button);
		button_start_pressed = isServiceRunning(this);
		if (button_start_pressed) {
			startbutton.setImageDrawable(getResources().getDrawable(
					R.drawable.start_button_pressed));
		}
		startbutton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_start_pressed) {
					startbutton.setImageDrawable(getResources().getDrawable(
							R.drawable.start_button_pressed));
					button_start_pressed = true;
			        Log.i(TAG, "meian"+button_start_pressed);  
			        startService(new Intent(Record.this,RecordManager.class));
				} else {
					startbutton.setImageDrawable(getResources().getDrawable(
							R.drawable.start_button));
					button_start_pressed = false;
			        Log.i(TAG, "anle"+button_start_pressed);  
			        stopService(new Intent(Record.this,RecordManager.class));
			        Log.i(TAG, "buttonstartstate"+button_start_pressed);  

				}
			}
		});
	}

    public boolean isServiceRunning(Context mContext) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
        mContext.getSystemService(Context.ACTIVITY_SERVICE); 
        List<ActivityManager.RunningServiceInfo> serviceList 
                   = activityManager.getRunningServices(30);

        if (!(serviceList.size()>0)) {
            return false;
        }

        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals("com.bigwiv.snorehunter.RecordManager") == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}

