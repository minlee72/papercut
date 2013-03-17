package com.example.papercult;
import java.util.Vector;
import android.graphics.*;

public class PaperLayer {
	Vector<PointF> vPoint;
	Path path;
	Paint mPaint;
	Paint oPaint;
	
	public PaperLayer(){
		vPoint = new Vector<PointF>();
		path = new Path();
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(0x40ff0000);
		
		oPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		oPaint.setColor(Color.BLACK);
		oPaint.setStyle(Paint.Style.STROKE);
	}
	
	public void AddPoint(int loc, PointF pt){
		vPoint.add(loc, pt);
	}
	
	public void RemovePoint(int loc){
		vPoint.remove(loc);
	}
}
