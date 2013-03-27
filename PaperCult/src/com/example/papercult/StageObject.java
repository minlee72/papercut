package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * 스테이지 클리어 조건 클래스
 * @author 2013-03-27 폴더폰
 *
 */
public class StageObject {
	/**
	 * 스테이지 클리어 조건을 나타내는 다각형
	 */
	Polygon objPolygon;
	/**
	 * 클리어 조건을 검사할때 허용되는 점의 범위. 최소한 objPolygon 범위를 포함해야함.
	 */
	Polygon objTestPolygon;

	/**
	 * 생성자
	 * @param poly 스테이지 클리어 조건을 나타내는 다각형
	 * @param containTestPoly 점의 허용 범위
	 */
	StageObject(Polygon poly, Polygon containTestPoly){
		objPolygon = poly;
		objTestPolygon = containTestPoly;
	}
	
	/**
	 * 스테이지 클리어 조건을 만족하느닞 확인
	 * @param paper 확인 하고자 하는 종이 객체
	 * @param scale 직선을 검사할때 사용되는 점 검사 함수에 사용될 인수
	 * @return 만족하면 true 아니면 false 반환
	 */
	public boolean objClearCheck(Paper paper, int scale){
		if ( check_All_ObjPolygonPoint(paper) && check_All_ObjPolygonLine(paper,scale)) 
			return true;
		else
			return false;
	}
	
	/**
	 * 스테이지 클리어 조건을 뷰에 그린다
	 * @param canvas 그려질 뷰의 캔버스 객체
	 */
	public void draw(Canvas canvas){
		Vector<PointF> pointVector = objPolygon.pointVector;
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		
		for(int i = 0; i<pointVector.size(); i++){              //벡터에서 점 두개씩 뽑아서 직선 그려줌
			if(i == 0){
				path.moveTo(pointVector.get(i).x, pointVector.get(i).y);
			}
			
			if(i == (pointVector.size()-1)){
				path.lineTo(pointVector.get(0).x, pointVector.get(0).y);
			}
			else{
				path.lineTo(pointVector.get(i+1).x, pointVector.get(i+1).y);
			}
		}
		
		Pnt.setAntiAlias(true);
		Pnt.setStrokeWidth(3);
		Pnt.setColor(Color.GREEN);
		Pnt.setStyle(Paint.Style.STROKE);
		DashPathEffect dashpath = new DashPathEffect(new float[]{20,30},1);
		Pnt.setPathEffect(dashpath);
		canvas.drawPath(path, Pnt);
		
	}
	
	/**
	 * 스테이지 클리어 검사에 사용되는 objTestPolygon를 화면에 그림
	 * @param canvas
	 */
	public void testDraw(Canvas canvas){
		Vector<PointF> pointVector = objTestPolygon.pointVector;
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		
		for(int i = 0; i<pointVector.size(); i++){              //벡터에서 점 두개씩 뽑아서 직선 그려줌
			if(i == 0){
				path.moveTo(pointVector.get(i).x, pointVector.get(i).y);
			}
			
			if(i == (pointVector.size()-1)){
				path.lineTo(pointVector.get(0).x, pointVector.get(0).y);
			}
			else{
				path.lineTo(pointVector.get(i+1).x, pointVector.get(i+1).y);
			}
		}
		
		Pnt.setAntiAlias(true);
		Pnt.setStrokeWidth(3);
		Pnt.setColor(Color.BLUE);
		Pnt.setStyle(Paint.Style.STROKE);
		DashPathEffect dashpath = new DashPathEffect(new float[]{20,30},1);
		Pnt.setPathEffect(dashpath);
		canvas.drawPath(path, Pnt);
		
	}
	
	/**
	 * 페이퍼 객체의 모든 점을 돌며 objTestPolygon다각형의 범위를 벗어난 점이 있는지 확인
	 * @param paper 검사할 페이퍼 객체 
	 * @return 한 점이라도 범위 밖으로 나갔다면 false, 모두 안에 있다면 true
	 */
	private boolean check_All_ObjPolygonPoint(Paper paper){
		for (int i=0; i<paper.base.size(); i++){
			Polygon poly = paper.base.get(i);
			
			for (int j=0; j<poly.pointVector.size(); j++){
				PointF test = poly.pointVector.get(j);
				if(objTestPolygon.contains(test.x, test.y) == false)
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 클리어 조건 다각형의 모든 직선이 페이퍼 객체에 존재하는가 검사
	 * @param paper 검사할 페이퍼 객체
	 * @param scale 점 검사 함수를 사용할때 허용할 범위
	 * @return paper 객체에 클리어 조건 다각형의 모든 직선에 대응하는 직선이 존재하면 true
	 */
	private boolean check_All_ObjPolygonLine(Paper paper, int scale){
		int numOfLine = objPolygon.pointVector.size()-1;
		boolean[] lineCheck = new boolean[numOfLine];
		for(int i=0; i<numOfLine; i++)
			lineCheck[i] = false;
		
		for(int i=0; i<numOfLine; i++){
			PointF blStart = objPolygon.pointVector.get(i);
			PointF blEnd = objPolygon.pointVector.get(i+1);
			
			for(int j=0; j<paper.base.size(); j++){
				Polygon testPoly = paper.base.get(j);
				
				for(int z=0; z<testPoly.pointVector.size()-1; z++){
					PointF tlStart = testPoly.pointVector.get(z);
					PointF tlEnd = testPoly.pointVector.get(z+1);
					
					if(compareLine(blStart, blEnd, tlStart, tlEnd, scale) == true ){
						lineCheck[i] = true;
						z = testPoly.pointVector.size();
						j = paper.base.size(); 	
					}
				}
			}
		}
		
		for(int i=0; i<numOfLine; i++){
			if(lineCheck[i] == false)
				return false;
		}
		return true;
	}
	
	/**
	 * 두 선이 같은 위치에 존재하는지 검사
	 * @param blStart 검사할 선의 시작점
	 * @param blEnd  검사할 선의 끝점
	 * @param tlStart 검사할 선의 시작점
	 * @param tlEnd 검사할 선의 끝점
	 * @param scale 선의 끝점검사에 사용할 허용범위 인수
	 * @return 일치하면 true 불일치 하면 false
	 */
	private boolean compareLine(PointF blStart, PointF blEnd, PointF tlStart, PointF tlEnd, int scale){
		if( (comparePoint(blStart, tlStart, scale)) && (comparePoint(blEnd, tlEnd, scale)) )
			return true;
		else if( (comparePoint(blStart, tlEnd, scale)) && (comparePoint(blEnd, tlStart, scale)) )
			return true;
		else
			return false;
	}
	
	/**
	 * 두 점이 같은 위치에 존재하는지 검사
	 * @param basePoint 검사할 점
	 * @param testPoint 검사할 점
	 * @param scale 같은 점이라고 판단할 범위
	 * @return 같은 위치면 true 아니면 false
	 */
	private boolean comparePoint(PointF basePoint, PointF testPoint, int scale){
		Polygon basePoly = new Polygon();
		basePoly.add(new PointF(basePoint.x-scale, basePoint.y));
		basePoly.add(new PointF(basePoint.x        , basePoint.y-scale));
		basePoly.add(new PointF(basePoint.x+scale, basePoint.y));
		basePoly.add(new PointF(basePoint.x        , basePoint.y+scale));
		
		if( basePoly.contains(testPoint.x, testPoint.y) == true){
			return true;
		}
		else
			return false;
	}
}
