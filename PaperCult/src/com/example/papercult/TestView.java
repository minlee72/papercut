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
	Vector<PointF> d;
	Vector<PointF> c;
	int i=0;
	Vector<PointF> t1;
	Vector<PointF> t2;
	Vector<PointF> lst;
	Vector<Vector<PointF>> h1= new Vector<Vector<PointF>>();
	Vector<Vector<PointF>> h2= new Vector<Vector<PointF>>();
	Toast mToast = null;
	boolean drawPaper = true;
	
	public TestView(Context context, float scrWidth, float scrHeight) {
		super(context);
		con = context;
		
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(20);
		
		paper = new Paper(scrWidth, scrHeight);
		paper.reset();
		
		d = new Vector<PointF>();
		d.add(new PointF(72f, 496.45193f));
		d.add(new PointF(251.58147f, 494.05896f));
		d.add(new PointF(264.94513f, 460.17053f));
		d.add(new PointF(371.90167f, 471.50293f));
		d.add(new PointF(375.33643f, 492.4099f));
		d.add(new PointF(353.44092f, 518.54706f));
		d.add(new PointF(280.0297f, 705.62274f));
		d.add(new PointF(258.16818f, 697.3784f));
		d.add(new PointF(257.2465f, 699.61664f));
		d.add(new PointF(182.6366f, 668.8941f));
		d.add(new PointF(210.60956f, 597.9582f));
		d.add(new PointF(62.31135f, 599.6865f));
		d.add(new PointF(52.23735f, 504.15906f));
		d.add(new PointF(72.0f, 502.07498f));
		
		c = new Vector<PointF>();
		c.add(new PointF(286.6666f, 688.7114f));
		c.add(new PointF(245.99467f, 668.27155f));
		c.add(new PointF(346.24872f, 468.7849f));
		c.add(new PointF(371.902f, 471.50293f));
		
		poly1.pointVector = d;
		poly2.pointVector = c;
		
		PointF sf = new PointF(226.3238f, 270.301077f);
		PointF ef = new PointF(443.21945f, 538.57996f);
		
		Vector<PointF> tep = new Vector<PointF>();
		tep.add(new PointF(0.0003f, 0.0002f));
		tep.add(new PointF(0.0007f, 0.0002f));
		tep.add(new PointF(0.0006f, 0.0004f));
		tep.add(new PointF(0.0002f, 0.0004f));
		//if(Polygon.pointIsInLine(sf, ef, new PointF(253.77339f, 304)))
		//	Toast.makeText(context, "dfdfd", Toast.LENGTH_LONG).show();
		if(Polygon.contains(tep, 0.0004f, 0.0002f))
			Toast.makeText(con, "dfd", Toast.LENGTH_SHORT).show();
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if((event.getX()<100)&&(event.getY()<100)){
					this.resetPolygon();
					h1.clear();
					h2.clear();
					sum.pointVector.clear();
					i=0;
					drawPaper = true;
					return true;
				}
				else if((event.getX()>600)&&(event.getY()<100)){
					
					Vector<Polygon> pv = (Vector<Polygon>) paper.poly.clone();
					Vector<PointF> tp;
					tp = pv.get(0).pointVector;
					for(int i=0; i<pv.size(); i++){
						tp = polySum(tp, pv.get(i).pointVector);
						if(tp!=null){
							h1.add(tp);
							h2.add(pv.get(i).pointVector);
							sum.pointVector = tp;
						}
						else{
							tp = sum.pointVector;
						}
					}
					
					/*
					if(i==0){
						t1=paper.poly.get(i).pointVector;
						t2=paper.poly.get(i+1).pointVector;
					}
					else{
						t1=sum.pointVector;
						t2=paper.poly.get(i+1).pointVector;
					}
					
					sum.pointVector = polySum(t1,t2);
					if(sum.pointVector == null){
						sum.pointVector = t1;
						Toast.makeText(con, "dfdfd", Toast.LENGTH_SHORT).show();
					}
					else{
						tt.add(sum.pointVector);
					}
					Toast.makeText(con, "sum + "+ (i+1), Toast.LENGTH_SHORT).show();
					i++;
					if((i+1)==paper.poly.size()){
						i=0;
						Toast.makeText(con, "sum + "+ "end", Toast.LENGTH_SHORT).show();
					}
					*/
					//sum.pointVector = polySum(d,c );
					this.invalidate();
					return true;
				}
				else if((event.getX()<100)&&(event.getY()>800)){
					int a = 20;
					int b = a + 300;
				}
				else if((event.getX()>600)&&(event.getY()>800)){
					drawPaper = false;
					poly1.pointVector = h1.get(i);
					if(mToast == null){
						mToast = Toast.makeText(con, "hi.get( "+i+" )", Toast.LENGTH_LONG);
					}
					else{
						mToast.setText("hi.get( " + i + ")");
					}
					mToast.show();
					i++;
					if(i>=h1.size())
						i=0;
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
		
		if(drawPaper){
			paper.draw(canvas, 0x40000000);
			sum.draw(canvas, 0x40FF0000);
		}
		else{
			poly1.draw(canvas, 0x400000ff);
		}
		
		//if(drawPaper){
		//	poly1.draw(canvas, 0x0111111);
		//	poly2.draw(canvas, 0x0111111);
		//}
		sum.draw(canvas, 0x40FF0000);
		
		canvas.drawCircle(50, 50, 50, paint);
		canvas.drawCircle(600, 50, 50, paint);
		canvas.drawCircle(50, 940, 50, paint);
		canvas.drawCircle(600, 940, 50, paint);
		canvas.drawRect(100, 100, 110, 110, paint);
	}
	
	public Vector<PointF> polySum(Vector<PointF> pv1, Vector<PointF> pv2){
		
		if((pv1.size()>=3)&&(pv2.size()<3))
			return pv1;
		else if((pv1.size()<3)&&(pv2.size()>=3))
			return pv2;
		else if((pv1.size()<3)&&(pv2.size()<3))
			return null;
		
		Vector<PointF> result = new Vector<PointF>();
		Vector<Vector<PointF>> ppp = getIncludeCrossPoint(pv1, pv2);
		Vector<PointF> cpv = ppp.get(0);
		Vector<PointF> ocpv = ppp.get(1);
		Vector<PointF> swap;
		
		if((cpv.size()-pv1.size()==0)&&(ocpv.size()-pv2.size()==0)){
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
			if(inCheck == false){
				boolean samePoly = true;
				for(int i=0; i<cpv.size(); i++){
					boolean samePoint = false;
					for(int j=0; j<ocpv.size(); j++){
						if(cpv.get(i).equals(ocpv.get(j).x, ocpv.get(j).y))
							samePoint = true;
					}
					if(samePoint == false)
						samePoly = false;
				}
				if(samePoly == true)
					return cpv;
				else
					return null;
			}
		}
		
		int index = 0;
		int nextIndex;
		int prevIndex;
		PointF sp = null;
		PointF cp;
		PointF np;
		PointF pp;
		
		for(int i=0; i<cpv.size(); i++){
			if(!(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y))){
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
				if(!(Polygon.containsEXP(ocpv, cpv.get(i).x, cpv.get(i).y))){
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
		boolean swapInc = true;
		int c = 0;
		while(true)
		{
			c++;
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

			
			if(result.size()>2){
				PointF ls = result.get(result.size()-3);
				PointF le = result.get(result.size()-1);
				PointF ip = result.get(result.size()-2);
				if(Polygon.pointIsInLine(ls, le, ip))
					result.remove(result.size()-2);
			}
			index = pointIsInPolygon(cp, ocpv);
			if(index == -1){
				index = nextIndex;
			}
			else{
				PointF t1 = ocpv.get(incIndex(index,ocpv));
				PointF t2 = ocpv.get(decIndex(index,ocpv));
				if( (np.equals(t1.x, t1.y)) || (np.equals(t2.x, t2.y)) )
						index = nextIndex;
				else{
					if(containsLine(ocpv, cp, np)){
						swap = cpv;
						cpv = ocpv;
						ocpv = swap;
						
						swapInc = cpvInc;
						cpvInc = ocpvInc;
						ocpvInc = swapInc;
						
						nextIndex = (cpvInc)? incIndex(index, cpv) : decIndex(index, cpv);
						if(containsLine(ocpv, cpv.get(index), cpv.get(nextIndex))
								||(cpv.get(nextIndex).equals(pp.x, pp.y)))
								cpvInc = !cpvInc;	
					}
					else
						index = nextIndex;
				}
			}
			if(cpv.get(index).equals(sp.x, sp.y))
				break;
			if(c>30)
				break;
		}
		if(result.size()>2){
			PointF ls = result.get(result.size()-2);
			PointF le = result.get(0);
			PointF ip = result.get(result.size()-1);
			if(Polygon.pointIsInLine(ls, le, ip))
				result.remove(result.size()-1);
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
		addPv = polyDstCorrect(orgPv, addPv);
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
				if((!(Polygon.isInlineExps(orgSP, orgEP, crsP)) || (!(Polygon.isInlineExps(addSP, addEP, crsP))) ))
					continue;
				
				crsP = Polygon.pointDstCorrect(orgSP,  crsP, 2);
				crsP = Polygon.pointDstCorrect(orgEP,    crsP, 2);
				crsP = Polygon.pointDstCorrect(addSP, crsP, 2);
				crsP = Polygon.pointDstCorrect(addEP,   crsP, 2);
				
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
	public Vector<PointF> polyDstCorrect(Vector<PointF> bp, Vector<PointF> tp){
		Vector<PointF> result = new Vector<PointF>();
		PointF isp;
		for(int i=0; i<tp.size(); i++){
			isp = tp.get(i);
			for(int j=0; j<bp.size(); j++){
				isp = Polygon.pointDstCorrect(bp.get(j), isp, 2);
			}
			result.add(isp);
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
	public boolean containsLine(Vector<PointF> ocpv, PointF sp, PointF ep){
		PointF stp;
		PointF etp;
		PointF ctp = Polygon.getCenterPoint(sp, ep);
		if(Polygon.contains(ocpv, ctp.x, ctp.y))
			return true;
		return false;
	}
}