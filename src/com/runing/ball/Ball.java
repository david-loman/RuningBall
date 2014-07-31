package com.runing.ball;

import java.util.Random;

import com.example.runingball.Tools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

public class Ball {

	//小球圆心坐标x，y
	private int x;
	private int y;
	//小球在水平与素质方向上的速度
	private int increX;
	private int increY;
	//小球底色
	private int color;
	//小球半径
	private int radius=0;
	private Random random;
	private Paint paint;

	public Ball() {
		init();
	}

	// 初始化
	private void init() {
		random = new Random();
		// 获得随机半径
		this.radius = getRandom(0, 50)+30;
		// 获得随机坐标
		this.x = getRandom(radius, Tools.WIDTH - radius);
		this.y = getRandom(radius, Tools.HEIGHT - radius);
		// 获得随机速度
		this.increX = getRandom(-50, 50);
		this.increY = getRandom(-50, 50);
		// 获得随机颜色
		this.color = Color.rgb(getRandom(0, 255), getRandom(0, 255),
				getRandom(0, 255));
	}

	// 获得随机值
	private int getRandom(int min, int max) {
		int value;
		value = random.nextInt(max) % (max - min + 1) + min;
		return value;
	}

	// 画圆
	public void drawCircle(Canvas canvas) {
		paint = new Paint();
		paint.setColor(color);
		paint.setAntiAlias(true);
		canvas.drawCircle(x, y, radius, paint);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIncreX() {
		return increX;
	}

	public void setIncreX(int increX) {
		this.increX = increX;
	}

	public int getIncreY() {
		return increY;
	}

	public void setIncreY(int increY) {
		this.increY = increY;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

}
