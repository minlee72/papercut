package com.example.test;

import java.util.Vector;
import android.graphics.Canvas;
import android.graphics.PointF;


public class Paper {
	Polygon baseRect = new Polygon();
	Vector<Polygon> poly = new Vector<Polygon>();
	Vector<Polygon> base = new Vector<Polygon>();
	
	Paper(){
		baseRect.add(new PointF(100,100));
		baseRect.add(new PointF(600,100));
		baseRect.add(new PointF(600,600));
		baseRect.add(new PointF(100,600));
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
	
	public boolean contains(Polygon p, float x, float y) {
		Vector<PointF> v = p.pointVector;
        if ( v.size() <= 2) {
            return false;
        }
        int hits = 0;

        int lastx = (int)v.get(v.size()-1).x;
        int lasty = (int)v.get(v.size()-1).y;
        int curx, cury;

        // Walk the edges of the polygon
        for (int i = 0; i < v.size(); lastx = curx, lasty = cury, i++) {
            curx = (int)v.get(i).x;
            cury = (int)v.get(i).y;

            if (cury == lasty) {
                continue;
            }

            int leftx;
            if (curx < lastx) {
                if (x >= lastx) {
                    continue;
                }
                leftx = curx;
            } else {
                if (x >= curx) {
                    continue;
                }
                leftx = lastx;
            }

            double test1, test2;
            if (cury < lasty) {
                if (y < cury || y >= lasty) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - curx;
                test2 = y - cury;
            } else {
                if (y < lasty || y >= cury) {
                    continue;
                }
                if (x < leftx) {
                    hits++;
                    continue;
                }
                test1 = x - lastx;
                test2 = y - lasty;
            }

            if (test1 < (test2 / (lasty - cury) * (lastx - curx))) {
                hits++;
            }
        }

        return ((hits & 1) != 0);
    }
}
