package com.example.papercult;

import java.util.Vector;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;

/**
 * �������� Ŭ���� ���� Ŭ����
 * @author 2013-03-27 ������
 *
 */
public class StageObject {
	/**
	 * �������� Ŭ���� ������ ��Ÿ���� �ٰ���
	 */
	Polygon objPolygon;
	/**
	 * Ŭ���� ������ �˻��Ҷ� ���Ǵ� ���� ����. �ּ��� objPolygon ������ �����ؾ���.
	 */
	Polygon objTestPolygon;

	/**
	 * ������
	 * @param poly �������� Ŭ���� ������ ��Ÿ���� �ٰ���
	 * @param containTestPoly ���� ��� ����
	 */
	StageObject(Polygon poly, Polygon containTestPoly){
		objPolygon = poly;
		objTestPolygon = containTestPoly;
	}
	
	/**
	 * �������� Ŭ���� ������ �����ϴ��� Ȯ��
	 * @param paper Ȯ�� �ϰ��� �ϴ� ���� ��ü
	 * @param scale ������ �˻��Ҷ� ���Ǵ� �� �˻� �Լ��� ���� �μ�
	 * @return �����ϸ� true �ƴϸ� false ��ȯ
	 */
	public boolean objClearCheck(Paper paper, int scale){
		if ( check_All_ObjPolygonPoint(paper) && check_All_ObjPolygonLine(paper,scale)) 
			return true;
		else
			return false;
	}
	
	/**
	 * �������� Ŭ���� ������ �信 �׸���
	 * @param canvas �׷��� ���� ĵ���� ��ü
	 */
	public void draw(Canvas canvas){
		Vector<PointF> pointVector = objPolygon.pointVector;
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		
		for(int i = 0; i<pointVector.size(); i++){              //���Ϳ��� �� �ΰ��� �̾Ƽ� ���� �׷���
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
	 * �������� Ŭ���� �˻翡 ���Ǵ� objTestPolygon�� ȭ�鿡 �׸�
	 * @param canvas
	 */
	public void testDraw(Canvas canvas){
		Vector<PointF> pointVector = objTestPolygon.pointVector;
		Paint Pnt = new Paint();
		Path path = new Path();
		path.reset();
		
		
		for(int i = 0; i<pointVector.size(); i++){              //���Ϳ��� �� �ΰ��� �̾Ƽ� ���� �׷���
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
	 * ������ ��ü�� ��� ���� ���� objTestPolygon�ٰ����� ������ ��� ���� �ִ��� Ȯ��
	 * @param paper �˻��� ������ ��ü 
	 * @return �� ���̶� ���� ������ �����ٸ� false, ��� �ȿ� �ִٸ� true
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
	 * Ŭ���� ���� �ٰ����� ��� ������ ������ ��ü�� �����ϴ°� �˻�
	 * @param paper �˻��� ������ ��ü
	 * @param scale �� �˻� �Լ��� ����Ҷ� ����� ����
	 * @return paper ��ü�� Ŭ���� ���� �ٰ����� ��� ������ �����ϴ� ������ �����ϸ� true
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
	 * �� ���� ���� ��ġ�� �����ϴ��� �˻�
	 * @param blStart �˻��� ���� ������
	 * @param blEnd  �˻��� ���� ����
	 * @param tlStart �˻��� ���� ������
	 * @param tlEnd �˻��� ���� ����
	 * @param scale ���� �����˻翡 ����� ������ �μ�
	 * @return ��ġ�ϸ� true ����ġ �ϸ� false
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
	 * �� ���� ���� ��ġ�� �����ϴ��� �˻�
	 * @param basePoint �˻��� ��
	 * @param testPoint �˻��� ��
	 * @param scale ���� ���̶�� �Ǵ��� ����
	 * @return ���� ��ġ�� true �ƴϸ� false
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
