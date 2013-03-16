package com.example.line;

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
		Line q;

		public PaperView(Context context) {
			super(context);
			Pnt = new Paint();
			Pnt.setStrokeWidth(5);
			q = new Line(new PointF(100, 200), new PointF(500, 500)); // target �� - ����
			a = new Line();

		}

		public void onDraw(Canvas canvas) {

			Pnt.setColor(Color.RED);
			
			//start, end, left, right : get �Լ��� ���� or  ����� public ���� ������?
			canvas.drawLine(q.getStart().x, q.getStart().y, q.getEnd().x, q.getEnd().y, Pnt);
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
				findCrossingPoint(canvas);
			}
		}

		public void findCrossingPoint(Canvas canvas) {
			Pnt.setColor(Color.LTGRAY);
			canvas.drawLine(start.x, start.y, end.x, end.y, Pnt);					//���̵弱 - ����ȸ��
			Pnt.setColor(Color.GRAY);
			canvas.drawLine(a.left.x, a.left.y, a.right.x, a.right.y, Pnt);			//test �� - ȸ��
			Pnt.setColor(Color.YELLOW);

			if (q.compareLine(a).x > q.start.x && q.compareLine(a).x < q.end.x)		//target ��(����) �ȿ� ������
				if (q.compareLine(a).y > q.start.y&& q.compareLine(a).y < q.end.y)
					canvas.drawPoint(q.compareLine(a).x, q.compareLine(a).y, Pnt);	//����� �� ���~
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
				a.setAll();			//ȸ��
				q.setAllQ();		//����

				invalidate();
			}
			if (touchAct == MotionEvent.ACTION_UP) {
				invalidate();
			}
			return true;
		}
	}
}
