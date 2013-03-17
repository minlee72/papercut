package com.example.papercult;

import java.util.Vector;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;

public class PaperView extends View {
	Context con;
	boolean mouseClick = false;
	boolean haveLine = false;
	boolean isUp;
	PointF start = new PointF();
	PointF end = new PointF();
	PointF nStart = new PointF();
	PointF nEnd = new PointF();
	PointF mid = new PointF();
	PointF[] p1 = new PointF[3];
	PointF[] p1p = new PointF[3];
	
	Path path = new Path();
	Paint mPaint;
	Paint oPaint;
	
	float p1X, p1Y;
	float p2X, p2Y;
	int action=0;
	
	Vector<PaperLayer> vPaperLayer;
	Vector<PaperLayer> tempvPaperLayer;
	PaperLayer[] newPaperLayer;
	
	float incline, xIntercept, yIntercept; // a -> incline, b -> intercept

	public PaperView(Context context) {
		super(context);
		con = context;
		
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(0x40ff0000);
		
		oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		oPaint.setColor(Color.BLACK);
		oPaint.setStyle(Paint.Style.STROKE);
		for(int i=0; i<3; i++){
			p1[i] = new PointF();
			p1p[i] = new PointF();
		}
		p1[0].x = 200;
		p1[0].y = 150; 
		p1[1].x = 100;
		p1[1].y = 300;
		p1[2].x = 180;
		p1[2].y = 40;
		p1p[0].x = p1p[0].y = 0;
		p1p[1].x = p1p[1].y = 0;
		p1p[2].x = p1p[2].y = 0;
		vPaperLayer = new Vector<PaperLayer>();
		tempvPaperLayer = new Vector<PaperLayer>();
		
		PaperLayer first = new PaperLayer();
		first.AddPoint(0, new PointF(160,280));
		first.AddPoint(1, new PointF(560,280));
		first.AddPoint(2, new PointF(560,680));
		first.AddPoint(3, new PointF(160,680));
		vPaperLayer.add(first);
		
		for(int i=0; i< vPaperLayer.get(0).vPoint.size(); i++){
			if(i==0){
				vPaperLayer.get(0).path.moveTo(vPaperLayer.get(0).vPoint.get(i).x, vPaperLayer.get(0).vPoint.get(i).y);
			}
			else if(i==vPaperLayer.get(0).vPoint.size()-1){
				vPaperLayer.get(0).path.lineTo(vPaperLayer.get(0).vPoint.get(i).x, vPaperLayer.get(0).vPoint.get(i).y);
				vPaperLayer.get(0).path.lineTo(vPaperLayer.get(0).vPoint.get(0).x, vPaperLayer.get(0).vPoint.get(0).y);
			}
			else{
				vPaperLayer.get(0).path.lineTo(vPaperLayer.get(0).vPoint.get(i).x, vPaperLayer.get(0).vPoint.get(i).y);
			}
		}
		
	}
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			mouseClick = true;
			start.x = (int)event.getX();
			start.y = (int)event.getY();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_MOVE)
		{
			tempvPaperLayer = vPaperLayer;
			newPaperLayer = new PaperLayer[tempvPaperLayer.size()];
			
			end.x = (int)event.getX();
			end.y = (int)event.getY();
			
			mid.x = (start.x + end.x)/2;
			mid.y = (start.y + end.y)/2;
			
			if(end.x - start.x == 0){
				xIntercept = (start.x + end.x)/2;
				nStart.x = xIntercept;
				nStart.y = 0;
				nEnd.y = xIntercept;
				nEnd.y = 1280;
				if(start.x > xIntercept)
					isUp = true;
				else
					isUp = false;
				
				if(isUp){
					for(int k=0; k<tempvPaperLayer.size(); k++){
						PointF[] cross = new PointF[2];
						cross[0] = new PointF();
						cross[1] = new PointF();
						int index = 0;
					
						for(int i=0; i<tempvPaperLayer.get(k).vPoint.size(); i++){
							if((tempvPaperLayer.get(k).vPoint.get(i).x < xIntercept && xIntercept < tempvPaperLayer.get(k).vPoint.get(i+1).x)
									|| tempvPaperLayer.get(k).vPoint.get(i+1).x < xIntercept && xIntercept < tempvPaperLayer.get(k).vPoint.get(i).x){
								cross[index].x = xIntercept;
//								crossX1 = xIntercept;
								
								//가로와의 교차
								if(tempvPaperLayer.get(k).vPoint.get(i).y == tempvPaperLayer.get(k).vPoint.get(i+1).y)
									cross[index].y = tempvPaperLayer.get(k).vPoint.get(i).y;
//									crossY1 = tempvPaperLayer.get(k).vPoint.get(i).y;
								//대각선과의 교차
								else{
									//대각선의 직선방정식부터 구한다
									incline = tempvPaperLayer.get(k).vPoint.get(i+1).y - tempvPaperLayer.get(k).vPoint.get(i).y /
											tempvPaperLayer.get(k).vPoint.get(i+1).x - tempvPaperLayer.get(k).vPoint.get(i).x;
									//y절편
									yIntercept = tempvPaperLayer.get(k).vPoint.get(i).y - incline*tempvPaperLayer.get(k).vPoint.get(i).x;
									cross[index].y = incline*xIntercept + yIntercept;
//									crossY1 = incline*xIntercept + yIntercept;
								}
							}
							index++;
						}
						newPaperLayer[k] = new PaperLayer();
						newPaperLayer[k].vPoint.add(cross[0]);
						newPaperLayer[k].vPoint.add(cross[1]);
						int loc = 0;
						float len;
						int i=0;
						int isFirst = 0;
						while(i<tempvPaperLayer.get(k).vPoint.size()){
							if(tempvPaperLayer.get(k).vPoint.get(i).x > xIntercept){
								len = tempvPaperLayer.get(k).vPoint.get(i).x - xIntercept;
								isFirst++;
								if(tempvPaperLayer.get(k).vPoint.get(i).y > cross[1].y){
									newPaperLayer[k].vPoint.add(loc, new PointF(tempvPaperLayer.get(k).vPoint.get(i).x - 2*len,
											tempvPaperLayer.get(k).vPoint.get(i).y));
									loc++;
								}
								else{
									newPaperLayer[k].vPoint.add(loc+2, new PointF(tempvPaperLayer.get(k).vPoint.get(i).x - 2*len,
											tempvPaperLayer.get(k).vPoint.get(i).y));
								}
							}
							
							if(isFirst == 1){
								tempvPaperLayer.get(k).vPoint.remove(i);
								tempvPaperLayer.get(k).vPoint.add(i, cross[1]);
								tempvPaperLayer.get(k).vPoint.add(i, cross[0]);
							}
							else if(isFirst > 0){
								tempvPaperLayer.get(k).vPoint.remove(i);
							}
						}
					}
				}
					
			}
			
			/*
			if(end.x - start.x == 0){
				xIntercept = (start.x + end.x)/2;
				nStart.x = xIntercept;
				nStart.y = 0;
				nEnd.x = xIntercept;
				nEnd.y = 1280;
				if(start.x > xIntercept)
					isUp = true;
				else
					isUp = false;
				
				if(isUp){
					for(int k=0; k<tempvPaperLayer.size();k++){
						PointF[] cross  = new PointF[2];
						cross[0] = new PointF();
						cross[1] = new PointF();
						PaperLayer tempPaperLayer = new PaperLayer();
						int index=0;
						float crossX1, crossY1, crossX2, crossY2;
						
						for(int i=0; i<tempvPaperLayer.get(k).vPoint.size(); i++){
							//->일때
							if(tempvPaperLayer.get(k).vPoint.get(i).x < xIntercept && xIntercept < tempvPaperLayer.get(k).vPoint.get(i+1).x){
								cross[index].x = xIntercept;
//								crossX1 = xIntercept;
								
								//가로와의 교차
								if(tempvPaperLayer.get(k).vPoint.get(i).y == tempvPaperLayer.get(k).vPoint.get(i+1).y)
									cross[index].y = tempvPaperLayer.get(k).vPoint.get(i).y;
//									crossY1 = tempvPaperLayer.get(k).vPoint.get(i).y;
								//대각선과의 교차
								else{
									//대각선의 직선방정식부터 구한다
									incline = tempvPaperLayer.get(k).vPoint.get(i+1).y - tempvPaperLayer.get(k).vPoint.get(i).y /
											tempvPaperLayer.get(k).vPoint.get(i+1).x - tempvPaperLayer.get(k).vPoint.get(i).x;
									//y절편
									yIntercept = tempvPaperLayer.get(k).vPoint.get(i).y - incline*tempvPaperLayer.get(k).vPoint.get(i).x;
									cross[index].y = incline*xIntercept + yIntercept;
//									crossY1 = incline*xIntercept + yIntercept;
								}
								
								//새층을만들고 좌표생성 및 기존층 좌표의 변경
								tempPaperLayer.vPoint.add(new PointF(tempvPaperLayer.get(k).vPoint.get(i+1).x - 2*xIntercept,
										tempvPaperLayer.get(k).vPoint.get(i+1).y));
								tempPaperLayer.vPoint.add(cross[index]);
								tempvPaperLayer.get(k).vPoint.set(i+1, cross[index]);
								index++;
							}
							
							// <- 일때
							else if(tempvPaperLayer.get(k).vPoint.get(i+1).x < xIntercept && xIntercept < tempvPaperLayer.get(k).vPoint.get(i).x){
								cross[index].x = xIntercept;
//								crossX1 = xIntercept;
								
								//가로와의 교차
								if(tempvPaperLayer.get(k).vPoint.get(i).y == tempvPaperLayer.get(k).vPoint.get(i+1).y)
									cross[index].y = tempvPaperLayer.get(k).vPoint.get(i).y;
//									crossY1 = tempvPaperLayer.get(k).vPoint.get(i).y;
								//대각선과의 교차
								else{
									//대각선의 직선방정식부터 구한다
									incline = tempvPaperLayer.get(k).vPoint.get(i+1).y - tempvPaperLayer.get(k).vPoint.get(i).y /
											tempvPaperLayer.get(k).vPoint.get(i+1).x - tempvPaperLayer.get(k).vPoint.get(i).x;
									//y절편
									yIntercept = tempvPaperLayer.get(k).vPoint.get(i).y - incline*tempvPaperLayer.get(k).vPoint.get(i).x;
									cross[index].y = incline*xIntercept + yIntercept;
//									crossY1 = incline*xIntercept + yIntercept;
								}
								
								tempPaperLayer.vPoint.add(cross[index]);
								tempPaperLayer.vPoint.add(new PointF(tempvPaperLayer.get(k).vPoint.get(i+1).x - 2*xIntercept,
										tempvPaperLayer.get(k).vPoint.get(i+1).y));
								tempvPaperLayer.get(k).vPoint.set(i, cross[index]);
								index++;
							}
						}
					}
				}
				else{
					
				}
			}
			*/
			for(int k=0; k< tempvPaperLayer.size(); k++){
				for(int i=0; i< tempvPaperLayer.get(k).vPoint.size(); i++){
					if(i==0){
						tempvPaperLayer.get(k).path.moveTo(tempvPaperLayer.get(k).vPoint.get(i).x, 
								tempvPaperLayer.get(k).vPoint.get(i).y);
					}
					else if(i==tempvPaperLayer.get(k).vPoint.size()-1){
						tempvPaperLayer.get(k).path.lineTo(tempvPaperLayer.get(k).vPoint.get(i).x, 
								tempvPaperLayer.get(k).vPoint.get(i).y);
						tempvPaperLayer.get(k).path.lineTo(tempvPaperLayer.get(k).vPoint.get(0).x, 
								tempvPaperLayer.get(k).vPoint.get(0).y);
					}
					else{
						tempvPaperLayer.get(k).path.lineTo(tempvPaperLayer.get(k).vPoint.get(i).x, 
								tempvPaperLayer.get(k).vPoint.get(i).y);
					}
				}
			}
			
			incline = (start.y - end.y) / (start.x - end.x); 
			incline = -(1 / incline);
			
			
			yIntercept = mid.y - (incline*mid.x);
			
			nStart.x = 0;
			nStart.y = yIntercept;
			
			nEnd.x = 800;
			nEnd.y = incline*nEnd.x + yIntercept;
			
			if(start.y > incline*start.x+yIntercept)
				isUp = true;
			else
				isUp = false;
			
			action = 1;
			invalidate();
			return true;
		}
		else if(event.getAction() == MotionEvent.ACTION_UP)
		{
			if(mouseClick == true){
				mouseClick = false;
				haveLine = true;
			}
			for(int i=0; i<3; i++){
				if(isUp){
					if(p1[i].y > incline * p1[i].x + yIntercept){
						p1p[i].x = (p1[i].x*(1 - incline*incline)-2*incline*(yIntercept-p1[i].y)) / (incline*incline + 1);
						p1p[i].y = (p1[i].y*(incline*incline - 1)+2*(incline*p1[i].x + yIntercept)) / (incline*incline +1);
					}
					else{
						p1p[i].x = p1p[i].y = 0;
					}
				}
				else{
					if(p1[i].y < incline * p1[i].x + yIntercept){
						p1p[i].x = (p1[i].x*(1 - incline*incline)-2*incline*(yIntercept-p1[i].y)) / (incline*incline + 1);
						p1p[i].y = (p1[i].y*(incline*incline - 1)+2*(incline*p1[i].x + yIntercept)) / (incline*incline +1);
					}
					else{
						p1p[i].x = p1p[i].y = 0;
					}
				}
			}
			invalidate();
			return true;
		}
		return false;
	}
	
	public void onDraw(Canvas canvas){
		Paint Pnt = new Paint();
		Pnt.setStrokeWidth(5);
		Pnt.setColor(Color.RED);
		/*
		if(action == 0){
			canvas.drawPath(vPaperLayer.get(0).path, mPaint);
			canvas.drawPath(vPaperLayer.get(0).path, oPaint);
		}
		*/
//		else
		if(action == 1){
			for(int k=0; k<tempvPaperLayer.size(); k++){
				canvas.drawPath(tempvPaperLayer.get(1).path, mPaint);
				canvas.drawPath(tempvPaperLayer.get(1).path, oPaint);
			}
			
		}
		if(haveLine == true)
		{
			haveLine = false;
			canvas.drawLine(start.x, start.y, end.x, end.y, Pnt);
			canvas.drawLine(nStart.x, nStart.y, nEnd.x, nEnd.y, Pnt);
			Pnt.setColor(Color.BLUE);
			canvas.drawPoint(mid.x, mid.y, Pnt);
			
			for(int i=0; i<3; i++){
				Pnt.setColor(Color.RED);
				canvas.drawPoint(p1[i].x, p1[i].y, Pnt);
				 if(p1p[i].x != 0){
					 Pnt.setColor(Color.BLUE);
					 canvas.drawPoint(p1p[i].x, p1p[i].y, Pnt);
				 }
			}
		}
	}
}


