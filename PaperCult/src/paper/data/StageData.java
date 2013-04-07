
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
		polyl.add((float)-0.1, (float)-0.1);
		polyl.add((float)1.2, (float)-0.1);
		polyl.add((float)-0.1, (float)1.2);
		
		Stage st = new Stage("test", 1, poly, polyl);
		st.titleImage = R.drawable.back;
		
		Stage st1 = new Stage("23",1, poly, polyl);
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

