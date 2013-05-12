package com.example.papercult;


import java.util.Vector;

import paper.gameActivity.Paper;
import paper.gameActivity.Polygon;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TestView extends View {
	Paint paint;
	Paper paper;
	PointF touchStart = new PointF();
	PointF touchEnd = new PointF();
	boolean click = false;
	Context con;
	Polygon sum = new Polygon();
	Polygon poly1 = new Polygon();
	Polygon poly2 = new Polygon();
	
	
	public TestView(Context context, float scrWidth, float scrHeight) {
		super(context);
		con = context;
		
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(20);
		
		paper = new Paper(scrWidth, scrHeight);
		paper.reset();
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if((event.getX()<100)&&(event.getY()<100)){
					this.resetPolygon();
					return true;
				}
				else if((event.getX()>600)&&(event.getY()<100)){
					sum = polySum(paper.poly.get(0), paper.poly.get(1));
					Toast.makeText(con, "dfdfd", Toast.LENGTH_SHORT).show();
					return true;
				}
				click = true;
				touchStart.x = event.getX();
				touchStart.y = event.getY();
				touchEnd.x = touchStart.x;
				touchEnd.y = touchStart.y;
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if(click == true){
				touchEnd.x = event.getX();
				touchEnd.y = event.getY();
				paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
				paper.foldEnd();
				click = false;
			}
			return true;
		}
		return false;
	}
	public void resetPolygon(){
		paper.reset();
		sum = paper.baseRect;
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
		paper.draw(canvas, 0x40000000);
		//sum.draw(canvas, 0x40FF0000);
		canvas.drawCircle(50, 50, 50, paint);
		canvas.drawCircle(600, 50, 50, paint);
	}
	
	public Polygon polySum(Polygon poly1, Polygon poly2){
		Vector<PointF> pv1 = poly1.pointVector;
		Vector<PointF> pv2 = poly2.pointVector;
		Vector<PointF> cpv1 = getIncludeCrossPoint(pv1, pv2);
		Vector<PointF> cpv2 = getIncludeCrossPoint(pv2, pv1);
		return null;
		
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
	public int pointIsInPolygon(PointF p, Vector<PointF> findv){
		for(int i=0; i<findv.size(); i++){
			if(findv.get(i).equals(p.x, p.y))
				return i;
		}
		return -1;
	}
	public Vector<PointF> getIncludeCrossPoint(Vector<PointF> orgPv, Vector<PointF> addPv){
		Vector<PointF> result = new Vector<PointF>();
		
		for(int i=0; i<orgPv.size(); i++){
			result.add(orgPv.get(i));
			
			Vector<PointF> crsPoints = new Vector<PointF>();
			
			int nextIindex = i+1;
			if(nextIindex == orgPv.size())
				nextIindex = 0;
			PointF orgSP = orgPv.get(i);
			PointF orgEP = orgPv.get(nextIindex);
			
			for(int j=0; j<addPv.size(); j++){
				int nextJindex = j+1;
				if(nextJindex == addPv.size())
					nextJindex = 0;
				PointF addSP = addPv.get(j);
				PointF addEP = addPv.get(nextJindex);
				PointF crsP = Polygon.getCrossPointFromLine(orgSP, orgEP, addSP, addEP);
				if(crsP == null)
					continue;
				else if((orgSP.equals(crsP.x, crsP.y))||(orgEP.equals(crsP.x, crsP.y)))
					continue;
				else if(Polygon.isInlineExps(orgSP, orgEP, crsP)&&(Polygon.isInlineExps(addSP, addEP, crsP)))
					if(crsPoints.size()==0)
						crsPoints.add(crsP);
					else if(!(crsPoints.lastElement().equals(crsP.x, crsP.y)))
						crsPoints.add(crsP);
			}
			if(crsPoints.size() == 0)
				continue;
			else if(crsPoints.size() == 1)
				result.add(crsPoints.get(0));
			else{
				crsPoints = getSortedCrossPoint(orgSP, orgEP, crsPoints);
				for(int j=0; j<crsPoints.size(); j++){
					result.add(crsPoints.get(j));
				}
			}
		}
		return result;
	}

	public Vector<PointF> getSortedCrossPoint(PointF stp, PointF edp, Vector<PointF> crsPoints){
		if((stp.equals(edp.x, edp.y)) || (crsPoints.size()==0))
			return null;
		if(crsPoints.size()==1)
			return crsPoints;
		
		int i = 0;
		if((stp.x-edp.x) == 0){ //수직
			if(stp.y > edp.y){ //y가 높은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).y) < (crsPoints.get(i+1).y)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
			else{ //y가 낱은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).y) > (crsPoints.get(i+1).y)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
		}
		else{ //수평
			if(stp.x > edp.x){ //x가 높은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).x) < (crsPoints.get(i+1).x)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
			else{ //x가 낮은 값이 앞에 오도록
				while(true){
					if(i == (crsPoints.size()-1))
						break;
					if((crsPoints.get(i).x) > (crsPoints.get(i+1).x)){
						PointF temp = crsPoints.get(i);
						crsPoints.set(i, crsPoints.get(i+1));
						crsPoints.set(i+1, temp);
						i=0;
					}
					else
						i++;
				}
			}
		}
		return crsPoints;
	}
	public Vector<PointF> polySortDirection(Vector<PointF> pv1, Vector<PointF> pv2){
		int index1 = 0;
		int index2 = 0;
		for(int i=0; i<pv1.size(); i++){
			for(int j=0; j<pv2.size(); j++){
				PointF point1 = pv1.get(i);
				PointF point2 = pv2.get(j);
				if(point1.equals(point2.x, point2.y)){
					index1 = i;
					index2 = j;
				}
			}
		}
		
		int nextIndex1 = index1 + 1;
		if(nextIndex1 == pv1.size())
			nextIndex1 = 0;
		
		int prevIndex1 = index1 - 1;
		if(prevIndex1 < 0)
			prevIndex1 = pv1.size()-1;
		
		int nextIndex2 = index2 + 1;
		if(nextIndex2 == pv2.size())
			nextIndex2 = 0;
		
		int prevIndex2 = index2 - 1;
		if(prevIndex2 < 0)
			prevIndex2 = pv2.size()-1;
		
		if( !((pv1.get(nextIndex1).equals(pv2.get(nextIndex2).x, pv2.get(nextIndex2).y))
				|| pv1.get(prevIndex1).equals(pv2.get(prevIndex2).x, pv2.get(prevIndex2).y))){
			Vector<PointF> temp = new Vector<PointF>();
			for(int i=pv2.size()-1; i>=0; i--){
				temp.add(pv2.get(i));
			}
			pv2 = temp;
		}
		return pv2;
	}
}