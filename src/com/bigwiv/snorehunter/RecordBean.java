package com.bigwiv.snorehunter;

import java.util.ArrayList;
import java.util.List;

public class RecordBean {
	private String date;
	private String time;
	private String duration;
	private ArrayList<String> startpoints;
	private String recordfile;
	private String threshold;
	
	public RecordBean(){
		
	}

	public RecordBean(String date, String time, String duration,
			ArrayList<String> startpoints, String recordfile, String threshold) {
		this.date = date;
		this.time = time;
		this.duration = duration;
		this.startpoints = startpoints;
		this.recordfile = recordfile;
		this.threshold = threshold;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public void setStartPoints(ArrayList<String> startpoints) {
		this.startpoints = startpoints;
	}

	public void setRecordFile(String recordfile) {
		this.recordfile = recordfile;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getDuration() {
		return duration;
	}

	public ArrayList<String> getStartPoints() {
		return startpoints;
	}

	public String getRecordFile() {
		return recordfile;
	}

	public String getThreshold() {
		return threshold;
	}

}
