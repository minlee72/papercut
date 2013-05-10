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
	Vector<Polygon> pv = new Vector<Polygon>();
	
	String tps1;
	String tps2;

	public TestView(Context context, float scrWidth, float scrHeight) {
		super(context);
		con = context;
		
		paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(20);
		
		paper = new Paper(scrWidth, scrHeight);
		paper.reset();
		
		Polygon temp1 = new Polygon();
		temp1.add(new PointF(1,2));
		temp1.add(new PointF(1,3));
		temp1.add(new PointF(1,5));
		temp1.add(new PointF(1,1));
		
		Polygon temp2 = new Polygon();
		temp2.add(new PointF(1,4));
		temp2.add(new PointF(1,3));
		temp2.add(new PointF(1,2));
		temp2.add(new PointF(1,1));
		temp2.add(new PointF(1,5));
		
		Vector<Polygon> tv = polySort(temp1, temp2);
		
		Polygon tp1 = tv.get(0);
		Polygon tp2 = tv.get(1);

		tps1 = "";
		tps2 = "";
		for(int i=0; i<tp1.pointVector.size(); i++){
			tps1 = tps1 + "(";
			tps1 = tps1 + tp1.pointVector.get(i).x;
			tps1 = tps1 + ",";
			tps1 = tps1 + tp1.pointVector.get(i).y;
			tps1 = tps1 + ") ";
		}
		
		for(int i=0; i<tp2.pointVector.size(); i++){
			tps2 = tps2 + "(";
			tps2 = tps2 + tp2.pointVector.get(i).x;
			tps2 = tps2 + ",";
			tps2 = tps2 + tp2.pointVector.get(i).y;
			tps2 = tps2 + ") ";
		}
		
	}

	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			if(click == false){
				if((event.getX()<100)&&(event.getY()<100)){
					//this.resetPolygon();
					Toast.makeText(con, tps1, Toast.LENGTH_LONG).show();
					return true;
				}
				else if((event.getX()>600)&&(event.getY()<100)){
					//polySum();
					Toast.makeText(con, tps2, Toast.LENGTH_LONG).show();
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
			//	paper.foldStart(touchStart, touchEnd);
				this.invalidate();
			}
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(click == true){
			//	paper.foldEnd();
				click = false;
			}
			return true;
		}
		return false;
	}
	public void resetPolygon(){
		paper.reset();
		//sum = paper.baseRect;
		this.invalidate();
	}
	
	public void onDraw(Canvas canvas){
		paper.draw(canvas, 0x40000000);
		//sum.draw(canvas, 0x40FF0000);
		canvas.drawCircle(50, 50, 50, paint);
		canvas.drawCircle(600, 50, 50, paint);
		for(int i=0; i<pv.size(); i++){
			pv.get(i).draw(canvas, 0x20FF0000);
		}
	}
	
	public void polySum(Polygon poly1, Polygon poly2){
		
		this.invalidate();
	}

	public Vector<Polygon> polySort(Polygon poly1, Polygon poly2){
		Vector<Polygon> result = new Vector<Polygon>();
		Vector<PointF> pv1 = poly1.pointVector;
		Vector<PointF> pv2 = poly2.pointVector;
		int index1 = 0, index2 = 0;
		int count = 0;
		
		for(int i=0; i<pv1.size(); i++){
			for(int j=0; j<pv2.size(); j++){
				PointF point1 = pv1.get(i);
				PointF point2 = pv2.get(j);
				if(point1.equals(point2.x, point2.y)){
					index1 = i;
					index2 = j;
					count++;
				}
			}
		}
		if(count < 2)
			return null;
		
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
				if(i == index2)
					index2 = temp.size();
			}
			pv2 = temp;
			nextIndex2 = index2 + 1;
			if(nextIndex2 == pv2.size())
				nextIndex2 = 0;
		}
		
		while(true){
			index1 = nextIndex1;
			nextIndex1 = index1 + 1;
			if(nextIndex1 == pv1.size())
				nextIndex1 = 0;
			
			boolean onePoint = true;
			for(int i=0; i<pv2.size(); i++){
				if(pv1.get(index1).equals(pv2.get(i).x, pv2.get(i).y)){
					onePoint = false;
					i = pv2.size();
				}
			}
			if (onePoint == true){
				index1 = index1 - 1;
				if(index1 < 0)
					index1 = pv1.size()-1;
				break;
			}
		}
		
		while(true){
			index2 = nextIndex2;
			nextIndex2 = index2 + 1;
			if(nextIndex2 == pv2.size())
				nextIndex2 = 0;
			
			boolean onePoint = true;
			for(int i=0; i<pv1.size(); i++){
				if(pv2.get(index2).equals(pv1.get(i).x, pv1.get(i).y)){
					onePoint = false;
					i = pv1.size();
				}
			}
			if (onePoint == true){
				index2 = index2 - 1;
				if(index2 < 0)
					index2 = pv2.size()-1;
				break;
			}
		}
		
		Vector<PointF> temp1 = new Vector<PointF>();
		for(int i=0; i<pv1.size(); i++){
			temp1.add(pv1.get(index1));
			index1 = index1 + 1;
			if(index1 == pv1.size())
				index1 = 0;
		}
		
		Vector<PointF> temp2 = new Vector<PointF>();
		for(int i=0; i<pv2.size(); i++){
			temp2.add(pv2.get(index2));
			index2 = index2 + 1;
			if(index2 == pv2.size())
				index2 = 0;
		}
		
		poly1.pointVector = temp1;
		poly2.pointVector = temp2;
		result.add(poly1);
		result.add(poly2);
		return result;
	}
}