package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * 접혀지는 다각형을 관리하기 위한 클래스
 * @author 김호진
 *
 */
public class Polygon {
	/**
	 * 다각형의 점 정보를 저장하는 벡터
	 * 0번 인덱스 부터 끝까지 순차적으로 직선으로 이어진다
	 */
	Vector<PointF> pointVector;
	
	/**
	 * 생성자
	 */
	public Polygon(){
		pointVector = new Vector<PointF>();
	}
	
	/**
	 * 다각형이 터치 입력에 따른 기울기에 기준하여 접혀질때, 접혀지지않는 나머지 부분을 구한다
	 * @param tlStart	터치입력 시작점
	 * @param tlEnd   터치입력 끝점
	 * @return   접혀지지 않은 나머지 다각형
	 */
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
	
	/**
	 * 다각형이 터치 입력에 따른 기울기에 기준하여 접혀질때, 접혀지는 부분을 구한다
	 * @param tlStart  터치입력 시작점
	 * @param tlEnd   터치입력 끝점
	 * @return  접혀지는 다각형
	 */
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
	
	/**
	 * 다각형의 점 정보를 저장하는 벡터를 비운다
	 */
	public void clear(){
		pointVector.clear();
	}
	
	/**
	 * 벡터에 점 정보를 추가한다
	 * @param p  추가될 PointF 객체
	 */
	public void add(PointF p){
		pointVector.add(p);
	}
	
	/**
	 * 다각형을 화면에 그린다
	 * @param canvas  그려질 캔버스 객체
	 */
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
	
	/**
	 * 터치 입력의 중간점을 통과하는 수직 기울기에 입력한 점을 대칭시킨 값을 구한다
	 * @param targetPoint   대칭시킨 값을 구할 점
	 * @param tlStart         터치 입력 시작점
	 * @param tlEnd           터치 입력 끝점
	 * @return 대칭된 결과 값
	 */
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
	
	/**
	 * 터치 입력의 중간점을 통과하는 수직기울기와 입력한 직선이 교차하는 점을 구한다
	 * @param lStart  직선의 시작점
	 * @param lEnd    직선의 끝점
	 * @param tlStart 터치 입력의 시작점
	 * @param tlEnd   터치 입력의 끝점
	 * @return           직선과 기울기의 교차점. 교차하지 않을때는 null값 반환
	 */
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
	
	/**
	 * 교차점이 직선의 범위에 포함되는지 확인한다
	 * @param lStart  직선의 시작점
	 * @param lEnd    직선의 끝점
	 * @param cPoint 확인할 교차점
	 * @return   포함되면 true 포함하지 않으면 false 반환
	 */
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
	
	/**
	 * 특정 점과 터치 입력의 시작점이 기울기를 기준으로 나뉘어진 두 면적중 같은 공간에 있는지 확인한다
	 * @param point     확인 하고자 하는 점 
	 * @param tlStart   터치 입력의 시작 점. 비교대상이 된다.
	 * @param tlEnd     터치 입력의 끝점
	 * @return  같은 면적에 있으면 true. 다른 면적에 있으면 false 반환
	 */
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
	/**
	 * 두 점을 잇는 직선의 기울기를 구한다. 
	 * @param start  직선의 시작점
	 * @param end    직선의 끝점
	 * @return          기울기를 반환 한다
	 */
	private float getGradient(PointF start, PointF end){
		return (start.y - end.y)/(start.x - end.x);
	}
	
	/**
	 * 기울기와 기울기의 중간 점으로 절편을 구한다
	 * @param start  기울기의 중간 점
	 * @param gradient 기울기
	 * @return 절편을 반환 한다
	 */
	private float getIntercept(PointF start, float gradient){
		return start.y - (gradient * start.x);
	}
	
	/**
	 * 직선의 중간 좌표를 구한다
	 * @param start 직선의 시작점
	 * @param end   직선의 끝점
	 * @return         직선 중간점
	 */
	private PointF getCenterPoint(PointF start, PointF end){
		PointF p = new PointF();
		p.x = (start.x + end.x) / 2;
		p.y = (start.y + end.y) / 2;
		return p;
	}
}
