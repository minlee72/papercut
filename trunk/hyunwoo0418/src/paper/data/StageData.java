
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
		
		Stage dum = new Stage(4, poly, polyl);
		dum.titleImage = R.drawable.invisible;
		dum.score = 0;
		dum.locked = false;
		
		Stage st = new Stage(4, poly, polyl);
		st.titleImage = R.drawable.s_name1;
		st.score = 0;
		st.locked = false;
		
		Stage st1 = new Stage(5, poly, polyl);
		st1.titleImage = R.drawable.s_name1;
		st1.score = 0;
		st1.locked = true;
		
		Stage st2 = new Stage(5, poly, polyl);
		st2.titleImage = R.drawable.s_name1;
		st2.score = 0;
		st2.locked = true;
		
		Stage st3 = new Stage(5, poly, polyl);
		st3.titleImage = R.drawable.s_name1;
		st3.score = 0;
		st3.locked = true;
		
		Stage st4 = new Stage(5, poly, polyl);
		st4.titleImage = R.drawable.s_name1;
		st4.score = 0;
		st4.locked = true;
		
		Stage st5 = new Stage(5, poly, polyl);
		st5.titleImage = R.drawable.s_name1;
		st5.score = 0;
		st5.locked = true;
		
		list.add(dum);
		list.add(dum);
		list.add(st);
		list.add(st1);
		list.add(st2);
		list.add(st3);
		list.add(st4);
		list.add(st5);
		list.add(dum);
		list.add(dum);
		
	}
	public static StageData getInstance(){
		if (instance == null)
			instance = new StageData();
		return instance;
	}
	public Vector<Stage>getList(){
		return list;
	}
	public void setStageLock(){
		for(int i=0; i<list.size()-1; i++){
			if(list.get(i).score > 80)
				list.get(i+1).locked = false;
		}
	}
}

