package paper.data;

import java.util.Vector;

import com.example.papercult.R;

import paper.gameActivity.Stage;
import paper.gameActivity.StagePolygon;

public class StageData {
	private static StageData instance = new StageData();;
	
	private Vector<Stage> list;
	
	private StageData() {
		StagePolygon poly = new StagePolygon();
		StagePolygon polyl = new StagePolygon();
		
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
	
	public static StageData getData(){
		return instance;
	}
	public Vector<Stage>getList(){
		return list;
	}
}
