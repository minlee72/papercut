package com.example.papercult;

import java.util.Vector;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;


public class PaperView extends View {
	Vector<Polygon> poly = new Vector<Polygon>();
	Vector<Polygon> foldPoly = new Vector<Polygon>();
	Vector<Polygon> temp  = new Vector<Polygon>();
	
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
				
				PointF start = new PointF(touchStart.x, touchStart.y);
				PointF end = new PointF(touchEnd.x, touchEnd.y);
				
				foldPoly.removeAllElements();
				for(int i=0; i<poly.size(); i++){
					Polygon cut = poly.get(i).cutPolygon(start, end);    //종이 접을때 변하지 않는 부분
					Polygon pull = poly.get(i).pullPolygon(start, end);    //종이 접을때 넘겨지는 부분
					
					if(cut != null)
						foldPoly.add(cut);
					if(pull != null)
						foldPoly.add(pull);
				}
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			temp = poly;
			poly = foldPoly;
			foldPoly = temp;
			
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
		poly.removeAllElements();
		foldPoly.removeAllElements();
		Polygon p = new Polygon();
		p.add(new PointF(100,100));
		p.add(new PointF(600,100));
		p.add(new PointF(600,600));
		p.add(new PointF(100,600));
		poly.add(p);
		foldPoly.add(p);
		this.invalidate();
	}
	public void onDraw(Canvas canvas){
		
		if(click == true){
			for(int i=0; i<foldPoly.size(); i++)
				foldPoly.get(i).draw(canvas);
		}
		else{
			for(int i=0; i<poly.size(); i++)
				poly.get(i).draw(canvas);
		}
	}
}


