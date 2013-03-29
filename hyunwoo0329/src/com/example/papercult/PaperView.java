package com.example.papercult;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.view.*;
import android.widget.*;

public class PaperView extends View {
	Paper paper;
	StageObject sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public GLView glv;
	boolean click = false;
	
	int isSoundPlay = 0;
	SoundPool sound_pool;
	int sound_beep;
	
	public PaperView(Context context) {
		super(context);
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
		
		sObj = new StageObject(inner, outer);
		resetPolygon();
		
		sound_pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sound_beep = sound_pool.load(getContext(), R.raw.fold3, 1);
	}
	
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (click == false)
				glv.onTouchEvent(event);
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(isSoundPlay != 0){
				sound_pool.pause(sound_beep);
			}
			else{
				isSoundPlay = sound_pool.play(sound_beep, 1f, 1f, 1, -1, 0.7f);
			}
			
			sound_pool.resume(sound_beep);
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
			sound_pool.pause(sound_beep);
			paper.foldEnd();
			if (sObj.clearCheck(paper, 90, 20) == true)
				Toast.makeText(this.getContext(), "Clear", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(this.getContext(), "no", Toast.LENGTH_SHORT).show();	
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
		sObj.innerPolyDraw(canvas);
		sObj.outerPolyDraw(canvas);
		paper.draw(canvas);
	}
}


