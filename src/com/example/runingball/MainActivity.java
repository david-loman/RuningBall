package com.example.runingball;

import android.os.Bundle;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button addButton ;
	private Button resetButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏显示
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_main);
		// 获取屏幕像素值
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		Tools.WIDTH = metrics.widthPixels;
		Tools.HEIGHT = metrics.heightPixels;
		
		addButton =(Button)findViewById(R.id.addButton);
		resetButton =(Button)findViewById(R.id.resetButton);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//增加小球
		addButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Tools.ADDBALL=true;
			}
		});
		
		resetButton.setOnClickListener(new View.OnClickListener() {
			//重置小球
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			    BallsurfaceView.flag=false;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
