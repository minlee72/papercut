package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

public class Polygon {
	Vector<PointF> pointVector;
	
	public Polygon(){
		pointVector = new Vector<PointF>();
	}
	
	public Polygon pullPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);
			PointF nextP;
			if (i != (pointVector.size()-1)){
				nextP = pointVector.get(i+1);
			}
			else{
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == true){
				result.add(getSymmetryPoint(hereP, tlStart, tlEnd));
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){
				result.add(crossPoint);
			}
		}
		return result;
	}
	
	public Polygon cutPolygon (PointF tlStart, PointF tlEnd){
		if(pointVector.size() < 1)
			return null;	
		
		Polygon result = new Polygon();
		
		for (int i = 0; i<(pointVector.size()); i++){
			PointF hereP = pointVector.get(i);
			PointF nextP;
			if (i != (pointVector.size()-1)){
				nextP = pointVector.get(i+1);
			}
			else{
				nextP = pointVector.get(0);
			}
					
			if(containsPoint(hereP, tlStart, tlEnd) == false){
				result.add(hereP);
			}
			PointF crossPoint = getCrossPoint(hereP, nextP, tlStart, tlEnd);
			if(isInline(pointVector.get(i), nextP, crossPoint) == true){
				result.add(crossPoint);
			}
		}
		return result;
	}
	
	public void clear(){
		pointVector.clear();
	}
	
	public void add(PointF p){
		pointVector.add(p);
	}
	
	public void draw(Canvas canvas){
		Paint Pnt = new Paint();
		Pnt.setStrokeWidth(5);
		Pnt.setColor(Color.RED);
		Pnt.setStyle(Paint.Style.STROKE);
		
		Path path = new Path();
		path.reset();
		
		for(int i = 0; i<pointVector.size(); i++){
			path.moveTo(pointVector.get(i).x, pointVector.get(i).y);
			if(i == (pointVector.size()-1))
				path.lineTo(pointVector.get(0).x, pointVector.get(0).y);
			else
				path.lineTo(pointVector.get(i+1).x, pointVector.get(i+1).y);
		}
		canvas.drawPath(path, Pnt);
	}
	
	private PointF getSymmetryPoint(PointF targetPoint, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		float gradient = getGradient(tlStart, tlEnd);
		gradient = -1 / gradient;
		
		PointF center = new PointF();                                        
		center = getCenterPoint(tlStart, tlEnd); 
		
		float intercept = center.y - (gradient * center.x);
		
		if ((tlStart.y-tlEnd.y)==0){   //기울기가 수직일 때 (기울기 값이 0 이여서 계산이 안됨)
			if(center.x > targetPoint.x){
				result.x = targetPoint.x + ((center.x - targetPoint.x) * 2);
			}
			else if(center.x < targetPoint.x){
				result.x = targetPoint.x - ((targetPoint.x - center.x) * 2);
			}
			else{
				result.x = targetPoint.x;
			}
			result.y = targetPoint.y;
		}
		else {  //기울기가 수직이 아닐 때
			result.x = (targetPoint.x * ((1) - (gradient*gradient))) - ((2*gradient) * (-1 * targetPoint.y + intercept));
			result.x = result.x / ((gradient * gradient) + (1));
			
			result.y = (targetPoint.y * ((gradient*gradient)-1)) - ((2*-1)*(gradient*targetPoint.x + intercept));
			result.y = result.y / ((gradient * gradient) + (1));
		}
		return result;
	}
	
	private PointF getCrossPoint(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF result;
		result = new PointF();
		
		PointF center = new PointF();                                          //터치 라인의 가운데 점
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //터치 라인의 수직이되는 기울기
		tlGradient = -1 / tlGradient;
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		
		if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0))           //수직&수직
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0))    //수평&수평
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0)){   //수직&수평
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0)){  //수평&수직
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //수평&대각선
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //수직&대각선
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.x-tlEnd.x)==0){                                       //대각선&수평
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.y-tlEnd.y)==0){                                      //대각선&수직
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //대각선&대각선
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	
	private boolean isInline(PointF lStart, PointF lEnd, PointF cPoint){
		float big, small, mid;
		
		if(cPoint == null)
			return false;
		
		if((lStart.x-lEnd.x)==0){                                                         //수직일때
			big = lStart.y;
			small = lEnd.y;
			mid = cPoint.y;
		}
		else{
			big = lStart.x;
			small = lEnd.x;
			mid =cPoint.x;
		}
		
		if(big<small){
			float temp = big;
			big = small;
			small = temp;
		}
		
		if((big>mid) && (small<mid)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean containsPoint(PointF point, PointF tlStart, PointF tlEnd){
		float tlGradient = getGradient(tlStart, tlEnd);	  //터치 라인의 수직이되는 기울기
		tlGradient = -1 / tlGradient;
		
		PointF center = new PointF();                                          //터치 라인의 가운데 점
		center = getCenterPoint(tlStart, tlEnd);  
		
		if ((tlStart.y-tlEnd.y)==0){                     //수직일때
			if(((point.x>center.x)&&(tlStart.x>center.x)) || ((point.x<center.x)&&(tlStart.x<center.x))){
				return true;
			}
			else
				return false;
		}
		else{
			float pointOnGradY = tlGradient * (point.x - center.x) + center.y;
			float tlStartOnGradY = tlGradient * (tlStart.x - center.x) + center.y;
			if( ((point.y>pointOnGradY)&&(tlStart.y>tlStartOnGradY)) || ((point.y<pointOnGradY)&&(tlStart.y<tlStartOnGradY)) ){
				return true;
			}
			else
				return false;
		}
	}
	
	private float getGradient(PointF start, PointF end){
		return (start.y - end.y)/(start.x - end.x);
	}
	
	private float getIntercept(PointF start, float gradient){
		return start.y - (gradient * start.x);
	}
	
	private PointF getCenterPoint(PointF start, PointF end){
		PointF p = new PointF();
		p.x = (start.x + end.x) / 2;
		p.y = (start.y + end.y) / 2;
		return p;
	}
}
