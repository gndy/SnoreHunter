package com.bigwiv.snorehunter;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import java.util.*;


public class RecordManager extends Service implements Runnable {
	private final String TAG = "RecordManager";
	private RecordManager rm;
	private MediaRecorder mMediaRecorder;
	public static final int MAX_LENGTH = 1000 * 60 * 1000;// 最大录音时长1000*60*10;
	private File file;
	private int BASE = 600; // 基准

	static int sampleRate,threshold,minTimes;
	//xml文件记录数据
	private String date;
	private long duration;
	private String recordfilename;
	private long snoreStartTime;
	private long snoreEndTime;
	private ArrayList<String> snorepoints;
	
	// 鼾声判断相关的参数及数据记录
	private boolean snoring = false;
	private boolean onStopRecord = false;

	

	public RecordManager(){

	}

	private long startTime;
	private long endTime;
	private String startTimeString;
	private String endTimeString;

	/**
	 * 开始录音 使用amr格式
	 * 
	 * @param mRecAudioFile
	 *            录音文件
	 * @return
	 */
	public void startRecord() {
		if (mMediaRecorder == null)
			mMediaRecorder = new MediaRecorder();
		try {
			mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
			mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT);

			mMediaRecorder.setAudioSamplingRate(sampleRate);
			SimpleDateFormat formatter0 = new SimpleDateFormat("yyyy-MM-dd-HH-mm");
			String filenameString = formatter0.format(System.currentTimeMillis());
			recordfilename = Environment.getExternalStorageDirectory()+"/SnoreHunter/"+filenameString+".3gp";
			mMediaRecorder.setOutputFile(recordfilename);
			mMediaRecorder.setMaxDuration(MAX_LENGTH);
			mMediaRecorder.prepare();
			mMediaRecorder.start();
			/* 获取开始时间* */
			startTime = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.format(startTime);
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			startTimeString = formatter1.format(startTime);
			snorepoints = new ArrayList<String>();
			Thread recordThread = new Thread(this);
			recordThread.start();

			Log.i("ACTION_START", "startTime" + startTime);
		} catch (IllegalStateException e) {
			Log.i(TAG,
					"call startAmr(File mRecAudioFile) failed!"
							+ e.getMessage());
		} catch (IOException e) {
			Log.i(TAG,
					"call startAmr(File mRecAudioFile) failed!"
							+ e.getMessage());
		}

	}

	/**
	 * 停止录音
	 * 
	 * @param mMediaRecorder
	 */
	public long stopRecord() {
		if (mMediaRecorder == null)
			return 0L; 
		endTime = System.currentTimeMillis();
		Log.i("ACTION_END", "endTime" + endTime);
		mMediaRecorder.stop();
		mMediaRecorder.reset();
		mMediaRecorder.release();
		mMediaRecorder = null;
		onStopRecord = true;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		endTimeString = formatter.format(System.currentTimeMillis());
		RecordBean rb = new RecordBean(date, startTimeString+"-"+endTimeString, duration+"", snorepoints, recordfilename,Integer.toString(threshold));
		XmlManager.XmlFileCreator(rb);
		return endTime - startTime;
	}
    
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	public void run() {
		int false_time = 0;
		try {
			while (!onStopRecord) {

				if (estimateSnore()) {
					if(!snoring){
					snoreStartTime =System.currentTimeMillis();
					snorepoints.add(Math.abs((snoreStartTime-startTime-5000))+"");
					snoring = true;
					}
					
				} else {
					if (snoring) {
						false_time++;
						if (false_time>1) {
							snoreEndTime = System.currentTimeMillis();
							duration += snoreEndTime-snoreStartTime;
							Log.i("estimateSnore", "false"+duration);
							false_time = 0;
							snoring = false;
						}

					}

				}
				int sleepTime =(int)(4*Math.random());
				Thread.sleep(sleepTime*1000+3000);
				Log.i("sleeptime", sleepTime*1000+3000+"");

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	};

	
	

	// 核心的鼾声判断算法，5秒内每500ms取样一次，共十次，如果有六次超过阈值，就判定为打鼾
	private boolean estimateSnore() throws InterruptedException {
		int j = 0;
		if (mMediaRecorder != null) {
			for (int i = 0; i < 15; i++) {
				if (mMediaRecorder != null) {
					//int vuSize = 100 * mMediaRecorder.getMaxAmplitude() / 32768;
					 int ratio = mMediaRecorder.getMaxAmplitude() / BASE;  
			            int vuSize = 0;// 分贝  
			            if (ratio > 1)  
			                vuSize = (int) (20 * Math.log10(ratio));  
					Log.i("estimate",threshold+""+minTimes+sampleRate);
					Log.i("estimate",vuSize+"");

					if (vuSize > threshold) {
						j++;
					}
					Thread.sleep(500);
				}
			}
		}

		if (j > minTimes) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharedPreferences preference1 = getSharedPreferences("snorehunter",MODE_PRIVATE);
	    sampleRate = preference1.getInt("samplerate", 8000);
	    threshold = preference1.getInt("threshold", 10);
	    minTimes = preference1.getInt("mintimes", 4);
		rm = new RecordManager();
		rm.startRecord();


	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (rm!=null) {
			Log.i("stop", threshold + "");
			rm.stopRecord();

		}
	}
	
	

}
