package com.example.papercult;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.PointF;


public class Paper {
	Polygon baseRect = new Polygon();
	Vector<Polygon> poly = new Vector<Polygon>();
	Vector<Polygon> base = new Vector<Polygon>();
	
	Paper(float left, float top, float right, float bottom){
		baseRect.add(new PointF(left,top));
		baseRect.add(new PointF(right,top));
		baseRect.add(new PointF(right,bottom));
		baseRect.add(new PointF(left,bottom));
		reset();
	}
	
	public void foldStart (PointF touchStart, PointF touchEnd){
		poly.clear();
		
		for(int i=0; i<base.size(); i++){
			Polygon cut = base.get(i).cutPolygon(touchStart, touchEnd);    
			Polygon pull = base.get(i).pullPolygon(touchStart, touchEnd);  
			
			if(cut != null)
				poly.add(cut);
			if(pull != null)
				poly.add(pull);
		}
	}
	
	public void foldEnd (){
		base.clear();
		base = (Vector<Polygon>)poly.clone();
	}
	
	public void reset (){
		poly.removeAllElements();
		poly.add(baseRect);
		base = (Vector<Polygon>)poly.clone();
	}
	
	public void draw(Canvas canvas){
		for(int i=0; i<poly.size(); i++){
			poly.get(i).draw(canvas);
		}
	}
	
	public boolean comparePolygon(Polygon p){
		return true;
	}
	
	private boolean compareLine(PointF startA, PointF endA, PointF startB, PointF endB){
		return true;
	}
	
	private boolean comparePoint(PointF pointA, PointF pointB, float distance){
		return true;
	}
	
}
