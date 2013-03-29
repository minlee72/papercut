package com.example.papercult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PaperView extends View {
	Paper paper;
	StageObject sObj;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	public BGView bgView;
	boolean click = false;
	
	public PaperView(Context context) {
		super(context);
		this.setBackgroundColor(Color.BLUE);
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
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if (click == false)
				bgView.onTouchEvent(event);
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


