package com.example.runingball;

import java.util.ArrayList;

import com.runing.ball.Ball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class BallsurfaceView extends SurfaceView implements Callback, Runnable {

	private Canvas canvas;
	private SurfaceHolder mHolder;
	private Ball ball;
	private Thread drawBall;
	private RuningBallThread runingBallThread;
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
			// 加入一个小球
			if (Tools.ADDBALL) {
				update(true);	
			}
			try {
				Thread.sleep(100);
                //获得canvas
				if (mHolder == null) {
					return;
				}
				canvas = mHolder.lockCanvas();
				if (canvas == null) {
					return;
				}
                canvas.drawColor(Color.WHITE);
                //画圆
                runingBallThread.ondraw(canvas);
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
        //画图线程
		drawBall = new Thread(this);
		drawBall.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		drawBall=null;
	}

	private void init() {
		// 获得并注册SurfaceHolder
		mHolder = getHolder();
		mHolder.addCallback(this);

		ball = new Ball();
		list.add(ball);
		//开启小球运动线程
		runingBallThread=new RuningBallThread(list);
		runingBallThread.start();
		runingBallThread.setOneBall(true);
	}

	private void rePaint() {
		//清空list
		list.clear();
		//重新创建小球
		ball = new Ball();
		list.add(ball);
		//开启小球运动线程
		runingBallThread.setList(list);
		runingBallThread.setOneBall(true);
		BallsurfaceView.flag = true;
		//开启画图线程
		drawBall = new Thread(this);
		drawBall.start();
	}

	private void update(boolean next) {
		Ball saveBall[] = new Ball[list.size()];
		int j = 0;
		// 取出list中的球类信息
		for (Ball getBall : list) {
			saveBall[j++] = getBall;
		}
		// 清空旧表
		list.clear();
		// 刷新
		for (int i = 0; i < j; i++) {
			list.add(saveBall[i]);
		}
		// 如果next为真，新建小球
		if (next) {
			ball = new Ball();
			list.add(ball);
			Tools.ADDBALL = false;
		}
		//修改小球里的信息
		runingBallThread.setList(list);
		runingBallThread.setOneBall(false);
	}

}
