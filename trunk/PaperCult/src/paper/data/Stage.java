package paper.data;

import java.util.Vector;


import com.example.papercult.R;

import bayaba.engine.lib.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.FloatMath;


/**
 * 스테이지 클리어 조건 클래스
 * @author 2013-03-27 폴더폰
 *
 */
public class Stage {
	public int limit;
	public int current;
	public int titleImage;
	public int titleClearImage;
	public int score;
	public boolean locked;
	public String name;
	float pll;
	
	StagePolygon innerStagePolygon;
	StagePolygon outerStagePolygon;

	Polygon innerPolygon;
	Polygon outerPolygon;

	Vector<objPoint>innerPolyPoints;
	Vector<objPoint>outerPolyPoints;
	
	public Stage(int lim, StagePolygon innerPoly, StagePolygon outerPoly){
		limit = lim;
		current = lim;
		
		innerStagePolygon = innerPoly;
		outerStagePolygon = outerPoly;
	}
	public Stage(Polygon p, float paperLineLength){
		pll = paperLineLength;
		limit = 0;
		innerPolygon = new Polygon();
		outerPolygon = new Polygon();
		innerStagePolygon = new StagePolygon();
		outerStagePolygon = new StagePolygon();
		innerPolygon.pointVector = (Vector<PointF>)p.pointVector.clone();
	}
	public Stage(Stage s){
		score = 0;
		limit = s.limit;
		pll = s.pll;
		innerPolygon = new Polygon();
		outerPolygon = new Polygon();
		innerPolygon.pointVector = (Vector<PointF>)s.innerPolygon.pointVector.clone();
		innerStagePolygon = new StagePolygon();
		outerStagePolygon = new StagePolygon();
		innerPolyPoints = new Vector<objPoint>();
		outerPolyPoints = new Vector<objPoint>();
	}
	public void setInnerStgPolygon(Paper p){
		innerStagePolygon.setPolygon(p, innerPolygon.pointVector);
	}
	public void setOuterStgPolygon(Paper p){
		outerStagePolygon.setPolygon(p, outerPolygon.pointVector);
	}
	public void setInnerPolygon(Polygon p){
		innerPolygon.pointVector = (Vector<PointF>)p.pointVector.clone();
	}
	public void setInnerPolygon(Vector<PointF> pv){
		innerPolygon.pointVector = pv;
	}
	public void setOuterPolygon(){
		outerPolygon.pointVector = polyExtPoint(innerPolygon.pointVector, pll/18);
	}
	public void setInspPolyPoints(Paper paper){
		float inspDst = paper.getHeight()/15;
		RectF innerRect = innerPolygon.getBounds();
		RectF outerRect = outerPolygon.getBounds();
		
		for(float i=innerRect.top; i<(innerRect.bottom+1); i=i+inspDst){
			for(float j=innerRect.left; j<(innerRect.right+1); j=j+inspDst){
				if(innerPolygon.contains(j, i) == true){
					innerPolyPoints.add(new objPoint(j,i));
				}
			}
		}
		
		for(float i=outerRect.top; i<(outerRect.bottom+1); i=i+inspDst){
			for(float j=outerRect.left; j<(outerRect.right+1); j=j+inspDst){
				if((outerPolygon.contains(j, i) == true) && (innerPolygon.contains(j, i) == false)){
					outerPolyPoints.add(new objPoint(j,i));
				}
			}
		}
	}
	public void loadStage(Paper paper){
		innerPolygon = innerStagePolygon.getPolygon(paper);
		outerPolygon = outerStagePolygon.getPolygon(paper);
		
		RectF innerRect = innerPolygon.getBounds();
		RectF outerRect = outerPolygon.getBounds();
		
		innerPolyPoints = new Vector<objPoint>();
		outerPolyPoints = new Vector<objPoint>();
		
		float inspInDst = paper.getHeight()/10;
		float inspOutDst = paper.getHeight()/20;
		for(float i=innerRect.top; i<(innerRect.bottom+1); i=i+inspInDst){
			for(float j=innerRect.left; j<(innerRect.right+1); j=j+inspInDst){
				if(innerPolygon.contains(j, i) == true){
					innerPolyPoints.add(new objPoint(j,i));
				}
			}
		}
		
		
		for(float i=outerRect.top; i<(outerRect.bottom+1); i=i+inspOutDst){
			for(float j=outerRect.left; j<(outerRect.right+1); j=j+inspOutDst){
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
	public int calcScore(Paper paper){
		int[] percent = new int[2];
		percent = polygonFillCheck(paper);
		float inPer = percent[0];
		float outPer = percent[1];
		float totalPer;
		if (pointIsInOuterPolygon(paper))
			totalPer = (((inPer-outPer)/100)*30) + 70;
		else
			totalPer = (((inPer-outPer)/100)*69);
		return (int)totalPer;
	}
	public int inMoveCalcScore(Paper paper){
		int[] percent = new int[2];
		percent = inMovePolygonFillCheck(paper);
		float inPer = percent[0];
		float outPer = percent[1];
		float totalPer;
		if (inMovePointIsInOuterPolygon(paper))
			totalPer = (((inPer-outPer)/100)*30) + 70;
		else
			totalPer = (((inPer-outPer)/100)*69);
		return (int)totalPer;
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
		Pnt.setStrokeWidth(7);
		Pnt.setStrokeCap(Paint.Cap.ROUND);
		Pnt.setStrokeJoin(Paint.Join.ROUND);
		Pnt.setColor(Color.WHITE);
		Pnt.setStyle(Paint.Style.STROKE);
		DashPathEffect dashpath = new DashPathEffect(new float[]{20,20},1);

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
	private boolean inMovePointIsInOuterPolygon(Paper paper){
		for (int i=0; i<paper.poly.size(); i++){
			Polygon poly = paper.poly.get(i);
			
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
	private int[] inMovePolygonFillCheck(Paper paper){
		int percent[] = new int[2];
		percent[0] = 0;
		percent[1] = 0;
		
		for (int i=0; i<innerPolyPoints.size(); i++)
			innerPolyPoints.get(i).setFalse();
		
		for (int i=0; i<outerPolyPoints.size(); i++)
			outerPolyPoints.get(i).setFalse();
		
		for (int i=0; i<paper.poly.size(); i++){
			Polygon poly = paper.poly.get(i);
			
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
	public Vector<PointF> polyExtPoint(Vector<PointF> v, float dst){
		Vector<PointF> result = new Vector<PointF>();
		PointF[] prevLine;
		PointF[] nextLine;
		PointF[] nLine;
		PointF inLine;
		PointF outLine;
		PointF nsp;
		PointF nep;
		
		for(int i=0; i<v.size(); i++){
			prevLine = lineEndExt( v.get(decIndex(i,v)), v.get(i), dst );
			nextLine = lineEndExt( v.get(incIndex(i,v)), v.get(i), dst );
			inLine = Polygon.getCenterPoint(prevLine[0], nextLine[0]);
			outLine = Polygon.getCenterPoint(prevLine[1], nextLine[1]);
			nsp = (Polygon.contains(v, inLine.x, inLine.y)) ? outLine : inLine ;
			nep = v.get(i);
			nLine = lineEndExt( nsp, nep, dst );
			if(Polygon.contains(v, nLine[0].x, nLine[0].y))
				result.add(nLine[1]);
			else
				result.add(nLine[0]);
		}
		return result;
	}
	public PointF[] lineEndExt(PointF sp, PointF ep, float dst){
		PointF[] result = new PointF[2];
		result[0] = new PointF();
		result[1] = new PointF();
		
		if(sp.x == ep.x){
			result[0].x = ep.x;
			result[0].y = ep.y+dst;
			
			result[1].x = ep.x;
			result[1].y = ep.y-dst;
		}
		else if(sp.y == ep.y){
			result[0].x = ep.x+dst;
			result[0].y = ep.y;
			
			result[1].x = ep.x-dst;;
			result[1].y = ep.y;
		}
		else{
			float grad = Polygon.getGradient(sp, ep);
			float inter = 0;
			float a = -2*grad*inter;
			float b = (4*grad*grad*inter*inter); 
			float d = 4*(grad*grad+1)*(inter*inter-dst*dst);
			b = FloatMath.sqrt(b-d);
			float c = 2*(grad*grad+1);
			result[0].x = (a + b) / c;
			result[1].x = (a - b) / c;
			
			result[0].y = grad * result[0].x + inter;
			result[1].y = grad * result[1].x + inter;
			
			result[0].x = result[0].x + ep.x;
			result[0].y = result[0].y + ep.y;
			result[1].x = result[1].x + ep.x;
			result[1].y = result[1].y + ep.y;
		}
		
		if((sp.x < ep.x)&&(result[1].x < ep.x )
			||(sp.x > ep.x)&&(result[1].x > ep.x)){
			PointF swap = result[0];
			result[0] = result[1];
			result[1] = swap;
		}
		else if((sp.y < ep.y)&&(result[1].y < ep.y )
			||(sp.y > ep.y)&&(result[1].y > ep.y)){
			PointF swap = result[0];
			result[0] = result[1];
			result[1] = swap;
		}

		return result;
	}
	public int incIndex(int index, Vector v){
		int nextIndex = index + 1;
		if(nextIndex == v.size())
			nextIndex = 0;
		return nextIndex;
	}
	public int decIndex(int index, Vector v){
		int nextIndex = index - 1;
		if(nextIndex < 0)
			nextIndex = v.size() - 1;
		return nextIndex;
	}
	
	private class objPoint{
		PointF point;
		boolean isFill;
		
		objPoint(float x, float y){
			point  = new PointF(x,y);
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
