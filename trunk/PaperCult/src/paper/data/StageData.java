
package paper.data;

import java.util.Vector;

import com.example.papercult.R;

import paper.gameActivity.Stage;
import paper.gameActivity.StagePolygon;

public class StageData {
	private static StageData instance;
	public Vector<Stage> list = new Vector<Stage>();
	
	private StageData() {
		StagePolygon stp1 = new StagePolygon();
		stp1.add(0, 0);
		stp1.add((float)1, (float)0);
		stp1.add((float)0, (float)1);
		
	
		
		StagePolygon stp2 = new StagePolygon();		
		stp2.add(0, 0);	
		stp2.add((float)0.54, (float)0.223);
		stp2.add((float)0.8, (float)0.218);
		stp2.add((float)0.86, (float)0.357);
		stp2.add((float)0.356, (float)0.866);
		stp2.add((float)0.223, (float)0.813);
		stp2.add((float)0.223, (float)0.532);
		
		StagePolygon stp2_ = new StagePolygon();
		stp2_.add((float)-0.03, (float)-0.03);
		stp2_.add((float)0.54, (float)0.188);
		stp2_.add((float)0.83, (float)0.188);
		stp2_.add((float)0.89, (float)0.387);
		stp2_.add((float)0.356, (float)0.896);
		stp2_.add((float)0.193, (float)0.843);
		stp2_.add((float)0.193, (float)0.532);
		
		Stage stt = new Stage(4, stp2, stp2_);
		stt.titleImage = R.drawable.s_name1;
		stt.score = 0;
		stt.locked = false;
		
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
		list.add(stt);
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

