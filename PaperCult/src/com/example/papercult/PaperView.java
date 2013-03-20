package com.example.papercult;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class PaperView extends View {
	Context con;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	boolean click = false;
	
	public PaperView(Context context) {
		super(context);
		con = context;
		
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			click = true;
			touchStart.x = event.getX();
			touchStart.y = event.getY();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			click = false;
			this.invalidate();
			return true;
		}
		return false;
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.LTGRAY);
		
		Polygon p = new Polygon();
		
		p.add(new PointF(100,100));
		p.add(new PointF(200,100));
		p.add(new PointF(200,200));
		p.add(new PointF(100,200));
		
		Polygon cut = p.cutPolygon(new PointF(touchStart.x, touchStart.y), new PointF(touchEnd.x, touchEnd.y));
		Polygon pull = p.pullPolygon(new PointF(touchStart.x, touchStart.y), new PointF(touchEnd.x, touchEnd.y));
		
		cut.draw(canvas);
		pull.draw(canvas);
	}
}


