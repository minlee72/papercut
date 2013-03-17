package com.example.line;

import java.util.Vector;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
	PaperView view;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		view = new PaperView(this);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public class PaperView extends View {
		PointF start = new PointF();
		PointF end = new PointF();
		int touchAct;
		Paint Pnt;
		Line a;
		Line q, w, e, r;
		PointF tmpPoint;
		Vector<Line> v;
		
		public PaperView(Context context) {
			super(context);
			Pnt = new Paint();
			Pnt.setStrokeWidth(5);
			 // target 선 - 빨강
			q = new Line(new PointF(100, 100), new PointF(100, 500));
			w = new Line(new PointF(100, 500), new PointF(500, 500));
			e = new Line(new PointF(500, 500), new PointF(500, 100));
			r = new Line(new PointF(500, 100), new PointF(100, 100)); 
			a = new Line();
			
			v = new Vector<Line>();
			v.add(q);
			v.add(w);
			v.add(e);
			v.add(r);
		}

		public void onDraw(Canvas canvas) {

			Pnt.setColor(Color.RED);
			
			//start, end, left, right : get 함수를 쓸지 or  멤버를 public 으로 만들지?
			for(int i=0; i<v.size(); i++)
				canvas.drawLine(v.get(i).getStart().x, v.get(i).getStart().y, v.get(i).getEnd().x, v.get(i).getEnd().y, Pnt);
			/*
			 * if(touchAct == MotionEvent.ACTION_MOVE) {
			 * canvas.drawLine(a.start.x, a.start.y, a.end.x, a.end.y, Pnt); }
			 * if(touchAct == MotionEvent.ACTION_UP) {
			 * canvas.drawLine(a.start.x, a.start.y, a.end.x, a.end.y, Pnt); }
			 */
			if (touchAct == MotionEvent.ACTION_MOVE) {
				findCrossingPoint(canvas);
			}
			if (touchAct == MotionEvent.ACTION_UP) {
				Toast.makeText(MainActivity.this, e.compareLine(a).x + " , " + e.compareLine(a).y, Toast.LENGTH_SHORT).show();
				findCrossingPoint(canvas);
			}
		}

		public void findCrossingPoint(Canvas canvas) {
			Pnt.setColor(Color.LTGRAY);
			canvas.drawLine(start.x, start.y, end.x, end.y, Pnt);					//가이드선 - 밝은회색
			Pnt.setColor(Color.GRAY);
			canvas.drawLine(a.left.x, a.left.y, a.right.x, a.right.y, Pnt);			//test 선 - 회색
			Pnt.setColor(Color.YELLOW);
			
		/*	canvas.drawPoint(q.compareLine(a).x, q.compareLine(a).y, Pnt);	//노란색 점 찍고~
			canvas.drawPoint(w.compareLine(a).x, w.compareLine(a).y, Pnt);
			canvas.drawPoint(e.compareLine(a).x, e.compareLine(a).y, Pnt);
			canvas.drawPoint(r.compareLine(a).x, r.compareLine(a).y, Pnt);*/
			for(int i=0; i<v.size(); i++) {
				tmpPoint = v.get(i).compareLine(a);
				
				if(tmpPoint.x >= v.get(i).start.x && tmpPoint.x <= v.get(i).end.x)
					if(tmpPoint.y >= v.get(i).start.y && tmpPoint.y <= v.get(i).end.y)
						canvas.drawPoint(tmpPoint.x, tmpPoint.y, Pnt);
			}
			
			
		/*	if (q.compareLine(a).x >= q.start.x && q.compareLine(a).x <= q.end.x)		//target 선(빨강) 안에 들어오면
				if (q.compareLine(a).y >= q.start.y && q.compareLine(a).y <= q.end.y)
					canvas.drawPoint(q.compareLine(a).x, q.compareLine(a).y, Pnt);
			if (e.compareLine(a).x >= e.start.x && e.compareLine(a).x <= e.end.x)		//target 선(빨강) 안에 들어오면
				if (e.compareLine(a).y >= e.start.y && e.compareLine(a).y <= e.end.y)
					canvas.drawPoint(e.compareLine(a).x, e.compareLine(a).y, Pnt);*/
			
			//if(e.compareLine(a).x)
		}

		public boolean onTouchEvent(MotionEvent event) {
			touchAct = event.getAction();

			if (touchAct == MotionEvent.ACTION_DOWN) {
				start.x = event.getX();
				start.y = event.getY();
				a.setStart(start);
			}
			if (touchAct == MotionEvent.ACTION_MOVE) {
				end.x = event.getX();
				end.y = event.getY();
				a.setEnd(end);
				a.setAll();			//회색
				
				for(int i=0; i<v.size(); i++) {
					v.get(i).setAllQ();		//빨강
				}
				invalidate();
			}
			if (touchAct == MotionEvent.ACTION_UP) {
				invalidate();
			}
			return true;
		}
	}
}
