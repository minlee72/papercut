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
 * 스테이지 클리어 조건 클래스
 * @author 2013-03-27 폴더폰
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
	 * 스테이지 클리어 조건을 나타내는 다각형
	 */
	Polygon innerPolygon;
	
	/**
	 * 클리어 조건을 검사할때 허용되는 점의 범위. 최소한 objPolygon 범위를 포함해야함.
	 */
	Polygon outerPolygon;
	
	/**
	 * objPolygon에 속한 모든 좌표값들
	 */
	Vector<objPoint> innerPolyPoints;
	
	/**
	 * objTestPolygon에 속한 모든 좌표값들
	 */
	Vector<objPoint>outerPolyPoints;
	
	/**
	 * 생성자
	 * @param poly 스테이지 클리어 조건을 나타내는 다각형
	 * @param containTestPoly 점의 허용 범위
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
	 * 스테이지 클리어 조건을 만족하느닞 확인
	 * @param paper 확인 하고자 하는 종이 객체
	 * @param scale 직선을 검사할때 사용되는 점 검사 함수에 사용될 인수
	 * @return 만족하면 true 아니면 false 반환
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
	 * 스테이지 클리어 조건을 뷰에 그린다
	 * @param canvas 그려질 뷰의 캔버스 객체
	 */
	public void innerPolyDraw(Canvas canvas){
		Vector<PointF> pointVector = innerPolygon.pointVector;
		if (pointVector == null)
			return;
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
	public void outerPolyDraw(Canvas canvas){
		Vector<PointF> pointVector = outerPolygon.pointVector;
		if (pointVector == null)
			return;
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
