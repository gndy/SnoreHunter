package com.bigwiv.snorehunter;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {

	private LinearLayout container;
	private ImageView iv1; // record button
	private ImageView iv2; // history button
	private ImageView iv3; // usage button
	private ImageView iv4; // about button
	private ImageView iv5; // setting button

	private boolean button_record_pressed;
	private boolean button_history_pressed;
	private boolean button_usage_pressed;
	private boolean button_about_pressed;
	private boolean button_setting_pressed;
	
	public TextView tView;
	public File destDir;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		destDir = new File(Environment.getExternalStorageDirectory()+"/SnoreHunter/");
		  if (!destDir.exists()) {
		   destDir.mkdirs();
		  }
		
		container = (LinearLayout) findViewById(R.id.main_tab_container);

		iv1 = (ImageView) findViewById(R.id.button_record);
		iv2 = (ImageView) findViewById(R.id.button_history);
		iv3 = (ImageView) findViewById(R.id.button_usage);
		iv4 = (ImageView) findViewById(R.id.button_about);
		iv5 = (ImageView) findViewById(R.id.button_setting);

		recordUI();
		button_record_pressed = true;
		
		iv1.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_record_pressed) {
					init();
					recordUI();

				}
			}
		});
		
		iv2.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_history_pressed) {
					init();
					historyUI();

				}
			}
		});
		
		iv3.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_usage_pressed) {
					init();
					usageUI();

				}
			}
		});
		
		iv4.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_about_pressed) {
					init();
					aboutUI();

				}
			}
		});

		iv5.setOnClickListener(new View.OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!button_setting_pressed) {
					init();
					settingUI();

				}
			}
		});
		



	}

	private void init() {
		
		iv1.setBackgroundColor(Color.parseColor("#808082"));
		iv2.setBackgroundColor(Color.parseColor("#808082"));
		iv3.setBackgroundColor(Color.parseColor("#808082"));
		iv4.setBackgroundColor(Color.parseColor("#808082"));
		iv5.setBackgroundColor(Color.parseColor("#808082"));

		button_record_pressed = false;
		button_history_pressed = false;
		button_usage_pressed = false;
		button_about_pressed = false;
		button_setting_pressed = false;

	}

	private void recordUI() {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"record",
				new Intent(MainActivity.this, Record.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		iv1.setBackgroundColor(Color.parseColor("#dddfff"));
		button_record_pressed = true;

	}

	private void historyUI() {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"history",
				new Intent(MainActivity.this, History.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		iv2.setBackgroundColor(Color.parseColor("#dddfff"));
		button_history_pressed = true;

	}
	
	private void usageUI() {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"usage",
				new Intent(MainActivity.this, Usage.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		iv3.setBackgroundColor(Color.parseColor("#dddfff"));
		button_usage_pressed= true;

	}
	
	private void aboutUI() {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"about",
				new Intent(MainActivity.this, About.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		iv4.setBackgroundColor(Color.parseColor("#dddfff"));
		button_about_pressed = true;

	}
	
	private void settingUI() {
		container.removeAllViews();
		container.addView(getLocalActivityManager().startActivity(
				"setting",
				new Intent(MainActivity.this, Setting.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		iv5.setBackgroundColor(Color.parseColor("#dddfff"));
		button_setting_pressed = true;

	}


}
