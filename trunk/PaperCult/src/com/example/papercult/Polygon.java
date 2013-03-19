package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;


public class Polygon {
	Vector<PointF> pointVector;
	
	public Polygon(){
		pointVector = new Vector<PointF>();
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
		
		if ((tlStart.y-tlEnd.y)==0){   //���Ⱑ ������ �� (���� ���� 0 �̿��� ����� �ȵ�)
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
		else {  //���Ⱑ ������ �ƴ� ��
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
		
		PointF center = new PointF();                                          //��ġ ������ ��� ��
		center = getCenterPoint(tlStart, tlEnd);                             
		
		float tlGradient = getGradient(tlStart, tlEnd);	  //��ġ ������ �����̵Ǵ� ����
		tlGradient = -1 / tlGradient;
		
		float lGradient = getGradient(lStart, lEnd);
		float lIntercept = getIntercept(lStart, lGradient);
		
		if (((lStart.x-lEnd.x)==0) && ((tlStart.y-tlEnd.y)==0))           //����&����
			return null;
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.x-tlEnd.x)==0))    //����&����
			return null;
		else if (((lStart.x-lEnd.x)==0) && ((tlStart.x-tlEnd.x)==0)){   //����&����
			result.x = lStart.x;
			result.y = center.y;
		}
		else if (((lStart.y-lEnd.y)==0) && ((tlStart.y-tlEnd.y)==0)){  //����&����
			result.x = center.x;
			result.y = lStart.y;
		}
		else if ((lStart.y-lEnd.y)==0){                                          //����&�밢��
			result.y = lStart.y;
			result.x = (result.y + (tlGradient*center.x) - center.y) / tlGradient;
		}
		else if ((lStart.x-lEnd.x)==0){                                          //����&�밢��
			result.x = lStart.x;
			result.y = (tlGradient * (result.x-center.x)) + center.y;
		}
		else if ((tlStart.x-tlEnd.x)==0){                                       //�밢��&����
			result.y = center.y;
			result.x = (result.y - lIntercept) / lGradient; 			
		}
		else if ((tlStart.y-tlEnd.y)==0){                                      //�밢��&����
			result.x = center.x;
			result.y = lGradient * result.x + lIntercept;
		}
		else{                                                                          //�밢��&�밢��
			result.x = (center.y - (tlGradient*center.x) - lIntercept) / (lGradient - tlGradient);
			result.y = lGradient * result.x + lIntercept;
		}
		return result;
	}
	
	public boolean isInline(PointF lStart, PointF lEnd, PointF cPoint){
		float big, small, mid;
		
		if((lStart.x-lEnd.x)==0){                                                         //�����϶�
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
	
	public boolean containsPoint(PointF point, PointF tlStart, PointF tlEnd){
		float tlGradient = getGradient(tlStart, tlEnd);	  //��ġ ������ �����̵Ǵ� ����
		tlGradient = -1 / tlGradient;
		
		PointF center = new PointF();                                          //��ġ ������ ��� ��
		center = getCenterPoint(tlStart, tlEnd);  
		
		if ((tlStart.y-tlEnd.y)==0){                     //�����϶�
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
	}
}
