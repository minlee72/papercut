package com.example.papercult;

import java.util.Vector;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;


public class Polygon {
	Vector<PointF> pointVector;
	Path pointPath;
	
	public Polygon(){
		pointVector = new Vector<PointF>();
		pointPath = new Path();
	}
	
	public Polygon fold(PointF start, PointF end){
		
		
		Polygon p = new Polygon();
		return p;
	}
	public Vector<PointF> cutPolygon (Vector<PointF> polygon, PointF tlStart, PointF tlEnd){
		Vector<PointF> result = new Vector<PointF>();
		for (int i = 0; i<polygon.size(); i++){
			
		}
		return result;
	}
	public PointF getSymmetryPoint(PointF targetPoint, PointF tlStart, PointF tlEnd){
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
	
	public PointF getCrossPoint(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
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
	
	public boolean isInline(PointF lStart, PointF lEnd, PointF crossPoint){
		RectF lineRect = new RectF(lStart.x, lStart.y, lEnd.x, lEnd.y);
		lineRect.sort();
		return lineRect.contains(crossPoint.x, crossPoint.y);
	}
	
	public PointF selectPoint(PointF lStart, PointF lEnd, PointF tlStart, PointF tlEnd){
		PointF p = new PointF();
		return p;
	}
	
	public float getGradient(PointF start, PointF end){
		return (start.y - end.y)/(start.x - end.x);
	}
	
	public float getIntercept(PointF start, float gradient){
		return start.y - (gradient * start.x);
	}
	
	public PointF getCenterPoint(PointF start, PointF end){
		PointF p = new PointF();
		p.x = (start.x + end.x) / 2;
		p.y = (start.y + end.y) / 2;
		return p;
	}
}
