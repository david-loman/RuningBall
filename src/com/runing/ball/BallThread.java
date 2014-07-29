package com.runing.ball;

import com.example.runingball.Tools;

public class BallThread extends Thread {

	private Ball ball;
	private int x, y, r;
	private static boolean flag = true;

	public BallThread(Ball ball) {
		this.ball = ball;
		this.x = ball.getX();
		this.y = ball.getY();
		this.r = ball.getRadius();
	}

	public void run() {
		while (flag) {
			try {
				x = x + ball.getIncreX();
				y = y + ball.getIncreY();

				collision();
				//将值传回ball中
				ball.setX(x);
				ball.setY(y);
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 发生碰撞
	private void collision() {
		int sub;
		// 左右边界判断
		if (x <= r) {
			sub = r - x;
			x = r;
			y = y + (sub * ball.getIncreY() / ball.getIncreX());
			// 速度改变
			ball.setIncreX(-ball.getIncreX());
		} else if (x >= (Tools.WIDTH - r)) {
			sub = Tools.WIDTH - r - x;
			x = Tools.WIDTH - r;
			y = y + (sub * ball.getIncreY() / ball.getIncreX());
			// 速度改变
			ball.setIncreX(-ball.getIncreX());
		}
		// 上下边界判断
		if (y <= r) {
			sub = r - y;
			y = r;
			x = x + (sub * ball.getIncreX() / ball.getIncreY());
			// 速度改变
			ball.setIncreY(-ball.getIncreY());
		} else if (y >= Tools.HEIGHT - r) {
			sub = Tools.HEIGHT - r - y;
			y = Tools.HEIGHT - r;
			x = x + (sub * ball.getIncreX() / ball.getIncreY());
			// 速度改变
			ball.setIncreY(-ball.getIncreY());
		}
	}

}
