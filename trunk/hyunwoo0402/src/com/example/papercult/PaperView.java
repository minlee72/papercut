package com.example.papercult;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.view.*;
import android.widget.*;

public class PaperView extends View {
	Paper paper;
	StageObject sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	PointF frontPoint = new PointF();
	PointF frontEnd = new PointF();
	public GLView glv;
	boolean click = false;
	
	int isSoundPlay = 0;
	SoundPool sound_pool;
	int sound_beep;
	int changeLength;
	int count=0;
	Context con;
	
	CountDownTimer cdt;
	
	public PaperView(Context context) {
		super(context);
		con = context;
		paper = new Paper(50,50,600,600);
		
		Polygon inner = new Polygon();
		inner.add(new PointF(400,100));
		inner.add(new PointF(400,400));
		inner.add(new PointF(100,400));
		
		Polygon outer = new Polygon();
		outer.add(new PointF(380,80));
		outer.add(new PointF(420,80));
		outer.add(new PointF(420,420));
		outer.add(new PointF(60,420));
		
		cdt = new CountDownTimer(10000, 1000){
			public void onTick(long millisUntilFinished){
				/*
				frontEnd = frontPoint;
				changeLength += (int)getChangeLength(frontEnd, touchEnd);
				frontPoint = frontEnd; */
				count++;
				sound_pool.setVolume(sound_beep, 0.1f*count, 0.1f*count);
			}
			
			public void onFinish(){
				count = 0;
				sound_pool.stop(sound_beep);
				/*
				Toast.makeText(con, "no"+" "+"count= "+count+"\nCL= "+changeLength, Toast.LENGTH_SHORT).show();
				if(changeLength<30){
					sound_pool.setVolume(sound_beep, 0.4f, 0.4f);
				}
				else if(changeLength <70){
					sound_pool.setVolume(sound_beep, 0.6f, 0.6f);
				}
				else if(changeLength <110){
					sound_pool.setVolume(sound_beep, 0.8f, 0.8f);
				}
				else if(changeLength <150){
					sound_pool.setVolume(sound_beep, 1f, 1f);
				}
//				else if(changeLength <90){
//					sound_pool.play(sound_beep, 1.2f, 1.2f, 1, 0, 0.6f);
//				}
				else{
					sound_pool.setVolume(sound_beep, 1.5f, 1.5f);
				}
				changeLength = 0;
				cdt.start();
				*/
			}
		};
		
		sObj = new StageObject(inner, outer);
		resetPolygon();
		
		sound_pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sound_beep = sound_pool.load(getContext(), R.raw.kalimba, 1);
	}
	
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (click == false)
				glv.onTouchEvent(event);
			sound_pool.play(sound_beep, 0f, 0f, 1, -1, 0.2f);
			touchStart.x = event.getX();
			touchStart.y = event.getY();
			changeLength = 0;
			frontPoint = touchStart;
			cdt.start();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paper.foldStart(touchStart, touchEnd);
			}
			this.invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			paper.foldEnd();
//			if (sObj.clearCheck(paper, 90, 20) == true)
//				Toast.makeText(this.getContext(), "Clear"+" "+"count= "+count+"\nCL= "+changeLength, Toast.LENGTH_SHORT).show();
//			else
//				Toast.makeText(this.getContext(), "no"+" "+"count= "+count+"\nCL= "+changeLength, Toast.LENGTH_SHORT).show();	
			click = false;
			changeLength = 0;
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
		sObj.innerPolyDraw(canvas);
		sObj.outerPolyDraw(canvas);
		paper.draw(canvas);
	}
	
	public double getChangeLength(PointF start, PointF end){
		double result;
		result = Math.sqrt(((end.x - start.x) * (end.x - start.x)) + ((end.y - start.y) * (end.y - start.y)));
		return result;
	}
}


