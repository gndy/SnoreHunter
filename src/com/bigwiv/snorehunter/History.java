package com.bigwiv.snorehunter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class History extends Activity{
	ListView listView1;
	static List<RecordBean> rbList;
	File xmlFile;
	static int position;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_layout);
		
		listView1 = (ListView) findViewById(R.id.listView1);
		String[] strings = {"xml_img","xml_time","xml_duration","xml_threshold"};//Map的key集合数组
		int[] ids = {R.id.xml_img,R.id.xml_time,R.id.xml_duration,R.id.xml_threshold};//对应布局文件的id
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, 
				getData(), R.layout.mylist, strings, ids);
		listView1.setAdapter(simpleAdapter);//绑定适配器
		listView1.setOnItemClickListener(new OnItemClickListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
			  int position, long id) {
			  setPosition(position);
			  Intent launchPlayer = new Intent(History.this,RecordPlayer.class);
			  startActivity(launchPlayer);
			 
			 
			}
			     });
	}
	// 初始化一个List
	private List<HashMap<String, Object>> getData() {
		xmlFile = new File(Environment.getExternalStorageDirectory()+"/SnoreHunter/"+ "record.xml");
		
		Log.i("history", xmlFile.getAbsolutePath()+xmlFile.exists());
		// 新建一个集合类，用于存放多条数据
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = null;
		try {
			rbList = XmlManager.getXmlData(xmlFile);
			Log.i("rblist", rbList.size()+"");
			for (int i = 0; i < rbList.size(); i++) {
				map = new HashMap<String, Object>();
				map.put("xml_threshold", "阈值： "+rbList.get(i).getThreshold());
				map.put("xml_duration", "打鼾时长： "+Integer.parseInt(rbList.get(i).getDuration())/1000+"秒");
				map.put("xml_time", rbList.get(i).getTime());
				
				if (Integer.parseInt(rbList.get(i).getDuration())>10000) {
					map.put("xml_img", R.drawable.red);

				}else {
					map.put("xml_img", R.drawable.green);

				}
				list.add(map);
			}
		
		}catch (Exception e) {
			// TODO: handle exception
			Log.i("history", "fileexception");

		}
		
		return list;
	}
	
	public static void setPosition(int _position){
		position = _position;
	}
	public static int getPosition(){
		return position;
	}
	public static  List<RecordBean> getRB(){
		return rbList;
	}
}
