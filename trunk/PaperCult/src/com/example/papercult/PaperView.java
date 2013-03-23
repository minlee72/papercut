package com.example.papercult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;


public class PaperView extends View {
	Paper paper = new Paper();
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public GLView glv;
	boolean click = false;
	
	public PaperView(Context context) {
		super(context);
		resetPolygon();
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			glv.onTouchEvent(event);
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				
				paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			paper.foldEnd();
			click = false;
			return true;
		}
		return false;
	}
	public void reTouchEvent(MotionEvent event){
		if(click == false){
			click = true;
			touchStart.x = event.getX();
			touchStart.y = event.getY();
		}
	}
	public void resetPolygon(){
		paper.reset();
		this.invalidate();
	}
	public void onDraw(Canvas canvas){
		paper.draw(canvas);
	}
}


