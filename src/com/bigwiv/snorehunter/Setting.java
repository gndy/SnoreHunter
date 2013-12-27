package com.bigwiv.snorehunter;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.R.integer;
import android.app.Activity;
import android.content.SharedPreferences;

public class Setting extends Activity {

	int sampleRate;
	int threshold;
	int minTimes;
    static final Integer[] thresholds={5,10,15,20};  
    static final Integer[] mintimes={1,2,3,4,5,6,7,8,9,10};  

    ArrayAdapter<Integer> adapter;
	RadioGroup radiogroup;
	RadioButton radiobutton1,radiobutton2;
	Spinner spinner1,spinner2;
	Button button_ok;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
        readPreferences();

        radiogroup=(RadioGroup)findViewById(R.id.radioGroup);  
        radiobutton1 = (RadioButton)findViewById(R.id.radioButton_8000);
        radiobutton2 = (RadioButton)findViewById(R.id.radioButton_16000);
        spinner1=(Spinner)findViewById(R.id.spinner1);  
        spinner2=(Spinner)findViewById(R.id.spinner2);  
        button_ok = (Button)findViewById(R.id.button_ok);
        
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {  
            
            @Override  
            public void onCheckedChanged(RadioGroup group, int checkedId) {  
                // TODO Auto-generated method stub  
                if(checkedId==radiobutton1.getId())  
                {  
                	Setting.this.sampleRate = 8000;
                }else  
                {  
                	Setting.this.sampleRate = 8000;
                }  
            }  
        });  
  
        
        adapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,thresholds);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        spinner1.setAdapter(adapter);  
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
        {  
  
            @Override  
            public void onItemSelected(AdapterView<?> arg0, View arg1,  
                    int arg2, long arg3) {  
                // TODO Auto-generated method stub  
            	threshold = thresholds[arg2];
                arg0.setVisibility(View.VISIBLE);  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> arg0) {  
                // TODO Auto-generated method stub  
            }  
              
        });  
        spinner1.setPrompt("阈值选择");
        int spinnerPosition1 = adapter.getPosition(threshold);
        spinner1.setSelection(spinnerPosition1,true);
        
        ArrayAdapter<Integer> adapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,mintimes);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        spinner2.setAdapter(adapter);  
        spinner2.setOnItemSelectedListener(new Spinner.OnItemSelectedListener()  
        {  
  
            @Override  
            public void onItemSelected(AdapterView<?> arg0, View arg1,  
                    int arg2, long arg3) {  
                // TODO Auto-generated method stub  
            	minTimes= mintimes[arg2];
                arg0.setVisibility(View.VISIBLE);  
            }  
  
            @Override  
            public void onNothingSelected(AdapterView<?> arg0) {  
                // TODO Auto-generated method stub  
                  
            }  
              
        });  
        spinner2.setPrompt("最低次数");
        int spinnerPosition2 = adapter.getPosition(minTimes);
        spinner2.setSelection(spinnerPosition2,true);
        
        button_ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setPreferences();
				Toast.makeText(Setting.this, "设置成功！", 2000).show();
			}
		});

	}
	
	  private void readPreferences() {
		    SharedPreferences preferences = getSharedPreferences("snorehunter",
		        MODE_PRIVATE);
		    sampleRate = preferences.getInt("samplerate", 8000);
		    threshold = preferences.getInt("threshold", 10);
		    minTimes = preferences.getInt("mintimes", 4);
		  }
	  
	  private void setPreferences() {
		    SharedPreferences preferences = getSharedPreferences("snorehunter",
		        MODE_PRIVATE);
		    SharedPreferences.Editor editor = preferences.edit();

		    editor.putInt("samplerate", sampleRate);
		    editor.putInt("threshold", threshold);
		    editor.putInt("mintimes", minTimes);
		    editor.commit();
		  }




}
