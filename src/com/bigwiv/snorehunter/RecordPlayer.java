package com.bigwiv.snorehunter;

import android.R.integer;
import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
public class RecordPlayer extends Activity {
	
	private Button button1;
	private Button button2;
	private Button button3;
	private TextView startpointstext;
	private SeekBar seekBar1;
	private MediaPlayer player;
	private int position;
	private int i;
	private RecordBean rb;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recordplayer);
        position = History.getPosition();
        rb = History.getRB().get(position);
        init();
        findView();
        setListener();
        startpointstext = (TextView)findViewById(R.id.startpoints_text);
        startpointstext.setText(getStartPointsText());
       

    }
    @Override
    public void onStop(){
		Toast.makeText(this, "stop", 2000).show();
		super.onStop();
		player.stop();
		//取消线程
		handler.removeCallbacks(updateThread);
    }
    
    private void init() {
    	i = 0;
    	player = new MediaPlayer();
    	try {
    		Log.i("filename", rb.getRecordFile());
			player.setDataSource(rb.getRecordFile());
			player.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    private void findView() {
    	button1 = (Button)findViewById(R.id.button1);
    	button2 = (Button)findViewById(R.id.button2);
    	button3 = (Button)findViewById(R.id.button3);
    	seekBar1 = (SeekBar)findViewById(R.id.seekbar1);
    	//获得歌曲的长度并设置成播放进度条的最大值
		seekBar1.setMax(player.getDuration());
		Toast.makeText(this, player.getDuration()+"", 1000).show();
    }
    
    Handler handler = new Handler();
    Runnable updateThread = new Runnable(){
    	public void run() {
    		//获得歌曲现在播放位置并设置成播放进度条的值
        seekBar1.setProgress(player.getCurrentPosition());
    		//每次延迟100毫秒再启动线程
    		handler.postDelayed(updateThread, 100);
    	}
    };
    
    private void setListener() {
    	button1.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0){
		        player.start();
		        //启动
				handler.post(updateThread);
			}		
    	});
    	button2.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				player.pause();
				//取消线程
				handler.removeCallbacks(updateThread);
			}	
    	});
    	button3.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Log.i("player",rb.getTime() );
				if (rb.getStartPoints()!=null&&i<rb.getStartPoints().size()&&rb.getStartPoints().get(i)!="") {
					player.seekTo(Integer.parseInt(rb.getStartPoints().get(i)));
					i++;
				}
				
			}	
    	});
    	seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// fromUser判断是用户改变的滑块的值
				if(fromUser==true){
					player.seekTo(progress);
				}
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
			}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub		
			}
    	});
    }
    
    private String getStartPointsText(){
		StringBuilder sb = new StringBuilder();
		if (rb.getStartPoints()!=null&&rb.getStartPoints().get(i)!="") {
			for (int i = 0; i < rb.getStartPoints().size(); i++) {
				if (i<10) {
					int seconds = Integer.parseInt(rb.getStartPoints().get(i))/1000;
					int minutes = seconds/60;
					int hours = minutes/60;
					seconds = seconds%60;
					String time = "第"+hours+"小时 "+minutes+"分钟 "+seconds+"秒 ";
					sb.append(time);
					sb.append("\n");
				}
					
				}
			return sb.toString();
		}
		sb.append("无");
		return sb.toString();
		
	}
                             
}