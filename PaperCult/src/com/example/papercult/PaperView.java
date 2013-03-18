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
			return true;
		}
		return false;
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawColor(Color.LTGRAY);
		
		Polygon p = new Polygon();
		PointF point1 = new PointF();
		PointF point2 = new PointF();
		PointF pointa = new PointF();
		PointF pointb = new PointF();
	
	
		
		point1.set(touchStart.x	, touchStart.y);
		point2.set(touchEnd.x, touchEnd.y);
		
		float gradient = p.getGradient(point1, point2);
		float intercept = p.getIntercept(point1, gradient);
		
		pointa.x = 300;
		pointa.y = 300;
		
		/*
		pointb.x = (pointa.x * ((1) - (gradient*gradient))) - ((2*gradient) * (-1 * pointa.y + intercept));
		pointb.x = pointb.x / ((gradient * gradient) + (1));
		
		pointb.y = (pointa.y * ((gradient*gradient)-1)) - ((2*-1)*(gradient*pointa.x + intercept));
		pointb.y = pointb.y / ((gradient * gradient) + (1));
		*/
		
		pointb = p.getSymmetryPoint(pointa, touchStart, touchEnd);
		
		
		Paint Pnt = new Paint();
		Pnt.setStrokeWidth(5);
		Pnt.setColor(Color.RED);
		Pnt.setStyle(Paint.Style.STROKE);
		
		Path path1 = new Path();
		Path path2 = new Path();
		
		path1.reset();
		path1.moveTo(point1.x, point1.y);
		path1.lineTo(point2.x, point2.y);
		
		
		canvas.drawPath(path1, Pnt);
		canvas.drawPath(path2, Pnt);
		
		Pnt.setColor(Color.BLUE);
		canvas.drawPoint(pointa.x, pointa.y, Pnt);
		
		Pnt.setColor(Color.GREEN);
		canvas.drawPoint(pointb.x, pointb.y, Pnt);
		
		
	}
}


