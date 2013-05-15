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
		
		Vector<PointF> d = new Vector<PointF>();
		d.add(new PointF(100,100));
		d.add(new PointF(200,100));
		d.add(new PointF(200,200));
		d.add(new PointF(100,100));
		
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
					if(paper.poly.size()>=2)
						sum.pointVector = polySum(paper.poly.get(0).pointVector, paper.poly.get(1).pointVector);
					Toast.makeText(con, ""+sum.pointVector.size(), Toast.LENGTH_SHORT).show();
					this.invalidate();
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
		sum.pointVector = (Vector<PointF>) paper.baseRect.pointVector.clone();
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
		paper.draw(canvas, 0x40000000);
		sum.draw(canvas, 0x40FF0000);
		canvas.drawCircle(50, 50, 50, paint);
		canvas.drawCircle(600, 50, 50, paint);
	}
	
	public Vector<PointF> polySum(Vector<PointF> pv1, Vector<PointF> pv2){
		Vector<PointF> result = new Vector<PointF>();
		//pv2 = polySortDirection(pv1, pv2);
		Vector<Vector<PointF>> ppp = getIncludeCrossPoint(pv1, pv2);
		Vector<PointF> cpv = ppp.get(0);
		Vector<PointF> ocpv = ppp.get(1);
		Vector<PointF> swap;
		
		if((cpv.size()-pv1.size()==0)){
			boolean inCheck = false;
			for(int i=0; i<cpv.size(); i++){
				if(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y)){
					inCheck  = true;
					i = cpv.size();
				}
			}
			for(int i=0; i<ocpv.size(); i++){
				if(Polygon.containsEXP(cpv, ocpv.get(i).x, ocpv.get(i).y)){
					inCheck  = true;
					i = ocpv.size(); 
				}
			}
			if(inCheck == false)
				return null;
		}
		
		int index = 0;
		int nextIndex;
		int prevIndex;
		PointF sp = null;
		PointF cp;
		PointF np;
		PointF pp;
		PointF inspp;
		
		for(int i=0; i<cpv.size(); i++){
			if(!(Polygon.contains(ocpv, cpv.get(i).x, cpv.get(i).y))){
				if(pointIsInPolygon(cpv.get(i), ocpv)==-1){
					index = i;
					sp = cpv.get(i);
					i=cpv.size();
				}
			}
		}
		if(sp==null){
			cpv = pv2;
			ocpv = pv1;
			for(int i=0; i<cpv.size(); i++){
				if(!(Polygon.contains(ocpv, cpv.get(i).x, cpv.get(i).y))){
					if(pointIsInPolygon(cpv.get(i), ocpv)==-1){
						index = i;
						sp = cpv.get(i);
						i=cpv.size();
					}
				}
			}
		}
		if(sp==null)
			return pv1;
		
		boolean cpvInc = true;
		boolean ocpvInc = true;
		boolean swapInc = false;
		while(true)
		{
			nextIndex = (cpvInc)? incIndex(index, cpv) : decIndex(index, cpv);
			prevIndex = (cpvInc)? decIndex(index, cpv) : incIndex(index, cpv);
			cp = cpv.get(index);
			np = cpv.get(nextIndex);
			pp = cpv.get(prevIndex);
			
			if(result.size()!=0){
				if(!(result.lastElement().equals(cp.x, cp.y)))
					result.add(cp);
			}
			else
				result.add(cp);
			
			inspp = getNextPoint(cp, np, 1);
			if(!(Polygon.containsEXP(ocpv, inspp.x, inspp.y))){
				index = nextIndex;
			}
			else{
				index = pointIsInPolygon(cp, ocpv);
				if(index == -1)
					index = nextIndex;
				else{
					swap = cpv;
					cpv = ocpv;
					ocpv = swap;
					
					swapInc = cpvInc;
					cpvInc = ocpvInc;
					ocpvInc = swapInc;
					
					nextIndex = (cpvInc)? incIndex(index, cpv) : decIndex(index, cpv);
					inspp = getNextPoint(cpv.get(index),cpv.get(nextIndex),1);
					if(Polygon.containsEXP(ocpv, inspp.x, inspp.y)
							||(cpv.get(nextIndex).equals(pp.x, pp.y))){
						cpvInc = !cpvInc;
					}
				}
			}
			if(cpv.get(index).equals(sp.x, sp.y))
				break;
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
	public int pointIsInPolygon(PointF p, Vector<PointF> findv){
		for(int i=0; i<findv.size(); i++){
			if(findv.get(i).equals(p.x, p.y))
				return i;
		}
		return -1;
	}
	public Vector<Vector<PointF>> getIncludeCrossPoint(Vector<PointF> orgPv, Vector<PointF> addPv){
		Vector<PointF> resultOrg = new Vector<PointF>();
		Vector<PointF> resultAdd = new Vector<PointF>();
		Vector<Vector<PointF>> yResult = new Vector<Vector<PointF>>();
		for(int i=0; i<addPv.size(); i++){
			yResult.add(new Vector<PointF>());
		}
		for(int i=0; i<orgPv.size(); i++){
			resultOrg.add(orgPv.get(i));
			
			Vector<PointF> crsPoints = new Vector<PointF>();
			
			int nextIindex = incIndex(i,orgPv);
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
				if(!((Polygon.isInlineExps(orgSP, orgEP, crsP))&&(Polygon.isInlineExps(addSP, addEP, crsP))) )
					continue;
				
				if(!((orgSP.equals(crsP.x, crsP.y))||(orgEP.equals(crsP.x, crsP.y)))){
					if(crsPoints.size()==0)
						crsPoints.add(crsP);
					else if(!(crsPoints.lastElement().equals(crsP.x, crsP.y)))
						crsPoints.add(crsP);
				}
					
				if(!((addSP.equals(crsP.x, crsP.y))||(addEP.equals(crsP.x, crsP.y)))){
					if(yResult.get(j).size()==0)
						yResult.get(j).add(crsP);
					else if(!(yResult.get(j).lastElement().equals(crsP.x, crsP.y)))
						yResult.get(j).add(crsP);
				}
			}

			if(crsPoints.size() == 0)
				continue;
			else if(crsPoints.size() == 1)
				resultOrg.add(crsPoints.get(0));
			else{
				crsPoints = getSortedCrossPoint(orgSP, orgEP, crsPoints);
				for(int j=0; j<crsPoints.size(); j++){
					resultOrg.add(crsPoints.get(j));
				}
			}
		}
		
		for(int i=0; i<yResult.size(); i++){
			Vector<PointF> temp = getSortedCrossPoint(addPv.get(i), addPv.get(incIndex(i,addPv)), yResult.get(i));
			resultAdd.add(addPv.get(i));
			if(temp != null){
				for(int j=0; j<temp.size(); j++)
					resultAdd.add(temp.get(j));
			}
		}
		
		Vector<Vector<PointF>> r = new Vector<Vector<PointF>>();
		r.add(resultOrg);
		r.add(resultAdd);
		return r;
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
					i=pv1.size();
					j=pv2.size();
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
	public PointF getNextPoint(PointF stp, PointF edp, float dst){
		float pdst;
		if(stp.equals(edp.x, edp.y))
			return null;
		PointF result = new PointF();
		if((stp.x-edp.x) == 0){ //수직
			pdst = stp.y - edp.y;
			if(pdst < 0)
				pdst = pdst * -1;
			if(pdst<dst)
				dst = pdst/2;
			result.x = stp.x;
			result.y = (stp.y>edp.y) ? stp.y-dst : stp.y+dst;
		}
		else if((stp.y-edp.y) == 0){ //수평
			pdst = stp.x - edp.x;
			if(pdst < 0)
				pdst = pdst * -1;
			if(pdst<dst)
				dst = pdst/2;
			result.y = stp.y;
			result.x = (stp.x>edp.x) ? stp.x-dst : stp.x+dst;
		}
		else{
			pdst = stp.x - edp.x;
			if(pdst < 0)
				pdst = pdst * -1;
			if(pdst<dst)
				dst = pdst/2;
			float gradient = Polygon.getGradient(stp, edp);
			float intercept = Polygon.getIntercept(stp, gradient);
			result.x = (stp.x>edp.x) ? stp.x-dst : stp.x+dst;
			result.y = gradient * result.x + intercept;

		}
		return result;
	}
}