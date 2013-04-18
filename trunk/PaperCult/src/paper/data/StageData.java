
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
		
		StagePolygon stp1_ = new StagePolygon();
		stp1_.add((float)-0.1, (float)-0.1);
		stp1_.add((float)1.2, (float)-0.1);
		stp1_.add((float)-0.1, (float)1.2);
		
		Stage dum = new Stage(4, stp1, stp1_);
		dum.titleImage = R.drawable.invisible;
		dum.score = 0;
		dum.locked = false;
		
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
		
		Stage st = new Stage(4, stp2, stp2_);
		st.titleImage = R.drawable.s_name1;
		st.titleClearImage = R.drawable.s_name1_clear;
		st.score = 0;
		st.locked = false;
		
		StagePolygon stp3 = new StagePolygon();
		stp3.add((float)0.183, (float)0.4802);
		stp3.add((float)0.466, (float)0.4802);
		stp3.add((float)0.5463, (float)0.3104);
		stp3.add((float)0.86, (float)0.1923);
		stp3.add((float)0.9662, (float)0.4802);
		stp3.add((float)1, (float)0.4802);
		stp3.add((float)1, (float)0.5634);
		stp3.add((float)0.0319, (float)0.7394);
		stp3.add((float)0, (float)0.7394);
		stp3.add((float)0, (float)0.662);
		
		StagePolygon stp3_ = new StagePolygon();
		stp3_.add((float)0.153, (float)0.4502);
		stp3_.add((float)0.436, (float)0.4502);
		stp3_.add((float)0.5263, (float)0.2804);
		stp3_.add((float)0.88, (float)0.1623);
		stp3_.add((float)0.9862, (float)0.4502);
		stp3_.add((float)1.03, (float)0.4502);
		stp3_.add((float)1.03, (float)0.5934);
		stp3_.add((float)0.0339, (float)0.7694);
		stp3_.add((float)-0.03, (float)0.7694);
		stp3_.add((float)-0.03, (float)0.632);
		
		
		Stage st3 = new Stage(5, stp3, stp3_);
		st3.titleImage = R.drawable.s_name2;
		st3.titleClearImage = R.drawable.s_name2_clear;
		st3.score = 0;
		st3.locked = false;
		
		StagePolygon stp4 = new StagePolygon();
		stp4.add((float)0.3, 0);
		stp4.add((float)0.37, (float)-0.03);
		stp4.add((float)0.4, (float)-0.1);
		stp4.add((float)0.495, (float)-0.075);
		stp4.add((float)0.57, (float)-0.1);
		stp4.add((float)0.6, (float)-0.035);
		stp4.add((float)0.7, (float)0);
		stp4.add((float)0.515, (float)1);
		stp4.add((float)0.485, (float)1);
		
		StagePolygon stp4_ = new StagePolygon();
		stp4_.add((float)0.27, (float)-0.03);
		stp4_.add((float)0.34, (float)-0.06);
		stp4_.add((float)0.4, (float)-0.13);
		stp4_.add((float)0.495, (float)-0.105);
		stp4_.add((float)0.57, (float)-0.13);
		stp4_.add((float)0.63, (float)-0.065);
		stp4_.add((float)0.73, (float)-0.01);
		stp4_.add((float)0.545, (float)1.03);
		stp4_.add((float)0.455, (float)1.03);
		
		
		Stage st4 = new Stage(5, stp4, stp4_);
		st4.titleImage = R.drawable.s_name3;
		st4.titleClearImage = R.drawable.s_name3_clear;
		st4.score = 0;
		st4.locked = false;
		
		StagePolygon stp5 = new StagePolygon();
		stp5.add((float)0.37, (float)0.36);
		stp5.add((float)0.459, (float)0.236);
		stp5.add((float)0.5, (float)0.2);
		stp5.add((float)0.627, (float)0.267);
		stp5.add((float)0.627, (float)0.475);
		stp5.add((float)0.865, (float)0.475);
		stp5.add((float)0.904, (float)0.403);
		stp5.add((float)1, (float)0.475);
		stp5.add((float)0.798, (float)0.602);
		stp5.add((float)0.6267, (float)0.602);
		stp5.add((float)0.5, (float)0.475);
		stp5.add((float)0.5, (float)0.357);
		stp5.add((float)0.407, (float)0.415);
		
		StagePolygon stp5_ = new StagePolygon();
		stp5_.add((float)0.33, (float)0.33);
		stp5_.add((float)0.449, (float)0.206);
		stp5_.add((float)0.5, (float)0.17);
		stp5_.add((float)0.657, (float)0.267);
		stp5_.add((float)0.657, (float)0.445);
		stp5_.add((float)0.835, (float)0.445);
		stp5_.add((float)0.904, (float)0.373);
		stp5_.add((float)1.03, (float)0.475);
		stp5_.add((float)0.828, (float)0.632);
		stp5_.add((float)0.5967, (float)0.632);
		stp5_.add((float)0.47, (float)0.475);
		stp5_.add((float)0.47, (float)0.397);
		stp5_.add((float)0.397, (float)0.445);
		
		Stage st5 = new Stage(5, stp5, stp5_);
		st5.titleImage = R.drawable.s_name4;
		st5.titleClearImage = R.drawable.s_name4_clear;
		st5.score = 0;
		st5.locked = false;
		
		list.add(dum);
		list.add(dum);
		list.add(st);
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

