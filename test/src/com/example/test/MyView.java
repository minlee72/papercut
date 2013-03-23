package com.example.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MyView extends View{
	Context con;
	Paper p = new Paper();
	Polygon poly = new Polygon();
	boolean result = false;
	
	public MyView(Context context) {
		super(context);
		con = context;
		
		poly.add(new PointF(100,100));
		poly.add(new PointF(50,200));
		poly.add(new PointF(150,300));
		poly.add(new PointF(200,180));
		poly.add(new PointF(170,130));
		// TODO Auto-generated constructor stub
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			result = p.contains(poly, event.getX(), event.getY());
			if(result)
				Toast.makeText(con, "¾ÈÂÊ", Toast.LENGTH_SHORT).show();
			else
				Toast.makeText(con, "¹Ù±ùÂÊ", Toast.LENGTH_SHORT).show();
			return true;
		}
	
		return false;
	}
	
	public void onDraw(Canvas canvas){
		poly.draw(canvas);
	}
}
