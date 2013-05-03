package paper.gameActivity;

import java.util.Vector;


import android.graphics.PointF;

public class StagePolygon {
	Vector<PointF> vector = new Vector<PointF>();
	
	public void add(float XDistnaceRatio, float YDistnaceRatio){
		vector.add(new PointF(XDistnaceRatio, YDistnaceRatio));
	}
	
	public boolean isPolygon(){
		if (vector.size()>=3)
			return true;
		else
			return false;
	}
	
	public Polygon getPolygon(Paper p){
		PointF base = p.getLeftTop();
		float paperWidth = p.getWidth();
		float paperHeight = p.getHeight();
				
		Polygon poly = new Polygon();
		if (isPolygon() == true){
			for(int i=0; i<vector.size(); i++){
				PointF point = new PointF();
				point.x = base.x + (vector.get(i).x * paperWidth);
				point.y = base.y + (vector.get(i).y * paperHeight);
				poly.add(point);
			}
		}
		return poly;
	}
	
}
