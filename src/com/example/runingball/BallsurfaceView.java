package com.example.runingball;

import java.util.ArrayList;

import com.runing.ball.Ball;
import com.runing.ball.BallThread;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BallsurfaceView extends SurfaceView implements Callback, Runnable {

	private Canvas canvas;
	private SurfaceHolder mHolder;
	private Ball ball;
	private Thread ballRun;
	public static boolean flag = true;
	ArrayList<Ball> list = new ArrayList<Ball>();

	public BallsurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public BallsurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (BallsurfaceView.flag) {
			if (Tools.ADDBALL) {
				update();
			}
			try {
				Thread.sleep(100);
				if (mHolder == null) {
					return;
				}
				canvas = mHolder.lockCanvas();
				if (canvas == null) {
					return;
				}
				canvas.drawColor(Color.WHITE);
				for (Ball mball : list) {
					mball.drawCircle(canvas);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					mHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
		if (!BallsurfaceView.flag) {
			rePaint();
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

		ballRun = new Thread(this);
		ballRun.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub

	}

	private void init() {
		// 获得并注册SurfaceHolder
		mHolder = getHolder();
		mHolder.addCallback(this);

		ball = new Ball();
		list.add(ball);
	}

	private void rePaint() {
		list.clear();
		Tools.HASRADIUS = false;
		ball=new Ball();
		list.add(ball);
		BallsurfaceView.flag = true;
		ballRun =new Thread(this);
		ballRun.start();
	}

	private void update (){
		Ball saveBall[] = new Ball [list.size()];
		int j=0;
		//取出list中的球类信息
		for (Ball getBall : list){
			saveBall[j++]=getBall;
		}
		//清空旧表
		list.clear();
		//刷新
		for (int i=0;i<j;i++){
			list.add(saveBall[i]);
		}
		ball =new Ball();
		list.add(ball);
		Tools.ADDBALL=false;
	}
}
