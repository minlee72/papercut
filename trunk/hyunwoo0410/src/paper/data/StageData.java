
package paper.data;

import java.util.Vector;

import com.example.papercult.R;

import paper.gameActivity.Stage;
import paper.gameActivity.StagePolygon;

public class StageData {
	private static StageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData() {
		StagePolygon poly = new StagePolygon();
		poly.add(0, 0);
		poly.add((float)1, (float)0);
		poly.add((float)0, (float)1);
		
		StagePolygon polyl = new StagePolygon();
		polyl.add((float)-0.01, (float)-0.01);
		polyl.add((float)1.02, (float)-0.01);
		polyl.add((float)-0.01, (float)1.02);
		
		Stage st = new Stage("test", 1, poly, polyl);
		st.titleImage = R.drawable.back;
		
		StagePolygon poly2 = new StagePolygon();
		poly2.add(0, 0);
		poly2.add((float) 0.5, 0);
		poly2.add((float)0.5, (float)0.5);
		poly2.add(0, (float)0.5);
		
		StagePolygon poly2l = new StagePolygon();		
		poly2l.add((float)-0.03, (float)-0.03);
		poly2l.add((float)0.53, (float)-0.03);
		poly2l.add((float)0.53, (float)0.53);
		poly2l.add((float)-0.03, (float)0.53);
		
		Stage st1 = new Stage("23",1, poly2, poly2l);
		st1.titleImage = R.drawable.bg;
		
		list.add(st);
		list.add(st1);
		list.add(st);
		list.add(st1);
		list.add(st);
		list.add(st1);
		list.add(st);
		list.add(st1);
		list.add(st);
		list.add(st1);
		list.add(st);
		list.add(st1);
	}
	public static StageData getInstance(){
		if (instance == null)
			instance = new StageData();
		
		return instance;
	}
	public Vector<Stage>getList(){
		return list;
	}
}

