package paper.gameActivity;

import java.util.Vector;

import com.example.papercult.R;

import bayaba.engine.lib.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;


/**
 * �������� Ŭ���� ���� Ŭ����
 * @author 2013-03-27 ������
 *
 */
public class Stage {
	String name;
	int limit;
	int current = 0;
	public int titleImage;
	
	stagePolygon innerStagePolygon;
	stagePolygon outerStagePolygon;
	/**
	 * �������� Ŭ���� ������ ��Ÿ���� �ٰ���
	 */
	Polygon innerPolygon;
	
	/**
	 * Ŭ���� ������ �˻��Ҷ� ���Ǵ� ���� ����. �ּ��� objPolygon ������ �����ؾ���.
	 */
	Polygon outerPolygon;
	
	/**
	 * objPolygon�� ���� ��� ��ǥ����
	 */
	Vector<objPoint> innerPolyPoints;
	
	/**
	 * objTestPolygon�� ���� ��� ��ǥ����
	 */
	Vector<objPoint>outerPolyPoints;
	
	/**
	 * ������
	 * @param poly �������� Ŭ���� ������ ��Ÿ���� �ٰ���
	 * @param containTestPoly ���� ��� ����
	 */
	public Stage(String n, int lim, stagePolygon innerPoly, stagePolygon outerPoly){
		name = n;
		limit = lim;
		
		innerStagePolygon = innerPoly;
		outerStagePolygon = outerPoly;
	}
	
	public void setStage(Paper paper){
		innerPolygon = innerStagePolygon.getPolygon(paper);
		outerPolygon = outerStagePolygon.getPolygon(paper);
		
		Rect innerRect = innerPolygon.getBounds();
		Rect outerRect = outerPolygon.getBounds();
		
		innerPolyPoints = new Vector<objPoint>();
		outerPolyPoints = new Vector<objPoint>();
		
		for(int i=innerRect.top; i<(innerRect.bottom+1); i=i+10){
			for(int j=innerRect.left; j<(innerRect.right+1); j=j+10){
				if(innerPolygon.contains(j, i) == true){
					innerPolyPoints.add(new objPoint(j,i));
				}
			}
		}
		
		for(int i=outerRect.top; i<(outerRect.bottom+1); i=i+10){
			for(int j=outerRect.left; j<(outerRect.right+1); j=j+10){
				if((outerPolygon.contains(j, i) == true) && (innerPolygon.contains(j, i) == false)){
					outerPolyPoints.add(new objPoint(j,i));
				}
			}
		}
	}
	
	/**
	 * �������� Ŭ���� ������ �����ϴ��� Ȯ��
	 * @param paper Ȯ�� �ϰ��� �ϴ� ���� ��ü
	 * @param scale ������ �˻��Ҷ� ���Ǵ� �� �˻� �Լ��� ���� �μ�
	 * @return �����ϸ� true �ƴϸ� false ��ȯ
	 */
	public boolean clearCheck(Paper paper, int innerPolyPercent, int outerPolyPercent){
		if (pointIsInOuterPolygon(paper)){
			int[] percent = new int[2];
			percent = polygonFillCheck(paper);
			
			if((percent[0]>=innerPolyPercent) && (percent[1]<=outerPolyPercent))
				return true;
			else 
				return false;
		}
		else
			return false;
	}
	
	/**
	 * �������� Ŭ���� ������ �信 �׸���
	 * @param canvas �׷��� ���� ĵ���� ��ü
	 */
	public void innerPolyDraw(Canvas canvas){
		Vector<PointF> pointVector = innerPolygon.pointVector;
		if (pointVector == null)
			return;
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
	public void outerPolyDraw(Canvas canvas){
		Vector<PointF> pointVector = outerPolygon.pointVector;
		if (pointVector == null)
			return;
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
	private boolean pointIsInOuterPolygon(Paper paper){
		for (int i=0; i<paper.base.size(); i++){
			Polygon poly = paper.base.get(i);
			
			for (int j=0; j<poly.pointVector.size(); j++){
				PointF test = poly.pointVector.get(j);
				if(outerPolygon.contains(test.x, test.y) == false)
					return false;
			}
		}
		return true;
	}
	private int[] polygonFillCheck(Paper paper){
		int percent[] = new int[2];
		percent[0] = 0;
		percent[1] = 0;
		
		for (int i=0; i<innerPolyPoints.size(); i++)
			innerPolyPoints.get(i).setFalse();
		
		for (int i=0; i<outerPolyPoints.size(); i++)
			outerPolyPoints.get(i).setFalse();
		
		for (int i=0; i<paper.base.size(); i++){
			Polygon poly = paper.base.get(i);
			
			for (int j=0; j<innerPolyPoints.size(); j++){
				objPoint point = innerPolyPoints.get(j);
				if(point.isFill == false){
					if(poly.contains(point.getX(), point.getY()) == true){
						point.setTrue();
						percent[0]++;
					}
				}
			}
			for (int z=0; z<outerPolyPoints.size(); z++){
				objPoint point = outerPolyPoints.get(z);
				if(point.isFill == false){
					if(poly.contains(point.getX(), point.getY()) == true){
						point.setTrue();
						percent[1]++;
					}
				}
			}
		}
		float p0 = ((float)percent[0] / (float)innerPolyPoints.size()) * 100;
		float p1 = ((float)percent[1] / (float)outerPolyPoints.size()) * 100;
		percent[0] = (int)p0;
		percent[1] = (int)p1;
		return percent;
	}
	
	private class objPoint{
		Point point;
		boolean isFill;
		
		objPoint(int x, int y){
			point  = new Point(x,y);
			isFill = false;
		}
		
		public float getX(){
			return point.x;
		}
		public float getY(){
			return point.y;
		}
		public void setTrue(){
			isFill = true;
		}
		public void setFalse(){
			isFill = false;
		}
	}
}
