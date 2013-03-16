package com.example.papercult;

//import java.util.Vector;
import java.util.Vector;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class PaperView extends View {
	Context con;
	boolean mouseClick = false;
	boolean haveLine = false;
	boolean isUp;
	PointF start = new PointF();
	PointF end = new PointF();
	PointF nStart = new PointF();
	PointF nEnd = new PointF();
	PointF mid = new PointF();
	PointF[] p1 = new PointF[3];
	PointF[] p1p = new PointF[3];
	
	Vector<Vertex> vVertex;
//	Vector<Vertex> tempvVertex;
	Path path = new Path();
	Paint mPaint;
	Paint oPaint;
	
	float p1X, p1Y;
	float p2X, p2Y;
	

	float incline, intercept, tempIncline; // a -> incline, b -> intercept
	
	
	public class Vertex{
		float x;
		float y;
		
		Vertex(float ax, float ay){
			x = ax;
			y = ay;
		}
	}
	
	public PaperView(Context context) {
		super(context);
		con = context;
		vVertex.add(new Vertex(150, 350));
		vVertex.add(new Vertex(650, 350));
		vVertex.add(new Vertex(650, 850));
		vVertex.add(new Vertex(150, 850));
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(0x40ff0000);
		
		oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		oPaint.setColor(Color.BLACK);
		oPaint.setStyle(Paint.Style.STROKE);
		for(int i=0; i<3; i++){
			p1[i] = new PointF();
			p1p[i] = new PointF();
		}
		p1[0].x = 650;
		p1[0].y = 300; 
		p1[1].x = 200;
		p1[1].y = 940;
		p1[2].x = 700;
		p1[2].y = 100;
		p1p[0].x = p1p[0].y = 0;
		p1p[1].x = p1p[1].y = 0;
		p1p[2].x = p1p[2].y = 0;
		
	}
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			mouseClick = true;
			start.x = (int)event.getX();
			start.y = (int)event.getY();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			end.x = (int)event.getX();
			end.y = (int)event.getY();
			
			incline = (start.y - end.y) / (start.x - end.x); //선택한 지점사이의 기울기
			tempIncline = incline;
			incline = -(1 / incline); // 직각을 이루는 기울기
			
			mid.x = (start.x + end.x)/2;
			mid.y = (start.y + end.y)/2;
			
			intercept = mid.y - (incline*mid.x); // y절편값
			
			nStart.x = 0;
			nStart.y = intercept;
			
			nEnd.x = 800;
			nEnd.y = incline*nEnd.x + intercept;
			
			if(start.y > incline*start.x+intercept)
				isUp = true;
			else
				isUp = false;
//			haveLine = true;
			/*
			nStart.x = 0;
			nStart.y = incline * (nStart.x - mid.x) + mid.y;
			
			nEnd.x = 800;
			nEnd.y = incline * (nEnd.x - mid.x) + mid.y;
			*/
			this.invalidate();	//?
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(mouseClick == true){
				mouseClick = false;
				haveLine = true;		//ACTION_UP
			}
			for(int i=0; i<3; i++){
				if(isUp){
					if(p1[i].y > incline * p1[i].x + intercept){
						p1p[i].x = (p1[i].x*(1 - incline*incline)-2*incline*(intercept-p1[i].y)) / (incline*incline + 1);
						p1p[i].y = (p1[i].y*(incline*incline - 1)+2*(incline*p1[i].x + intercept)) / (incline*incline +1);
					}
					else{
						p1p[i].x = p1p[i].y = 0;
					}
				}
				else{
					if(p1[i].y < incline * p1[i].x + intercept){
						p1p[i].x = (p1[i].x*(1 - incline*incline)-2*incline*(intercept-p1[i].y)) / (incline*incline + 1);
						p1p[i].y = (p1[i].y*(incline*incline - 1)+2*(incline*p1[i].x + intercept)) / (incline*incline +1);
					}
					else{
						p1p[i].x = p1p[i].y = 0;
					}
				}
			}
			
			invalidate();
			return true;
		}
		return false;
	}
	
	public void onDraw(Canvas canvas){
		
		//사각형 종이 + path
		//
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		Paint Pnt = new Paint();
		Pnt.setStrokeWidth(5);
		Pnt.setColor(Color.RED);
		
		if(haveLine == true)		//ACTION_UP
		{
			haveLine = false;
			canvas.drawLine(start.x, start.y, end.x, end.y, Pnt);
			canvas.drawLine(nStart.x, nStart.y, nEnd.x, nEnd.y, Pnt);
			Pnt.setColor(Color.BLUE);
			canvas.drawPoint(mid.x, mid.y, Pnt);
			
			for(int i=0; i<3; i++){
				Pnt.setColor(Color.RED);
				canvas.drawPoint(p1[i].x, p1[i].y, Pnt);
				 if(p1p[i].x != 0){
					 Pnt.setColor(Color.BLUE);
					 canvas.drawPoint(p1p[i].x, p1p[i].y, Pnt);
				 }
			}
		}
	}
}


