package com.example.runingball;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.util.Log;

import com.runing.ball.Ball;

public class RuningBallThread extends Thread {

	private boolean oneBall = false;  
	private int x, y, r, k;
	private Ball ball;
	private Ball thisBall[];
	private ArrayList<Ball> list = new ArrayList<Ball>();

	public RuningBallThread(ArrayList<Ball> mlist) {
		this.list = mlist;
		for (Ball mball : list) {
			this.ball = mball;
		}
		this.x = ball.getX();
		this.y = ball.getY();
		this.r = ball.getRadius();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		while (true) {
			try {
				Thread.sleep(100);
				k = 0;
				thisBall = new Ball[list.size()];
				for (Ball mball : list) {
					thisBall[k++] = mball;
				}
				for (int i = 0; i < k; i++) {
					thisBall[i].setX(thisBall[i].getX()+thisBall[i].getIncreX());
				    thisBall[i].setY(thisBall[i].getY()+thisBall[i].getIncreY());
					// 判断两球是否相碰
					for (int j = 0; j < k; j++) {
						int disx = thisBall[i].getX() - thisBall[j].getX();
						int disy = thisBall[i].getY() - thisBall[j].getY();
						int disr = thisBall[i].getRadius()
								+ thisBall[j].getRadius();
						disx *= disx;
						disy *= disy;
						disr *= disr;
						if (!oneBall && i != j && disr >= (disx + disy)) {
							arctan(i, j, disx, disy);
						}
					}
					ball = thisBall[i];
					x = ball.getX() ;
					y = ball.getY() ;
					r = ball.getRadius();
					// 判断边界
					border();
					// 更新
					thisBall[i].setX(x);
					thisBall[i].setY(y);
				}

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 画图
	public void ondraw(Canvas canvas) {
		for (int i = 0; i < k; i++) {
			thisBall[i].drawCircle(canvas);
		}
	}

	// 发生碰撞
	private void border() {
		// 左右边界判断
		if (x <= r) {
			x = r;
			// 速度改变
			ball.setIncreX(-ball.getIncreX());
		} else if (x >= (Tools.WIDTH - r)) {
			x = Tools.WIDTH - r;
			// 速度改变
			ball.setIncreX(-ball.getIncreX());
		}
		// 上下边界判断
		if (y <= r) {
			y = r;
			// 速度改变
			ball.setIncreY(-ball.getIncreY());
		} else if (y >= Tools.HEIGHT - r) {
			y = Tools.HEIGHT - r;
			// 速度改变
			ball.setIncreY(-ball.getIncreY());
		}
	}

	// 碰撞后触发
	private void arctan(int i, int j, int disx, int disy) {
		// 两球相距的距离
		disx = thisBall[j].getX() - thisBall[i].getX();
		disy = thisBall[j].getY() - thisBall[i].getY();
		int weighti=thisBall[i].getRadius()*thisBall[i].getRadius()/2;
		int weightj=thisBall[j].getRadius()*thisBall[j].getRadius()/2;
		// 线段的斜角
		double ang = Math.atan2(disy, disx);
		double sinVal = Math.sin(ang);
		double cosVal = Math.cos(ang);
		// 将球以i为中心翻转
		double xi = 0;
		double yi = 0;
		double xj = disx * cosVal + disy * sinVal;
		double yj = disy * cosVal - disx * sinVal;
		// 速度分解
		double vxi = thisBall[i].getIncreX() * cosVal + thisBall[i].getIncreY()
				* sinVal;
		double vyi = thisBall[i].getIncreY() * cosVal - thisBall[i].getIncreX()
				* sinVal;
		double vxj = thisBall[j].getIncreX() * cosVal + thisBall[j].getIncreY()
				* sinVal;
		double vyj = thisBall[j].getIncreY() * cosVal - thisBall[j].getIncreX()
				* sinVal;
		//处理分解后的速度
		double vxiFin = (2 * weightj * vxj + (weighti - weightj) * vxi)
				/ (weighti + weightj);
		double vxjFin= (2 * weighti * vxi + (weightj - weighti) * vxj)
				/ (weighti + weightj);;
        //处理碰撞的坐标
		int sumR=thisBall[i].getRadius()+thisBall[j].getRadius();
		double overlap=sumR-Math.abs(xi-xj);
		//重叠的比重
		double iover =thisBall[i].getRadius()/(sumR*1.0);
		double jover =thisBall[j].getRadius()/(sumR*1.0);
		if (overlap>0){
			if (xi>xj){
				xi+=overlap*iover;
				xj-=overlap*jover;
			}
			else{
				xi-=overlap*iover;
				xj+=overlap*jover;
			}
		}
		//反算会x，y坐标
		double xiFin=xi*cosVal-yi*sinVal;
		double yiFin=yi*cosVal+xi*sinVal;
		double xjFin=xj*cosVal-yj*sinVal;
		double yjFin=yj*cosVal+xj*sinVal;
		//设置x，y位置
		thisBall[j].setX((int)Math.round(thisBall[i].getX()+xjFin));
		thisBall[j].setY((int)Math.round(thisBall[i].getY()+yjFin));
		thisBall[i].setX((int)Math.round(thisBall[i].getX()+xiFin));
		thisBall[i].setY((int)Math.round(thisBall[i].getY()+yiFin));
		//速度反算
		thisBall[i].setIncreX((int)Math.round(vxiFin*cosVal-vyi*sinVal));
		thisBall[i].setIncreY((int)Math.round(vyi*cosVal+vxiFin*sinVal));
		thisBall[j].setIncreX((int)Math.round(vxjFin*cosVal-vyj*sinVal));
		thisBall[j].setIncreY((int)Math.round(vyj*cosVal+vxjFin*sinVal));
	}

	public void setList(ArrayList<Ball> list) {
		this.list = list;
	}

	public void setOneBall(boolean oneBall) {
		this.oneBall = oneBall;
	}

}
