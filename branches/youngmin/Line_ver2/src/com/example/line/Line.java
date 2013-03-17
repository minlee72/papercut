package com.example.line;

import android.graphics.PointF;

public class Line {
	static final int FALSE = -1;
	public PointF start;
	public PointF end;
	PointF mid = new PointF();
	
	float originIncline;
	float incline;
	public float xIntercept;
	public float yIntercept;
	public PointF left = new PointF();
	public PointF right = new PointF();
	
	PointF crossingPoint = new PointF();
	
	boolean xFlag = false;
	boolean yFlag = false;
	
	public Line() {		
	}
	public Line (PointF start) {
		this.start = start;
	}
	public Line(PointF start, PointF end) {
		this.start = start;
		this.end = end;
	}
	
	
	public void setStart(PointF start) {
		this.start = start;
	}
	public void setEnd(PointF end) {
		this.end = end;
	}
	public PointF getStart() {
		return start;
	}
	public PointF getEnd() {
		return end;
	}
	
	
	public void setAllQ() {		//ㅡ.ㅡ...빨간색 선분 test
		
		if(start.x == end.x)
		{
			xFlag = true;
			return;
			//yIntercept = start.y;	//?
			//xIntercept = -1;		//존재하지않음.
			//incline = 1;
		}
		if(start.y == end.y)
		{
			yFlag = true;
			return;
		}
		
		originIncline = (start.y - end.y) / (start.x - end.x);	//선택한 지점사이의 기울기
		incline = originIncline;
		mid.x = (start.x + end.x)/2;
		mid.y = (start.y + end.y)/2;
		
		yIntercept = mid.y - (incline*mid.x);
		xIntercept = -yIntercept / incline;
	}
	
	public void setAll() {		//기울기, x절편, y절편 구하기
		originIncline = (start.y - end.y) / (start.x - end.x);	//선택한 지점사이의 기울기
		incline = -(1 / originIncline);				//직각을 이루는 기울기 : 구하려는 선분
		mid.x = (start.x + end.x)/2;
		mid.y = (start.y + end.y)/2;
		
		
		//try {
			yIntercept = mid.y - (incline*mid.x);
		//}
		//catch(Exception e) {
		//	yIntercept = FALSE;		//빨강 target 선? 회색선? 이 y=상수 & x=상수 일때 thinking
		//}
		//try {
			xIntercept = -yIntercept / incline;
		//}
		//catch(Exception e) {
		//	xIntercept = FALSE;
		//}
		//
		left.x = 0;
		left.y = yIntercept;
		
		right.x = 800;
		
		//y=ax+b 일차방정식
		right.y = incline*right.x + yIntercept;
	}
	
	public PointF compareLine(Line a) {											//두 직선의 교차점
		if(xFlag) {
			return compareLineXFlag(a);
		}
		else if(yFlag) {
			return compareLineYFlag(a);
		}
		
		//y=ax+b & y=cx+d 두개 일차방정식 연립
		crossingPoint.x = (a.yIntercept-yIntercept) / (incline-a.incline);		// (d-b) / (a-c) 
		crossingPoint.y = (a.incline*yIntercept - incline*a.yIntercept) / (a.incline - incline);	// (cb-ad) / (c-a)
		
		return crossingPoint;
		
	}
	public PointF compareLineXFlag(Line a) {
		crossingPoint.x = start.x;
		crossingPoint.y = a.incline*start.x + a.yIntercept;
		return crossingPoint;
	}
	public PointF compareLineYFlag(Line a) {
		crossingPoint.x = (start.y-a.yIntercept)/a.incline;
		crossingPoint.y = start.y;
		return crossingPoint;
	}
	
}
