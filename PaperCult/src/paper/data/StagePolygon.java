package paper.data;

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
	public void setPolygon(Paper p, Vector<PointF> pv){
		vector.clear();
		PointF base = p.getLeftTop();
		float paperWidth = p.getWidth();
		float paperHeight = p.getHeight();
		
		for(int i=0; i<pv.size(); i++){
			PointF ratioPoint = new PointF();
			ratioPoint.x = (pv.get(i).x - base.x) / paperWidth;
			ratioPoint.y = (pv.get(i).y - base.y) / paperHeight;
			vector.add(ratioPoint);
		}
	}
}
