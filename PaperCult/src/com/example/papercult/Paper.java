package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.PointF;


public class Paper {
	Polygon baseRect = new Polygon();
	Vector<Polygon> poly = new Vector<Polygon>();
	Vector<Polygon> base  = new Vector<Polygon>();
	
	Paper(){
		baseRect.add(new PointF(100,100));
		baseRect.add(new PointF(600,100));
		baseRect.add(new PointF(600,600));
		baseRect.add(new PointF(100,600));
		reset();
	}
	
	void foldStart (PointF touchStart, PointF touchEnd){
		poly.clear();
		
		for(int i=0; i<base.size(); i++){
			Polygon cut = base.get(i).cutPolygon(touchStart, touchEnd);    //종이 접을때 변하지 않는 부분
			Polygon pull = base.get(i).pullPolygon(touchStart, touchEnd);    //종이 접을때 넘겨지는 부분
			
			if(cut != null)
				poly.add(cut);
			if(pull != null)
				poly.add(pull);
		}
	}
	
	void foldEnd (){
		base.clear();
		base = (Vector<Polygon>)poly.clone();
	}
	
	void reset (){
		poly.removeAllElements();
		poly.add(baseRect);
		base = (Vector<Polygon>)poly.clone();
	}
	
	void draw(Canvas canvas){
		for(int i=0; i<poly.size(); i++){
			poly.get(i).draw(canvas);
		}
	}
}
